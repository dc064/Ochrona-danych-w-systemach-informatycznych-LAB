package com.noteapp.pwlab.noteapp.user.requests;

import javax.validation.constraints.Email;

import lombok.Data;

@Data
public class LoginRequest {
    @Email
	private String email;
	private String password;
}
