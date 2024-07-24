package com.example.springbootexercise.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/books")
public class BookC {
    @GetMapping
    public String viewBooks(Model model) {
        // Add attributes to the model if needed
        return "book";
    }
}
