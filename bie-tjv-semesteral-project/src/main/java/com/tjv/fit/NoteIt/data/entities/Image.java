package com.tjv.fit.NoteIt.data.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date createdAt;
    private String imageURL;

    @ManyToOne
    @JoinColumn(name = "note_id")
    private Note note;

    public Image(Date createdAt, String imageURL, Note note) {
        this.createdAt = createdAt;
        this.imageURL = imageURL;
        this.note = note;
    }

    public Image() {}

    public Long getId() {
        return id;
    }

    public void setId(Long imageId) {
        this.id = imageId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date uploadDate) {
        this.createdAt = uploadDate;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageUrl) {
        this.imageURL = imageUrl;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public void setNoteId(Long noteId) {
        this.note.setId(noteId);
    }
}
