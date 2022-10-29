package com.pdafr.computer.science.library.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.pdafr.computer.science.library.enums.Category;
import com.pdafr.computer.science.library.model.Book;

public interface BookRepository extends CrudRepository<Book, Integer> {

    // Find all books ordered by different columns and asc/desc order
    List<Book> findAllByOrderByIdAsc();
    List<Book> findAllByOrderByIdDesc();
    List<Book> findAllByOrderByTitleAsc();
    List<Book> findAllByOrderByTitleDesc();
    List<Book> findAllByOrderByAuthorAsc();
    List<Book> findAllByOrderByAuthorDesc();
    List<Book> findAllByOrderByCategoryAsc();
    List<Book> findAllByOrderByCategoryDesc();
    List<Book> findAllByOrderByNumPagesAsc();
    List<Book> findAllByOrderByNumPagesDesc();
    
    // Find a list of books by matching with columns
    // One colum
    List<Book> findByTitleContainingIgnoreCase(String title);
    List<Book> findByAuthorContainingIgnoreCase(String title);
    List<Book> findByCategory(Category category);
    
    // Two columns
    List<Book> findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCase(String title, String author);
    List<Book> findByTitleContainingIgnoreCaseAndCategory(String title, Category category);
    List<Book> findByAuthorContainingIgnoreCaseAndCategory(String author, Category category);
    
    // Three columns
    List<Book> findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCaseAndCategory(String title, String author, Category category);
    
}
