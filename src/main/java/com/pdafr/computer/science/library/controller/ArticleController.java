package com.pdafr.computer.science.library.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.pdafr.computer.science.library.exceptions.EntityNotFoundException;
import com.pdafr.computer.science.library.exceptions.InvalidInputObjectException;
import com.pdafr.computer.science.library.exceptions.InvalidQueryParameterException;
import com.pdafr.computer.science.library.model.Article;
import com.pdafr.computer.science.library.repository.ArticleRepository;
import com.pdafr.computer.science.library.validator.ArticleValidator;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleRepository articleRepository;
    
    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }
    
    /**
     * Get all article entities
     * @param sortBy optional query parameter to specify an attribute to sort the list for
     * @param asc optional query boolean parameter to specify if sorting order is ascending; defaults to true if not specified
     * @return a list of articles
     * @throws InvalidQueryParameterException
     */
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Article> getAllArticles(@RequestParam(name="sort_by", required=false) String sortBy, @RequestParam(required=false) Boolean asc) {
        // Initialize asc variable to a default value if it's not specified in the request
        if (asc == null) {
            asc = true;
        }
        // If sort request parameter is specified, return a sorted list of books according to the parameter
        if (sortBy != null) {
            switch (sortBy) {
              case "title":
                  if (asc) {
                      return this.articleRepository.findAllByOrderByTitleAsc();
                  } else {
                      return this.articleRepository.findAllByOrderByTitleDesc();
                  }
              case "author":
                  if (asc) {
                    return this.articleRepository.findAllByOrderByAuthorAsc();
                  } else {
                    return this.articleRepository.findAllByOrderByAuthorDesc();
                  }
              case "category":
                  if (asc) {
                    return this.articleRepository.findAllByOrderByCategoryAsc();
                  } else {
                    return this.articleRepository.findAllByOrderByCategoryDesc();
                  }
              case "readTime":
                if (asc) {
                  return this.articleRepository.findAllByOrderByReadTimeAsc();
                } else {
                  return this.articleRepository.findAllByOrderByReadTimeDesc();
                }
              default:
                throw new InvalidQueryParameterException(sortBy + " is not a valid query parameter");
            }
        }
        // If sort by parameter is not specified, return list of articles ordered by ID in asc or desc order
        if (asc) {
            return this.articleRepository.findAllByOrderByIdAsc();
        } else {
            return this.articleRepository.findAllByOrderByIdDesc();
        }
    }
    
    /**
     * Get an article by its ID
     * @param id 
     * @return an article object
     * @throws EntityNotFoundException if no article matches the ID provided 
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Article getArticleById(@PathVariable("id") Integer id) {
        Optional<Article> articleOptional = this.articleRepository.findById(id);
        if (!articleOptional.isPresent()) {
            throw new EntityNotFoundException("Can't find an article with ID " + id);
        }
        Article article = articleOptional.get();
        return article;
    }
    
    /**
     * Save a new article object
     * @param article to be saved
     * @return newly created article object
     * @throws InvalidInputObjectException if input article object is missing fields
     */
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Article saveNewArticle(@RequestBody Article article) {
        if (!ArticleValidator.validateArticle(article)) {
            throw new InvalidInputObjectException("Input article object missing field(s)");
        }
        Article newArticle = this.articleRepository.save(article);
        return newArticle;
    }

    /**
     * Update an article object
     * @param id of article to be updated
     * @param article object with updated fields
     * @return updated article object
     * @throws EntityNotFoundException if no article matches the ID provided
     * @throws InvalidInputObjectException if input article object is missing fields
     */
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Article updateArticle(@PathVariable("id") Integer id, @RequestBody Article article) {
        Optional<Article> articleToUpdateOptional = this.articleRepository.findById(id);
        if (!articleToUpdateOptional.isPresent()) {
            throw new EntityNotFoundException("Can't find an article with ID " + id);
        }
        if (!ArticleValidator.validateArticle(article)) {
          throw new InvalidInputObjectException("Input article object missing field(s)");
        }
        Article articleToUpdate = articleToUpdateOptional.get();
        articleToUpdate.setTitle(article.getTitle());
        articleToUpdate.setAuthor(article.getAuthor());
        articleToUpdate.setCategory(article.getCategory());
        articleToUpdate.setReadTime(article.getReadTime());
        articleToUpdate.setLink(article.getLink());
        Article updatedArticle = this.articleRepository.save(articleToUpdate);
        return updatedArticle;
    }
    
    /**
     * Patch an article object
     * @param id of article to be patched
     * @param article object with patched field(s)
     * @return patched article object
     * @throws EntityNotFoundException if no article matches the ID provided
     */
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Article patchArticle(@PathVariable("id") Integer id, @RequestBody Article article) {
        Optional<Article> articleToPatchOptional = this.articleRepository.findById(id);
        if (!articleToPatchOptional.isPresent()) {
            throw new EntityNotFoundException("Can't find an article with ID " + id);
        }
        Article articleToPatch = articleToPatchOptional.get();
        if (ArticleValidator.validateTitle(article.getTitle())) {
            articleToPatch.setTitle(article.getTitle());
        }
        if (ArticleValidator.validateAuthor(article.getAuthor())) {
            articleToPatch.setAuthor(article.getAuthor());
        }
        if (article.getCategory() != null) {
            articleToPatch.setCategory(article.getCategory());
        }
        if (ArticleValidator.validateReadTime(article.getReadTime())) {
            articleToPatch.setReadTime(article.getReadTime());
        }
        if (ArticleValidator.validateLink(article.getLink())) {
            articleToPatch.setLink(article.getLink());
        }
        Article patchedArticle = this.articleRepository.save(articleToPatch);
        return patchedArticle;
    }
    
    /**
     * Delete an article object
     * @param id of the article to delete
     * @return message confirming deletion
     * @throws EntityNotFoundException if no article matches the ID provided
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteArticle(@PathVariable("id") Integer id) {
        Optional<Article> articleToDeleteOptional = this.articleRepository.findById(id);
        if (!articleToDeleteOptional.isPresent()) {
            throw new EntityNotFoundException("Can't find an article with ID " + id);
        }
        Article articleToDelete = articleToDeleteOptional.get();
        this.articleRepository.delete(articleToDelete);
        return "Article with ID " +  id + " deleted successfully";
    }
    
}
