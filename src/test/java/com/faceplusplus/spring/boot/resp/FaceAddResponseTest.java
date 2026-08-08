package com.faceplusplus.spring.boot.resp;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FaceAddResponseTest {

    @Test
    void shouldCreateWithDefaults() {
        FaceAddResponse response = new FaceAddResponse();
        assertNull(response.getFacesetToken());
        assertNull(response.getOuterId());
        assertNull(response.getFaceAdded());
        assertNull(response.getFaceCount());
        assertNull(response.getDetail());
    }

    @Test
    void shouldSupportSettersAndGetters() {
        FaceAddResponse response = new FaceAddResponse();
        response.setFacesetToken("token-1");
        response.setOuterId("outer-1");
        response.setFaceAdded(3);
        response.setFaceCount(10);
        List<FaceAddResponse.FailureFetail> details = new ArrayList<>();
        response.setDetail(details);

        assertEquals("token-1", response.getFacesetToken());
        assertEquals("outer-1", response.getOuterId());
        assertEquals(3, response.getFaceAdded());
        assertEquals(10, response.getFaceCount());
        assertSame(details, response.getDetail());
    }

    @Test
    void shouldSupportFailureDetail() {
        FaceAddResponse.FailureFetail detail = new FaceAddResponse.FailureFetail();
        detail.setToken("face-token-1");
        detail.setReason("INVALID_FACE_TOKEN");
        assertEquals("face-token-1", detail.getToken());
        assertEquals("INVALID_FACE_TOKEN", detail.getReason());
    }

    @Test
    void shouldSupportFailureDetailEquals() {
        FaceAddResponse.FailureFetail d1 = new FaceAddResponse.FailureFetail();
        d1.setToken("token-1");
        d1.setReason("reason");
        FaceAddResponse.FailureFetail d2 = new FaceAddResponse.FailureFetail();
        d2.setToken("token-1");
        d2.setReason("reason");
        assertEquals(d1, d2);
        assertEquals(d1.hashCode(), d2.hashCode());
    }

    @Test
    void shouldExtendFaceppResponse() {
        FaceAddResponse response = new FaceAddResponse();
        assertInstanceOf(FaceppResponse.class, response);
    }
}
