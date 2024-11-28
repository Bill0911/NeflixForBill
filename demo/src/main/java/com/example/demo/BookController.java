package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    // 1. Get all books
    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    // 2. Get books by author ID
    @GetMapping("/author/{authorId}")
    public List<Book> getBooksByAuthor(@PathVariable Long authorId) {
        return bookService.getBooksByAuthor(authorId);
    }

    // 3. Add a new book
    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        Book savedBook = bookService.addBook(book);
        return ResponseEntity.ok(savedBook);
    }
}
