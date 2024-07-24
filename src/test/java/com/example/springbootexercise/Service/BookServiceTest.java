package com.example.springbootexercise.Service;

import com.example.springbootexercise.dto.AuthorDto;
import com.example.springbootexercise.dto.BookDto;
import com.example.springbootexercise.entity.Author;
import com.example.springbootexercise.entity.Book;
import com.example.springbootexercise.exception.ConflictException;
import com.example.springbootexercise.exception.NoDataFoundException;
import com.example.springbootexercise.repository.AuthorRepository;
import com.example.springbootexercise.repository.BookRepository;
import com.example.springbootexercise.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @Mock
    BookRepository bookRepository;

    @Mock
    AuthorRepository authorRepository;

    @InjectMocks
    BookServiceImpl bookService;

    static List<Author> authors = new ArrayList<>();
    static List<Book> books = new ArrayList<>();

    @BeforeAll
    public static void setUpAll() {
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

        Book book1 = Book.builder()
                .id(1L)
                .code("MB01")
                .title("Văn học 1")
                .quantity(2)
                .authors(Set.of(author1))
                .build();
        books.add(book1);

        Book book2 = Book.builder()
                .id(2L)
                .code("MB02")
                .title("Văn học 2")
                .quantity(4)
                .authors(Set.of(author1, author2))
                .build();
        books.add(book2);
    }

    @Test
    public void whenGetAll_shouldReturnList() {
        when(bookRepository.findAll()).thenReturn(books);
        List<BookDto> actualBooks = bookService.getAllBooks();
        assertThat(actualBooks).isNotNull();
        assertThat(actualBooks).hasSize(2);
        assertThat(actualBooks.get(0).getTitle()).isEqualTo("Văn học 1");
    }

    @Test
    public void whenGetAll_shouldThrowException() {
        when(bookRepository.findAll()).thenThrow(new NoDataFoundException("No books data found"));
        assertThatThrownBy(() -> bookService.getAllBooks())
                .isInstanceOf(NoDataFoundException.class)
                .hasMessage("No books data found");
    }

    @Test
    public void whenCreateBook_shouldReturnBookDto() {
        BookDto bookDto = BookDto.builder()
                .id(3L)
                .code("MB03")
                .title("Văn học 3")
                .quantity(1)
                .authors(null)
                .authorNames(List.of("An An", "Anh Anh"))
                .build();
        when(bookRepository.findByCode(bookDto.getCode())).thenReturn(Optional.empty());

        Author author1 = new Author("An An");
        Author author2 = new Author("Anh Anh");
        when(authorRepository.findByName("An An")).thenReturn(Optional.of(author1));
        when(authorRepository.findByName("Anh Anh")).thenReturn(Optional.of(author2));

        // Giả lập behavior của bookRepository.save để trả về đối tượng sách đã lưu
        Book savedBook = new Book(3L, "MB03", "Văn học 3", 1, "", Set.of(author1, author2));
        when(bookRepository.save(any(Book.class))).thenReturn(savedBook);

        BookDto dto = bookService.createBook(bookDto);
        assertThat(dto).isNotNull();
        assertThat(dto.getTitle()).isEqualTo("Văn học 3");
        assertThat(dto.getAuthors()).hasSize(2);
    }

    @Test
    public void whenCreateBook_shouldThrownException() {
        BookDto dto = new BookDto(null, "ISM1", "Sách mới", 3, "", null);
        when(bookRepository.findByCode(dto.getCode())).thenThrow(new ConflictException("Code existed!"));
        assertThatThrownBy(() -> bookService.createBook(dto))
                .isInstanceOf(ConflictException.class)
                .hasMessage("Code existed!");
    }
}
