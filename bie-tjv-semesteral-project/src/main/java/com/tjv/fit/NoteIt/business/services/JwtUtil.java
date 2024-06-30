package com.tjv.fit.NoteIt.business.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.util.Date;

public class JwtUtil {


    private static final String SECRET_KEY = "IdontKnowWhatImDoingontKnowWhatImDoingontKnowWhatImDoingontKnowWhatImDoingontKnowWhatImDoingIdontKnowWhatImDoingontKnowWhatImDoingontKnowWhatImDoingontKnowWhatImDoingontKnowWhatImDoing";

    public static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))  // 10 hours token validity
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
}