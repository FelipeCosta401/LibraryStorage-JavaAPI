package com.felipe.controller;

import com.felipe.model.entity.Book;
import com.felipe.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService service;

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks(){
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllBooks());
    }

    @GetMapping("/")

    @PostMapping
    public ResponseEntity<Book> registerBook(@RequestBody Book book){
        Book createdBook = service.registerBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
    }
}
