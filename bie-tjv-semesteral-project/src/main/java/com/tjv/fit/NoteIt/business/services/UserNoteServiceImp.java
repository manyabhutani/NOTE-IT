package com.tjv.fit.NoteIt.business.services;

import com.tjv.fit.NoteIt.presentation.controller.dto.NoteDTO;
import com.tjv.fit.NoteIt.presentation.controller.dto.UserDTO;
import com.tjv.fit.NoteIt.data.entities.Note;
import com.tjv.fit.NoteIt.data.entities.User;
import com.tjv.fit.NoteIt.data.repositories.NoteRepository;
import com.tjv.fit.NoteIt.data.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserNoteServiceImp {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NoteRepository noteRepository;



    public List<NoteDTO> getNotesByUserId(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if(userOptional.isEmpty()) {
            throw new EntityNotFoundException("User not found");
        }
        else {
            User user = userOptional.get();
            List<Note> notes = noteRepository.findAllByOwnerId(userId);
            return notes.stream().map(this::toDTO).collect(Collectors.toList());
        }
    }

    public void updateNotesForUser(Long userId, List<Long> noteIds) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // Clear existing notes and add the new ones
            user.getNotes().clear();
            noteIds.forEach(noteId -> {
                Optional<Note> noteOptional = noteRepository.findById(noteId);
                noteOptional.ifPresent(user.getNotes()::add);
            });

            userRepository.save(user);
        } else {
            throw new EntityNotFoundException("User not found with ID: " + userId);
        }
    }

    public void removeNote(Long noteId) {
        Optional<Note> noteOptional = noteRepository.findById(noteId);
        if (noteOptional.isPresent()) {
            Note note = noteOptional.get();
            Set<User> users = note.getContributors();
            users.forEach(user -> user.getNotes().remove(note));
            noteRepository.deleteById(noteId);
        } else {
            throw new EntityNotFoundException("Note not found with ID: " + noteId);
        }
    }

    public List<UserDTO> getContributorsByNoteId(Long noteId) {
        Optional<Note> noteOptional = noteRepository.findById(noteId);
        if (noteOptional.isPresent()) {
            Note note = noteOptional.get();
            return note.getContributors().stream().map(this::toDTO).collect(Collectors.toList());
        } else {
            throw new EntityNotFoundException("Note not found with ID: " + noteId);
        }
    }

    public void addContributorToNote(Long noteId, Long userId) {
        Optional<Note> noteOptional = noteRepository.findById(noteId);
        Optional<User> userOptional = userRepository.findById(userId);
        if (noteOptional.isPresent() && userOptional.isPresent()) {
            Note note = noteOptional.get();
            User user = userOptional.get();
            user.addNoteToUser(note);
            note.addContributor(user);
            noteRepository.save(note);
        } else {
            throw new EntityNotFoundException("Note or User not found");
        }
    }

    public void deleteContributorFromNoteById (Long noteId, Long userId) {
        Optional<Note> noteOptional = noteRepository.findById(noteId);
        Optional<User> userOptional = userRepository.findById(userId);
        if (noteOptional.isPresent() && userOptional.isPresent()) {
            Note note = noteOptional.get();
            User user = userOptional.get();
            user.removeNoteFromUser(note);
            note.removeContributor(user);
            noteRepository.save(note);
        } else {
            throw new EntityNotFoundException("Note or User not found");
        }
    }

    public List<NoteDTO> searchNotes(String keyword) {
        List<Note> notes = noteRepository.search(keyword);
        return notes.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private NoteDTO toDTO(Note note) {
        return new NoteDTO(
                note.getId(),
                note.getOwnerId(),
                note.getTitle(),
                note.getContent(),
                note.getCreationDate(),
                note.getModificationDate());
    }

    private UserDTO toDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail());
    }

}


