package com.faceplusplus.spring.boot.resp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FacesetDeleteResponseTest {

    @Test
    void shouldCreateWithDefaults() {
        FacesetDeleteResponse response = new FacesetDeleteResponse();
        assertNull(response.getFacesetToken());
        assertNull(response.getOuterId());
    }

    @Test
    void shouldSupportSettersAndGetters() {
        FacesetDeleteResponse response = new FacesetDeleteResponse();
        response.setFacesetToken("token-1");
        response.setOuterId("outer-1");
        assertEquals("token-1", response.getFacesetToken());
        assertEquals("outer-1", response.getOuterId());
    }

    @Test
    void shouldExtendFaceppResponse() {
        FacesetDeleteResponse response = new FacesetDeleteResponse();
        assertInstanceOf(FaceppResponse.class, response);
    }

    @Test
    void shouldSupportEqualsAndHashCode() {
        FacesetDeleteResponse r1 = new FacesetDeleteResponse();
        r1.setFacesetToken("token-1");
        FacesetDeleteResponse r2 = new FacesetDeleteResponse();
        r2.setFacesetToken("token-1");
        assertEquals(r1, r2);
        assertEquals(r1.hashCode(), r2.hashCode());
    }
}
