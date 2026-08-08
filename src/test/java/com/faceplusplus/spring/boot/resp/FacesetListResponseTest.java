package com.faceplusplus.spring.boot.resp;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FacesetListResponseTest {

    @Test
    void shouldCreateWithDefaults() {
        FacesetListResponse response = new FacesetListResponse();
        assertNull(response.getNext());
        assertNull(response.getFacesets());
    }

    @Test
    void shouldSupportSettersAndGetters() {
        FacesetListResponse response = new FacesetListResponse();
        response.setNext("cursor-1");
        List<FacesetListResponse.FacesetFetail> facesets = new ArrayList<>();
        FacesetListResponse.FacesetFetail fetail = new FacesetListResponse.FacesetFetail();
        fetail.setFacesetToken("token-1");
        fetail.setOuterId("outer-1");
        fetail.setDisplayName("MySet");
        fetail.setTags("tag1");
        facesets.add(fetail);
        response.setFacesets(facesets);

        assertEquals("cursor-1", response.getNext());
        assertEquals(1, response.getFacesets().size());
        assertEquals("token-1", response.getFacesets().get(0).getFacesetToken());
        assertEquals("outer-1", response.getFacesets().get(0).getOuterId());
        assertEquals("MySet", response.getFacesets().get(0).getDisplayName());
        assertEquals("tag1", response.getFacesets().get(0).getTags());
    }

    @Test
    void shouldSupportFacesetFetailEquals() {
        FacesetListResponse.FacesetFetail f1 = new FacesetListResponse.FacesetFetail();
        f1.setFacesetToken("token-1");
        FacesetListResponse.FacesetFetail f2 = new FacesetListResponse.FacesetFetail();
        f2.setFacesetToken("token-1");
        assertEquals(f1, f2);
        assertEquals(f1.hashCode(), f2.hashCode());
    }

    @Test
    void shouldExtendFaceppResponse() {
        FacesetListResponse response = new FacesetListResponse();
        assertInstanceOf(FaceppResponse.class, response);
    }
}
