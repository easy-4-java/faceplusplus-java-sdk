package com.faceplusplus.spring.boot.resp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FaceLandmarkTest {

    @Test
    void shouldCreateWithDefaults() {
        FaceLandmark landmark = new FaceLandmark();
        assertNull(landmark.getTop());
        assertNull(landmark.getLeft());
        assertNull(landmark.getWidth());
        assertNull(landmark.getHeight());
    }

    @Test
    void shouldSupportSettersAndGetters() {
        FaceLandmark landmark = new FaceLandmark();
        landmark.setTop(5);
        landmark.setLeft(15);
        landmark.setWidth(50);
        landmark.setHeight(60);
        assertEquals(5, landmark.getTop());
        assertEquals(15, landmark.getLeft());
        assertEquals(50, landmark.getWidth());
        assertEquals(60, landmark.getHeight());
    }

    @Test
    void shouldSupportEqualsAndHashCode() {
        FaceLandmark l1 = new FaceLandmark();
        l1.setTop(5);
        FaceLandmark l2 = new FaceLandmark();
        l2.setTop(5);
        assertEquals(l1, l2);
        assertEquals(l1.hashCode(), l2.hashCode());
    }
}
