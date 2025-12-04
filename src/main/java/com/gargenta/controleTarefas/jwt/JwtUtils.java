package com.gargenta.controleTarefas.jwt;

import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Slf4j
public class JwtUtils {

    private static final String JWT_BEARER = "Bearer ";
    private static final String JWT_AUTHORIZATION = "Authorization";
    private static final String SECRET_KEY = "0123456789-0123456789-0123456789";
    private static final long EXPIRE_DAYS = 0;
    private static final long EXPIRE_HOURS = 0;
    private static final long EXPIRE_MINUTES = 4;

    private JwtUtils() {}

    private static Key generateKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    private static Date expireDate(Date start) {
        LocalDateTime dateTime = LocalDateTime.from(start.toInstant());
        LocalDateTime end = dateTime.plusDays(EXPIRE_DAYS).plusHours(EXPIRE_HOURS).plusMinutes(EXPIRE_MINUTES);
        return Date.from(Instant.from(end));
    }
}
