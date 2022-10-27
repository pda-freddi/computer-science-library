package com.pdafr.computer.science.library.repository;

import org.springframework.data.repository.CrudRepository;

import com.pdafr.computer.science.library.model.Book;

public interface BookRepository extends CrudRepository<Book, Integer> {

}
