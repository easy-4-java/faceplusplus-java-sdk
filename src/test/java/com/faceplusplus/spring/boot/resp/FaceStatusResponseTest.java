package com.faceplusplus.spring.boot.resp;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FaceStatusResponseTest {

    @Test
    void shouldCreateWithDefaults() {
        FaceStatusResponse response = new FaceStatusResponse();
        assertNull(response.getTaskId());
        assertNull(response.getStatus());
        assertNull(response.getFacesetToken());
        assertNull(response.getOuterId());
        assertNull(response.getFaceAdded());
        assertNull(response.getFaceRemoved());
        assertNull(response.getFaceCount());
        assertNull(response.getDetail());
    }

    @Test
    void shouldSupportSettersAndGetters() {
        FaceStatusResponse response = new FaceStatusResponse();
        response.setTaskId("task-123");
        response.setStatus(1);
        response.setFacesetToken("token-1");
        response.setOuterId("outer-1");
        response.setFaceAdded(5);
        response.setFaceRemoved(3);
        response.setFaceCount(10);
        List<FaceAddResponse.FailureFetail> details = new ArrayList<>();
        response.setDetail(details);

        assertEquals("task-123", response.getTaskId());
        assertEquals(1, response.getStatus());
        assertEquals("token-1", response.getFacesetToken());
        assertEquals("outer-1", response.getOuterId());
        assertEquals(5, response.getFaceAdded());
        assertEquals(3, response.getFaceRemoved());
        assertEquals(10, response.getFaceCount());
        assertSame(details, response.getDetail());
    }

    @Test
    void shouldSupportFailureDetail() {
        FaceStatusResponse.FailureFetail detail = new FaceStatusResponse.FailureFetail();
        detail.setToken("face-token-1");
        detail.setReason("QUOTA_EXCEEDED");
        assertEquals("face-token-1", detail.getToken());
        assertEquals("QUOTA_EXCEEDED", detail.getReason());
    }

    @Test
    void shouldExtendFaceppResponse() {
        FaceStatusResponse response = new FaceStatusResponse();
        assertInstanceOf(FaceppResponse.class, response);
    }
}
