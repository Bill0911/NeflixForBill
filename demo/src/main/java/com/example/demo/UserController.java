package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Autowired
    private BookForUserService bookForUserService;

    // Endpoint to create a user
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User createdUser = userService.createUser(user.getName(), user.getPassword());
        return ResponseEntity.ok(createdUser);
    }

    // Endpoint to login
    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody User user) {
        User loggedInUser = userService.login(user.getName(), user.getPassword());
        return ResponseEntity.ok(loggedInUser);
    }

    @PostMapping("/{userId}/books/{bookId}")
    public ResponseEntity<String> addBookToUser(@PathVariable Long userId, @PathVariable Long bookId) {
        User user = userService.getUserById(userId);
        Book book = bookService.getBookById(bookId);

        if (user == null || book == null) {
            return ResponseEntity.badRequest().body("User or Book not found.");
        }

        BookForUser bookForUser = new BookForUser();
        bookForUser.setUser(user);
        bookForUser.setBook(book);

        bookForUserService.save(bookForUser);
        return ResponseEntity.ok("Book added to user.");
    }
}
