package com.faceplusplus.spring.boot.resp;

import com.alibaba.fastjson.JSONArray;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FaceDetectResponseTest {

    @Test
    void shouldCreateWithDefaults() {
        FaceDetectResponse response = new FaceDetectResponse();
        assertNull(response.getImageId());
        assertNull(response.getFaceNum());
        assertNull(response.getFaces());
    }

    @Test
    void shouldSupportSettersAndGetters() {
        FaceDetectResponse response = new FaceDetectResponse();
        response.setImageId("img-123");
        response.setFaceNum(3);
        JSONArray faces = new JSONArray();
        response.setFaces(faces);
        assertEquals("img-123", response.getImageId());
        assertEquals(3, response.getFaceNum());
        assertSame(faces, response.getFaces());
    }

    @Test
    void shouldExtendFaceppResponse() {
        FaceDetectResponse response = new FaceDetectResponse();
        assertInstanceOf(FaceppResponse.class, response);
        response.setCode(200);
        assertTrue(response.isSuccess());
    }

    @Test
    void shouldSupportEqualsAndHashCode() {
        FaceDetectResponse r1 = new FaceDetectResponse();
        r1.setImageId("img-1");
        FaceDetectResponse r2 = new FaceDetectResponse();
        r2.setImageId("img-1");
        assertEquals(r1, r2);
        assertEquals(r1.hashCode(), r2.hashCode());
    }
}
