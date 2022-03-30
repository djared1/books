package com.example.books.component;

import java.net.URI;
import java.time.Instant;
import java.util.List;

import com.example.books.ApplicationTestConfig;
import com.example.books.controller.schema.Book;
import com.example.books.controller.schema.Books;
import com.example.books.domain.AuthorEntity;
import com.example.books.domain.AuthorsRepository;
import com.example.books.domain.BooksRepository;
import com.example.books.service.AuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {ApplicationTestConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BooksControllerComponentTests {

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private Environment springEnvironment;

  @Autowired
  private BooksRepository booksRepository;

  @Autowired
  private AuthorsRepository authorsRepository;

  @Autowired
  private AuthorService authorService;

  private Long defaultAuthorId;

  @BeforeEach
  public void beforeEach() {
    booksRepository.deleteAll();

    authorService.deleteAll();
    AuthorEntity authorEntity = authorService.createAuthor(makeDefaultAuthorEntity());
    defaultAuthorId = authorEntity.id();
  }

  @Test
  void testGetBooks_emptyList() {
    ResponseEntity<Books> booksResponseEntity =
      restTemplate.exchange(RequestEntity.get(getBooksUri())
        .accept(MediaType.APPLICATION_JSON)
        .build(), Books.class);

    assertEquals(HttpStatus.OK, booksResponseEntity.getStatusCode());
    List<Book> bookList = booksResponseEntity.getBody().getBooks();
    assertEquals(0, bookList.size());
  }

  @Test
  void testCreateBooks() {
    Book book = makeDefaultBook();
    ResponseEntity<Books> booksResponseEntity =
      restTemplate.exchange(RequestEntity.post(getBooksUri())
        .accept(MediaType.APPLICATION_JSON)
        .body(book), Books.class);
    assertEquals(HttpStatus.CREATED, booksResponseEntity.getStatusCode());
    List<Book> allBooks = getAllBooks();
    assertEquals(1, allBooks.size());
    assertEquals("Tales From the Crypt", allBooks.get(0).getBookTitle());
    assertEquals("Fiction", allBooks.get(0).getBookCategory());
  }

  @Test
  void testPutBooks() {
    ResponseEntity<Books> booksResponseEntity =
      restTemplate.exchange(RequestEntity.post(getBooksUri())
        .accept(MediaType.APPLICATION_JSON)
        .body(makeDefaultBook()), Books.class);
    assertEquals(HttpStatus.CREATED, booksResponseEntity.getStatusCode());
    URI location = booksResponseEntity.getHeaders().getLocation();
    Long bookId = Long.valueOf(location.toString());

    Book requestedBook = getBook(bookId);
    requestedBook.setBookTitle("A New Tale");
    ResponseEntity<String> updateEntity =
      restTemplate.exchange(RequestEntity.put(getBooksUri(bookId))
        .accept(MediaType.APPLICATION_JSON)
        .body(requestedBook), String.class);
    assertEquals(HttpStatus.NO_CONTENT, updateEntity.getStatusCode());

    Book updatedBook = getBook(bookId);
    assertEquals("A New Tale", updatedBook.getBookTitle());
  }

  @Test
  void testDeleteBooks() {
    Book defaultBook = makeDefaultBook();
    ResponseEntity<Books> booksResponseEntity =
      restTemplate.exchange(RequestEntity.post(getBooksUri())
        .accept(MediaType.APPLICATION_JSON)
        .body(defaultBook), Books.class);
    assertEquals(HttpStatus.CREATED, booksResponseEntity.getStatusCode());
    URI location = booksResponseEntity.getHeaders().getLocation();
    Long bookId = Long.valueOf(location.toString());

    ResponseEntity<String> deleteEntity =
      restTemplate.exchange(RequestEntity.delete(getBooksUri(bookId))
        .accept(MediaType.APPLICATION_JSON)
        .build(), String.class);
    assertEquals(HttpStatus.NO_CONTENT, deleteEntity.getStatusCode());
    List<Book> allBooks = getAllBooks();
    assertEquals(0, allBooks.size());
  }

  private List<Book> getAllBooks() {
    return restTemplate.exchange(RequestEntity.get(getBooksUri())
      .accept(MediaType.APPLICATION_JSON)
      .build(), Books.class).getBody().getBooks();
  }

  private Book getBook(Long bookId) {
    return restTemplate.exchange(RequestEntity.get(getBooksUri(bookId))
      .accept(MediaType.APPLICATION_JSON)
      .build(), Book.class).getBody();
  }

  private Book makeDefaultBook() {
    Book book = new Book();
    book.setBookTitle("Tales From the Crypt");
    book.setBookCategory("Fiction");
    book.setPublishDate(Instant.now());
    book.setAuthorId(defaultAuthorId);
    return book;
  }

  private AuthorEntity makeDefaultAuthorEntity() {
    AuthorEntity authorEntity = new AuthorEntity();
    authorEntity.authorFirstName("Ellen");
    authorEntity.authorLastName("Weiss");
    return authorEntity;
  }

  private String getBooksUri(Long bookId) {
    return getBooksUri() + "/" + bookId;
  }

  private String getBooksUri() {
    String port = springEnvironment.getProperty("local.server.port", "5000");
    return "http://localhost:" + port + "/books";
  }

}
