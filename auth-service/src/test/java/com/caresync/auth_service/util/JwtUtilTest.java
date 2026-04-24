package com.caresync.auth_service.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class JwtUtilTest {

    @Test
    void generateTokenReturnsJwtWhenSecretIsStrongEnough() {
        JwtUtil jwtUtil = new JwtUtil("ChangeMeCareSyncAuthSecretKey2026DevOnly!");

        String token = jwtUtil.generateToken("doctor@caresync.com", "ADMIN");

        assertNotNull(token);
        assertEquals(3, token.split("\\.").length);
    }

    @Test
    void constructorRejectsShortSecrets() {
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> new JwtUtil("too-short-secret"));

        assertTrue(exception.getMessage().contains("32 characters"));
    }
}
