package com.pdafr.computer.science.library.validator;

import com.pdafr.computer.science.library.model.Video;

public class VideoValidator {

    public static boolean validateVideo(Video video) {
        boolean hasTitle = video.getTitle() != null && !video.getTitle().isBlank() && !video.getTitle().isEmpty();
        boolean hasAuthor = video.getChannel() != null && !video.getChannel().isBlank() && !video.getChannel().isEmpty();
        boolean hasCategory = video.getCategory() != null;
        boolean hasLength = video.getLength() != null;
        boolean hasLink = video.getLink() != null &&  !video.getLink().isBlank() && !video.getLink().isEmpty();
        if (hasTitle && hasAuthor && hasCategory && hasLength && hasLink) {
            return true;
        } else {
            return false;
        }
    }
    
    public static boolean validateTitle(String title) {
        return title != null && !title.isBlank() && !title.isEmpty();
    }

    public static boolean validateChannel(String channel) {
        return channel != null && !channel.isBlank() && !channel.isEmpty();
    }

    public static boolean validateLength(Integer length) {
        return length != null && length.intValue() != 0;
    }
  
    public static boolean validateLink(String link) {
        return link != null && !link.isBlank() && !link.isEmpty();
    }
  
}
