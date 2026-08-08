package com.faceplusplus.spring.boot.resp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FaceRectangleTest {

    @Test
    void shouldCreateWithDefaults() {
        FaceRectangle rect = new FaceRectangle();
        assertNull(rect.getTop());
        assertNull(rect.getLeft());
        assertNull(rect.getWidth());
        assertNull(rect.getHeight());
    }

    @Test
    void shouldSupportSettersAndGetters() {
        FaceRectangle rect = new FaceRectangle();
        rect.setTop(10);
        rect.setLeft(20);
        rect.setWidth(100);
        rect.setHeight(120);
        assertEquals(10, rect.getTop());
        assertEquals(20, rect.getLeft());
        assertEquals(100, rect.getWidth());
        assertEquals(120, rect.getHeight());
    }

    @Test
    void shouldSupportEqualsAndHashCode() {
        FaceRectangle r1 = new FaceRectangle();
        r1.setTop(10);
        r1.setLeft(20);
        FaceRectangle r2 = new FaceRectangle();
        r2.setTop(10);
        r2.setLeft(20);
        assertEquals(r1, r2);
        assertEquals(r1.hashCode(), r2.hashCode());
    }

    @Test
    void shouldSupportToString() {
        FaceRectangle rect = new FaceRectangle();
        rect.setTop(10);
        assertNotNull(rect.toString());
        assertTrue(rect.toString().contains("10"));
    }
}
