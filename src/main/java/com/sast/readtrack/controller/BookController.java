package com.sast.readtrack.controller;

import com.sast.readtrack.common.Result;
import com.sast.readtrack.entity.Book;
import com.sast.readtrack.service.BookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/books")
    public Result<Void> add(@RequestBody Book book) {
        return bookService.create(book);
    }

    @GetMapping("/books/{id}")
    public Result<Book> detail(@PathVariable Integer id) {
        return bookService.detail(id);
    }
}
