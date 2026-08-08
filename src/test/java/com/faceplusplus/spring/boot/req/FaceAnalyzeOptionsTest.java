package com.faceplusplus.spring.boot.req;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FaceAnalyzeOptionsTest {

    @Test
    void shouldBuildWithDefaults() {
        FaceAnalyzeOptions options = FaceAnalyzeOptions.builder().build();
        assertEquals(0, options.getReturnLandmark());
        assertNull(options.getReturnAttributes());
        assertEquals(0, options.getBeautyScoreMin());
        assertEquals(0, options.getBeautyScoreMax());
    }

    @Test
    void shouldBuildWithCustomValues() {
        FaceAnalyzeOptions options = FaceAnalyzeOptions.builder()
                .returnLandmark(2)
                .returnAttributes("gender,age,beauty")
                .beautyScoreMin(10)
                .beautyScoreMax(90)
                .build();
        assertEquals(2, options.getReturnLandmark());
        assertEquals("gender,age,beauty", options.getReturnAttributes());
        assertEquals(10, options.getBeautyScoreMin());
        assertEquals(90, options.getBeautyScoreMax());
    }

    @Test
    void shouldSupportSettersAndGetters() {
        FaceAnalyzeOptions options = FaceAnalyzeOptions.builder().build();
        options.setReturnLandmark(1);
        options.setReturnAttributes("emotion");
        options.setBeautyScoreMin(5);
        options.setBeautyScoreMax(95);
        assertEquals(1, options.getReturnLandmark());
        assertEquals("emotion", options.getReturnAttributes());
        assertEquals(5, options.getBeautyScoreMin());
        assertEquals(95, options.getBeautyScoreMax());
    }

    @Test
    void shouldSupportEqualsAndHashCode() {
        FaceAnalyzeOptions o1 = FaceAnalyzeOptions.builder().returnLandmark(1).build();
        FaceAnalyzeOptions o2 = FaceAnalyzeOptions.builder().returnLandmark(1).build();
        assertEquals(o1, o2);
        assertEquals(o1.hashCode(), o2.hashCode());
    }

    @Test
    void shouldSupportToString() {
        FaceAnalyzeOptions options = FaceAnalyzeOptions.builder().build();
        assertNotNull(options.toString());
    }
}
