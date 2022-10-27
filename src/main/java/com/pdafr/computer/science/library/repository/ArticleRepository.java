package com.pdafr.computer.science.library.repository;

import org.springframework.data.repository.CrudRepository;

import com.pdafr.computer.science.library.model.Article;

public interface ArticleRepository extends CrudRepository<Article, Integer> {

}
