package com.tjv.fit.NoteIt.presentation.controller;

import com.tjv.fit.NoteIt.presentation.controller.dto.ImageDTO;
import com.tjv.fit.NoteIt.business.services.ImageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/images")

public class ImageController {
    private final ImageServiceImpl imageService;

    @Autowired
    public ImageController(ImageServiceImpl imageService) {
        this.imageService = imageService;
    }

    @GetMapping()
    public ResponseEntity<?> getAllImages() {
        try {
            return new ResponseEntity<>(imageService.getAllImages(), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error getting images: " + e.getMessage());
            return new ResponseEntity<>("Error getting images", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{imageId}")
    public ResponseEntity<?> getImageById(@PathVariable Long imageId) {
        try {
            if(imageService.getImageById(imageId) == null) {
            return new ResponseEntity<>("Image not found", HttpStatus.NOT_FOUND);
        }
            return new ResponseEntity<>(imageService.getImageById(imageId), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error getting images: " + e.getMessage());
            return new ResponseEntity<>("Images Not Found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{imageId}")
    public ResponseEntity<?> updateImageById(@PathVariable Long imageId, @RequestBody ImageDTO imageDTO) {
        try {
            if(imageService.getImageById(imageId) == null) {
                return new ResponseEntity<>("Image not found", HttpStatus.NOT_FOUND);
            }
            imageService.updateImageById(imageId, imageDTO);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.out.println("Error updating image: " + e.getMessage());
            return new ResponseEntity<>("Error updating image", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{imageId}")
    public ResponseEntity<?> deleteImageById(@PathVariable Long imageId) {
        try {
            if(imageService.getImageById(imageId) == null) {
                return new ResponseEntity<>("Image not found", HttpStatus.NOT_FOUND);
            }
            imageService.deleteImageById(imageId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.out.println("Error deleting image: " + e.getMessage());
            return new ResponseEntity<>("Error deleting image", HttpStatus.BAD_REQUEST);
        }
    }
}
