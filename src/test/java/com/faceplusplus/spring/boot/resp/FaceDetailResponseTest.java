package com.faceplusplus.spring.boot.resp;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FaceDetailResponseTest {

    @Test
    void shouldCreateWithDefaults() {
        FaceDetailResponse response = new FaceDetailResponse();
        assertNull(response.getImageId());
        assertNull(response.getFaceToken());
        assertNull(response.getUserId());
        assertNull(response.getFaceRectangle());
        assertNull(response.getFacesets());
    }

    @Test
    void shouldSupportSettersAndGetters() {
        FaceDetailResponse response = new FaceDetailResponse();
        response.setImageId("img-1");
        response.setFaceToken("face-token-1");
        response.setUserId("user-1");

        FaceRectangle rect = new FaceRectangle();
        rect.setTop(10);
        rect.setLeft(20);
        rect.setWidth(100);
        rect.setHeight(120);
        response.setFaceRectangle(rect);

        List<FaceSetFetail> facesets = new ArrayList<>();
        FaceSetFetail detail = new FaceSetFetail();
        detail.setFacesetToken("faceset-1");
        detail.setOuterId("outer-1");
        detail.setTags("tag1");
        facesets.add(detail);
        response.setFacesets(facesets);

        assertEquals("img-1", response.getImageId());
        assertEquals("face-token-1", response.getFaceToken());
        assertEquals("user-1", response.getUserId());
        assertEquals(10, response.getFaceRectangle().getTop());
        assertEquals(1, response.getFacesets().size());
        assertEquals("faceset-1", response.getFacesets().get(0).getFacesetToken());
    }

    @Test
    void shouldExtendFaceppResponse() {
        FaceDetailResponse response = new FaceDetailResponse();
        assertInstanceOf(FaceppResponse.class, response);
    }
}
