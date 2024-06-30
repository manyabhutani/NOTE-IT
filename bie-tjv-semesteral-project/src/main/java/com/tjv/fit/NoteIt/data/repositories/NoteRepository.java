package com.tjv.fit.NoteIt.data.repositories;

import com.tjv.fit.NoteIt.data.entities.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {


    List<Note> findAllByOwnerId(Long ownerId);

    @Query("SELECT n FROM Note n WHERE n.title LIKE %:keyword% OR n.content LIKE %:keyword%")
    List<Note> search(@Param("keyword") String keyword);

}

