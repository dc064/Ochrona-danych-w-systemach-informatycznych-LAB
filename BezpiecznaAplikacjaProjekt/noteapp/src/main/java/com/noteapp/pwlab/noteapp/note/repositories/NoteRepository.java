package com.noteapp.pwlab.noteapp.note.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.noteapp.pwlab.noteapp.note.entities.Note;

public interface NoteRepository extends JpaRepository<Note, Long> {
    
}
