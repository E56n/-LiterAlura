package com.example.gutendexapi.controller;

import com.example.gutendexapi.model.Book;
import com.example.gutendexapi.service.BookService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @PostMapping("/fetch")
    public Book fetchBookFromGutendex(@RequestParam String title) {
        String url = "https://gutendex.com/books?search=" + title;
        RestTemplate restTemplate = new RestTemplate();
        var response = restTemplate.getForObject(url, String.class);

        // Aquí puedes parsear la respuesta y guardar los datos.
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor("Autor desconocido");
        book.setLanguage("EN");
        book.setDownloads(0);

        return bookService.saveBook(book);
    }
}
