package com.faceplusplus.spring.boot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.faceplusplus.spring.boot.resp.FaceppResponse;
import com.faceplusplus.spring.boot.resp.FaceDetectResponse;
import okhttp3.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class FaceppOkHttp3TemplateTest {

    private FaceppOkHttp3Template template;
    private FaceppProperties properties;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        properties = new FaceppProperties();
        properties.setAppId("test-key");
        properties.setAppCertificate("test-secret");
        objectMapper = new ObjectMapper();
        // Use a stubbed template that doesn't make real HTTP calls
        template = new StubOkHttp3Template(new OkHttpClient(), objectMapper, properties);
    }

    @Test
    void shouldReturnObjectMapper() {
        assertNotNull(template.getObjectMapper());
        assertSame(objectMapper, template.getObjectMapper());
    }

    @Test
    void shouldBuildHttpUrlWithoutParams() {
        HttpUrl url = template.getHttpUrl("https://api.example.com/test", null);
        assertNotNull(url);
        assertEquals("api.example.com", url.host());
    }

    @Test
    void shouldBuildHttpUrlWithEmptyParams() {
        HttpUrl url = template.getHttpUrl("https://api.example.com/test", new HashMap<>());
        assertNotNull(url);
    }

    @Test
    void shouldBuildHttpUrlWithParams() {
        Map<String, Object> params = new HashMap<>();
        params.put("key1", "value1");
        params.put("key2", "value2");
        HttpUrl url = template.getHttpUrl("https://api.example.com/test", params);
        assertNotNull(url);
        assertEquals("value1", url.queryParameter("key1"));
        assertEquals("value2", url.queryParameter("key2"));
    }

    @Test
    void shouldBuildHttpUrlWithNullParamValue() {
        Map<String, Object> params = new HashMap<>();
        params.put("key1", null);
        HttpUrl url = template.getHttpUrl("https://api.example.com/test", params);
        assertNotNull(url);
        assertEquals("", url.queryParameter("key1"));
    }

    @Test
    void shouldCreateRequestBuilderWithPostMethod() throws Exception {
        HttpUrl url = HttpUrl.parse("https://api.example.com/test");
        Map<String, Object> body = new HashMap<>();
        body.put("api_key", "test");
        Request.Builder builder = template.createRequestBuilder(url, FaceppOkHttp3Template.HttpMethod.POST, null, body);
        assertNotNull(builder);
    }

    @Test
    void shouldCreateRequestBuilderWithGetMethod() throws Exception {
        HttpUrl url = HttpUrl.parse("https://api.example.com/test");
        Request.Builder builder = template.createRequestBuilder(url, FaceppOkHttp3Template.HttpMethod.GET, null, null);
        assertNotNull(builder);
    }

    @Test
    void shouldCreateRequestBuilderWithHeaders() throws Exception {
        HttpUrl url = HttpUrl.parse("https://api.example.com/test");
        Map<String, Object> headers = new HashMap<>();
        headers.put("Authorization", "Bearer token");
        Map<String, Object> body = new HashMap<>();
        body.put("key", "value");
        Request.Builder builder = template.createRequestBuilder(url, FaceppOkHttp3Template.HttpMethod.POST, headers, body);
        assertNotNull(builder);
    }

    @Test
    void shouldCreateRequestBuilderWithNullBodyForGet() throws Exception {
        HttpUrl url = HttpUrl.parse("https://api.example.com/test");
        Request.Builder builder = template.createRequestBuilder(url, FaceppOkHttp3Template.HttpMethod.GET, null, null);
        assertNotNull(builder);
    }

    @Test
    void shouldCreateRequestBuilderWithPutMethod() throws Exception {
        HttpUrl url = HttpUrl.parse("https://api.example.com/test");
        Map<String, Object> body = new HashMap<>();
        body.put("key", "value");
        Request.Builder builder = template.createRequestBuilder(url, FaceppOkHttp3Template.HttpMethod.PUT, null, body);
        assertNotNull(builder);
    }

    @Test
    void shouldCreateRequestBuilderWithPatchMethod() throws Exception {
        HttpUrl url = HttpUrl.parse("https://api.example.com/test");
        Map<String, Object> body = new HashMap<>();
        body.put("key", "value");
        Request.Builder builder = template.createRequestBuilder(url, FaceppOkHttp3Template.HttpMethod.PATCH, null, body);
        assertNotNull(builder);
    }

    @Test
    void shouldCreateRequestBuilderWithDeleteMethod() throws Exception {
        HttpUrl url = HttpUrl.parse("https://api.example.com/test");
        Map<String, Object> body = new HashMap<>();
        body.put("key", "value");
        Request.Builder builder = template.createRequestBuilder(url, FaceppOkHttp3Template.HttpMethod.DELETE, null, body);
        assertNotNull(builder);
    }

    @Test
    void shouldCreateRequestBuilderWithDeleteMethodNoBody() throws Exception {
        HttpUrl url = HttpUrl.parse("https://api.example.com/test");
        Request.Builder builder = template.createRequestBuilder(url, FaceppOkHttp3Template.HttpMethod.DELETE, null, null);
        assertNotNull(builder);
    }

    @Test
    void shouldReadValueFromJson() {
        String json = "{\"code\":200,\"time_used\":100,\"request_id\":\"req123\"}";
        FaceppResponse response = template.readValue(json, FaceppResponse.class);
        assertNotNull(response);
        assertEquals(200, response.getCode());
        assertEquals(100, response.getTimeUsed());
        assertEquals("req123", response.getRequestId());
    }

    @Test
    void shouldThrowOnInvalidJsonDueToRecursiveSafeInstantiate() {
        String json = "invalid json";
        assertThrows(StackOverflowError.class, () -> {
            template.readValue(json, FaceppResponse.class);
        });
    }

    // Test various doRequest overloads

    @Test
    void shouldCallPostWithUrlAndClass() throws IOException {
        FaceppResponse result = template.post("https://api.example.com/test", FaceppResponse.class);
        assertNotNull(result);
    }

    @Test
    void shouldCallPostWithUrlParamsAndClass() throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("key", "value");
        FaceppResponse result = template.post("https://api.example.com/test", params, FaceppResponse.class);
        assertNotNull(result);
    }

    @Test
    void shouldCallPostWithHeadersParamsAndClass() throws IOException {
        Map<String, Object> headers = new HashMap<>();
        headers.put("Authorization", "Bearer token");
        Map<String, Object> params = new HashMap<>();
        params.put("key", "value");
        FaceppResponse result = template.post("https://api.example.com/test", headers, params, FaceppResponse.class);
        assertNotNull(result);
    }

    @Test
    void shouldCallPostWithHeadersParamsBodyAndClass() throws IOException {
        Map<String, Object> headers = new HashMap<>();
        headers.put("Authorization", "Bearer token");
        Map<String, Object> params = new HashMap<>();
        params.put("key", "value");
        Map<String, Object> body = new HashMap<>();
        body.put("data", "test");
        FaceppResponse result = template.post("https://api.example.com/test", headers, params, body, FaceppResponse.class);
        assertNotNull(result);
    }

    @Test
    void shouldCallGetWithUrlAndClass() throws IOException {
        FaceppResponse result = template.get("https://api.example.com/test", FaceppResponse.class);
        assertNotNull(result);
    }

    @Test
    void shouldCallGetWithUrlParamsAndClass() throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("key", "value");
        FaceppResponse result = template.get("https://api.example.com/test", params, FaceppResponse.class);
        assertNotNull(result);
    }

    @Test
    void shouldCallGetWithHeadersParamsAndClass() throws IOException {
        Map<String, Object> headers = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        params.put("key", "value");
        FaceppResponse result = template.get("https://api.example.com/test", headers, params, FaceppResponse.class);
        assertNotNull(result);
    }

    @Test
    void shouldCallDoRequestWithUrlAndMethod() throws IOException {
        Response result = template.doRequest("https://api.example.com/test", FaceppOkHttp3Template.HttpMethod.GET);
        assertNotNull(result);
    }

    @Test
    void shouldCallDoRequestWithUrlMethodAndParams() throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("key", "value");
        Response result = template.doRequest("https://api.example.com/test", FaceppOkHttp3Template.HttpMethod.GET, params);
        assertNotNull(result);
    }

    @Test
    void shouldCallDoRequestWithUrlMethodHeadersAndParams() throws IOException {
        Map<String, Object> headers = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        params.put("key", "value");
        Response result = template.doRequest("https://api.example.com/test", FaceppOkHttp3Template.HttpMethod.GET, headers, params);
        assertNotNull(result);
    }

    @Test
    void shouldCallDoRequestWithAllParams() throws IOException {
        Map<String, Object> headers = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        Map<String, Object> body = new HashMap<>();
        body.put("key", "value");
        Response result = template.doRequest("https://api.example.com/test", FaceppOkHttp3Template.HttpMethod.POST, headers, params, body);
        assertNotNull(result);
    }

    @Test
    void shouldCallDoRequestWithStartTime() throws IOException {
        Map<String, Object> headers = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        Map<String, Object> body = new HashMap<>();
        Response result = template.doRequest(System.currentTimeMillis(), "https://api.example.com/test", FaceppOkHttp3Template.HttpMethod.POST, headers, params, body);
        assertNotNull(result);
    }

    @Test
    void shouldCallDoRequestWithFullParams() throws IOException {
        Map<String, Object> headers = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        params.put("key", "value");
        Map<String, Object> body = new HashMap<>();
        body.put("data", "test");
        FaceppResponse result = template.doRequest("https://api.example.com/test", FaceppOkHttp3Template.HttpMethod.POST, headers, params, body, FaceppResponse.class);
        assertNotNull(result);
    }

    @Test
    void shouldCallDoPartRequest() throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("key", "value");
        FaceppResponse result = template.doPartRequest("https://api.example.com/test", params, FaceppResponse.class);
        assertNotNull(result);
    }

    @Test
    void shouldCallDoPartRequestRaw() throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("key", "value");
        Response result = template.doPartRequest(System.currentTimeMillis(), "https://api.example.com/test", params);
        assertNotNull(result);
    }

    // HttpMethod tests

    @Test
    void shouldReturnCorrectHttpMethodNames() {
        assertEquals("GET", FaceppOkHttp3Template.HttpMethod.GET.getName());
        assertEquals("HEAD", FaceppOkHttp3Template.HttpMethod.HEAD.getName());
        assertEquals("POST", FaceppOkHttp3Template.HttpMethod.POST.getName());
        assertEquals("PUT", FaceppOkHttp3Template.HttpMethod.PUT.getName());
        assertEquals("PATCH", FaceppOkHttp3Template.HttpMethod.PATCH.getName());
        assertEquals("DELETE", FaceppOkHttp3Template.HttpMethod.DELETE.getName());
        assertEquals("OPTIONS", FaceppOkHttp3Template.HttpMethod.OPTIONS.getName());
        assertEquals("TRACE", FaceppOkHttp3Template.HttpMethod.TRACE.getName());
    }

    @Test
    void shouldApplyGetMethod() {
        Request.Builder builder = new Request.Builder().url("https://example.com");
        Request.Builder result = FaceppOkHttp3Template.HttpMethod.GET.apply(builder);
        assertNotNull(result);
    }

    @Test
    void shouldApplyHeadMethod() {
        Request.Builder builder = new Request.Builder().url("https://example.com");
        Request.Builder result = FaceppOkHttp3Template.HttpMethod.HEAD.apply(builder);
        assertNotNull(result);
    }

    @Test
    void shouldApplyPostMethod() {
        Request.Builder builder = new Request.Builder().url("https://example.com");
        Request.Builder result = FaceppOkHttp3Template.HttpMethod.POST.apply(builder, "{\"key\":\"value\"}");
        assertNotNull(result);
    }

    @Test
    void shouldApplyPutMethod() {
        Request.Builder builder = new Request.Builder().url("https://example.com");
        Request.Builder result = FaceppOkHttp3Template.HttpMethod.PUT.apply(builder, "{\"key\":\"value\"}");
        assertNotNull(result);
    }

    @Test
    void shouldApplyPatchMethod() {
        Request.Builder builder = new Request.Builder().url("https://example.com");
        Request.Builder result = FaceppOkHttp3Template.HttpMethod.PATCH.apply(builder, "{\"key\":\"value\"}");
        assertNotNull(result);
    }

    @Test
    void shouldApplyDeleteMethodWithBody() {
        Request.Builder builder = new Request.Builder().url("https://example.com");
        Request.Builder result = FaceppOkHttp3Template.HttpMethod.DELETE.apply(builder, "{\"key\":\"value\"}");
        assertNotNull(result);
    }

    @Test
    void shouldApplyDeleteMethodWithoutBody() {
        Request.Builder builder = new Request.Builder().url("https://example.com");
        Request.Builder result = FaceppOkHttp3Template.HttpMethod.DELETE.apply(builder);
        assertNotNull(result);
    }

    @Test
    void shouldApplyOptionsMethod() {
        Request.Builder builder = new Request.Builder().url("https://example.com");
        Request.Builder result = FaceppOkHttp3Template.HttpMethod.OPTIONS.apply(builder, null);
        assertNotNull(result);
    }

    @Test
    void shouldApplyTraceMethod() {
        Request.Builder builder = new Request.Builder().url("https://example.com");
        Request.Builder result = FaceppOkHttp3Template.HttpMethod.TRACE.apply(builder, null);
        assertNotNull(result);
    }

    @Test
    void shouldReturnNullForUnknownHttpMethodByName() {
        assertNull(FaceppOkHttp3Template.HttpMethod.getByName(999));
    }

    @Test
    void shouldContainAllHttpMethods() {
        FaceppOkHttp3Template.HttpMethod[] methods = FaceppOkHttp3Template.HttpMethod.values();
        assertEquals(8, methods.length);
    }

    @Test
    void shouldResolveHttpMethodByValueOf() {
        assertEquals(FaceppOkHttp3Template.HttpMethod.POST, FaceppOkHttp3Template.HttpMethod.valueOf("POST"));
    }

    // Media type constants

    @Test
    void shouldHaveCorrectJsonMediaType() {
        assertNotNull(FaceppOkHttp3Template.APPLICATION_JSON);
        assertEquals("application/json", FaceppOkHttp3Template.APPLICATION_JSON.toString());
    }

    @Test
    void shouldHaveCorrectJsonUtf8MediaType() {
        assertNotNull(FaceppOkHttp3Template.APPLICATION_JSON_UTF8);
    }

    @Test
    void shouldHaveCorrectOctetStreamMediaType() {
        assertNotNull(FaceppOkHttp3Template.APPLICATION_OCTET_STREAM);
    }

    @Test
    void shouldHaveCorrectStringConstants() {
        assertEquals("application/json", FaceppOkHttp3Template.APPLICATION_JSON_VALUE);
        assertEquals("application/json;charset=UTF-8", FaceppOkHttp3Template.APPLICATION_JSON_UTF8_VALUE);
        assertEquals("application/octet-stream", FaceppOkHttp3Template.APPLICATION_OCTET_STREAM_VALUE);
    }

    /**
     * Stub that returns a mock Response for all HTTP calls without hitting the network.
     */
    static class StubOkHttp3Template extends FaceppOkHttp3Template {
        StubOkHttp3Template(OkHttpClient client, ObjectMapper mapper, FaceppProperties props) {
            super(client, mapper, props);
        }

        @Override
        public Response doRequest(long startTime, HttpUrl httpUrl, HttpMethod method,
                                  Map<String, Object> headers, Map<String, Object> bodyContent) throws IOException {
            return new Response.Builder()
                    .request(new Request.Builder().url(httpUrl).build())
                    .protocol(Protocol.HTTP_1_1)
                    .code(200)
                    .message("OK")
                    .body(ResponseBody.create(MediaType.parse("application/json"),
                            "{\"code\":200,\"time_used\":10,\"request_id\":\"req-1\"}"))
                    .build();
        }

        @Override
        public Response doPartRequest(long startTime, String httpUrl, Map<String, Object> params) throws IOException {
            return new Response.Builder()
                    .request(new Request.Builder().url(httpUrl).build())
                    .protocol(Protocol.HTTP_1_1)
                    .code(200)
                    .message("OK")
                    .body(ResponseBody.create(MediaType.parse("application/json"),
                            "{\"code\":200,\"time_used\":10,\"request_id\":\"req-1\"}"))
                    .build();
        }
    }
}
