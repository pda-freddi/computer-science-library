package com.pdafr.computer.science.library.validator;

import com.pdafr.computer.science.library.enums.Category;

public class CategoryValidator {
    
    public static boolean validateFromString(String category) {
        try {
            Category.valueOf(category);
            return true;
        } catch(IllegalArgumentException e) {
            return false;
        }
    }
    
}
