package com.faceplusplus.spring.boot;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.faceplusplus.spring.boot.resp.FaceppResponse;
import okhttp3.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class ResponseFallbackTest {
 private FaceppOkHttp3Template template(int code, String body) {
  OkHttpClient client = new OkHttpClient.Builder().addInterceptor(chain -> new Response.Builder()
   .request(chain.request()).protocol(Protocol.HTTP_1_1).code(code).message("test")
   .body(ResponseBody.create(MediaType.parse("application/json"), body)).build()).build();
  return new FaceppOkHttp3Template(client, new ObjectMapper(), new FaceppProperties());
 }
 @Test void invalidJsonReturnsDefaultResponse() throws Exception {
  FaceppResponse result = template(200,"invalid json").get("http://localhost/test",FaceppResponse.class);
  assertNotNull(result); assertEquals(200,result.getCode());
 }
 @Test void unsuccessfulHttpReturnsStatus() throws Exception {
  FaceppResponse result = template(503,"unavailable").get("http://localhost/test",FaceppResponse.class);
  assertNotNull(result); assertEquals(503,result.getCode());
 }
 @Test void successfulResponsePreservesFields() throws Exception {
  FaceppResponse result = template(200,"{\"request_id\":\"local-test\"}").get("http://localhost/test",FaceppResponse.class);
  assertEquals(200,result.getCode()); assertEquals("local-test",result.getRequestId());
 }
 public abstract static class AbstractResponse extends FaceppResponse {}
 public static class NoDefaultResponse extends FaceppResponse { public NoDefaultResponse(String value) {} }
 @Test void unconstructibleResponsesReturnNull() throws Exception {
  assertNull(template(503,"bad").get("http://localhost/test",AbstractResponse.class));
  assertNull(template(503,"bad").get("http://localhost/test",NoDefaultResponse.class));
  assertNull(template(200,"bad").readValue("bad",AbstractResponse.class));
 }
}
