package com.example.springbootexercise.controller;

import com.example.springbootexercise.dto.AuthorDto;
import com.example.springbootexercise.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping()
    public ResponseEntity<List<AuthorDto>> getAllAuthors() {
        List<AuthorDto> authorDtos = authorService.getAllAuthors();
        return new ResponseEntity<>(authorDtos, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto authorDto) {
        AuthorDto dto = authorService.createAuthor(authorDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
}
