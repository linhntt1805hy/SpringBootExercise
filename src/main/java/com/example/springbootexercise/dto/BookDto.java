package com.example.springbootexercise.dto;

import com.example.springbootexercise.entity.Author;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class BookDto {
    private Long id;

    private String code;

    private String title;

    private int quantity;

    private String description;

    private Set<AuthorDto> authors;

    private List<String> authorNames;

    public BookDto(Long id, String code, String title, int quantity, String description, Set<AuthorDto> authors) {
        this.id = id;
        this.code = code;
        this.title = title;
        this.quantity = quantity;
        this.description = description;
        this.authors = authors;
    }
}
