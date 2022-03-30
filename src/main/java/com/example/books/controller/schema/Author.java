package com.example.books.controller.schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Author {
  private String firstName;
  private String lastName;
  private Long authorId;
}
