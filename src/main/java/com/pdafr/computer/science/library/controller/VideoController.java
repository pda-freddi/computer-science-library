package com.pdafr.computer.science.library.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
import com.pdafr.computer.science.library.model.Video;
import com.pdafr.computer.science.library.repository.VideoRepository;

@RestController
@RequestMapping("/videos")
public class VideoController {

    private VideoRepository videoRepository;
    
    public VideoController(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }
    
    /**
     * Get all video entities
     * @param sortBy optional query parameter to specify an attribute to sort the list for
     * @param asc optional query boolean parameter to specify if sorting order is ascending; defaults to true if not specified
     * @return a list of videos
     * @throws InvalidQueryParameterException
     */
    @GetMapping()
    public Iterable<Video> getAllVideos(@RequestParam(name="sort_by", required=false) String sortBy, @RequestParam(required=false) Boolean asc) {
        // Initialize asc variable to a default value if it's not specified in the request
        if (sortBy != null && asc == null) {
            asc = true;
        }
        // If sort request parameter is specified, return a sorted list of videos according to the parameter
        if (sortBy != null) {
            switch (sortBy) {
              case "title":
                  if (asc) {
                      return this.videoRepository.findAllByOrderByTitleAsc();
                  } else {
                      return this.videoRepository.findAllByOrderByTitleDesc();
                  }
              case "channel":
                  if (asc) {
                    return this.videoRepository.findAllByOrderByChannelAsc();
                  } else {
                    return this.videoRepository.findAllByOrderByChannelDesc();
                  }
              case "category":
                  if (asc) {
                    return this.videoRepository.findAllByOrderByCategoryAsc();
                  } else {
                    return this.videoRepository.findAllByOrderByCategoryDesc();
                  }
              case "length":
                if (asc) {
                  return this.videoRepository.findAllByOrderByLengthAsc();
                } else {
                  return this.videoRepository.findAllByOrderByLengthDesc();
                }
              default:
                throw new InvalidQueryParameterException(sortBy + " is not a valid query parameter");
            }
        }
        // If sort by parameter is not specified, return list of videos ordered by ID
        return this.videoRepository.findAllByOrderByIdAsc();
    }
    
    /**
     * Get a video by its ID
     * @param id 
     * @return a video object
     * @throws EntityNotFoundException if no videos matches the ID provided 
     */
    @GetMapping("/{id}")
    public Video getVideoById(@PathVariable("id") Integer id) {
        Optional<Video> videoOptional = this.videoRepository.findById(id);
        if (!videoOptional.isPresent()) {
            throw new EntityNotFoundException("Can't find a video with ID " + id);
        }
        Video video = videoOptional.get();
        return video;
    }
    
    /**
     * Save a new video object
     * @param video to be saved
     * @return newly created video object
     * @throws InvalidInputObjectException if input video object is missing fields
     */
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Video saveNewVideo(@RequestBody Video video) {
        if (video.getTitle() == null || video.getChannel() == null || video.getCategory() == null || video.getLength() == null || video.getLink() == null) {
            throw new InvalidInputObjectException("Input video object missing field(s)");
        }
        Video newVideo = this.videoRepository.save(video);
        return newVideo;
    }
    
    /**
     * Update a video object
     * @param id of video to be updated
     * @param video object with updated fields
     * @return updated video object
     * @throws EntityNotFoundException if no video matches the ID provided
     */
    @PutMapping("/{id}")
    public Video updateVideo(@PathVariable("id") Integer id, @RequestBody Video video) {
        Optional<Video> videoToUpdateOptional = this.videoRepository.findById(id);
        if (!videoToUpdateOptional.isPresent()) {
            throw new EntityNotFoundException("Can't find a video with ID " + id);
        }
        Video videoToUpdate = videoToUpdateOptional.get();
        if (video.getTitle() != null) {
            videoToUpdate.setTitle(video.getTitle());
        }
        if (video.getChannel() != null) {
            videoToUpdate.setChannel(video.getChannel());
        }
        if (video.getCategory() != null) {
            videoToUpdate.setCategory(video.getCategory());
        }
        if (video.getLength() != null) {
            videoToUpdate.setLength(video.getLength());
        }
        if (video.getLink() != null) {
            videoToUpdate.setLink(video.getLink());
        }
        Video updatedVideo = this.videoRepository.save(videoToUpdate);
        return updatedVideo;
    }
    
    /**
     * Delete a video object
     * @param id of the video to delete
     * @return message confirming deletion
     * @throws EntityNotFoundException if no video matches the ID provided
     */
    @DeleteMapping("/{id}")
    public String deleteVideo(@PathVariable("id") Integer id) {
        Optional<Video> videoToDeleteOptional = this.videoRepository.findById(id);
        if (!videoToDeleteOptional.isPresent()) {
            throw new EntityNotFoundException("Can't find a video with ID " + id);
        }
        Video videoToDelete = videoToDeleteOptional.get();
        this.videoRepository.delete(videoToDelete);
        return "Video with ID " +  id + " deleted successfully";
    }
  
}
