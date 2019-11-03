package com.tjlee.jwtsecurity.controller;

import com.tjlee.jwtsecurity.model.JwtUser;
import com.tjlee.jwtsecurity.security.JwtTokenGenerator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/token")
public class TokenController {

    private final JwtTokenGenerator jwtTokenGenerator;

    public TokenController(JwtTokenGenerator jwtTokenGenerator) {
        this.jwtTokenGenerator = jwtTokenGenerator;
    }

    @PostMapping
    public String generate(@RequestBody final JwtUser jwtUser){
        return jwtTokenGenerator.generate(jwtUser);
    }
}
