package com.tjlee.jwtsecurity.security;

import com.tjlee.jwtsecurity.model.JwtUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenGenerator {

    public String generate(JwtUser jwtUser) {

        Claims claims = Jwts.claims()
                .setSubject(jwtUser.getUserName());
        claims.put("userId", jwtUser.getId());
        claims.put("role", jwtUser.getRole());
        return Jwts.builder().setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, "youtube").compact();
    }
}
