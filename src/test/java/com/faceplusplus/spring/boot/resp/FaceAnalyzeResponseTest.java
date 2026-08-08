package com.faceplusplus.spring.boot.resp;

import com.alibaba.fastjson.JSONArray;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FaceAnalyzeResponseTest {

    @Test
    void shouldCreateWithDefaults() {
        FaceAnalyzeResponse response = new FaceAnalyzeResponse();
        assertNull(response.getImageId());
        assertNull(response.getFaceNum());
        assertNull(response.getFaces());
    }

    @Test
    void shouldSupportSettersAndGetters() {
        FaceAnalyzeResponse response = new FaceAnalyzeResponse();
        response.setImageId("img-123");
        response.setFaceNum(2);
        JSONArray faces = new JSONArray();
        response.setFaces(faces);
        assertEquals("img-123", response.getImageId());
        assertEquals(2, response.getFaceNum());
        assertSame(faces, response.getFaces());
    }

    @Test
    void shouldExtendFaceppResponse() {
        FaceAnalyzeResponse response = new FaceAnalyzeResponse();
        assertInstanceOf(FaceppResponse.class, response);
    }
}
