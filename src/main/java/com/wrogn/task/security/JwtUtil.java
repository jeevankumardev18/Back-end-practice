package com.wrogn.task.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;


public class JwtUtil
{
    private static final String SECRET="mysecretkeymysecretkeymysecretkeyjwtauthenticationforspringsecurity";
    private static final Key KEY=Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    public static String generateToken(String email)
    {
        return Jwts
                .builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+86400000))
                .signWith(KEY)
                .compact();

    }

    public static String extractEmail(String token)
    {
        return Jwts
                .parserBuilder()
                .setSigningKey(KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public static boolean validateToken(String token)
    {
        try
        {
            Jwts
                    .parserBuilder()
                    .setSigningKey(KEY)
                    .build()
                    .parseClaimsJws(token);

            return true;
        } catch (Exception e) {
             return false;
        }
    }







}
