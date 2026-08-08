package com.faceplusplus.spring.boot.resp;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FacesetDetailResponseTest {

    @Test
    void shouldCreateWithDefaults() {
        FacesetDetailResponse response = new FacesetDetailResponse();
        assertNull(response.getFacesetToken());
        assertNull(response.getDisplayName());
        assertNull(response.getFaceCount());
        assertNull(response.getFaceTokens());
        assertNull(response.getTags());
        assertNull(response.getOuterId());
        assertNull(response.getUserData());
        assertNull(response.getNext());
    }

    @Test
    void shouldSupportSettersAndGetters() {
        FacesetDetailResponse response = new FacesetDetailResponse();
        response.setFacesetToken("token-1");
        response.setDisplayName("MySet");
        response.setFaceCount(3);
        List<String> tokens = Arrays.asList("t1", "t2", "t3");
        response.setFaceTokens(tokens);
        response.setTags("tag1,tag2");
        response.setOuterId("outer-1");
        response.setUserData("data");
        response.setNext("cursor-1");

        assertEquals("token-1", response.getFacesetToken());
        assertEquals("MySet", response.getDisplayName());
        assertEquals(3, response.getFaceCount());
        assertEquals(3, response.getFaceTokens().size());
        assertEquals("tag1,tag2", response.getTags());
        assertEquals("outer-1", response.getOuterId());
        assertEquals("data", response.getUserData());
        assertEquals("cursor-1", response.getNext());
    }

    @Test
    void shouldExtendFaceppResponse() {
        FacesetDetailResponse response = new FacesetDetailResponse();
        assertInstanceOf(FaceppResponse.class, response);
    }
}
