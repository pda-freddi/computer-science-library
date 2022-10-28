package com.pdafr.computer.science.library.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.pdafr.computer.science.library.model.Book;

public interface BookRepository extends CrudRepository<Book, Integer> {

    List<Book> findAllByOrderByIdAsc();
    List<Book> findAllByOrderByTitleAsc();
    List<Book> findAllByOrderByTitleDesc();
    List<Book> findAllByOrderByAuthorAsc();
    List<Book> findAllByOrderByAuthorDesc();
    List<Book> findAllByOrderByCategoryAsc();
    List<Book> findAllByOrderByCategoryDesc();
    List<Book> findAllByOrderByNumPagesAsc();
    List<Book> findAllByOrderByNumPagesDesc();
    
}
