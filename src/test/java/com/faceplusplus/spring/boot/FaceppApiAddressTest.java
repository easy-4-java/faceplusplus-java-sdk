package com.faceplusplus.spring.boot;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

class FaceppApiAddressTest {

    @Test
    void shouldContainExpectedNumberOfEntries() {
        FaceppApiAddress[] values = FaceppApiAddress.values();
        assertTrue(values.length >= 25, "Expected at least 25 API addresses");
    }

    @ParameterizedTest
    @EnumSource(FaceppApiAddress.class)
    void shouldHaveNonNullOpt(FaceppApiAddress address) {
        assertNotNull(address.getOpt());
        assertFalse(address.getOpt().isEmpty());
    }

    @ParameterizedTest
    @EnumSource(FaceppApiAddress.class)
    void shouldHaveNonNullMethod(FaceppApiAddress address) {
        assertNotNull(address.getMethod());
    }

    @ParameterizedTest
    @EnumSource(FaceppApiAddress.class)
    void shouldHaveNonNullUrl(FaceppApiAddress address) {
        assertNotNull(address.getUrl());
        assertFalse(address.getUrl().isEmpty());
    }

    @Test
    void shouldResolveUrlWithArguments() {
        String resolved = FaceppApiAddress.ACQUIRE_RESOURCE_ID.getUrl();
        assertNotNull(resolved);
    }

    @Test
    void shouldResolveTemplateUrlWithArguments() {
        String resolved = FaceppApiAddress.STOP_CLOUD_RECORDING.getUrl("app123", "res456", "sid789", "mix");
        assertNotNull(resolved);
        assertTrue(resolved.contains("app123"));
        assertTrue(resolved.contains("res456"));
    }

    @Test
    void shouldReturnPostMethodForFaceDetect() {
        assertEquals(RequestMethod.POST, FaceppApiAddress.FACE_DETECT.getMethod());
    }

    @Test
    void shouldReturnCorrectOptForFaceDetect() {
        assertEquals("Face Detect", FaceppApiAddress.FACE_DETECT.getOpt());
    }

    @Test
    void shouldContainFaceSetApis() {
        assertNotNull(FaceppApiAddress.FACESET_CREATE);
        assertNotNull(FaceppApiAddress.FACESET_DELETE);
        assertNotNull(FaceppApiAddress.FACESET_UPDATE);
        assertNotNull(FaceppApiAddress.FACESET_LIST);
        assertNotNull(FaceppApiAddress.FACESET_DETAIL);
    }

    @Test
    void shouldContainSkinAnalyzeApis() {
        assertNotNull(FaceppApiAddress.FACE_SKIN_ANALYZE);
        assertNotNull(FaceppApiAddress.FACE_SKIN_ANALYZE_ADVANCED);
        assertNotNull(FaceppApiAddress.FACE_SKIN_ANALYZE_PRO);
    }
}
