package com.faceplusplus.spring.boot;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FaceppFacesetOperationsTest {

    private FaceppTemplate template;
    private FaceppFacesetOperations operations;

    @BeforeEach
    void setUp() {
        FaceppProperties properties = new FaceppProperties();
        properties.setAppId("test-key");
        properties.setAppCertificate("test-secret");
        FaceppOkHttp3Template httpTemplate = new FaceppOkHttp3Template(new OkHttpClient(), new ObjectMapper(), properties);
        template = new FaceppTemplate(httpTemplate, properties);
        operations = new FaceppFacesetOperations(template);
    }

    @Test
    void shouldConstructSuccessfully() {
        assertNotNull(operations);
    }

    @Test
    void shouldExtendFaceppOperations() {
        assertInstanceOf(FaceppOperations.class, operations);
    }
}
