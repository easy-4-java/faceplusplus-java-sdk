package com.faceplusplus.spring.boot.resp;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FaceSearchResponseTest {

    @Test
    void shouldCreateWithDefaults() {
        FaceSearchResponse response = new FaceSearchResponse();
        assertNull(response.getResults());
        assertNull(response.getThresholds());
        assertNull(response.getImageId());
        assertNull(response.getFaces());
    }

    @Test
    void shouldSupportSettersAndGetters() {
        FaceSearchResponse response = new FaceSearchResponse();
        JSONArray results = new JSONArray();
        response.setResults(results);
        JSONObject thresholds = new JSONObject();
        response.setThresholds(thresholds);
        response.setImageId("img-1");
        JSONArray faces = new JSONArray();
        response.setFaces(faces);

        assertSame(results, response.getResults());
        assertSame(thresholds, response.getThresholds());
        assertEquals("img-1", response.getImageId());
        assertSame(faces, response.getFaces());
    }

    @Test
    void shouldExtendFaceppResponse() {
        FaceSearchResponse response = new FaceSearchResponse();
        assertInstanceOf(FaceppResponse.class, response);
    }
}
