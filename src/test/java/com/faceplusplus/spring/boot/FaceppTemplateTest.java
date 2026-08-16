package com.faceplusplus.spring.boot;

import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FaceppTemplateTest {

    private FaceppTemplate template;
    private FaceppProperties properties;
    private FaceppOkHttp3Template httpTemplate;

    @BeforeEach
    void setUp() {
        properties = new FaceppProperties();
        properties.setAppId("test-key");
        properties.setAppCertificate("test-secret");
        httpTemplate = new FaceppOkHttp3Template(new OkHttpClient(), new JsonMapper(), properties);
        template = new FaceppTemplate(httpTemplate, properties);
    }

    @Test
    void shouldReturnFacesetOperations() {
        FaceppFacesetAsyncOperations ops = template.opsForFaceset();
        assertNotNull(ops);
    }

    @Test
    void shouldReturnSameFacesetOperationsInstance() {
        FaceppFacesetAsyncOperations ops1 = template.opsForFaceset();
        FaceppFacesetAsyncOperations ops2 = template.opsForFaceset();
        assertSame(ops1, ops2);
    }

    @Test
    void shouldReturnFaceDetectOperations() {
        FaceppFaceAsyncOperations ops = template.opsForFaceDetect();
        assertNotNull(ops);
    }

    @Test
    void shouldReturnSameFaceDetectOperationsInstance() {
        FaceppFaceAsyncOperations ops1 = template.opsForFaceDetect();
        FaceppFaceAsyncOperations ops2 = template.opsForFaceDetect();
        assertSame(ops1, ops2);
    }

    @Test
    void shouldReturnProperties() {
        FaceppProperties result = template.getFaceppProperties();
        assertNotNull(result);
        assertSame(properties, result);
    }

    @Test
    void shouldReturnOkHttp3Template() {
        FaceppOkHttp3Template result = template.getFaceppOkHttp3Template();
        assertNotNull(result);
        assertSame(httpTemplate, result);
    }
}
