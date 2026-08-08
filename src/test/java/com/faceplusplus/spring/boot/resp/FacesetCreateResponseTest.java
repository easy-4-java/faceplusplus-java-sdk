package com.faceplusplus.spring.boot.resp;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FacesetCreateResponseTest {

    @Test
    void shouldCreateWithDefaults() {
        FacesetCreateResponse response = new FacesetCreateResponse();
        assertNull(response.getFacesetToken());
        assertNull(response.getOuterId());
        assertNull(response.getFaceAdded());
        assertNull(response.getFaceCount());
        assertNull(response.getDetail());
    }

    @Test
    void shouldSupportSettersAndGetters() {
        FacesetCreateResponse response = new FacesetCreateResponse();
        response.setFacesetToken("token-1");
        response.setOuterId("outer-1");
        response.setFaceAdded(5);
        response.setFaceCount(5);
        List<FacesetCreateResponse.FailureFetail> details = new ArrayList<>();
        response.setDetail(details);

        assertEquals("token-1", response.getFacesetToken());
        assertEquals("outer-1", response.getOuterId());
        assertEquals(5, response.getFaceAdded());
        assertEquals(5, response.getFaceCount());
        assertSame(details, response.getDetail());
    }

    @Test
    void shouldSupportFailureDetail() {
        FacesetCreateResponse.FailureFetail detail = new FacesetCreateResponse.FailureFetail();
        detail.setToken("face-token-1");
        detail.setReason("QUOTA_EXCEEDED");
        assertEquals("face-token-1", detail.getToken());
        assertEquals("QUOTA_EXCEEDED", detail.getReason());
    }

    @Test
    void shouldExtendFaceppResponse() {
        FacesetCreateResponse response = new FacesetCreateResponse();
        assertInstanceOf(FaceppResponse.class, response);
    }
}
