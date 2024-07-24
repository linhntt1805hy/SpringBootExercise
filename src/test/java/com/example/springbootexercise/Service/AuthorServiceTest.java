package com.example.springbootexercise.Service;

import com.example.springbootexercise.dto.AuthorDto;
import com.example.springbootexercise.entity.Author;
import com.example.springbootexercise.exception.ConflictException;
import com.example.springbootexercise.exception.NoDataFoundException;
import com.example.springbootexercise.repository.AuthorRepository;
import com.example.springbootexercise.service.impl.AuthorServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {
    @Mock
    AuthorRepository authorRepository;

    @InjectMocks
    AuthorServiceImpl authorService;

    @Test
    public void whenGetAllAuthors_shouldReturnListAuthors() {
        List<Author> authors = new ArrayList<>();
        Author author1 = Author.builder()
                .id(1L)
                .name("Linh Linh")
                .dateOfBirth(LocalDate.of(2000, 03, 19))
                .build();
        authors.add(author1);

        Author author2 = Author.builder()
                .id(1L)
                .name("Anh Anh")
                .dateOfBirth(LocalDate.of(2001, 02, 23))
                .build();
        authors.add(author2);
        when(authorRepository.findAll()).thenReturn(authors);
        List<AuthorDto> actual = authorService.getAllAuthors();
        assertThat(actual).isNotNull();
        assertThat(actual).hasSize(2);
        assertThat(actual.get(0).getName()).isEqualTo("Linh Linh");
    }

    @Test
    public void whenGetAllAuthors_shouldThrownException() {
        when(authorRepository.findAll()).thenThrow(new NoDataFoundException("No authors data found"));
        assertThatThrownBy(() -> authorService.getAllAuthors())
                .isInstanceOf(NoDataFoundException.class)
                .hasMessage("No authors data found");
    }

    @Test
    public void whenCreateAuthor_shouldReturnAuthorDto() {
        AuthorDto authorDto = new AuthorDto(null,"Thu Hà", null, "");
        when(authorRepository.findByName(authorDto.getName())).thenReturn(Optional.empty());
        AuthorDto dto = authorService.createAuthor(authorDto);
        assertThat(dto).isNotNull();
        assertThat(dto.getName()).isEqualTo("Thu Hà");
    }

    @Test
    public void whenCreateAuthor_shouldThrownException() {
        AuthorDto authorDto = new AuthorDto(null,"Thu Hà", null, "");
        when(authorRepository.findByName(authorDto.getName())).thenThrow(new ConflictException("Book's name existed!"));
        assertThatThrownBy(() -> authorService.createAuthor(authorDto))
                .isInstanceOf(ConflictException.class)
                .hasMessage("Book's name existed!");
    }

}
