package com.noteapp.pwlab.noteapp.application.controllers.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.noteapp.pwlab.noteapp.note.entities.Note;
import com.noteapp.pwlab.noteapp.note.repositories.CreateNoteRequest;
import com.noteapp.pwlab.noteapp.note.repositories.DecryptNoteRequest;
import com.noteapp.pwlab.noteapp.note.repositories.NoteRepository;
import com.noteapp.pwlab.noteapp.note.services.CryptoService;
import com.noteapp.pwlab.noteapp.note.services.HTMLFilter;
import com.noteapp.pwlab.noteapp.note.services.NoteAccessFilterService;
import com.noteapp.pwlab.noteapp.user.entities.User;
import com.noteapp.pwlab.noteapp.user.entities.UserIdentity;
import com.noteapp.pwlab.noteapp.user.repositories.UserIdentityRepository;
import com.noteapp.pwlab.noteapp.user.repositories.UserRepository;
import com.noteapp.pwlab.noteapp.user.requests.AddUserAccessRequest;

import javax.validation.Valid;
import java.util.Locale;
import java.util.Objects;

@Controller
@Slf4j
@RequiredArgsConstructor
public class AppController {
	private final NoteRepository noteRepository;
	private final UserIdentityRepository userIdentityRepository;
	private final CryptoService cryptoService;
	private final NoteAccessFilterService noteAccessFilterService;
	private final UserRepository userRepository;
	private final HTMLFilter htmlFilter;

	 @GetMapping("/")
	 public String getMain(@AuthenticationPrincipal User user, Model model) {
	 	noteAccessFilterService.addNotes(user, model);
	 	return "index";
	 }

	@GetMapping("/create_note")
	public String getCreateNote(Model model) {

		model.addAttribute("createNoteRequest", new CreateNoteRequest());
		return "create_note";
	}

	@GetMapping("/show_note/{note_id}")
	public String getShowNote(@AuthenticationPrincipal User user, Model model, @PathVariable("note_id") Long noteId) {
		noteAccessFilterService.addNotes(user, model);

		Note note = noteRepository.findById(noteId).orElse(null);
		if (note == null) {
			model.addAttribute("element_not_found", true);
			return "index";
		}
		if (!noteAccessFilterService.verifyAccess(user, note, model)) {
			model.addAttribute("element_not_found", true);
			return "index";
		}
		if (note.getEncrypted()) {
			return "redirect:/decode_note/" + noteId;
		}
		model.addAttribute("note", note);
		return "show_note";
	}

	@PostMapping("/show_note/{note_id}")
	public String postShowNote(@AuthenticationPrincipal User user, @PathVariable("note_id") Long noteId, Model model, @Valid DecryptNoteRequest decryptRequest, Errors errors) {
		noteAccessFilterService.addNotes(user, model);
		model.addAttribute("id", noteId);

		Note note = noteRepository.findById(noteId).orElse(null);
		if (note == null) {
			model.addAttribute("element_not_found", true);
			return "index";
		}
		if (!noteAccessFilterService.verifyAccess(user, note, model)) {
			model.addAttribute("element_not_found", true);
			return "index";
		}


		if (!note.getEncrypted()) {
			return "redirect:/show_note/" + noteId;
		}
		if (errors.hasErrors()) {
			model.addAttribute("error", true);

			return "decrypt_note";
		}
		try {
			note.setNote(cryptoService.decrypt(decryptRequest.getPassword(), note.getNote()));
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", true);

			return "decrypt_note";
		}
		model.addAttribute("note", note);
		return "show_note";
	}

	@PostMapping("/add_user/{note_id}")
	public String postAddUser(@AuthenticationPrincipal User user, @PathVariable("note_id") Long noteId, 
	@Valid AddUserAccessRequest addUserAccessRequest, Errors errors, Model model) {
		noteAccessFilterService.addNotes(user, model);

		Note note = noteRepository.findById(noteId).orElse(null);
		if (note == null || errors.hasErrors()) {
			model.addAttribute("element_not_found", true);
			return "index";
		}

		if (!noteAccessFilterService.verifyAccess(user, note, model)) {
			model.addAttribute("element_not_found", true);
			return "index";
		}

		if (!Objects.equals(note.getUser().getUsername(), user.getUsername())) {
			model.addAttribute("element_not_found", true);
			return "index";
		}

		if (addUserAccessRequest.getUsername() != null) {
			if (note.getUsersWithAccess().stream().anyMatch(o -> o.getUsername().toUpperCase(Locale.ROOT).equals(addUserAccessRequest.getUsername().toUpperCase(Locale.ROOT)))) {
				return "redirect:/show_note/" + noteId;
			}
		}

		if(userRepository.findFirstByEmailIgnoreCase(addUserAccessRequest.getUsername()) != null) {
		UserIdentity userToAdd = new UserIdentity();
		userToAdd.setUsername(addUserAccessRequest.getUsername());
		userToAdd = userIdentityRepository.save(userToAdd);

		note.getUsersWithAccess().add(userToAdd);
		noteRepository.save(note);
		log.info("USER ADDED");
		}
		return "redirect:/show_note/" + noteId;
	}

	@GetMapping("/decode_note/{note_id}")
	public String getDecryptNote(@AuthenticationPrincipal User user, Model model, @PathVariable("note_id") Long noteId) throws InterruptedException {
		noteAccessFilterService.addNotes(user, model);

		Thread.sleep(3000);
		
		Note note = noteRepository.findById(noteId).orElse(null);
		if (note == null) {
			model.addAttribute("element_not_found", true);
			return "index";
		}

		if (!noteAccessFilterService.verifyAccess(user, note, model)) {
			model.addAttribute("element_not_found", true);
			return "index";
		}

		model.addAttribute("decryptNoteRequest", new DecryptNoteRequest());
		model.addAttribute("id", noteId);
		return "decrypt_note";
	}

	@PostMapping("/create_note")
	public String postCreateNote(@AuthenticationPrincipal User user, @Valid CreateNoteRequest createNoteRequest, Errors errors, Model model) {
		noteAccessFilterService.addNotes(user, model);

		if (errors.hasErrors()) {
			return "create_note";
		}
		Note note = new Note();
		note.setName(createNoteRequest.getName());
		//String noteText = createNoteRequest.getNote();
		String noteText = htmlFilter.sanitize(createNoteRequest.getNote());

		if (createNoteRequest.getPassword().length() > 0) {
			try {
				String result = cryptoService.encrypt(createNoteRequest.getPassword(), noteText);
				note.setNote(result);
			} catch (Exception e) {
				log.error(e.toString());
				model.addAttribute("createNoteError", true);
				return "create_note";
			}
			note.setEncrypted(true);
		} else {
			note.setNote(noteText);
			note.setEncrypted(false);
		}
		note.setUser(user);
		note.setIsPublic(createNoteRequest.getIsPublic());
		noteRepository.save(note);
		return "redirect:/";
	}
}

