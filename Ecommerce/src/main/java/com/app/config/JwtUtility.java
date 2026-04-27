package com.app.config;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

import java.util.Date;

@Component
public class JwtUtility {

    private static final String SECRET = "sshuhksdht9475394ygur638tgeu0w498934gu34877y7834yu";

    private SecretKey getKey(){
        return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }



    public String generateToken(String username){
      return   Jwts.builder()
                .subject(username)
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+1000*60*5))
                .compact();

    }

    public Claims extractClaims(String token){
       return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isValid(token));
    }

    public String extractUsername(String token){
        return extractClaims(token).getSubject();
    }

    public boolean isValid(String token){
        return extractClaims(token).getExpiration().after(new Date());
    }

}
