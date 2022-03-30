package com.example.books.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@Entity(name = "author")
@Table(name = "authors")
@Accessors(fluent = true)
@Data
@Slf4j
public class AuthorEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "authorsSequenceGenerator")
  @SequenceGenerator(name = "authorsSequenceGenerator", sequenceName = "SEQ_AUTHORS_ID", allocationSize = 1)
  private Long id;

  @Column(name = "author_first_name")
  private String authorFirstName;

  @Column(name = "author_last_name")
  private String authorLastName;

  @Column(name = "created_timestamp")
  private Instant createdTimestamp;

  @Column(name = "updated_timestamp")
  private Instant updatedTimestamp;
}
