package com.faceplusplus.spring.boot;

import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;
import com.faceplusplus.spring.boot.resp.FaceppResponse;
import okhttp3.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for FaceppOkHttp3Template using an OkHttp interceptor to mock network calls.
 * Covers the real doRequest/doPartRequest/doAsyncRequest code paths including
 * logging, error handling, and the async Callback class.
 */
class FaceppOkHttp3TemplateRealTest {

    private static final String SUCCESS_BODY = "{\"code\":200,\"time_used\":10,\"request_id\":\"req-1\"}";
    private FaceppOkHttp3Template template;
    private FaceppProperties properties;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        properties = new FaceppProperties();
        properties.setAppId("test-key");
        properties.setAppCertificate("test-secret");
        objectMapper = new JsonMapper();

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request request = chain.request();
                    return new Response.Builder()
                            .request(request)
                            .protocol(Protocol.HTTP_1_1)
                            .code(200)
                            .message("OK")
                            .body(ResponseBody.create(MediaType.parse("application/json"), SUCCESS_BODY))
                            .build();
                })
                .build();
        template = new FaceppOkHttp3Template(client, objectMapper, properties);
    }

    // GET-based tests (GET doesn't require body content)

    @Test
    void shouldExecuteRealGetRequest() throws IOException {
        FaceppResponse result = template.get("https://api.example.com/test", FaceppResponse.class);
        assertNotNull(result);
        assertEquals(200, result.getCode());
    }

    @Test
    void shouldExecuteRealGetWithParams() throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("key", "value");
        FaceppResponse result = template.get("https://api.example.com/test", params, FaceppResponse.class);
        assertNotNull(result);
    }

    @Test
    void shouldExecuteRealGetWithHeadersAndParams() throws IOException {
        Map<String, Object> headers = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        params.put("key", "value");
        FaceppResponse result = template.get("https://api.example.com/test", headers, params, FaceppResponse.class);
        assertNotNull(result);
    }

    // POST with body content

    @Test
    void shouldExecuteRealPostWithBodyContent() throws IOException {
        Map<String, Object> headers = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        Map<String, Object> body = new HashMap<>();
        body.put("api_key", "test");
        body.put("api_secret", "secret");
        FaceppResponse result = template.post("https://api.example.com/test", headers, params, body, FaceppResponse.class);
        assertNotNull(result);
        assertEquals(200, result.getCode());
    }

    // doRequest overloads

    @Test
    void shouldExecuteRealDoRequestWithUrlAndMethod() throws IOException {
        Response result = template.doRequest("https://api.example.com/test", FaceppOkHttp3Template.HttpMethod.GET);
        assertNotNull(result);
        assertTrue(result.isSuccessful());
    }

    @Test
    void shouldExecuteRealDoRequestWithUrlMethodAndParams() throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("key", "value");
        Response result = template.doRequest("https://api.example.com/test", FaceppOkHttp3Template.HttpMethod.GET, params);
        assertNotNull(result);
    }

    @Test
    void shouldExecuteRealDoRequestWithHeadersAndParams() throws IOException {
        Map<String, Object> headers = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        Response result = template.doRequest("https://api.example.com/test", FaceppOkHttp3Template.HttpMethod.GET, headers, params);
        assertNotNull(result);
    }

    @Test
    void shouldExecuteRealDoRequestWithAllParams() throws IOException {
        Map<String, Object> headers = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        Map<String, Object> body = new HashMap<>();
        body.put("key", "value");
        Response result = template.doRequest("https://api.example.com/test", FaceppOkHttp3Template.HttpMethod.GET, headers, params, body);
        assertNotNull(result);
    }

    @Test
    void shouldExecuteRealDoRequestWithStartTime() throws IOException {
        Map<String, Object> headers = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        Map<String, Object> body = new HashMap<>();
        Response result = template.doRequest(System.currentTimeMillis(), "https://api.example.com/test", FaceppOkHttp3Template.HttpMethod.GET, headers, params, body);
        assertNotNull(result);
    }

    @Test
    void shouldExecuteRealDoRequestWithFullParams() throws IOException {
        Map<String, Object> headers = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        params.put("key", "value");
        Map<String, Object> body = new HashMap<>();
        body.put("data", "test");
        FaceppResponse result = template.doRequest("https://api.example.com/test", FaceppOkHttp3Template.HttpMethod.GET, headers, params, body, FaceppResponse.class);
        assertNotNull(result);
        assertEquals(200, result.getCode());
    }

    @Test
    void shouldExecuteRealDoRequestWithHttpUrl() throws IOException {
        HttpUrl url = HttpUrl.parse("https://api.example.com/test");
        Map<String, Object> body = new HashMap<>();
        body.put("key", "value");
        Response result = template.doRequest(System.currentTimeMillis(), url, FaceppOkHttp3Template.HttpMethod.GET, null, body);
        assertNotNull(result);
    }

    @Test
    void shouldExecuteRealDoRequestWithHttpUrlGet() throws IOException {
        HttpUrl url = HttpUrl.parse("https://api.example.com/test");
        Response result = template.doRequest(System.currentTimeMillis(), url, FaceppOkHttp3Template.HttpMethod.GET, null, null);
        assertNotNull(result);
    }

    @Test
    void shouldExecuteRealDoRequestTypedWithHttpUrl() throws IOException {
        HttpUrl url = HttpUrl.parse("https://api.example.com/test");
        Map<String, Object> body = new HashMap<>();
        body.put("key", "value");
        FaceppResponse result = template.doRequest(System.currentTimeMillis(), url, FaceppOkHttp3Template.HttpMethod.GET, null, body, FaceppResponse.class);
        assertNotNull(result);
        assertEquals(200, result.getCode());
    }

    // doPartRequest

    @Test
    void shouldExecuteRealDoPartRequest() throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("key", "value");
        FaceppResponse result = template.doPartRequest("https://api.example.com/test", params, FaceppResponse.class);
        assertNotNull(result);
    }

    @Test
    void shouldExecuteRealDoPartRequestRaw() throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("key", "value");
        Response result = template.doPartRequest(System.currentTimeMillis(), "https://api.example.com/test", params);
        assertNotNull(result);
    }

    @Test
    void shouldExecuteRealDoPartRequestWithFile() throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("key", "value");
        java.io.File tmpFile = java.io.File.createTempFile("test", ".jpg");
        tmpFile.deleteOnExit();
        params.put("image_file", tmpFile);
        Response result = template.doPartRequest(System.currentTimeMillis(), "https://api.example.com/test", params);
        assertNotNull(result);
    }

    // Async tests

    @Test
    void shouldExecuteRealAsyncRequest() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<FaceppResponse> ref = new AtomicReference<>();

        template.doAsyncRequest("https://api.example.com/test", FaceppOkHttp3Template.HttpMethod.GET,
                response -> {
                    ref.set(response);
                    latch.countDown();
                },
                FaceppResponse.class);

        assertTrue(latch.await(5, TimeUnit.SECONDS));
        assertNotNull(ref.get());
        assertEquals(200, ref.get().getCode());
    }

    @Test
    void shouldExecuteRealAsyncRequestWithFailureCallback() throws Exception {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    throw new IOException("Simulated network error");
                })
                .build();
        FaceppOkHttp3Template failTemplate = new FaceppOkHttp3Template(client, objectMapper, properties);

        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<IOException> errorRef = new AtomicReference<>();

        failTemplate.doAsyncRequest("https://api.example.com/test", FaceppOkHttp3Template.HttpMethod.GET,
                response -> {},
                (call, e) -> {
                    errorRef.set(e);
                    latch.countDown();
                    return true;
                },
                FaceppResponse.class);

        assertTrue(latch.await(5, TimeUnit.SECONDS));
        assertNotNull(errorRef.get());
    }

    @Test
    void shouldExecuteRealAsyncRequestWithNullFailureCallback() throws Exception {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    throw new IOException("Simulated network error");
                })
                .build();
        FaceppOkHttp3Template failTemplate = new FaceppOkHttp3Template(client, objectMapper, properties);

        // When failure callback is null and request fails, no exception should propagate
        // The onFailure handler just logs and returns without calling any callback
        assertDoesNotThrow(() -> {
            failTemplate.doAsyncRequest("https://api.example.com/test", FaceppOkHttp3Template.HttpMethod.GET,
                    response -> {},
                    null,
                    FaceppResponse.class);
            Thread.sleep(500); // Give async callback time to fire
        });
    }

    @Test
    void shouldExecuteRealAsyncRequestWithQueryParams() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<FaceppResponse> ref = new AtomicReference<>();

        Map<String, Object> params = new HashMap<>();
        params.put("key", "value");

        template.doAsyncRequest("https://api.example.com/test", FaceppOkHttp3Template.HttpMethod.GET,
                params,
                response -> {
                    ref.set(response);
                    latch.countDown();
                },
                null,
                FaceppResponse.class);

        assertTrue(latch.await(5, TimeUnit.SECONDS));
        assertNotNull(ref.get());
    }

    @Test
    void shouldExecuteRealAsyncRequestWithHeadersAndParams() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<FaceppResponse> ref = new AtomicReference<>();

        Map<String, Object> headers = new HashMap<>();
        headers.put("Authorization", "Bearer token");
        Map<String, Object> params = new HashMap<>();
        params.put("key", "value");

        template.doAsyncRequest("https://api.example.com/test", FaceppOkHttp3Template.HttpMethod.GET,
                headers, params,
                response -> {
                    ref.set(response);
                    latch.countDown();
                },
                null,
                FaceppResponse.class);

        assertTrue(latch.await(5, TimeUnit.SECONDS));
    }

    @Test
    void shouldExecuteRealAsyncRequestWithHeadersParamsAndBody() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<FaceppResponse> ref = new AtomicReference<>();

        Map<String, Object> headers = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        Map<String, Object> body = new HashMap<>();
        body.put("key", "value");

        template.doAsyncRequest("https://api.example.com/test", FaceppOkHttp3Template.HttpMethod.GET,
                headers, params, body,
                response -> {
                    ref.set(response);
                    latch.countDown();
                },
                null,
                FaceppResponse.class);

        assertTrue(latch.await(5, TimeUnit.SECONDS));
    }

    @Test
    void shouldExecuteRealAsyncRequestWithBiFunctionCallbacksAndBody() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<FaceppResponse> ref = new AtomicReference<>();

        Map<String, Object> body = new HashMap<>();
        body.put("key", "value");

        template.doAsyncRequest(System.currentTimeMillis(), HttpUrl.parse("https://api.example.com/test"),
                FaceppOkHttp3Template.HttpMethod.GET, null, body,
                (call, response) -> {
                    try {
                        FaceppResponse res = new FaceppResponse();
                        res.setCode(response.code());
                        ref.set(res);
                    } catch (Exception e) {
                        // ignore
                    }
                    latch.countDown();
                    return null;
                },
                null);

        assertTrue(latch.await(5, TimeUnit.SECONDS));
    }

    @Test
    void shouldExecuteRealAsyncRequestWithBiFunctionCallbacksFullUrl() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<FaceppResponse> ref = new AtomicReference<>();

        Map<String, Object> body = new HashMap<>();
        body.put("key", "value");

        template.doAsyncRequest(System.currentTimeMillis(), "https://api.example.com/test",
                FaceppOkHttp3Template.HttpMethod.GET, null, null, body,
                (call, response) -> {
                    try {
                        FaceppResponse res = new FaceppResponse();
                        res.setCode(response.code());
                        ref.set(res);
                    } catch (Exception e) {
                        // ignore
                    }
                    latch.countDown();
                    return null;
                },
                null);

        assertTrue(latch.await(5, TimeUnit.SECONDS));
    }
}
