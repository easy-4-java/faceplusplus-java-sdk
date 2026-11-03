package com.faceplusplus.spring.boot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.faceplusplus.spring.boot.resp.FaceppResponse;
import okhttp3.*;
import okio.Buffer;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class JacksonCompatibilityTest {
    @Test void serializesNestedRequestThroughHttpTemplate() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("label", "人脸\n\"test\"");
        body.put("options", Arrays.asList(true, null, 1234567890123L));
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(chain -> {
            Buffer bytes = new Buffer();
            chain.request().body().writeTo(bytes);
            JsonNode json = mapper.readTree(bytes.readUtf8());
            assertEquals("人脸\n\"test\"", json.get("label").asText());
            assertTrue(json.get("options").get(0).asBoolean());
            assertTrue(json.get("options").get(1).isNull());
            assertEquals(1234567890123L, json.get("options").get(2).asLong());
            return new Response.Builder().request(chain.request()).protocol(Protocol.HTTP_1_1)
                .code(200).message("OK").body(ResponseBody.create(MediaType.parse("application/json"), "{}" )).build();
        }).build();
        FaceppOkHttp3Template template = new FaceppOkHttp3Template(client, mapper, new FaceppProperties());
        assertSame(mapper, template.getObjectMapper());
        assertEquals(200, template.post("http://localhost/test", null, null, body, FaceppResponse.class).getCode());
    }

    @Test void preservesSdkResponseJacksonAnnotations() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        FaceppResponse response = mapper.readValue("{\"code\":200,\"time_used\":17,\"request_id\":\"local\"}", FaceppResponse.class);
        assertEquals(17, response.getTimeUsed());
        assertEquals("local", response.getRequestId());
        JsonNode json = mapper.readTree(mapper.writeValueAsString(response));
        assertEquals("local", json.get("request_id").asText());
        assertFalse(json.has("error_message"));
    }
}
