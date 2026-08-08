package com.faceplusplus.spring.boot.req;

import com.faceplusplus.spring.boot.FaceppApiAddress;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SkinAnalyzeTypeTest {

    @Test
    void shouldContainThreeTypes() {
        SkinAnalyzeType[] values = SkinAnalyzeType.values();
        assertEquals(3, values.length);
    }

    @Test
    void shouldMapBasicToSkinAnalyze() {
        assertEquals(FaceppApiAddress.FACE_SKIN_ANALYZE, SkinAnalyzeType.BASIC.getApiAddress());
    }

    @Test
    void shouldMapAdvancedToSkinAnalyzeAdvanced() {
        assertEquals(FaceppApiAddress.FACE_SKIN_ANALYZE_ADVANCED, SkinAnalyzeType.ADVANCED.getApiAddress());
    }

    @Test
    void shouldMapProToSkinAnalyzePro() {
        assertEquals(FaceppApiAddress.FACE_SKIN_ANALYZE_PRO, SkinAnalyzeType.PRO.getApiAddress());
    }

    @Test
    void shouldResolveByValueOf() {
        assertEquals(SkinAnalyzeType.BASIC, SkinAnalyzeType.valueOf("BASIC"));
        assertEquals(SkinAnalyzeType.ADVANCED, SkinAnalyzeType.valueOf("ADVANCED"));
        assertEquals(SkinAnalyzeType.PRO, SkinAnalyzeType.valueOf("PRO"));
    }
}
