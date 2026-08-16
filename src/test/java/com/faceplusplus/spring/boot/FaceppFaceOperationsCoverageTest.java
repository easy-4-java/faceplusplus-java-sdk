package com.faceplusplus.spring.boot;

import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;
import com.faceplusplus.spring.boot.req.*;
import com.faceplusplus.spring.boot.resp.*;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Coverage tests for FaceppFaceOperations.
 * Uses a testable subclass of FaceppOkHttp3Template to avoid network calls.
 */
class FaceppFaceOperationsCoverageTest {

    private FaceppTemplate template;
    private FaceppProperties properties;
    private StubOkHttp3Template stubHttpTemplate;
    private FaceppFaceOperations operations;

    @BeforeEach
    void setUp() {
        properties = new FaceppProperties();
        properties.setAppId("test-key");
        properties.setAppCertificate("test-secret");
        stubHttpTemplate = new StubOkHttp3Template(new OkHttpClient(), new JsonMapper(), properties);
        template = new FaceppTemplate(stubHttpTemplate, properties);
        operations = new FaceppFaceOperations(template);
    }

    @Test
    void shouldCallDetectUrl() throws IOException {
        stubHttpTemplate.setNextResponse(new FaceDetectResponse());
        FaceDetectOptions options = FaceDetectOptions.builder().returnLandmark(1).returnAttributes("gender").build();
        FaceDetectResponse result = operations.detectUrl("https://img.com/1.jpg", options);
        assertNotNull(result);
        assertEquals(FaceppApiAddress.FACE_DETECT.getUrl(), stubHttpTemplate.getLastUrl());
    }

    @Test
    void shouldCallDetectBase64() throws IOException {
        stubHttpTemplate.setNextResponse(new FaceDetectResponse());
        FaceDetectOptions options = FaceDetectOptions.builder().build();
        FaceDetectResponse result = operations.detectBase64("base64data", options);
        assertNotNull(result);
    }

    @Test
    void shouldCallDetectFile() throws IOException {
        stubHttpTemplate.setNextResponse(new FaceDetectResponse());
        FaceDetectOptions options = FaceDetectOptions.builder().build();
        File tmpFile = File.createTempFile("test", ".jpg");
        tmpFile.deleteOnExit();
        FaceDetectResponse result = operations.detectFile(tmpFile, options);
        assertNotNull(result);
    }

    @Test
    void shouldCallAnalyze() throws IOException {
        stubHttpTemplate.setNextResponse(new FaceAnalyzeResponse());
        FaceAnalyzeOptions options = FaceAnalyzeOptions.builder().returnAttributes("gender,age").build();
        FaceAnalyzeResponse result = operations.analyze(new String[]{"token1", "token2"}, options);
        assertNotNull(result);
        assertEquals(FaceppApiAddress.FACE_ANALYZE.getUrl(), stubHttpTemplate.getLastUrl());
    }

    @Test
    void shouldCallCompareUrl() throws IOException {
        stubHttpTemplate.setNextResponse(new FaceCompareResponse());
        FaceCompareResponse result = operations.compareUrl("https://img.com/1.jpg", "https://img.com/2.jpg");
        assertNotNull(result);
    }

    @Test
    void shouldCallCompareToken() throws IOException {
        stubHttpTemplate.setNextResponse(new FaceCompareResponse());
        FaceCompareResponse result = operations.compareToken("token1", "token2");
        assertNotNull(result);
    }

    @Test
    void shouldCallCompareBase64() throws IOException {
        stubHttpTemplate.setNextResponse(new FaceCompareResponse());
        FaceCompareResponse result = operations.compareBase64("base64_1", "base64_2");
        assertNotNull(result);
    }

    @Test
    void shouldCallCompareFile() throws IOException {
        stubHttpTemplate.setNextResponse(new FaceCompareResponse());
        File f1 = File.createTempFile("test1", ".jpg");
        File f2 = File.createTempFile("test2", ".jpg");
        f1.deleteOnExit();
        f2.deleteOnExit();
        FaceCompareResponse result = operations.compareFile(f1, f2);
        assertNotNull(result);
    }

    @Test
    void shouldCallSearchUrl() throws IOException {
        stubHttpTemplate.setNextResponse(new FaceSearchResponse());
        FaceSearchOptions options = FaceSearchOptions.builder().build();
        FaceSearchResponse result = operations.searchUrl("https://img.com/1.jpg", options);
        assertNotNull(result);
    }

    @Test
    void shouldCallSearchToken() throws IOException {
        stubHttpTemplate.setNextResponse(new FaceSearchResponse());
        FaceSearchOptions options = FaceSearchOptions.builder().build();
        FaceSearchResponse result = operations.searchToken("face-token-1", options);
        assertNotNull(result);
    }

    @Test
    void shouldCallSearchBase64() throws IOException {
        stubHttpTemplate.setNextResponse(new FaceSearchResponse());
        FaceSearchOptions options = FaceSearchOptions.builder().build();
        FaceSearchResponse result = operations.searchBase64("base64data", options);
        assertNotNull(result);
    }

    @Test
    void shouldCallSearchFile() throws IOException {
        stubHttpTemplate.setNextResponse(new FaceSearchResponse());
        FaceSearchOptions options = FaceSearchOptions.builder().build();
        File tmpFile = File.createTempFile("test", ".jpg");
        tmpFile.deleteOnExit();
        FaceSearchResponse result = operations.searchFile(tmpFile, options);
        assertNotNull(result);
    }

    @Test
    void shouldCallSkinAnalyzeUrl() throws IOException {
        stubHttpTemplate.setNextResponse(new FaceSkinAnalyzeResponse());
        SkinAnalyzeOptions options = SkinAnalyzeOptions.builder().build();
        FaceSkinAnalyzeResponse result = operations.skinAnalyzeUrl("https://img.com/1.jpg", SkinAnalyzeType.BASIC, options);
        assertNotNull(result);
    }

    @Test
    void shouldCallSkinAnalyzeBase64() throws IOException {
        stubHttpTemplate.setNextResponse(new FaceSkinAnalyzeResponse());
        SkinAnalyzeOptions options = SkinAnalyzeOptions.builder().build();
        FaceSkinAnalyzeResponse result = operations.skinAnalyzeBase64("base64data", SkinAnalyzeType.ADVANCED, options);
        assertNotNull(result);
    }

    @Test
    void shouldCallSkinAnalyzeFile() throws IOException {
        stubHttpTemplate.setNextResponse(new FaceSkinAnalyzeResponse());
        SkinAnalyzeOptions options = SkinAnalyzeOptions.builder().build();
        File tmpFile = File.createTempFile("test", ".jpg");
        tmpFile.deleteOnExit();
        FaceSkinAnalyzeResponse result = operations.skinAnalyzeFile(tmpFile, SkinAnalyzeType.PRO, options);
        assertNotNull(result);
    }

    /**
     * Stub subclass of FaceppOkHttp3Template that returns pre-set responses
     * without making real HTTP calls.
     */
    static class StubOkHttp3Template extends FaceppOkHttp3Template {
        private FaceppResponse nextResponse;
        private String lastUrl;
        private Map<String, Object> lastParams;

        StubOkHttp3Template(OkHttpClient client, ObjectMapper mapper, FaceppProperties props) {
            super(client, mapper, props);
        }

        void setNextResponse(FaceppResponse response) {
            this.nextResponse = response;
        }

        String getLastUrl() { return lastUrl; }
        Map<String, Object> getLastParams() { return lastParams; }

        @Override
        public <T extends FaceppResponse> T post(String url, Map<String, Object> headers, Map<String, Object> params, Class<T> rtClass) throws IOException {
            this.lastUrl = url;
            this.lastParams = params;
            try {
                T res = rtClass.getDeclaredConstructor().newInstance();
                res.setCode(200);
                return res;
            } catch (Exception e) {
                throw new IOException(e);
            }
        }

        @Override
        public <T extends FaceppResponse> T doPartRequest(String httpUrl, Map<String, Object> params, Class<T> rtClass) throws IOException {
            this.lastUrl = httpUrl;
            this.lastParams = params;
            try {
                T res = rtClass.getDeclaredConstructor().newInstance();
                res.setCode(200);
                return res;
            } catch (Exception e) {
                throw new IOException(e);
            }
        }
    }
}
