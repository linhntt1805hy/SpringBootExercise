package com.example.springbootexercise.service.impl;

import com.example.springbootexercise.dto.AuthorDto;
import com.example.springbootexercise.entity.Author;
import com.example.springbootexercise.exception.ConflictException;
import com.example.springbootexercise.exception.NoDataFoundException;
import com.example.springbootexercise.mapper.AuthorMapper;
import com.example.springbootexercise.repository.AuthorRepository;
import com.example.springbootexercise.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    @Override
    public AuthorDto createAuthor(AuthorDto authorDto) {
        Optional<Author> author = authorRepository.findByName(authorDto.getName());
        if (author.isPresent()){
            throw new ConflictException("Author's name existed!");
        } else {
            Author newAuthor = new Author();
            BeanUtils.copyProperties(authorDto, newAuthor);
            authorRepository.save(newAuthor);
            BeanUtils.copyProperties(newAuthor, authorDto);
            return authorDto;
        }
    }

    @Override
    public List<AuthorDto> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        List<AuthorDto> authorDtos = new ArrayList<>();
        if (authors.size() > 0 ) {
            for (Author author: authors) {
                AuthorDto authorDto = AuthorMapper.mapToAuthorDto(author);
                authorDtos.add(authorDto);
            }
            return authorDtos;
        }
        throw new NoDataFoundException("No authors data found");
    }
}
