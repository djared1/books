package com.example.books.service;

import java.time.Instant;

import com.example.books.domain.AuthorEntity;
import com.example.books.domain.AuthorsRepository;
import com.example.books.domain.BookEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AuthorService {
  private AuthorsRepository authorsRepository;

  public AuthorService(AuthorsRepository authorsRepository) {
    this.authorsRepository = authorsRepository;
  }

  @Transactional
  public AuthorEntity createAuthor(AuthorEntity authorEntity) {
    return authorsRepository.save(authorEntity);
  }

  @Transactional
  public void deleteAll() {
    authorsRepository.deleteAll();
  }

}
