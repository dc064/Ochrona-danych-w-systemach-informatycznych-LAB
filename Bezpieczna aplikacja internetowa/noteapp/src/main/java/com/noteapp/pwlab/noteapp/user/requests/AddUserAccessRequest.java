package com.noteapp.pwlab.noteapp.user.requests;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class AddUserAccessRequest {
    @NotEmpty
	@Size(min = 4, max = 32)
	private String username;
}
