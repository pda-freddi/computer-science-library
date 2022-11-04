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
import com.pdafr.computer.science.library.model.Video;
import com.pdafr.computer.science.library.repository.VideoRepository;
import com.pdafr.computer.science.library.validator.VideoValidator;

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
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Video> getAllVideos(@RequestParam(name="sort_by", required=false) String sortBy, @RequestParam(required=false) Boolean asc) {
        // Initialize asc variable to a default value if it's not specified in the request
        if (asc == null) {
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
        // If sort by parameter is not specified, return list of videos ordered by ID in asc or desc order
        if (asc) {    
            return this.videoRepository.findAllByOrderByIdAsc();
        } else {
            return this.videoRepository.findAllByOrderByIdDesc();
        }
    }
    
    /**
     * Get a video by its ID
     * @param id 
     * @return a video object
     * @throws EntityNotFoundException if no videos matches the ID provided 
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
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
        if (!VideoValidator.validateVideo(video)) {
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
     * @throws InvalidInputObjectException if input video object is missing fields
     */
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Video updateVideo(@PathVariable("id") Integer id, @RequestBody Video video) {
        Optional<Video> videoToUpdateOptional = this.videoRepository.findById(id);
        if (!videoToUpdateOptional.isPresent()) {
            throw new EntityNotFoundException("Can't find a video with ID " + id);
        }
        if (!VideoValidator.validateVideo(video)) {
          throw new InvalidInputObjectException("Input video object missing field(s)");
        }
        Video videoToUpdate = videoToUpdateOptional.get();
        videoToUpdate.setTitle(video.getTitle());
        videoToUpdate.setChannel(video.getChannel());
        videoToUpdate.setCategory(video.getCategory());
        videoToUpdate.setLength(video.getLength());
        videoToUpdate.setLink(video.getLink());
        Video updatedVideo = this.videoRepository.save(videoToUpdate);
        return updatedVideo;
    }
    
    /**
     * Patch a video object
     * @param id of video to be patched
     * @param video object with patched field(s)
     * @return patched video object
     * @throws EntityNotFoundException if no video matches the ID provided
     */
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Video patchVideo(@PathVariable("id") Integer id, @RequestBody Video video) {
        Optional<Video> videoToPatchOptional = this.videoRepository.findById(id);
        if (!videoToPatchOptional.isPresent()) {
            throw new EntityNotFoundException("Can't find a video with ID " + id);
        }
        Video videoToPatch = videoToPatchOptional.get();
        if (VideoValidator.validateTitle(video.getTitle())) {
            videoToPatch.setTitle(video.getTitle());
        }
        if (VideoValidator.validateChannel(video.getChannel())) {
            videoToPatch.setChannel(video.getChannel());
        }
        if (video.getCategory() != null) {
            videoToPatch.setCategory(video.getCategory());
        }
        if (VideoValidator.validateLength(video.getLength())) {
            videoToPatch.setLength(video.getLength());
        }
        if (VideoValidator.validateLink(video.getLink())) {
            videoToPatch.setLink(video.getLink());
        }
        Video patchedVideo = this.videoRepository.save(videoToPatch);
        return patchedVideo;
    }
    
    /**
     * Delete a video object
     * @param id of the video to delete
     * @return message confirming deletion
     * @throws EntityNotFoundException if no video matches the ID provided
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
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
