package com.tjv.fit.NoteIt.data.entities;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "notes")
public class Note {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long ownerId;
    private String title;
    private String content;
    private Date creationDate;
    private Date modificationDate;

    public Note() {}

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "contributedNotes")
    private Set<User> contributors = new HashSet<>();

    @OneToMany(mappedBy = "note",cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Image> images = new HashSet<>();

    public Note( Long ownerId, String title, String content, Date creationDate, Date modificationDate) {
        this.ownerId = ownerId;
        this.title = title;
        this.content = content;
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public void setId(Long id) {
        this.id = id;
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
        return this.creationDate != null ? (Date) this.creationDate.clone() : null;
    }

    public Set<User> getContributors() {
        return contributors;
    }

    public void addContributor(User contributor) {
        this.contributors.add(contributor);
    }

    public void removeContributor(User contributor) {
        this.contributors.remove(contributor);
    }

    public Date getModificationDate() {
        return this.modificationDate != null ? (Date) this.modificationDate.clone() : null;
    }

    public void setCreationDate(Date creationDate){
        this.creationDate = creationDate;
    }

    public void setModificationDate(Date modificationDate){
        this.modificationDate = modificationDate;
    }
}
