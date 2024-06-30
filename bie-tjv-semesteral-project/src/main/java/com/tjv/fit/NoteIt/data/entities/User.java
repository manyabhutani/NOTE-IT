package com.tjv.fit.NoteIt.data.entities;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    @Column(unique = true)

    private String email;

    @ManyToMany(cascade = { CascadeType.PERSIST })
    @JoinTable(
            name = "user_note",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "note_id") }
    )
    private Set<Note> contributedNotes = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long userId) {
        this.id = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Note> getNotes() {
        return contributedNotes;
    }

    public void setNotes(Set<Note> notes) {
        this.contributedNotes = notes;
    }

    public void addNoteToUser(Note note) {
        contributedNotes.add(note);
    }

    public void removeNoteFromUser(Note note) {
        contributedNotes.remove(note);
    }

}
