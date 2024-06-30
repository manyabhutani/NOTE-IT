package com.tjv.fit.NoteIt.ControllorTests;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;
@ActiveProfiles("test")


class ImageControllerAPITest {

    private static final String BASE_URL = "http://localhost:8080";

    @Test
    void testGetAllImages() {
        try {
            URL url = new URL(BASE_URL + "/images");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            assertEquals(200, responseCode, "Expected status code 200, but got " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            in.close();
            connection.disconnect();

            System.out.println("Response Content for getAllImages: " + content.toString());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception occurred during testGetAllImages");
        }
    }
}