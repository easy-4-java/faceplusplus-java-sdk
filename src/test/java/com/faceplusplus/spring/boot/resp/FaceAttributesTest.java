package com.faceplusplus.spring.boot.resp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FaceAttributesTest {

    @Test
    void shouldCreateWithDefaults() {
        FaceAttributes attrs = new FaceAttributes();
        assertNull(attrs.getGender());
        assertNull(attrs.getAge());
        assertNull(attrs.getSmile());
        assertNull(attrs.getHeadpose());
        assertNull(attrs.getBlur());
    }

    @Test
    void shouldSupportSettersAndGetters() {
        FaceAttributes attrs = new FaceAttributes();

        FaceAttributes.FaceAttrValue gender = new FaceAttributes.FaceAttrValue();
        gender.setValue("Male");
        attrs.setGender(gender);
        assertEquals("Male", attrs.getGender().getValue());

        FaceAttributes.FaceAttrValue age = new FaceAttributes.FaceAttrValue();
        age.setValue("25");
        attrs.setAge(age);
        assertEquals("25", attrs.getAge().getValue());
    }

    @Test
    void shouldSupportSmileAttribute() {
        FaceAttributes.FaceAttrSmile smile = new FaceAttributes.FaceAttrSmile();
        smile.setValue(85.5f);
        smile.setThreshold(50.0f);
        assertEquals(85.5f, smile.getValue());
        assertEquals(50.0f, smile.getThreshold());
    }

    @Test
    void shouldSupportHeadposeAttribute() {
        FaceAttributes.FaceAttrHeadpose headpose = new FaceAttributes.FaceAttrHeadpose();
        headpose.setYaw(10.5f);
        headpose.setPitch(-5.0f);
        headpose.setRoll(2.0f);
        assertEquals(10.5f, headpose.getYaw());
        assertEquals(-5.0f, headpose.getPitch());
        assertEquals(2.0f, headpose.getRoll());
    }

    @Test
    void shouldSupportBlurAttribute() {
        FaceAttributes.FaceAttrBlur blur = new FaceAttributes.FaceAttrBlur();
        blur.setYaw(1.0f);
        blur.setPitch(2.0f);
        blur.setRoll(3.0f);
        assertEquals(1.0f, blur.getYaw());
        assertEquals(2.0f, blur.getPitch());
        assertEquals(3.0f, blur.getRoll());
    }

    @Test
    void shouldSupportAttrValueEquals() {
        FaceAttributes.FaceAttrValue v1 = new FaceAttributes.FaceAttrValue();
        v1.setValue("Male");
        FaceAttributes.FaceAttrValue v2 = new FaceAttributes.FaceAttrValue();
        v2.setValue("Male");
        assertEquals(v1, v2);
        assertEquals(v1.hashCode(), v2.hashCode());
    }

    @Test
    void shouldSupportSmileEquals() {
        FaceAttributes.FaceAttrSmile s1 = new FaceAttributes.FaceAttrSmile();
        s1.setValue(80.0f);
        FaceAttributes.FaceAttrSmile s2 = new FaceAttributes.FaceAttrSmile();
        s2.setValue(80.0f);
        assertEquals(s1, s2);
        assertEquals(s1.hashCode(), s2.hashCode());
    }
}
