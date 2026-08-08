package com.faceplusplus.spring.boot;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RequestMethodTest {

    @Test
    void shouldContainAllExpectedValues() {
        RequestMethod[] values = RequestMethod.values();
        assertEquals(8, values.length);
    }

    @Test
    void shouldResolveGetByValueOf() {
        assertEquals(RequestMethod.GET, RequestMethod.valueOf("GET"));
    }

    @Test
    void shouldResolvePostByValueOf() {
        assertEquals(RequestMethod.POST, RequestMethod.valueOf("POST"));
    }

    @Test
    void shouldResolveAllMethodsByValueOf() {
        for (RequestMethod method : RequestMethod.values()) {
            assertEquals(method, RequestMethod.valueOf(method.name()));
        }
    }
}
