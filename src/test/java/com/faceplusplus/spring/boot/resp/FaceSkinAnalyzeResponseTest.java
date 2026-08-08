package com.faceplusplus.spring.boot.resp;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FaceSkinAnalyzeResponseTest {

    @Test
    void shouldCreateWithDefaults() {
        FaceSkinAnalyzeResponse response = new FaceSkinAnalyzeResponse();
        assertNull(response.getFaceRectangle());
        assertNull(response.getResult());
        assertNull(response.getWarning());
    }

    @Test
    void shouldSupportSettersAndGetters() {
        FaceSkinAnalyzeResponse response = new FaceSkinAnalyzeResponse();

        FaceRectangle rect = new FaceRectangle();
        rect.setTop(10);
        rect.setLeft(20);
        rect.setWidth(100);
        rect.setHeight(120);
        response.setFaceRectangle(rect);

        JSONObject result = new JSONObject();
        result.put("skin_type", "oily");
        response.setResult(result);

        JSONArray warning = new JSONArray();
        warning.add("improper_headpose");
        response.setWarning(warning);

        assertEquals(10, response.getFaceRectangle().getTop());
        assertEquals("oily", response.getResult().getString("skin_type"));
        assertEquals(1, response.getWarning().size());
    }

    @Test
    void shouldExtendFaceppResponse() {
        FaceSkinAnalyzeResponse response = new FaceSkinAnalyzeResponse();
        assertInstanceOf(FaceppResponse.class, response);
    }
}
