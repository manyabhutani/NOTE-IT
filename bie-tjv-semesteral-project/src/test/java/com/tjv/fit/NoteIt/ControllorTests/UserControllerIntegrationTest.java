package com.tjv.fit.NoteIt.ControllorTests;

import com.tjv.fit.NoteIt.presentation.controller.dto.UserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIntegrationTest {

        @LocalServerPort
        private int port;

        @Autowired
        private TestRestTemplate restTemplate;

        @Test
        void readAllUsers_ReturnsListOfUsers() {
            ResponseEntity<UserDTO[]> responseEntity = restTemplate.getForEntity(getBaseUrl() + "/users", UserDTO[].class);

            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

            UserDTO[] users = responseEntity.getBody();
            assertNotNull(users);
        }


        private String getBaseUrl() {
            return "http://localhost:" + port;
        }
}


