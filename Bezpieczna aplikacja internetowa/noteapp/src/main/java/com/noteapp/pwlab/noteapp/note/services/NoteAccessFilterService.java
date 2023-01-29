package com.noteapp.pwlab.noteapp.note.services;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.noteapp.pwlab.noteapp.note.entities.Note;
import com.noteapp.pwlab.noteapp.note.repositories.NoteRepository;
import com.noteapp.pwlab.noteapp.user.entities.User;
import com.noteapp.pwlab.noteapp.user.entities.UserIdentity;
import com.noteapp.pwlab.noteapp.user.requests.AddUserAccessRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoteAccessFilterService {
    private final NoteRepository noteRepository;

	public boolean verifyAccess(User user, Note note, Model model) {
		if (Objects.equals(user.getId(), note.getUser().getId())) {
			model.addAttribute("owner", true);
			model.addAttribute("id", note.getId());
			model.addAttribute("addedUsers", note.getUsersWithAccess());
			model.addAttribute("addUserAccessRequest", new AddUserAccessRequest());
			return true;
		}
		if (note.getIsPublic()) {
			return true;
		}
		return note.getUsersWithAccess().stream().map(UserIdentity::getUsername).anyMatch(o -> Objects.equals(o.toUpperCase(Locale.ROOT), user.getUsername().toUpperCase(Locale.ROOT)));
	}

	public void addNotes(User user, Model model) {
		List<Note> notes = noteRepository.findAll();
		if (user != null) {
			List<Note> myNotes = notes.stream().filter(o ->
					Objects.equals(o.getUser().getId(), user.getId())).collect(Collectors.toList());
			model.addAttribute("my_notes", myNotes);

			if(user.getUsername() != null) {
			List<Note> agNotes = notes.stream().filter(o ->
							o.getUsersWithAccess()
									.stream()
									.anyMatch(a -> a.getUsername() != null && Objects.equals(a.getUsername().toUpperCase(Locale.ROOT), user.getUsername().toUpperCase(Locale.ROOT))))
					.collect(Collectors.toList());
			model.addAttribute("access_granted", agNotes);
			}
		}
		List<Note> publicNotes = notes.stream().filter(Note::getIsPublic).collect(Collectors.toList());
		model.addAttribute("public_notes", publicNotes);
	}
}
