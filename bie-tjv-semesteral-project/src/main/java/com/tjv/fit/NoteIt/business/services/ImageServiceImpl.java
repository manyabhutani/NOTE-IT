package com.tjv.fit.NoteIt.business.services;

import com.tjv.fit.NoteIt.presentation.controller.dto.ImageDTO;
import com.tjv.fit.NoteIt.data.entities.Image;
import com.tjv.fit.NoteIt.data.entities.Note;
import com.tjv.fit.NoteIt.data.repositories.ImageRepository;
import com.tjv.fit.NoteIt.data.repositories.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ImageServiceImpl {

    private final ImageRepository imageRepository;
    private final NoteRepository noteRepository;

    @Autowired
    public ImageServiceImpl(ImageRepository imageRepository, NoteRepository noteRepository) {
        this.imageRepository = imageRepository;
        this.noteRepository = noteRepository;
    }

    public List<ImageDTO> getAllImages() {
        return imageRepository
                .findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ImageDTO getImageById(Long imageId) {
        Optional<Image> image = imageRepository.findById(imageId);
        if(image.isEmpty()) {
            return null;
        }
        else {
            return toDTO(image.get());
        }
    }

    public List<ImageDTO> getImagesByNoteId(Long noteId) {
        // Check if Note exists
        Optional<Note> optionalNote = noteRepository.findById(noteId);
        if(optionalNote.isEmpty()) {
            throw new RuntimeException("Note not found");
        }
        // Get Images with the provided parent note id
        else {
            return imageRepository
                    .findByNoteId(noteId)
                    .stream()
                    .map(this::toDTO)
                    .collect(Collectors.toList());
        }
    }

    public ImageDTO createImage(Long noteId, ImageDTO imageDTO){
        Optional<Note> optionalNote = noteRepository.findById(noteId);
        if(optionalNote.isEmpty()) {
            throw new RuntimeException("Note not found");
        }
        else {
            Image newImage = new Image(
                    new Date(System.currentTimeMillis()),
                    imageDTO.getImageURL(),
                    optionalNote.get());
            //noteRepository.save(newNote);
            imageRepository.save(newImage);
            return toDTO(newImage);
        }
    }

    public ImageDTO updateImageById(Long imageId, ImageDTO imageDTO) {
        Optional<Image> optionalImage = imageRepository.findById(imageId);
        if(optionalImage.isEmpty()) {
            throw new RuntimeException("Image not found");
        }
        else {
            Image image = optionalImage.get();
            //image.setCreatedAt(imageDTO.getCreatedAt());
            image.setImageURL(imageDTO.getImageURL());
            imageRepository.save(image);
            return toDTO(image);
        }
    }
    @Transactional
    public void deleteImageById(Long imageId) {
        if(!imageRepository.existsById(imageId)) {
            throw new RuntimeException("Image not found");
        }
        else {
            imageRepository.deleteById(imageId);
        }
    }

    public List<ImageDTO> getImagesByUserId(Long userId) {
        return imageRepository
                .findAllByOwnerOrContributor(userId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private ImageDTO toDTO(Image image) {
        return new ImageDTO(
            image.getId(),
            image.getCreatedAt(),
            image.getImageURL(),
            image.getNote().getId()
        );
    }
}
