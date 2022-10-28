package com.pdafr.computer.science.library.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.pdafr.computer.science.library.model.Video;

public interface VideoRepository extends CrudRepository<Video, Integer> {

    List<Video> findAllByOrderByIdAsc();
    List<Video> findAllByOrderByTitleAsc();
    List<Video> findAllByOrderByTitleDesc();
    List<Video> findAllByOrderByChannelAsc();
    List<Video> findAllByOrderByChannelDesc();
    List<Video> findAllByOrderByCategoryAsc();
    List<Video> findAllByOrderByCategoryDesc();
    List<Video> findAllByOrderByLengthAsc();
    List<Video> findAllByOrderByLengthDesc();
  
}
