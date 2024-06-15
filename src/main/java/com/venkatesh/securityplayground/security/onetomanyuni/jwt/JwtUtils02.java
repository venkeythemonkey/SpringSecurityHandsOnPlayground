package com.venkatesh.securityplayground.security.onetomanyuni.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtils02 implements Serializable {

    private static byte[] secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded();

    private static long expireTime = 1000 * 60 * 60;

    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles" , userDetails.getAuthorities());
        return doGenerate(userDetails.getUsername(), claims);
    }

    public String doGenerate(String username, Map<String, Object> claims){
        return Jwts.builder()
                .setIssuer("TCS")
                .setClaims(claims)
                .setSubject(username)
                .signWith(SignatureAlgorithm.HS256, Keys.hmacShaKeyFor(secretKey))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .compact();
    }

    public Claims parseAllClaims(String token){

        return Jwts.parser()
                .setSigningKey(secretKey)
                .build().parseClaimsJws(token).getBody();
    }


    public boolean isTokenExpired(){
        return false;
    }

    public boolean isTokenValid(){
        return false;
    }

    public boolean isTokenGood(String token, String username){
        if(token == null & token.isEmpty() && username == null && username.isEmpty()){
            return false;
        }
        Claims claims = parseAllClaims(token);
        return claims.getSubject().equals(username) && !claims.getExpiration().before(new Date());
    }

}
