package com.tjv.fit.NoteIt.presentation.controller.dto;

import com.tjv.fit.NoteIt.data.entities.Note;

import java.util.Date;

public class NoteDTO {
    private Long id;
    private Long ownerId;
    private String title;
    private String content;
    private Date creationDate;
    private Date modificationDate;

    public NoteDTO(Long id, Long ownerId, String title, String content, Date creationDate, Date modificationDate) {
        this.id = id;
        this.ownerId = ownerId;
        this.title = title;
        this.content = content;
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
    }

    public Note toEntity() {
        return new Note(
                this.ownerId,
                this.title,
                this.content,
                this.creationDate,
                this.modificationDate
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public Long getOwnerId() {
        return this.ownerId;
    }

    public void setOwnerId(Long ownerId){
        this.ownerId = ownerId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content){
        this.content = content;
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(Date creationDate){
        this.creationDate = creationDate;
    }

    public Date getModificationDate() {
        return this.modificationDate;
    }

    public void setModificationDate(Date modificationDate){
        this.modificationDate = modificationDate;
    }



}