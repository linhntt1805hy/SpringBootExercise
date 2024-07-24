package com.example.springbootexercise.service.impl;

import com.example.springbootexercise.dto.AuthorDto;
import com.example.springbootexercise.dto.BookDto;
import com.example.springbootexercise.entity.Author;
import com.example.springbootexercise.entity.Book;
import com.example.springbootexercise.exception.ConflictException;
import com.example.springbootexercise.exception.NoDataFoundException;
import com.example.springbootexercise.mapper.BookMapper;
import com.example.springbootexercise.repository.AuthorRepository;
import com.example.springbootexercise.repository.BookRepository;
import com.example.springbootexercise.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    @Transactional
    @Override
    public BookDto createBook(BookDto bookDto) {
        Optional<Book> checkBookCode = bookRepository.findByCode(bookDto.getCode());
        if (checkBookCode.isPresent()) {
            throw new ConflictException("Code existed");
        } else {
            Book book = BookMapper.mapToBook(bookDto);
            Set<Author> authors = new HashSet<>();
            for (int i = 0; i < bookDto.getAuthorNames().size(); i++) {
                Optional<Author> author = authorRepository.findByName(bookDto.getAuthorNames().get(i));
                if (author.isPresent()) {
                    authors.add(author.get());
                } else {
                    authorRepository.save(new Author(bookDto.getAuthorNames().get(i)));
                    authors.add(authorRepository.findByName(bookDto.getAuthorNames().get(i)).get());
                }
            }
            book.setAuthors(authors);
            bookRepository.save(book);
            return BookMapper.mapToBookDto(book);
        }
    }

    @Override
    public List<BookDto> getAllBooks() {
        List<BookDto> bookDtos = new ArrayList<>();
        List<Book> books = bookRepository.findAll();
        if (books.size() > 0) {
            for (Book book: books) {
                BookDto bookDto = BookMapper.mapToBookDto(book);
                bookDtos.add(bookDto);
            }
            return bookDtos;
        }
        throw new NoDataFoundException("No books data found");
    }
}
