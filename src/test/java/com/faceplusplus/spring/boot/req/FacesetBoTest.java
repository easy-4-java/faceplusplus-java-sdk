package com.faceplusplus.spring.boot.req;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FacesetBoTest {

    @Test
    void shouldCreateWithDefaults() {
        FacesetBo bo = new FacesetBo();
        assertNull(bo.getDisplayName());
        assertNull(bo.getOuterId());
        assertNull(bo.getTags());
        assertNull(bo.getFaceTokens());
        assertNull(bo.getUserData());
        assertEquals(0, bo.getForceMerge());
    }

    @Test
    void shouldSupportSettersAndGetters() {
        FacesetBo bo = new FacesetBo();
        bo.setDisplayName("TestSet");
        bo.setOuterId("outer-1");
        bo.setTags("tag1,tag2");
        bo.setFaceTokens("token1,token2");
        bo.setUserData("some data");
        bo.setForceMerge(1);

        assertEquals("TestSet", bo.getDisplayName());
        assertEquals("outer-1", bo.getOuterId());
        assertEquals("tag1,tag2", bo.getTags());
        assertEquals("token1,token2", bo.getFaceTokens());
        assertEquals("some data", bo.getUserData());
        assertEquals(1, bo.getForceMerge());
    }

    @Test
    void shouldSupportEqualsAndHashCode() {
        FacesetBo bo1 = new FacesetBo();
        bo1.setDisplayName("Test");
        FacesetBo bo2 = new FacesetBo();
        bo2.setDisplayName("Test");
        assertEquals(bo1, bo2);
        assertEquals(bo1.hashCode(), bo2.hashCode());
    }

    @Test
    void shouldSupportToString() {
        FacesetBo bo = new FacesetBo();
        bo.setDisplayName("Test");
        assertNotNull(bo.toString());
        assertTrue(bo.toString().contains("Test"));
    }
}
