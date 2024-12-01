package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    // Get all books
    public List<Book> getAllBooks() {
        return this.bookRepository.findAll();
    }

    // Get books by a specific author
    public List<Book> getBooksByAuthor(Long authorId) {
        return this.bookRepository.findByAuthorId(authorId);
    }

    // Add a new book
    public Book addBook(Book book) {
        Optional<Author> author = this.authorRepository.findById(book.getAuthor().getId());
        if (author.isPresent()) {
            book.setAuthor(author.get());
            return this.bookRepository.save(book);
        } else {
            throw new RuntimeException("Author not found");
        }
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }
}
