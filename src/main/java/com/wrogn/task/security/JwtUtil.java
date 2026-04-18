package com.wrogn.task.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import io.jsonwebtoken.SignatureException;

import java.util.Date;

@Component
public class JwtUtil
{
    @Value("${jwt.secret}")
    private  String secret;

    @Value("${jwt.expiration:1800000}")
    private  long expiration;


    @Value("${jwt.refresh.expiration:604800000}")
    private long refreshExpiration;


    private Key getSigningKey()
    {
        return Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8));
    }
    public  String generateToken(String email,String role)
    {
        return Jwts
                .builder()
                .claim("role",role)
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+expiration ))
                .signWith(getSigningKey())
                .compact();

    }


    public String generateRefreshToken(String email)
    {
        return Jwts
                .builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+refreshExpiration))
                .signWith(getSigningKey())
                .compact();
    }

    public  String extractEmail(String token)
    {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public  boolean validateToken(String token)
    {
        try
        {
            Jwts
                    .parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);

            return true;
        }
        catch(ExpiredJwtException e)
        {
            throw new RuntimeException("Token Expired");
        }

        catch(MalformedJwtException e)
        {
            throw new RuntimeException("Invalid Token");
        }

        catch(SignatureException e)
        {
            throw new RuntimeException("Invalid Signature");
        }

        catch(Exception e)
        {
            throw new RuntimeException("Token Validation Failed");
        }

    }


    public  String  extractRole(String token)
    {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role",String.class);
    }






}
