package com.venkatesh.securityplayground.security.manytomany_enum.model;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;

import java.security.Key;
import java.util.*;

import static io.jsonwebtoken.Jwts.SIG.HS256;

public class Main {
    public static void main(String[] args) {

        UserModel userModel = new UserModel();
        userModel.setEmail("venkat@gmail.com");
        userModel.setPassword("12344");
        Set<RoleModel> roleModels = new HashSet<>();
        roleModels.add(RoleModel.valueOf("ADMIN"));
        roleModels.add(RoleModel.valueOf("USER"));
        userModel.setRoles(roleModels);
        System.out.println(userModel.getAuthorities());
        System.out.println(userModel.getEmail());
        System.out.println(userModel.getPassword());
        System.out.println(generateToken(userModel));
    }


    private static final byte[] secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded();

    public static String generateToken(UserModel userModel){
        Map<String, Object> claim = new HashMap<>();
        Set<String> roles = new HashSet<>();
        for(GrantedAuthority claimroles : userModel.getAuthorities()){
            roles.add(claimroles.toString());
        }

        claim.put("roles", roles);
        return Jwts.builder()
                .claims(claim)
                .subject(userModel.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

}
