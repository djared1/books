/*
 * © 2022 by Intellectual Reserve, Inc.  All rights reserved.
 */

package com.example.books.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class RootController {

  @GetMapping
  public ResponseEntity<Void> forwardToDocs() {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Location", "/swagger-ui.html");
    return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
  }
}
