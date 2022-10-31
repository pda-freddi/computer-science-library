package com.pdafr.computer.science.library.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pdafr.computer.science.library.enums.Category;
import com.pdafr.computer.science.library.exceptions.InvalidCategoryException;
import com.pdafr.computer.science.library.model.Article;
import com.pdafr.computer.science.library.model.Book;
import com.pdafr.computer.science.library.model.Video;
import com.pdafr.computer.science.library.repository.ArticleRepository;
import com.pdafr.computer.science.library.repository.BookRepository;
import com.pdafr.computer.science.library.repository.VideoRepository;
import com.pdafr.computer.science.library.validator.CategoryValidator;

@RestController
@RequestMapping("/search")
public class SearchController {

    private BookRepository bookRepository;
    private ArticleRepository articleRepository;
    private VideoRepository videoRepository;
    
    public SearchController(BookRepository bookRepository, ArticleRepository articleRepository, VideoRepository videoRepository) {
        this.bookRepository = bookRepository;
        this.articleRepository = articleRepository;
        this.videoRepository = videoRepository;
    }
    
    /**
     * Search for books
     * @param title or term to search for; case insensitive
     * @param author whole name or part of name; case insensitive
     * @param category must match a category enum; case insensitive
     * @return a list of books that match the search parameters or all books if no parameters are specified
     * @throws InvalidCategoryException
     */
    @GetMapping("/books")
    public Iterable<Book> searchBooks(
        @RequestParam(required=false) String title,
        @RequestParam(required=false) String author,
        @RequestParam(required=false) String category) {
      
        // Check which query parameters the request has
        Boolean hasTitle = title != null && !title.isBlank() && !title.isEmpty();
        Boolean hasAuthor = author != null && !author.isBlank() && !author.isEmpty();
        Boolean hasCategory = category != null && !category.isBlank() && !category.isEmpty();
        
        // Validate category parameter if present and convert it to enum
        Category categoryEnum = null;
        if (category != null) {
            if (!CategoryValidator.validateFromString(category.toUpperCase())) {
                throw new InvalidCategoryException("Invalid category parameter");
            }
            categoryEnum = Category.valueOf(category.toUpperCase());
        }

        // Return list of results according to the parameters provided
        if (hasTitle && hasAuthor && hasCategory) {
            
            return this.bookRepository.findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCaseAndCategory(title, author, categoryEnum);
          
        }  else if (hasTitle && hasAuthor && !hasCategory) {
          
            return this.bookRepository.findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCase(title, author);
          
        } else if (hasTitle && !hasAuthor && hasCategory) {
          
            return this.bookRepository.findByTitleContainingIgnoreCaseAndCategory(title, categoryEnum);
          
        } else if (!hasTitle && hasAuthor && hasCategory) {
          
            return this.bookRepository.findByAuthorContainingIgnoreCaseAndCategory(author, categoryEnum);
          
        } else if (hasTitle && !hasAuthor && !hasCategory) {
          
            return this.bookRepository.findByTitleContainingIgnoreCase(title);
          
        } else if (!hasTitle && hasAuthor && !hasCategory) {
          
            return this.bookRepository.findByAuthorContainingIgnoreCase(author);
        
        } else if (!hasTitle && !hasAuthor && hasCategory) {
          
            return this.bookRepository.findByCategory(categoryEnum);
        
        } else {
          
            return this.bookRepository.findAllByOrderByIdAsc();
          
        }
        
    }

    /**
     * Search for articles
     * @param title or term to search for; case insensitive
     * @param author whole name or part of name; case insensitive
     * @param category must match a category enum; case insensitive
     * @return a list of articles that match the search parameters or all articles if no parameters are specified
     * @throws InvalidCategoryException
     */
    @GetMapping("/articles")
    public Iterable<Article> searchArticles(
        @RequestParam(required=false) String title,
        @RequestParam(required=false) String author,
        @RequestParam(required=false) String category,
        @RequestParam(name="max_read_time", required=false) Integer maxReadTime) {
      
        // Check which query parameters the request has
        Boolean hasTitle = title != null && !title.isBlank() && !title.isEmpty();
        Boolean hasAuthor = author != null && !author.isBlank() && !author.isEmpty();
        Boolean hasCategory = category != null && !category.isBlank() && !category.isEmpty();
        Boolean hasMaxReadTime = maxReadTime != null;
        
        // Validate category parameter if present and convert it to enum
        Category categoryEnum = null;
        if (category != null) {
            if (!CategoryValidator.validateFromString(category.toUpperCase())) {
                throw new InvalidCategoryException("Invalid category parameter");
            }
            categoryEnum = Category.valueOf(category.toUpperCase());
        }
        
        // Return list of results according to the parameters provided
        if (hasTitle && hasAuthor && hasCategory && hasMaxReadTime) {
            
            return this.articleRepository.findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCaseAndCategoryAndReadTimeLessThanEqual(title, author, categoryEnum, maxReadTime);
          
        } else if (hasTitle && hasAuthor && hasCategory && !hasMaxReadTime) {
          
            return this.articleRepository.findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCaseAndCategory(title, author, categoryEnum);
      
        } else if (hasTitle && hasAuthor && !hasCategory && hasMaxReadTime) {
          
            return this.articleRepository.findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCaseAndReadTimeLessThanEqual(title, author, maxReadTime);
        
        } else if (hasTitle && !hasAuthor && hasCategory && hasMaxReadTime) {
          
            return this.articleRepository.findByTitleContainingIgnoreCaseAndCategoryAndReadTimeLessThanEqual(title, categoryEnum, maxReadTime);
        
        } else if (!hasTitle && hasAuthor && hasCategory && hasMaxReadTime) {
          
            return this.articleRepository.findByAuthorContainingIgnoreCaseAndCategoryAndReadTimeLessThanEqual(author, categoryEnum, maxReadTime);
      
        } else if (hasTitle && hasAuthor && !hasCategory && !hasMaxReadTime) {
          
            return this.articleRepository.findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCase(title, author);
          
        } else if (hasTitle && !hasAuthor && hasCategory && !hasMaxReadTime) {
          
            return this.articleRepository.findByTitleContainingIgnoreCaseAndCategory(title, categoryEnum);
          
        } else if (!hasTitle && hasAuthor && hasCategory && !hasMaxReadTime) {
          
            return this.articleRepository.findByAuthorContainingIgnoreCaseAndCategory(author, categoryEnum);
          
        } else if (hasTitle && !hasAuthor && !hasCategory && hasMaxReadTime) {
          
            return this.articleRepository.findByTitleContainingIgnoreCaseAndReadTimeLessThanEqual(title, maxReadTime);
        
        } else if (!hasTitle && hasAuthor && !hasCategory && hasMaxReadTime) {
          
            return this.articleRepository.findByAuthorContainingIgnoreCaseAndReadTimeLessThanEqual(author, maxReadTime);
        
        } else if (!hasTitle && !hasAuthor && hasCategory && hasMaxReadTime) {
          
            return this.articleRepository.findByCategoryAndReadTimeLessThanEqual(categoryEnum, maxReadTime);
      
        } else if (hasTitle && !hasAuthor && !hasCategory && !hasMaxReadTime) {
          
            return this.articleRepository.findByTitleContainingIgnoreCase(title);
          
        } else if (!hasTitle && hasAuthor && !hasCategory && !hasMaxReadTime) {
          
            return this.articleRepository.findByAuthorContainingIgnoreCase(author);
        
        } else if (!hasTitle && !hasAuthor && hasCategory && !hasMaxReadTime) {
          
            return this.articleRepository.findByCategory(categoryEnum);
        
        } else if (!hasTitle && !hasAuthor && !hasCategory && hasMaxReadTime) {
          
            return this.articleRepository.findByReadTimeLessThanEqual(maxReadTime);
      
        } else {

            return this.articleRepository.findAllByOrderByIdAsc();
          
        }
        
    }
    
    /**
     * Search for videos
     * @param title or term to search for; case insensitive
     * @param channel whole name or part of name; case insensitive
     * @param category must match a category enum; case insensitive
     * @return a list of videos that match the search parameters or all videos if no parameters are specified
     * @throws InvalidCategoryException
     */
    @GetMapping("/videos")
    public Iterable<Video> searchVideos(
        @RequestParam(required=false) String title,
        @RequestParam(required=false) String channel,
        @RequestParam(required=false) String category,
        @RequestParam(name="max_length", required=false) Integer maxLength) {
      
        // Check which query parameters the request has
        Boolean hasTitle = title != null && !title.isBlank() && !title.isEmpty();
        Boolean hasChannel = channel != null && !channel.isBlank() && !channel.isEmpty();
        Boolean hasCategory = category != null && !category.isBlank() && !category.isEmpty();
        Boolean hasMaxLength = maxLength != null;
        
        // Validate category parameter if present and convert it to enum
        Category categoryEnum = null;
        if (category != null) {
            if (!CategoryValidator.validateFromString(category.toUpperCase())) {
                throw new InvalidCategoryException("Invalid category parameter");
            }
            categoryEnum = Category.valueOf(category.toUpperCase());
        }
        
        // Return list of results according to the parameters provided
        if (hasTitle && hasChannel && hasCategory && hasMaxLength) {
            
            return this.videoRepository.findByTitleContainingIgnoreCaseAndChannelContainingIgnoreCaseAndCategoryAndLengthLessThanEqual(title, channel, categoryEnum, maxLength);
          
        } else if (hasTitle && hasChannel && hasCategory && !hasMaxLength) {
          
            return this.videoRepository.findByTitleContainingIgnoreCaseAndChannelContainingIgnoreCaseAndCategory(title, channel, categoryEnum);
      
        } else if (hasTitle && hasChannel && !hasCategory && hasMaxLength) {
          
            return this.videoRepository.findByTitleContainingIgnoreCaseAndChannelContainingIgnoreCaseAndLengthLessThanEqual(title, channel, maxLength);
        
        } else if (hasTitle && !hasChannel && hasCategory && hasMaxLength) {
          
            return this.videoRepository.findByTitleContainingIgnoreCaseAndCategoryAndLengthLessThanEqual(title, categoryEnum, maxLength);
        
        } else if (!hasTitle && hasChannel && hasCategory && hasMaxLength) {
          
            return this.videoRepository.findByChannelContainingIgnoreCaseAndCategoryAndLengthLessThanEqual(channel, categoryEnum, maxLength);
      
        } else if (hasTitle && hasChannel && !hasCategory && !hasMaxLength) {
          
            return this.videoRepository.findByTitleContainingIgnoreCaseAndChannelContainingIgnoreCase(title, channel);
          
        } else if (hasTitle && !hasChannel && hasCategory && !hasMaxLength) {
          
            return this.videoRepository.findByTitleContainingIgnoreCaseAndCategory(title, categoryEnum);
          
        } else if (!hasTitle && hasChannel && hasCategory && !hasMaxLength) {
          
            return this.videoRepository.findByChannelContainingIgnoreCaseAndCategory(channel, categoryEnum);
          
        } else if (hasTitle && !hasChannel && !hasCategory && hasMaxLength) {
          
            return this.videoRepository.findByTitleContainingIgnoreCaseAndLengthLessThanEqual(title, maxLength);
        
        } else if (!hasTitle && hasChannel && !hasCategory && hasMaxLength) {
          
            return this.videoRepository.findByChannelContainingIgnoreCaseAndLengthLessThanEqual(channel, maxLength);
        
        } else if (!hasTitle && !hasChannel && hasCategory && hasMaxLength) {
          
            return this.videoRepository.findByCategoryAndLengthLessThanEqual(categoryEnum, maxLength);
      
        } else if (hasTitle && !hasChannel && !hasCategory && !hasMaxLength) {
          
            return this.videoRepository.findByTitleContainingIgnoreCase(title);
          
        } else if (!hasTitle && hasChannel && !hasCategory && !hasMaxLength) {
          
            return this.videoRepository.findByChannelContainingIgnoreCase(channel);
        
        } else if (!hasTitle && !hasChannel && hasCategory && !hasMaxLength) {
          
            return this.videoRepository.findByCategory(categoryEnum);
        
        } else if (!hasTitle && !hasChannel && !hasCategory && hasMaxLength) {
          
            return this.videoRepository.findByLengthLessThanEqual(maxLength);
      
        } else {

            return this.videoRepository.findAllByOrderByIdAsc();
          
        }
        
    }
    
}
