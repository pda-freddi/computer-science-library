package com.pdafr.computer.science.library.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.pdafr.computer.science.library.enums.Category;
import com.pdafr.computer.science.library.model.Article;

public interface ArticleRepository extends CrudRepository<Article, Integer> {

    // Find all articles ordered by different columns and asc/desc order
    List<Article> findAllByOrderByIdAsc();
    List<Article> findAllByOrderByIdDesc();
    List<Article> findAllByOrderByTitleAsc();
    List<Article> findAllByOrderByTitleDesc();
    List<Article> findAllByOrderByAuthorAsc();
    List<Article> findAllByOrderByAuthorDesc();
    List<Article> findAllByOrderByCategoryAsc();
    List<Article> findAllByOrderByCategoryDesc();
    List<Article> findAllByOrderByReadTimeAsc();
    List<Article> findAllByOrderByReadTimeDesc();
 
    // Find a list of articles by matching with columns
    // One colum
    List<Article> findByTitleContainingIgnoreCase(String title);
    List<Article> findByAuthorContainingIgnoreCase(String title);
    List<Article> findByCategory(Category category);
    List<Article> findByReadTimeLessThanEqual(Integer readTime);
    
    // Two Columns
    List<Article> findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCase(String title, String author);
    List<Article> findByTitleContainingIgnoreCaseAndCategory(String title, Category category);
    List<Article> findByAuthorContainingIgnoreCaseAndCategory(String author, Category category);
    List<Article> findByTitleContainingIgnoreCaseAndReadTimeLessThanEqual(String title, Integer maxReadTime);
    List<Article> findByAuthorContainingIgnoreCaseAndReadTimeLessThanEqual(String author, Integer maxReadTime);
    List<Article> findByCategoryAndReadTimeLessThanEqual(Category category, Integer maxReadTime);
    
    // Three columns  
    List<Article> findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCaseAndCategory(String title, String author, Category category);
    List<Article> findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCaseAndReadTimeLessThanEqual(String title, String author, Integer maxReadTime);
    List<Article> findByTitleContainingIgnoreCaseAndCategoryAndReadTimeLessThanEqual(String title, Category category, Integer maxReadTime);
    List<Article> findByAuthorContainingIgnoreCaseAndCategoryAndReadTimeLessThanEqual(String author, Category category, Integer maxReadTime);
   
    // Four columns
    List<Article> findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCaseAndCategoryAndReadTimeLessThanEqual(String title, String author, Category category, Integer maxReadTime);

}
