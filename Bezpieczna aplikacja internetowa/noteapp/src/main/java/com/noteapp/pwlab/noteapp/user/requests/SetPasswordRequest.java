package com.noteapp.pwlab.noteapp.user.requests;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class SetPasswordRequest {
    @NotEmpty
	@Size(min = 8, max = 32)
    private String password;
}
