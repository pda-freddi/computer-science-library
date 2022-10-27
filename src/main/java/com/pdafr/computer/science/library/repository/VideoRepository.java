package com.pdafr.computer.science.library.repository;

import org.springframework.data.repository.CrudRepository;

import com.pdafr.computer.science.library.model.Video;

public interface VideoRepository extends CrudRepository<Video, Integer> {

}
