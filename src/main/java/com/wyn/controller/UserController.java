package com.wyn.controller;

import com.wyn.annotation.GetMapping;
import com.wyn.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @GetMapping("/users")
    public List<String> getUsers() {
        return List.of(
                "Edi",
                "Budi",
                "Andi"
        );
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }
}
