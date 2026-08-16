package com.faceplusplus.spring.boot;

import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FaceppFaceAsyncOperationsTest {

    private FaceppTemplate template;
    private FaceppFaceAsyncOperations operations;

    @BeforeEach
    void setUp() {
        FaceppProperties properties = new FaceppProperties();
        properties.setAppId("test-key");
        properties.setAppCertificate("test-secret");
        FaceppOkHttp3Template httpTemplate = new FaceppOkHttp3Template(new OkHttpClient(), new JsonMapper(), properties);
        template = new FaceppTemplate(httpTemplate, properties);
        operations = new FaceppFaceAsyncOperations(template);
    }

    @Test
    void shouldConstructSuccessfully() {
        assertNotNull(operations);
    }

    @Test
    void shouldExtendFaceppFaceOperations() {
        assertInstanceOf(FaceppFaceOperations.class, operations);
    }

    @Test
    void shouldExtendFaceppOperations() {
        assertInstanceOf(FaceppOperations.class, operations);
    }
}
