package com.faceplusplus.spring.boot.req;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FacesetUpdateBoTest {

    @Test
    void shouldCreateWithDefaults() {
        FacesetUpdateBo bo = new FacesetUpdateBo();
        assertNull(bo.getFaceToken());
        assertNull(bo.getOuterId());
        assertNull(bo.getNewOuterId());
        assertNull(bo.getDisplayName());
        assertNull(bo.getTags());
        assertNull(bo.getUserData());
    }

    @Test
    void shouldSupportSettersAndGetters() {
        FacesetUpdateBo bo = new FacesetUpdateBo();
        bo.setFaceToken("token-1");
        bo.setOuterId("outer-1");
        bo.setNewOuterId("new-outer-1");
        bo.setDisplayName("New Name");
        bo.setTags("tag1,tag2");
        bo.setUserData("data");

        assertEquals("token-1", bo.getFaceToken());
        assertEquals("outer-1", bo.getOuterId());
        assertEquals("new-outer-1", bo.getNewOuterId());
        assertEquals("New Name", bo.getDisplayName());
        assertEquals("tag1,tag2", bo.getTags());
        assertEquals("data", bo.getUserData());
    }

    @Test
    void shouldSupportEqualsAndHashCode() {
        FacesetUpdateBo bo1 = new FacesetUpdateBo();
        bo1.setFaceToken("token-1");
        FacesetUpdateBo bo2 = new FacesetUpdateBo();
        bo2.setFaceToken("token-1");
        assertEquals(bo1, bo2);
        assertEquals(bo1.hashCode(), bo2.hashCode());
    }
}
