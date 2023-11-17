package com.reactive.bookApp.reactivebookapp.repository;

import com.reactive.bookApp.reactivebookapp.entities.Book;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface BookRepository extends ReactiveCrudRepository<Book,Integer> {

    @Query("select * from book_details where name= :name and author= :author")
    public Flux<Book> findByNameAndAuthor(String name, String author);

    @Query("select * from book_details where name like :titleKeyword")
    public Flux<Book> searchBooks(String titleKeyword);


}
