package com.example.springbootexercise.mapper;

import com.example.springbootexercise.dto.BookDto;
import com.example.springbootexercise.entity.Book;

public class BookMapper {
    public static BookDto mapToBookDto(Book book) {
        return new BookDto(
                book.getId(),
                book.getCode(),
                book.getTitle(),
                book.getQuantity(),
                book.getDescription(),
                AuthorMapper.toDtoSet(book.getAuthors())
        );
    }

    public static Book mapToBook(BookDto bookDto) {
        return new Book(
          bookDto.getId(),
          bookDto.getCode(),
          bookDto.getTitle(),
          bookDto.getQuantity(),
          bookDto.getDescription()
        );
    }
}
