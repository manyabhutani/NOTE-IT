package com.tjv.fit.NoteIt.presentation.controller.dto;

import com.tjv.fit.NoteIt.data.entities.Image;

import java.util.Date;

public class ImageDTO {
    private Long imageId;
    private Date createdAt;
    private String imageURL;
    private Long parentNoteId;

    public ImageDTO(Long imageId, Date createdAt, String imageURL, Long parentNoteId) {
        this.imageId = imageId;
        this.createdAt = createdAt;
        this.imageURL = imageURL;
        this.parentNoteId = parentNoteId;
    }

    public Image toEntity() {
        Image image = new Image();
        image.setId(this.imageId);
        image.setCreatedAt(this.createdAt);
        image.setImageURL(this.imageURL);
        image.setNoteId(this.parentNoteId);
        return image;
    }

    public Long getImageId() {
        return this.imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getImageURL() {
        return this.imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public Long getParentNoteId() {
        return this.parentNoteId;
    }

    public void setParentNoteId(Long parentNoteId) {
        this.parentNoteId = parentNoteId;
    }

}