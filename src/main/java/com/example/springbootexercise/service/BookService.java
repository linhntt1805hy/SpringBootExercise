package com.example.springbootexercise.service;

import com.example.springbootexercise.dto.BookDto;

import java.util.List;

public interface BookService {
    BookDto createBook(BookDto bookDto);
    List<BookDto> getAllBooks();
}
