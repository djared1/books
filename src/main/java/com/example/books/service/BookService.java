package com.example.books.service;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.example.books.controller.schema.Author;
import com.example.books.controller.schema.Book;
import com.example.books.domain.AuthorEntity;
import com.example.books.domain.BookEntity;
import com.example.books.domain.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class BookService {

  private BooksRepository booksRepository;

  @Autowired
  public BookService(BooksRepository booksRepository) {
    this.booksRepository = booksRepository;
  }

  @Transactional
  public void updateBook(Long bookId, Book book) {
    BookEntity bookEntity = booksRepository.getById(bookId);

    bookEntity.bookTitle(book.getBookTitle());
    bookEntity.bookCategory(book.getBookCategory());
    bookEntity.publishDate(book.getPublishDate());

    if (Objects.equals(book.getAuthorId(), bookEntity.author().id())) {
      // ToDo read authors table and update the author
    }

    bookEntity.updatedTimestamp(Instant.now());
    booksRepository.save(bookEntity);
  }

  @Transactional
  public Long createBook(Book book) {
    Instant now = Instant.now();

    BookEntity bookEntity = new BookEntity();

    bookEntity.bookTitle(book.getBookTitle());
    bookEntity.bookCategory(book.getBookCategory());
    bookEntity.publishDate(book.getPublishDate());
    bookEntity.authorId(book.getAuthorId());

    bookEntity.createdTimestamp(now);
    bookEntity.updatedTimestamp(now);

    BookEntity savedBook = booksRepository.save(bookEntity);
    return savedBook.id();
  }

  @Transactional
  public void deleteBook(Long bookId) {
    booksRepository.deleteById(bookId);
  }

  public List<Book> findAllBooks() {
    return booksRepository.findAll().stream()
      .map(this::mapToBook)
      .collect(Collectors.toList());
  }

  private Book mapToBook(BookEntity bookEntity) {
    Book book = new Book();
    book.setId(bookEntity.id());
    book.setBookTitle(bookEntity.bookTitle());
    book.setBookCategory(bookEntity.bookCategory());
    book.setPublishDate(bookEntity.publishDate());
    book.setAuthorId(bookEntity.authorId());

    return book;
  }

  public Book getBook(Long bookId) {
    return map(booksRepository.getById(bookId));
  }

  private Book map(BookEntity bookEntity) {
    Book book = new Book();
    book.setId(bookEntity.id());
    book.setBookTitle(bookEntity.bookTitle());
    book.setBookCategory(bookEntity.bookCategory());
    book.setPublishDate(bookEntity.publishDate());
    book.setAuthorId(bookEntity.authorId());
    return book;
  }

  private Author map(AuthorEntity authorEntity) {
    if (authorEntity != null) {
      Author author = new Author();
      author.setFirstName(authorEntity.authorFirstName());
      author.setLastName(authorEntity.authorLastName());
      author.setAuthorId(authorEntity.id());
      return author;
    }
    else {
      return null;
    }
  }
}
