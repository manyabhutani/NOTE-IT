package com.tjv.fit.NoteIt.presentation.controller;

import com.tjv.fit.NoteIt.presentation.controller.dto.AuthenticationDTO;
import com.tjv.fit.NoteIt.presentation.controller.dto.ImageDTO;
import com.tjv.fit.NoteIt.presentation.controller.dto.NoteDTO;
import com.tjv.fit.NoteIt.presentation.controller.dto.UserDTO;
import com.tjv.fit.NoteIt.business.services.*;
import jakarta.persistence.NonUniqueResultException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")  // Set a base path for user-related endpoints
public class    UserController {

    private final UserServiceImpl userService;
    private final UserNoteServiceImp userNoteService;
    private final NoteServiceImpl noteService;
    private final ImageServiceImpl imageService;


    @Autowired
    public UserController(UserServiceImpl userService, UserNoteServiceImp userNoteService, NoteServiceImpl noteService, ImageServiceImpl imageService) {
        this.userService = userService;
        this.userNoteService = userNoteService;
        this.noteService = noteService;
        this.imageService = imageService;
    }
    @GetMapping
    public ResponseEntity<?> readAllUsers() {
        try {
            return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error getting users: " + e.getMessage());
            return new ResponseEntity<>("Error getting users", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {if(userService.getUserById(id) == null){
            return new ResponseEntity<>("User does not exist ", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error getting user: " + e.getMessage());
            return new ResponseEntity<>("Error getting user", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationDTO authenticationRequest) throws Exception {
        try {
            userService.authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());

            // Authentication succeeded, generate and return JWT
            final String jwt = JwtUtil.generateToken(authenticationRequest.getEmail());
            return ResponseEntity.ok(jwt);
        } catch (RuntimeException e) {
            if (e.getMessage().equals("User not found")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
            } else if (e.getMessage().equals("Invalid credentials")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            } else {

                return new ResponseEntity<>("Error authenticating", HttpStatus.BAD_REQUEST);
            }
        }
    }


    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO userDTO) {
        try {
            if (userService.userExistsByEmail(userDTO.getEmail())) {
                System.out.println("User with this email already exists");
                return new ResponseEntity<>("User with this email already exists", HttpStatus.CONFLICT);
            }

            UserDTO createdUser = userService.createUser(userDTO);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (NonUniqueResultException e) {
            System.out.println("NonUniqueResultException: " + e.getMessage());
            return new ResponseEntity<>("Multiple users found with the same email", HttpStatus.CONFLICT);
        } catch (Exception e) {
            System.out.println("Error creating user: " + e.getMessage());
            return new ResponseEntity<>("Error creating user", HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDTO, @PathVariable Long id) {
        try {
            if(userService.getUserById(id) == null){
                return new ResponseEntity<>("User does not exist ", HttpStatus.NOT_FOUND);
            }
            userService.updateUser(userDTO, id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error updating user" + e.getMessage());
            return new ResponseEntity<>("Error updating user", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            if(userService.getUserById(id) == null){
                return new ResponseEntity<>("User does not exist ", HttpStatus.NOT_FOUND);
            }
            userService.deleteUser(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.err.println("Error deleting user: " + e.getMessage());
            return new ResponseEntity<>("Error deleting user", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{userId}/notes")
    ResponseEntity<?> readAllNotesByUserId(@PathVariable Long userId) {
        try {
            List<NoteDTO> notes = userNoteService.getNotesByUserId(userId);
            if(userService.getUserById(userId) == null){
                return new ResponseEntity<>("User does not exist ", HttpStatus.NOT_FOUND);
            }
            if (!notes.isEmpty()) {
                return new ResponseEntity<>(notes, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No notes found for the user", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error getting notes", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{userId}/notes")
    ResponseEntity<?> createNewNote(@PathVariable Long userId, @RequestBody NoteDTO noteDTO) {
        try {
            if(userService.getUserById(userId) == null){
                return new ResponseEntity<>("User does not exist ", HttpStatus.NOT_FOUND);
            }
            NoteDTO createdNote = noteService.createNewNote(userId, noteDTO);
            return new ResponseEntity<>(createdNote, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error creating note, please check payload.", HttpStatus.BAD_REQUEST);
        }
    }

    // MARK: Images

    @GetMapping("/{userId}/images")
    ResponseEntity<?> getAllImagesForUserId(@PathVariable Long userId) {
        try {
            if(userService.getUserById(userId) == null){
                return new ResponseEntity<>("User does not exist ", HttpStatus.NOT_FOUND);
            }
            List<ImageDTO> images = imageService.getImagesByUserId(userId);
            if (!images.isEmpty()) {
                return new ResponseEntity<>(images, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No images found for the user", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error getting images or user", HttpStatus.BAD_REQUEST);
        }
    }
}
