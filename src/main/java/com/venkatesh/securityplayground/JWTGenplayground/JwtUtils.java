package com.venkatesh.securityplayground.JWTGenplayground;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils implements Serializable {

    private final byte[] secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded();

    private final long expiretime =  60 * 1000;

    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles" , userDetails.getAuthorities());
        return doGenerateToken(claims, userDetails.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiretime) )
                .signWith(SignatureAlgorithm.HS256, Keys.hmacShaKeyFor(secretKey))
                .compact();

    }

    public boolean isTokenExpired(String token){
        Claims claims = parseClaims(token);
        return claims.getExpiration().before(new Date());

    }

    public Claims parseClaims(String token){
        return Jwts.parser()
                .setSigningKey(secretKey)
                .build().parseClaimsJws(token).getBody();
    }

    public boolean isTokenValid(String token, String username){
        Claims claims = parseClaims(token);
        return username.equals(claims.getSubject());
    }

}
