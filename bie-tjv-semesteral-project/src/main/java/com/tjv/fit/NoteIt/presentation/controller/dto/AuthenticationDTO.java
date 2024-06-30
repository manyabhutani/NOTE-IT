package com.tjv.fit.NoteIt.presentation.controller.dto;

public class AuthenticationDTO {
    private String email;
    private String password;

    public AuthenticationDTO() {
    }

    public AuthenticationDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail(){
        return this.email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getPassword(){
        return this.password;
    }

    public void setPassword(String password){
        this.password = password;
    }
}
