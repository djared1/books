package com.example.books.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@Entity(name = "book")
@Table(name = "books")
@Accessors(fluent = true)
@Data
@Slf4j
public class BookEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "booksSequenceGenerator")
  @SequenceGenerator(name = "booksSequenceGenerator", sequenceName = "SEQ_BOOKS_ID", allocationSize = 1)
  private Long id;

  @Column(name = "book_title")
  private String bookTitle;

  @Column(name = "book_category")
  private String bookCategory;

  @Column(name = "publish_date")
  private Instant publishDate;

  @Column(name = "author_id")
  private Long authorId;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "author_id", referencedColumnName = "id", insertable = false, updatable = false)
  private AuthorEntity author;

  @Column(name = "created_timestamp")
  private Instant createdTimestamp;

  @Column(name = "updated_timestamp")
  private Instant updatedTimestamp;
}

