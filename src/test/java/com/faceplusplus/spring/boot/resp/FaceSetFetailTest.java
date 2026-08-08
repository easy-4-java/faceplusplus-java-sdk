package com.faceplusplus.spring.boot.resp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FaceSetFetailTest {

    @Test
    void shouldCreateWithDefaults() {
        FaceSetFetail fetail = new FaceSetFetail();
        assertNull(fetail.getFacesetToken());
        assertNull(fetail.getTags());
        assertNull(fetail.getOuterId());
    }

    @Test
    void shouldSupportSettersAndGetters() {
        FaceSetFetail fetail = new FaceSetFetail();
        fetail.setFacesetToken("token-1");
        fetail.setTags("tag1,tag2");
        fetail.setOuterId("outer-1");
        assertEquals("token-1", fetail.getFacesetToken());
        assertEquals("tag1,tag2", fetail.getTags());
        assertEquals("outer-1", fetail.getOuterId());
    }

    @Test
    void shouldSupportEqualsAndHashCode() {
        FaceSetFetail f1 = new FaceSetFetail();
        f1.setFacesetToken("token-1");
        FaceSetFetail f2 = new FaceSetFetail();
        f2.setFacesetToken("token-1");
        assertEquals(f1, f2);
        assertEquals(f1.hashCode(), f2.hashCode());
    }

    @Test
    void shouldSupportToString() {
        FaceSetFetail fetail = new FaceSetFetail();
        fetail.setFacesetToken("token-1");
        assertNotNull(fetail.toString());
        assertTrue(fetail.toString().contains("token-1"));
    }
}
