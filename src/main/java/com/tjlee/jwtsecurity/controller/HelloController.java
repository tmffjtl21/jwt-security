package com.tjlee.jwtsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/rest/hello")
public class HelloController {

    @GetMapping
    public String hello(Principal principal){
        return "hello" + principal.getName();
    }
}
