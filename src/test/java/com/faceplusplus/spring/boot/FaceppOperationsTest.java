package com.faceplusplus.spring.boot;

import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FaceppOperationsTest {

    private FaceppTemplate template;
    private FaceppProperties properties;

    @BeforeEach
    void setUp() {
        properties = new FaceppProperties();
        properties.setAppId("test-key");
        properties.setAppCertificate("test-secret");
        FaceppOkHttp3Template httpTemplate = new FaceppOkHttp3Template(new OkHttpClient(), new JsonMapper(), properties);
        template = new FaceppTemplate(httpTemplate, properties);
    }

    @Test
    void shouldAccessPropertiesViaTemplate() {
        FaceppFaceOperations ops = new FaceppFaceOperations(template);
        assertNotNull(ops);
    }

    @Test
    void shouldAccessFacesetOperations() {
        FaceppFacesetOperations ops = new FaceppFacesetOperations(template);
        assertNotNull(ops);
    }
}
