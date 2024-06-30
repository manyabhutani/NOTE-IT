package com.tjv.fit.NoteIt.presentation.controller;


import com.tjv.fit.NoteIt.presentation.controller.dto.ImageDTO;
import com.tjv.fit.NoteIt.presentation.controller.dto.NoteDTO;
import com.tjv.fit.NoteIt.business.services.ImageServiceImpl;
import com.tjv.fit.NoteIt.business.services.NoteServiceImpl;
import com.tjv.fit.NoteIt.business.services.UserNoteServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notes")
public class NoteController {

    private final NoteServiceImpl noteService;
    private final UserNoteServiceImp userNoteService;
    private final ImageServiceImpl imageService;

    @Autowired
    public NoteController(NoteServiceImpl noteService, UserNoteServiceImp userNoteService, ImageServiceImpl imageService) {
        this.noteService = noteService;
        this.userNoteService = userNoteService;
        this.imageService = imageService;
    }

    @GetMapping()
    ResponseEntity<?> readAllNotes(@RequestParam(value = "search", required = false, defaultValue = "") String keyword) {
        if( keyword == null || keyword.isEmpty() ) {
            try {
                return new ResponseEntity<>(noteService.getAllNotes(), HttpStatus.OK);
            } catch (Exception e) {
                System.out.println("Error getting notes: " + e.getMessage());
                return new ResponseEntity<>("Error getting notes", HttpStatus.BAD_REQUEST);
            }
        } else {
            try {
                return new ResponseEntity<>(userNoteService.searchNotes(keyword), HttpStatus.OK);
            } catch (Exception e) {
                System.out.println("Error searching notes: " + e.getMessage());
                return new ResponseEntity<>("Error searching notes", HttpStatus.BAD_REQUEST);
            }
        }
    }

    @GetMapping("/{noteId}")
    ResponseEntity<?> getNoteById(@PathVariable Long noteId) {
        try {
            if(noteService.getNoteById(noteId) == null){
                return new ResponseEntity<>("Note does not exist.", HttpStatus.NOT_FOUND);

            }
            return new ResponseEntity<>(noteService.getNoteById(noteId), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error getting note: " + e.getMessage());
            return new ResponseEntity<>("Error getting note", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{noteId}")
    ResponseEntity<?> updateNoteByUserId(@PathVariable Long noteId, @RequestBody NoteDTO noteDTO) {
        try {
            noteService.update(noteDTO, noteId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating note, please check payload.", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{noteId}")
    ResponseEntity<?> deleteNoteByUserId(@PathVariable Long noteId) {
        try {
            if( noteService.getNoteById(noteId) == null ) {
                return new ResponseEntity<>("Note not found", HttpStatus.NOT_FOUND);
            }
            userNoteService.removeNote(noteId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>("Error removing note from user: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // MARK: Contributors

    @GetMapping("/{noteId}/contributors")
    ResponseEntity<?> getContributorsByNoteId(@PathVariable Long noteId) {
        try {
            if( noteService.getNoteById(noteId) == null ) {
                return new ResponseEntity<>("Note not found", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(userNoteService.getContributorsByNoteId(noteId), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error getting contributors: " + e.getMessage());
            return new ResponseEntity<>("Error getting contributors", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{noteId}/contributors")
    ResponseEntity<?> addContributorToNoteById(@PathVariable Long noteId, @RequestBody Long userId) {
        try {
            if( noteService.getNoteById(noteId) == null ) {
                return new ResponseEntity<>("Note not found", HttpStatus.NOT_FOUND);
            }
            userNoteService.addContributorToNote(noteId, userId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error adding contributor to note: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{noteId}/contributors/{userId}")
    ResponseEntity<?> removeContributorFromNoteById(@PathVariable Long noteId, @PathVariable Long userId) {
        try {
            userNoteService.deleteContributorFromNoteById(noteId, userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>("Error removing contributor from note: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // MARK: Images

    @GetMapping("/{noteId}/images")
    ResponseEntity<?> getImagesByNoteId(@PathVariable Long noteId) {
        try {
            if( noteService.getNoteById(noteId) == null ) {
                return new ResponseEntity<>("Note not found", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(imageService.getImagesByNoteId(noteId), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error getting images: " + e.getMessage());
            return new ResponseEntity<>("Error getting images", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{noteId}/images")
    ResponseEntity<?> addImageToNote(@PathVariable Long noteId, @RequestBody ImageDTO imageDTO) {
        try {
            if( noteService.getNoteById(noteId) == null ) {
                return new ResponseEntity<>("Note not found", HttpStatus.NOT_FOUND);
            }
            ImageDTO createdImage = imageService.createImage(noteId, imageDTO);
            return new ResponseEntity<>(createdImage, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error adding image to note: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}