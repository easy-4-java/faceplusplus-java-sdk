package com.faceplusplus.spring.boot;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FaceppPropertiesTest {

    @Test
    void shouldHaveCorrectPrefix() {
        assertEquals("faceplusplus.facepp", FaceppProperties.PREFIX);
    }

    @Test
    void shouldHaveDefaultHost() {
        FaceppProperties props = new FaceppProperties();
        assertEquals("https://api-cn.faceplusplus.com", props.getHost());
    }

    @Test
    void shouldHaveDefaultExpiration() {
        FaceppProperties props = new FaceppProperties();
        assertEquals(3600, props.getExpirationTimeInSeconds());
    }

    @Test
    void shouldAllowSettingAppId() {
        FaceppProperties props = new FaceppProperties();
        props.setAppId("test-app-id");
        assertEquals("test-app-id", props.getAppId());
    }

    @Test
    void shouldAllowSettingAppCertificate() {
        FaceppProperties props = new FaceppProperties();
        props.setAppCertificate("test-cert");
        assertEquals("test-cert", props.getAppCertificate());
    }

    @Test
    void shouldAllowSettingHost() {
        FaceppProperties props = new FaceppProperties();
        props.setHost("https://custom-host.com");
        assertEquals("https://custom-host.com", props.getHost());
    }

    @Test
    void shouldAllowSettingLoginKey() {
        FaceppProperties props = new FaceppProperties();
        props.setLoginKey("login-key");
        assertEquals("login-key", props.getLoginKey());
    }

    @Test
    void shouldAllowSettingLoginSecret() {
        FaceppProperties props = new FaceppProperties();
        props.setLoginSecret("login-secret");
        assertEquals("login-secret", props.getLoginSecret());
    }

    @Test
    void shouldAllowSettingOssRegion() {
        FaceppProperties props = new FaceppProperties();
        props.setOssRegion(7);
        assertEquals(7, props.getOssRegion());
    }

    @Test
    void shouldAllowSettingViewDimensions() {
        FaceppProperties props = new FaceppProperties();
        props.setViewWidth(1920);
        props.setViewHeight(1080);
        assertEquals(1920, props.getViewWidth());
        assertEquals(1080, props.getViewHeight());
    }

    @Test
    void shouldHaveNullDefaultsForOptionalFields() {
        FaceppProperties props = new FaceppProperties();
        assertNull(props.getAppId());
        assertNull(props.getAppCertificate());
        assertNull(props.getLoginKey());
        assertNull(props.getLoginSecret());
        assertNull(props.getOssRegion());
        assertNull(props.getViewWidth());
        assertNull(props.getViewHeight());
    }

    @Test
    void shouldSupportEqualsAndHashCode() {
        FaceppProperties props1 = new FaceppProperties();
        props1.setAppId("id1");
        FaceppProperties props2 = new FaceppProperties();
        props2.setAppId("id1");
        assertEquals(props1, props2);
        assertEquals(props1.hashCode(), props2.hashCode());
    }

    @Test
    void shouldSupportToString() {
        FaceppProperties props = new FaceppProperties();
        props.setAppId("test");
        String str = props.toString();
        assertNotNull(str);
        assertTrue(str.contains("test"));
    }
}
