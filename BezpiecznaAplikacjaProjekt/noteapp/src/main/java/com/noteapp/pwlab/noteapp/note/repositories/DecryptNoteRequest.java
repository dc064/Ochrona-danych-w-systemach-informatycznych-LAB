package com.noteapp.pwlab.noteapp.note.repositories;

import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class DecryptNoteRequest {
    @Size(max = 32)
    private String password;
}
