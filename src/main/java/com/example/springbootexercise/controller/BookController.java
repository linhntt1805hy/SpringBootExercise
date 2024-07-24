package com.example.springbootexercise.controller;

import com.example.springbootexercise.dto.BookDto;
import com.example.springbootexercise.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {
    public final BookService bookService;

    @GetMapping
    public ResponseEntity<List<BookDto>> getAllBooks() {
        List<BookDto> bookDtos = bookService.getAllBooks();
        return new ResponseEntity<>(bookDtos, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<BookDto> createBook(@RequestBody BookDto bookDto) {
        BookDto dto = bookService.createBook(bookDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
}
