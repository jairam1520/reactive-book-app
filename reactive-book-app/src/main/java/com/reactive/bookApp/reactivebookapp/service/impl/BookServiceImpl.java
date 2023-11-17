package com.reactive.bookApp.reactivebookapp.service.impl;

import com.reactive.bookApp.reactivebookapp.entities.Book;
import com.reactive.bookApp.reactivebookapp.repository.BookRepository;
import com.reactive.bookApp.reactivebookapp.service.BookService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository repository;
    @Override
    public Mono<Book> create(Book book) {
        return repository.save(book).log();
    }

    @Override
    public Flux<Book> getAll() {
        return repository.findAll().delayElements(Duration.ofSeconds(2)).log();
    }

    @Override
    public Mono<Book> get(int bookId) {
        return repository.findById(Mono.just(bookId));
    }

    @Override
    public Mono<Book> update(Book book, int bookId) {
        Mono<Book> oldBook = repository.findById(bookId);
        return oldBook.flatMap(book1->{
            book1.setName(book.getName());
            book1.setAuthor(book.getAuthor());
            book1.setPublisher(book.getPublisher());
            book1.setDescription(book.getDescription());

            return repository.save(book1);
        });

    }

    @Override
    public Mono<Void> delete(int bookId) {

        return repository.findById(bookId).flatMap(book -> repository.delete(book));
    }

    @Override
    public Flux<Book> searchBooks(String query) {
        return repository.searchBooks(query);
    }
}
