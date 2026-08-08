package com.faceplusplus.spring.boot.req;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SkinAnalyzeOptionsTest {

    @Test
    void shouldBuildWithDefaults() {
        SkinAnalyzeOptions options = SkinAnalyzeOptions.builder().build();
        assertEquals(0, options.getFaceQualityControl());
        assertEquals(0, options.getReturnRectConfidence());
        assertNull(options.getReturnMaps());
    }

    @Test
    void shouldBuildWithCustomValues() {
        SkinAnalyzeOptions options = SkinAnalyzeOptions.builder()
                .faceQualityControl(1)
                .returnRectConfidence(1)
                .returnMaps("red_area, brown_area")
                .build();
        assertEquals(1, options.getFaceQualityControl());
        assertEquals(1, options.getReturnRectConfidence());
        assertEquals("red_area, brown_area", options.getReturnMaps());
    }

    @Test
    void shouldSupportSettersAndGetters() {
        SkinAnalyzeOptions options = SkinAnalyzeOptions.builder().build();
        options.setFaceQualityControl(1);
        options.setReturnRectConfidence(1);
        options.setReturnMaps("texture_enhanced_pores");
        assertEquals(1, options.getFaceQualityControl());
        assertEquals(1, options.getReturnRectConfidence());
        assertEquals("texture_enhanced_pores", options.getReturnMaps());
    }

    @Test
    void shouldSupportEqualsAndHashCode() {
        SkinAnalyzeOptions o1 = SkinAnalyzeOptions.builder().faceQualityControl(1).build();
        SkinAnalyzeOptions o2 = SkinAnalyzeOptions.builder().faceQualityControl(1).build();
        assertEquals(o1, o2);
        assertEquals(o1.hashCode(), o2.hashCode());
    }
}
