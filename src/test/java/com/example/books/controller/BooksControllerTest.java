package com.example.books.controller;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.example.books.controller.schema.Author;
import com.example.books.controller.schema.Book;
import com.example.books.controller.schema.Books;
import com.example.books.domain.AuthorEntity;
import com.example.books.domain.BookEntity;
import com.example.books.domain.BooksRepository;
import com.example.books.service.BookService;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BooksControllerTest {

  private BooksRepository mockRepository;
  private BookService bookService;
  private BooksController booksController;

  @BeforeEach
  void setUp() {
    mockRepository = mock(BooksRepository.class);
    bookService = new BookService(mockRepository);
    booksController = new BooksController(mockRepository, bookService);
  }

  @Test
  void testGetBooks_emptyList() {
    when(mockRepository.findAll()).thenReturn(Collections.emptyList());

    Books books = booksController.getBooks();
    assertEquals(0, books.getBooks().size());
  }

  @Test
  void testGetBooks_singleBook() {
    when(mockRepository.findAll()).thenReturn(Collections.singletonList(createBookEntity()));

    Books books = booksController.getBooks();
    List<Book> bookList = books.getBooks();
    assertEquals(1, bookList.size());
    assertEquals("Some Book Title", bookList.get(0).getBookTitle());
    assertEquals("Fiction", bookList.get(0).getBookCategory());
  }

  @Test
  void testUpdateBook() {
    BookEntity bookEntity = createBookEntity();
    bookEntity.publishDate(Instant.now().minus(5, ChronoUnit.DAYS));
    when(mockRepository.getById(eq(bookEntity.id()))).thenReturn(bookEntity);
    when(mockRepository.save(eq(bookEntity))).thenReturn(bookEntity);

    Instant newPublishDate = Instant.now();
    Book book = new Book();
    book.setId(bookEntity.id());
    book.setPublishDate(newPublishDate);
    book.setBookCategory(bookEntity.bookCategory());
    book.setBookTitle(bookEntity.bookTitle());
    book.setAuthorId(bookEntity.author().id());
    booksController.updateBook(book, bookEntity.id());

    ArgumentCaptor<BookEntity> bookEntityArgumentCaptor = ArgumentCaptor.forClass(BookEntity.class);
    verify(mockRepository).save(bookEntityArgumentCaptor.capture());

    BookEntity savedBookEntity = bookEntityArgumentCaptor.getValue();
    assertEquals(savedBookEntity.publishDate(), newPublishDate);
  }

  @Test
  void testCreateBook() {
    // ToDo
  }

  @Test
  void testDeleteBook() {
    // ToDo
  }

  private BookEntity createBookEntity() {
      BookEntity bookEntity = new BookEntity();
      bookEntity.id(getNextBookId());
      bookEntity.bookTitle("Some Book Title");
      bookEntity.bookCategory("Fiction");
      bookEntity.publishDate(Instant.now());
      bookEntity.author(createAuthorEntity());
      bookEntity.createdTimestamp(Instant.now());
      bookEntity.updatedTimestamp(Instant.now());
      return bookEntity;
  }

  private AuthorEntity createAuthorEntity() {
    return new AuthorEntity()
      .authorFirstName("Ellen")
      .authorLastName("Weiss")
      .id(getNextAuthorId());
  }


  AtomicLong atomicBookId = new AtomicLong(0);
  private Long getNextBookId() {
    return atomicBookId.getAndIncrement();
  }

  AtomicLong atomicAuthorId = new AtomicLong(0);
  private Long getNextAuthorId() {
    return atomicAuthorId.getAndIncrement();
  }
}