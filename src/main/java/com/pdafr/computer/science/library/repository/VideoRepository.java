package com.pdafr.computer.science.library.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.pdafr.computer.science.library.enums.Category;
import com.pdafr.computer.science.library.model.Video;

public interface VideoRepository extends CrudRepository<Video, Integer> {

    //Find all videos ordered by different columns and asc/desc order
    List<Video> findAllByOrderByIdAsc();
    List<Video> findAllByOrderByIdDesc();
    List<Video> findAllByOrderByTitleAsc();
    List<Video> findAllByOrderByTitleDesc();
    List<Video> findAllByOrderByChannelAsc();
    List<Video> findAllByOrderByChannelDesc();
    List<Video> findAllByOrderByCategoryAsc();
    List<Video> findAllByOrderByCategoryDesc();
    List<Video> findAllByOrderByLengthAsc();
    List<Video> findAllByOrderByLengthDesc();
    
    // Find a list of videos by matching with columns
    // One colum
    List<Video> findByTitleContainingIgnoreCase(String title);
    List<Video> findByChannelContainingIgnoreCase(String channel);
    List<Video> findByCategory(Category category);
    List<Video> findByLengthLessThanEqual(Integer maxLength);
    
    // Two Columns
    List<Video> findByTitleContainingIgnoreCaseAndChannelContainingIgnoreCase(String title, String channel);
    List<Video> findByTitleContainingIgnoreCaseAndCategory(String title, Category category);
    List<Video> findByChannelContainingIgnoreCaseAndCategory(String channel, Category category);
    List<Video> findByTitleContainingIgnoreCaseAndLengthLessThanEqual(String title, Integer maxLength);
    List<Video> findByChannelContainingIgnoreCaseAndLengthLessThanEqual(String Channel, Integer maxLength);
    List<Video> findByCategoryAndLengthLessThanEqual(Category category, Integer maxLength);
    
    // Three columns  
    List<Video> findByTitleContainingIgnoreCaseAndChannelContainingIgnoreCaseAndCategory(String title, String channel, Category category);
    List<Video> findByTitleContainingIgnoreCaseAndChannelContainingIgnoreCaseAndLengthLessThanEqual(String title, String channel, Integer maxLength);
    List<Video> findByTitleContainingIgnoreCaseAndCategoryAndLengthLessThanEqual(String title, Category category, Integer maxLength);
    List<Video> findByChannelContainingIgnoreCaseAndCategoryAndLengthLessThanEqual(String channel, Category category, Integer maxLength);
   
    // Four columns
    List<Video> findByTitleContainingIgnoreCaseAndChannelContainingIgnoreCaseAndCategoryAndLengthLessThanEqual(String title, String channel, Category category, Integer maxLength);
  
}
