package com.noteservice.noteapp.entities;

public class Identity {

    public String username;
    public String email;
    private String passwordHash;
    private boolean confirmedEmail;

    public Identity (String usernanme, String email, String password) {
        this.username = usernanme;
        this.email = email;
        this.passwordHash = password;
        this.confirmedEmail = false;
    }

    public void confirmEmail() {
        this.confirmedEmail = true;
    }
}
