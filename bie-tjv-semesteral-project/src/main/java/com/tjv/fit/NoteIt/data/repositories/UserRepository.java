package com.tjv.fit.NoteIt.data.repositories;

import com.tjv.fit.NoteIt.data.entities.Note;
import com.tjv.fit.NoteIt.data.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User , Long>{

    User findByEmail(String email);

    User findByUsername(String username);

}
