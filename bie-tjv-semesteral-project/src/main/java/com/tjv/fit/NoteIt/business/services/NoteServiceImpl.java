package com.tjv.fit.NoteIt.business.services;

import com.tjv.fit.NoteIt.presentation.controller.dto.NoteDTO;
import com.tjv.fit.NoteIt.data.entities.Note;
import com.tjv.fit.NoteIt.data.repositories.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NoteServiceImpl {

    private final NoteRepository noteRepository;

    @Autowired
    public NoteServiceImpl(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<NoteDTO> getAllNotes() {
        List<NoteDTO> notes = noteRepository
                .findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        if (notes.isEmpty()) return Collections.emptyList();
        return notes;
    }

    public NoteDTO getNoteById(Long id) {
        Optional<Note> noteOptional = noteRepository.findById(id);
        if(noteOptional.isEmpty()) {
            return null;
        }
        else {
            return toDTO(noteOptional.get());
        }
    }

    public NoteDTO createNewNote(Long ownerId, NoteDTO noteDTO) {
        noteDTO.setOwnerId(ownerId);
        Note newNote = noteDTO.toEntity();
        newNote.setCreationDate(new Date(System.currentTimeMillis()));
        newNote.setModificationDate(new Date(System.currentTimeMillis()));
        noteRepository.save(newNote);
        return toDTO(newNote);
    }

    public void delete(Long id) {
        noteRepository.deleteById(id);
    }

    public void update(NoteDTO noteDto,Long id) {
        Optional<Note> noteToUpdate = noteRepository.findById(id);

        if(noteToUpdate.isEmpty()) {
            throw new RuntimeException("Note not found");
        }
        else {
            Note updatedNote = noteDto.toEntity();
            noteToUpdate.get().setTitle(updatedNote.getTitle());
            noteToUpdate.get().setContent(updatedNote.getContent());
            noteToUpdate.get().setModificationDate(new Date(System.currentTimeMillis()));
            noteRepository.save(noteToUpdate.get());
        }
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

//    public List<NoteDTO> searchByKeyword(String keyword) {
//        return noteRepository.findAllByKeyword(keyword);
//    }


}

