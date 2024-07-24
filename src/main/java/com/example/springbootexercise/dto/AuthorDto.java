package com.example.springbootexercise.dto;

import com.example.springbootexercise.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@Builder
public class AuthorDto {
    private Long id;

    private String name;

    private LocalDate dateOfBirth;

    private String biography;

}
