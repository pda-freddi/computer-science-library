package com.pdafr.computer.science.library.validator;

import com.pdafr.computer.science.library.model.Book;

public class BookValidator {

    public static boolean validateBook(Book book) {
        boolean hasTitle = book.getTitle() != null && !book.getTitle().isBlank() && !book.getTitle().isEmpty();
        boolean hasAuthor = book.getAuthor() != null && !book.getAuthor().isBlank() && !book.getAuthor().isEmpty();
        boolean hasCategory = book.getCategory() != null;
        boolean hasNumPages = book.getNumPages() != null && book.getNumPages().intValue() != 0;
        if (hasTitle && hasAuthor && hasCategory && hasNumPages) {
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
    
    public static boolean validateNumPages(Integer numPages) {
        return numPages != null && numPages.intValue() != 0;
    }
  
}
