package com.faceplusplus.spring.boot;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FaceppConstantTest {

    @Test
    void shouldHaveCorrectChannelUserUrl() {
        assertNotNull(FaceppConstant.URL_CHANNEL_USER);
        assertTrue(FaceppConstant.URL_CHANNEL_USER.contains("{0}"));
        assertTrue(FaceppConstant.URL_CHANNEL_USER.contains("{1}"));
    }

    @Test
    void shouldHaveCorrectRuleUrl() {
        assertEquals("https://api.agora.io/dev/v1/kicking-rule", FaceppConstant.URL_RULE);
    }

    @Test
    void shouldHaveCorrectRecordingUid() {
        assertEquals("10", FaceppConstant.RECORDING_UID);
    }

    @Test
    void shouldHaveCorrectVideoPath() {
        assertEquals("video", FaceppConstant.VEIDO_PAHT);
    }
}
