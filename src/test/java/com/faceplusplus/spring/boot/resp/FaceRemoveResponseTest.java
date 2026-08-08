package com.faceplusplus.spring.boot.resp;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FaceRemoveResponseTest {

    @Test
    void shouldCreateWithDefaults() {
        FaceRemoveResponse response = new FaceRemoveResponse();
        assertNull(response.getFacesetToken());
        assertNull(response.getOuterId());
        assertNull(response.getFaceRemoved());
        assertNull(response.getFaceCount());
        assertNull(response.getDetail());
    }

    @Test
    void shouldSupportSettersAndGetters() {
        FaceRemoveResponse response = new FaceRemoveResponse();
        response.setFacesetToken("token-1");
        response.setOuterId("outer-1");
        response.setFaceRemoved(2);
        response.setFaceCount(8);
        List<FaceAddResponse.FailureFetail> details = new ArrayList<>();
        response.setDetail(details);

        assertEquals("token-1", response.getFacesetToken());
        assertEquals("outer-1", response.getOuterId());
        assertEquals(2, response.getFaceRemoved());
        assertEquals(8, response.getFaceCount());
        assertSame(details, response.getDetail());
    }

    @Test
    void shouldSupportFailureDetail() {
        FaceRemoveResponse.FailureFetail detail = new FaceRemoveResponse.FailureFetail();
        detail.setToken("face-token-1");
        detail.setReason("INVALID_FACE_TOKEN");
        assertEquals("face-token-1", detail.getToken());
        assertEquals("INVALID_FACE_TOKEN", detail.getReason());
    }

    @Test
    void shouldExtendFaceppResponse() {
        FaceRemoveResponse response = new FaceRemoveResponse();
        assertInstanceOf(FaceppResponse.class, response);
    }
}
