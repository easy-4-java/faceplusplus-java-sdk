package com.faceplusplus.spring.boot.resp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FaceppResponseTest {

    @Test
    void shouldCreateWithDefaults() {
        FaceppResponse response = new FaceppResponse();
        assertEquals(0, response.getCode());
        assertEquals(0, response.getTimeUsed());
        assertNull(response.getRequestId());
        assertNull(response.getErrorMsg());
    }

    @Test
    void shouldReportSuccessWhenCodeIs200() {
        FaceppResponse response = new FaceppResponse();
        response.setCode(200);
        assertTrue(response.isSuccess());
    }

    @Test
    void shouldReportFailureWhenCodeIsNot200() {
        FaceppResponse response = new FaceppResponse();
        response.setCode(400);
        assertFalse(response.isSuccess());
    }

    @Test
    void shouldReportFailureWhenCodeIs403() {
        FaceppResponse response = new FaceppResponse();
        response.setCode(403);
        assertFalse(response.isSuccess());
    }

    @Test
    void shouldSupportSettersAndGetters() {
        FaceppResponse response = new FaceppResponse();
        response.setCode(200);
        response.setTimeUsed(150);
        response.setRequestId("req-123");
        response.setErrorMsg("error");
        assertEquals(200, response.getCode());
        assertEquals(150, response.getTimeUsed());
        assertEquals("req-123", response.getRequestId());
        assertEquals("error", response.getErrorMsg());
    }

    @Test
    void shouldSupportEqualsAndHashCode() {
        FaceppResponse r1 = new FaceppResponse();
        r1.setCode(200);
        r1.setRequestId("req-1");
        FaceppResponse r2 = new FaceppResponse();
        r2.setCode(200);
        r2.setRequestId("req-1");
        assertEquals(r1, r2);
        assertEquals(r1.hashCode(), r2.hashCode());
    }

    @Test
    void shouldSupportToString() {
        FaceppResponse response = new FaceppResponse();
        response.setCode(200);
        assertNotNull(response.toString());
    }
}
