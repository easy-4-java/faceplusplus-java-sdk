package com.faceplusplus.spring.boot.resp;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FaceCompareResponseTest {

    @Test
    void shouldCreateWithDefaults() {
        FaceCompareResponse response = new FaceCompareResponse();
        assertNull(response.getConfidence());
        assertNull(response.getThresholds());
        assertNull(response.getImageId1());
        assertNull(response.getImageId2());
        assertNull(response.getFaces1());
        assertNull(response.getFaces2());
    }

    @Test
    void shouldSupportSettersAndGetters() {
        FaceCompareResponse response = new FaceCompareResponse();
        response.setConfidence(95.5f);
        JSONObject thresholds = new JSONObject();
        response.setThresholds(thresholds);
        response.setImageId1("img-1");
        response.setImageId2("img-2");
        JSONArray faces1 = new JSONArray();
        JSONArray faces2 = new JSONArray();
        response.setFaces1(faces1);
        response.setFaces2(faces2);

        assertEquals(95.5f, response.getConfidence());
        assertSame(thresholds, response.getThresholds());
        assertEquals("img-1", response.getImageId1());
        assertEquals("img-2", response.getImageId2());
        assertSame(faces1, response.getFaces1());
        assertSame(faces2, response.getFaces2());
    }

    @Test
    void shouldExtendFaceppResponse() {
        FaceCompareResponse response = new FaceCompareResponse();
        assertInstanceOf(FaceppResponse.class, response);
    }
}
