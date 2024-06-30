package com.tjv.fit.NoteIt.business.services;

import com.tjv.fit.NoteIt.presentation.controller.dto.UserDTO;
import com.tjv.fit.NoteIt.data.entities.User;
import com.tjv.fit.NoteIt.data.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl {

    private final UserRepository UserRepository;


    public UserServiceImpl(UserRepository userRepository) {
        this.UserRepository = userRepository;
    }


    public void authenticate(String email, String password) {
        User user = UserRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // Check password only if user is not null
        if (user.getPassword() == null || !user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid credentials");
        }
        // Successful authentication
    }


    public UserDTO getUserById(Long id) {
        Optional<User> user = UserRepository.findById(id);
        if (user.isEmpty()) {
            return null;
        }
        return toDTO(user.get());
    }

    public List<UserDTO> getAllUsers() {
        List<UserDTO> users = UserRepository
                .findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        if (users.isEmpty()) return Collections.emptyList();
        return users;
    }

    public UserDTO createUser(UserDTO userDTO) {
        if (userDTO.getUsername() == null || userDTO.getEmail().isEmpty()) {
            throw new RuntimeException("User cannot be null");
        }
        User newUser = userDTO.toEntity();
        UserRepository.save(newUser);
        return toDTO(newUser);
    }

    public boolean userExistsByEmail(String email) {
        return UserRepository.findByEmail(email) != null;
    }

    public void deleteUser(Long id) {
        UserRepository.deleteById(id);
    }

    public void updateUser(UserDTO userDto, Long id) {
        Optional<User> userToUpdate = UserRepository.findById(id);

        if (userToUpdate.isEmpty()) {
            throw new RuntimeException("User not found");
        } else {
            if( userDto.getUsername() == null || userDto.getEmail() == null) {
                throw new RuntimeException("username or email cannot be null");
            }
            if( UserRepository.findByEmail(userDto.getEmail()) != null) {
                throw new RuntimeException("username or email already exists");
            }
            User updatedUser = userDto.toEntity();
            userToUpdate.get().setUsername(updatedUser.getUsername());
            userToUpdate.get().setPassword(updatedUser.getPassword());
            userToUpdate.get().setEmail(updatedUser.getEmail());
            UserRepository.save(userToUpdate.get());
        }
    }

    private UserDTO toDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail()
        );
    }
}
