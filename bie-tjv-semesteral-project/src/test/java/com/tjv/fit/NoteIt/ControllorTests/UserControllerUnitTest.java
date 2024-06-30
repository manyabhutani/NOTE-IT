package com.tjv.fit.NoteIt.ControllorTests;

import com.tjv.fit.NoteIt.business.services.UserServiceImpl;
import com.tjv.fit.NoteIt.presentation.controller.UserController;
import com.tjv.fit.NoteIt.presentation.controller.dto.UserDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;


import java.util.Collections;
import java.util.List;
@SpringBootTest
public class UserControllerUnitTest {


    @Mock
    private UserServiceImpl userService;{
        this.userService = new UserServiceImpl();
    }

    @InjectMocks
    private UserController userController;

    @Test
    void readAllUsers_ReturnsListOfUsers() {
        // Arrange
        List<UserDTO> expectedUsers = List.of(
                new UserDTO(1L, "user1", "password1", "user1@example.com"),
                new UserDTO(2L, "user2", "password2", "user2@example.com")
        );
        Mockito.when(userService.getAllUsers()).thenReturn(expectedUsers);

        // Act
        ResponseEntity<?> responseEntity = userController.readAllUsers();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedUsers, responseEntity.getBody());
    }

    @Test
    void readAllUsers_ReturnsEmptyList() {
        // Arrange
        Mockito.when(userService.getAllUsers()).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<?> responseEntity = userController.readAllUsers();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(Collections.emptyList(), responseEntity.getBody());
    }


}
