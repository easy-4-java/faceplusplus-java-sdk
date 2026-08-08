package com.faceplusplus.spring.boot.req;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FaceDetectOptionsTest {

    @Test
    void shouldBuildWithDefaults() {
        FaceDetectOptions options = FaceDetectOptions.builder().build();
        assertEquals(0, options.getReturnLandmark());
        assertNull(options.getReturnAttributes());
        assertEquals(0, options.getCalculateAll());
        assertNull(options.getFaceRectangle());
        assertEquals(0, options.getBeautyScoreMin());
        assertEquals(0, options.getBeautyScoreMax());
    }

    @Test
    void shouldBuildWithCustomValues() {
        FaceDetectOptions options = FaceDetectOptions.builder()
                .returnLandmark(2)
                .returnAttributes("gender,age")
                .calculateAll(1)
                .faceRectangle("70,80,100,100")
                .beautyScoreMin(10)
                .beautyScoreMax(90)
                .build();
        assertEquals(2, options.getReturnLandmark());
        assertEquals("gender,age", options.getReturnAttributes());
        assertEquals(1, options.getCalculateAll());
        assertEquals("70,80,100,100", options.getFaceRectangle());
        assertEquals(10, options.getBeautyScoreMin());
        assertEquals(90, options.getBeautyScoreMax());
    }

    @Test
    void shouldSupportSettersAndGetters() {
        FaceDetectOptions options = FaceDetectOptions.builder().build();
        options.setFaceRectangle("10,20,50,50");
        assertEquals("10,20,50,50", options.getFaceRectangle());
    }

    @Test
    void shouldSupportEqualsAndHashCode() {
        FaceDetectOptions o1 = FaceDetectOptions.builder().calculateAll(1).build();
        FaceDetectOptions o2 = FaceDetectOptions.builder().calculateAll(1).build();
        assertEquals(o1, o2);
        assertEquals(o1.hashCode(), o2.hashCode());
    }
}
