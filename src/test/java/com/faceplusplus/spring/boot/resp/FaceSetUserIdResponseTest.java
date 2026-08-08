package com.faceplusplus.spring.boot.resp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FaceSetUserIdResponseTest {

    @Test
    void shouldCreateWithDefaults() {
        FaceSetUserIdResponse response = new FaceSetUserIdResponse();
        assertNull(response.getFaceToken());
        assertNull(response.getUserId());
    }

    @Test
    void shouldSupportSettersAndGetters() {
        FaceSetUserIdResponse response = new FaceSetUserIdResponse();
        response.setFaceToken("face-token-1");
        response.setUserId("user-123");
        assertEquals("face-token-1", response.getFaceToken());
        assertEquals("user-123", response.getUserId());
    }

    @Test
    void shouldExtendFaceppResponse() {
        FaceSetUserIdResponse response = new FaceSetUserIdResponse();
        assertInstanceOf(FaceppResponse.class, response);
    }

    @Test
    void shouldSupportEqualsAndHashCode() {
        FaceSetUserIdResponse r1 = new FaceSetUserIdResponse();
        r1.setFaceToken("token-1");
        r1.setUserId("user-1");
        FaceSetUserIdResponse r2 = new FaceSetUserIdResponse();
        r2.setFaceToken("token-1");
        r2.setUserId("user-1");
        assertEquals(r1, r2);
        assertEquals(r1.hashCode(), r2.hashCode());
    }
}
