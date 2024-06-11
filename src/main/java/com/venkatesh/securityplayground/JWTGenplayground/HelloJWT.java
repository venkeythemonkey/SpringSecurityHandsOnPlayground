package com.venkatesh.securityplayground.JWTGenplayground;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

public class HelloJWT {
    public static void main(String[] args) throws InterruptedException {
        JwtUtils jwtUtils = new JwtUtils();
        UserDetails userDetails = User.builder()
                                .username("Venkatesh")
                                .password("12345")
                                        .roles("USER")
                                                .build();
        String jwtToken = jwtUtils.generateToken(userDetails);
        System.out.println("Below is the token");
        System.out.println(jwtToken);

        Claims claims = jwtUtils.parseClaims(jwtToken);
        System.out.println(claims.getSubject());
        System.out.println(claims.get("roles"));
        System.out.println(claims.getIssuedAt());

        System.out.println("is token expired?");
        System.out.println(jwtUtils.isTokenExpired(jwtToken));

        System.out.println("is token valid?");
        System.out.println(jwtUtils.isTokenValid(jwtToken, userDetails.getUsername()));

        Thread.sleep(30000);
        System.out.println(new Date(System.currentTimeMillis()));
        System.out.println("Is token expired? (after sleep)");
        System.out.println(jwtUtils.isTokenExpired(jwtToken));

        System.out.println("Is token valid? (after sleep)");
        System.out.println(jwtUtils.isTokenValid(jwtToken, userDetails.getUsername()));


        Thread.sleep(30000);
        System.out.println(new Date(System.currentTimeMillis()));
        System.out.println("Is token expired? (after sleep)");
        System.out.println(jwtUtils.isTokenExpired(jwtToken));

        System.out.println("Is token valid? (after sleep)");
        System.out.println(jwtUtils.isTokenValid(jwtToken, userDetails.getUsername()));


    }


}
