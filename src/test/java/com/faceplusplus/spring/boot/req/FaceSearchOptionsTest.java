package com.faceplusplus.spring.boot.req;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FaceSearchOptionsTest {

    @Test
    void shouldBuildWithDefaults() {
        FaceSearchOptions options = FaceSearchOptions.builder().build();
        assertEquals(0, options.getReturnLandmark());
        assertNull(options.getReturnAttributes());
        assertEquals(0, options.getBeautyScoreMin());
        assertEquals(0, options.getBeautyScoreMax());
    }

    @Test
    void shouldBuildWithCustomValues() {
        FaceSearchOptions options = FaceSearchOptions.builder()
                .returnLandmark(1)
                .returnAttributes("gender")
                .beautyScoreMin(5)
                .beautyScoreMax(95)
                .build();
        assertEquals(1, options.getReturnLandmark());
        assertEquals("gender", options.getReturnAttributes());
        assertEquals(5, options.getBeautyScoreMin());
        assertEquals(95, options.getBeautyScoreMax());
    }

    @Test
    void shouldSupportEqualsAndHashCode() {
        FaceSearchOptions o1 = FaceSearchOptions.builder().returnLandmark(2).build();
        FaceSearchOptions o2 = FaceSearchOptions.builder().returnLandmark(2).build();
        assertEquals(o1, o2);
        assertEquals(o1.hashCode(), o2.hashCode());
    }
}
