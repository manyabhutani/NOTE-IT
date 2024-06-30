package com.tjv.fit.NoteIt.data.repositories;

import com.tjv.fit.NoteIt.data.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findAll();

    @Query("SELECT n from Image n WHERE n.note.id = ?1")
    List<Image> findByNoteId(Long id);

    @Query("SELECT i FROM Image i JOIN i.note n LEFT JOIN n.contributors c WHERE n.ownerId = :userId OR c.id = :userId")
    List<Image> findAllByOwnerOrContributor(@Param("userId") Long userId);

}