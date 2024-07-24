package com.example.springbootexercise.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/authors")
public class AuthorC {
    @GetMapping
    public String viewAuthors() {
        return "author";
    }
}
