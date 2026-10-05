package com.sast.readtrack.controller;

import com.sast.readtrack.common.Result;
import com.sast.readtrack.entity.Book;
import com.sast.readtrack.service.BookService;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/books/{id}/progress")
    public Result<Void> updateProgress(@PathVariable Integer id, @RequestBody Book book) {
        return bookService.updateProgress(id, book);
    }

    @DeleteMapping("/books/{id}")
    public Result<Void> delete(@PathVariable Integer id) {
        return bookService.delete(id);
    }

}
