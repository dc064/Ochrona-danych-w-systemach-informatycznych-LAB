package com.noteapp.pwlab.noteapp.user.requests;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class CreateUserRequest {
    @NotEmpty
	@Size(min = 4, max = 32)
	@Pattern(regexp = "\\A\\p{ASCII}*\\z", message = "only ascii characters")
	private String username;
	
    @NotEmpty
	@Email
	private String email;
	
    @NotEmpty
	@Size(min = 8, max = 32)
	private String password;
}
