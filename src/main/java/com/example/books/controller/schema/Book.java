package com.example.books.controller.schema;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
  private Long   id;
  private String bookTitle;
  private String bookCategory;
  private Instant publishDate;
  private Long authorId;
}
