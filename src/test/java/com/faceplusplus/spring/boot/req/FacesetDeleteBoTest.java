package com.faceplusplus.spring.boot.req;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FacesetDeleteBoTest {

    @Test
    void shouldCreateWithDefaults() {
        FacesetDeleteBo bo = new FacesetDeleteBo();
        assertNull(bo.getOuterId());
        assertNull(bo.getFaceTokens());
        assertEquals(1, bo.getCheckEmpty());
    }

    @Test
    void shouldSupportSettersAndGetters() {
        FacesetDeleteBo bo = new FacesetDeleteBo();
        bo.setOuterId("outer-1");
        bo.setFaceTokens("token1,token2");
        bo.setCheckEmpty(0);
        assertEquals("outer-1", bo.getOuterId());
        assertEquals("token1,token2", bo.getFaceTokens());
        assertEquals(0, bo.getCheckEmpty());
    }

    @Test
    void shouldSupportEqualsAndHashCode() {
        FacesetDeleteBo bo1 = new FacesetDeleteBo();
        bo1.setOuterId("outer-1");
        FacesetDeleteBo bo2 = new FacesetDeleteBo();
        bo2.setOuterId("outer-1");
        assertEquals(bo1, bo2);
        assertEquals(bo1.hashCode(), bo2.hashCode());
    }
}
