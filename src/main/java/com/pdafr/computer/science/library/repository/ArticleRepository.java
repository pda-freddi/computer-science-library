package com.pdafr.computer.science.library.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.pdafr.computer.science.library.model.Article;

public interface ArticleRepository extends CrudRepository<Article, Integer> {

    List<Article> findAllByOrderByIdAsc();
    List<Article> findAllByOrderByTitleAsc();
    List<Article> findAllByOrderByTitleDesc();
    List<Article> findAllByOrderByAuthorAsc();
    List<Article> findAllByOrderByAuthorDesc();
    List<Article> findAllByOrderByCategoryAsc();
    List<Article> findAllByOrderByCategoryDesc();
    List<Article> findAllByOrderByReadTimeAsc();
    List<Article> findAllByOrderByReadTimeDesc();
    
}
