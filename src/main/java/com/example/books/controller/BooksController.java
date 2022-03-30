package com.example.books.controller;

import java.net.URI;

import com.example.books.controller.schema.Book;
import com.example.books.controller.schema.Books;
import com.example.books.domain.BooksRepository;
import com.example.books.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BooksController {

  private BooksRepository booksRepository;
  private BookService bookService;

  @Autowired
  public BooksController(BooksRepository booksRepository, BookService bookService) {
    this.booksRepository = booksRepository;
    this.bookService = bookService;
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(code = HttpStatus.CREATED)
  public ResponseEntity createBook(@RequestBody Book book) {
    Long bookId = bookService.createBook(book);
    return ResponseEntity.created(URI.create(String.valueOf(bookId))).build();
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(code = HttpStatus.OK)
  public Books getBooks() {
    return new Books(bookService.findAllBooks());
  }

  @GetMapping(value = "/{bookId}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(code = HttpStatus.OK)
  public Book getBook(@PathVariable("bookId") Long bookId) {
    return bookService.getBook(bookId);
  }

  @PutMapping(value = "/{bookId}", consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(code = HttpStatus.NO_CONTENT)
  public void updateBook(@RequestBody Book book,
                         @PathVariable("bookId") Long bookId) {
    bookService.updateBook(bookId, book);
  }

  @DeleteMapping(value = "/{bookId}")
  @ResponseStatus(code = HttpStatus.NO_CONTENT)
  public void deleteBook(@PathVariable("bookId") Long bookId) {
    bookService.deleteBook(bookId);
  }

}
