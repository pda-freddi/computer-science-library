package com.pdafr.computer.science.library.validator;

import com.pdafr.computer.science.library.model.Article;

public class ArticleValidator {
  
    public static boolean validateArticle(Article article) {
        boolean hasTitle = article.getTitle() != null && !article.getTitle().isBlank() && !article.getTitle().isEmpty();
        boolean hasAuthor = article.getAuthor() != null && !article.getAuthor().isBlank() && !article.getAuthor().isEmpty();
        boolean hasCategory = article.getCategory() != null;
        boolean hasReadTime = article.getReadTime() != null;
        boolean hasLink = article.getLink() != null &&  !article.getLink().isBlank() && !article.getLink().isEmpty();
        if (hasTitle && hasAuthor && hasCategory && hasReadTime && hasLink) {
            return true;
        } else {
            return false;
        }
    }
    
    public static boolean validateTitle(String title) {
        return title != null && !title.isBlank() && !title.isEmpty();
    }
  
    public static boolean validateAuthor(String author) {
        return author != null && !author.isBlank() && !author.isEmpty();
    }
  
    public static boolean validateReadTime(Integer readTime) {
        return readTime != null && readTime.intValue() != 0;
    }
    
    public static boolean validateLink(String link) {
        return link != null && !link.isBlank() && !link.isEmpty();
    }
  
}
