package com.example.springbootexercise.mapper;

import com.example.springbootexercise.dto.AuthorDto;
import com.example.springbootexercise.entity.Author;

import java.util.Set;
import java.util.stream.Collectors;

public class AuthorMapper {
    public static AuthorDto mapToAuthorDto(Author author) {
        return new AuthorDto(
                author.getId(),
                author.getName(),
                author.getDateOfBirth(),
                author.getBiography()
        );
    }

/*    public Author mapToAuthor(AuthorDto authorDto) {
        return new Author(
                authorDto.getId(),
                authorDto.getName(),
                authorDto.getDateOfBirth(),
                authorDto.getBiography()
        );
    }*/

    public static Set<AuthorDto> toDtoSet(Set<Author> authors) {
        return authors.stream()
                .map(AuthorMapper::mapToAuthorDto)
                .collect(Collectors.toSet());
    }
}
