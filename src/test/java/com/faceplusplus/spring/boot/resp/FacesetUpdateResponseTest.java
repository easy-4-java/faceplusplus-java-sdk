package com.faceplusplus.spring.boot.resp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FacesetUpdateResponseTest {

    @Test
    void shouldCreateWithDefaults() {
        FacesetUpdateResponse response = new FacesetUpdateResponse();
        assertNull(response.getFacesetToken());
        assertNull(response.getOuterId());
    }

    @Test
    void shouldSupportSettersAndGetters() {
        FacesetUpdateResponse response = new FacesetUpdateResponse();
        response.setFacesetToken("token-1");
        response.setOuterId("outer-1");
        assertEquals("token-1", response.getFacesetToken());
        assertEquals("outer-1", response.getOuterId());
    }

    @Test
    void shouldExtendFaceppResponse() {
        FacesetUpdateResponse response = new FacesetUpdateResponse();
        assertInstanceOf(FaceppResponse.class, response);
    }

    @Test
    void shouldSupportEqualsAndHashCode() {
        FacesetUpdateResponse r1 = new FacesetUpdateResponse();
        r1.setFacesetToken("token-1");
        FacesetUpdateResponse r2 = new FacesetUpdateResponse();
        r2.setFacesetToken("token-1");
        assertEquals(r1, r2);
        assertEquals(r1.hashCode(), r2.hashCode());
    }
}
