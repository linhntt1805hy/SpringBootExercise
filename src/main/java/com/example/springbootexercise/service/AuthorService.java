package com.example.springbootexercise.service;

import com.example.springbootexercise.dto.AuthorDto;

import java.util.List;

public interface AuthorService {
    AuthorDto createAuthor(AuthorDto authorDto);
    List<AuthorDto> getAllAuthors();
}
