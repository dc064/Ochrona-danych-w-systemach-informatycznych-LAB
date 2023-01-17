package com.noteapp.pwlab.noteapp.application.controllers.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.noteapp.pwlab.noteapp.application.services.CalendarService;
import com.noteapp.pwlab.noteapp.user.entities.User;
import com.noteapp.pwlab.noteapp.user.entities.records.ResetPasswdRecord;
import com.noteapp.pwlab.noteapp.user.repositories.ResetPasswordRecordRepository;
import com.noteapp.pwlab.noteapp.user.repositories.UserActivateRecordRepository;
import com.noteapp.pwlab.noteapp.user.repositories.UserRepository;
import com.noteapp.pwlab.noteapp.user.requests.CreateUserRequest;
import com.noteapp.pwlab.noteapp.user.requests.LoginRequest;
import com.noteapp.pwlab.noteapp.user.requests.SetPasswordRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class AuthController {

	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final UserActivateRecordRepository userActivateRecordRepository;
	private final ResetPasswordRecordRepository resetPasswordRecordRepository;
	private final CalendarService calendarService;

	@GetMapping("/login")
	public String getLogin(Model model) throws InterruptedException {
		model.addAttribute("login_request", new LoginRequest());
		Thread.sleep(2000);
		
		return "login";
	}

	@GetMapping("/login-error")
	public String loginError(Model model) {
		model.addAttribute("loginError", true);

		return "login";
	}

	@GetMapping("/perform_logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/";
	}

	@GetMapping("/register")
	public String getRegister(Model model) {

		model.addAttribute("createUserRequest", new CreateUserRequest());

		return "register";
	}

	@PostMapping("/register")
	public String postCreateNote(@Valid CreateUserRequest createUserRequest, Errors errors, Model model) throws InterruptedException {

		if (errors.hasErrors()) {
			return "register";
		}
		Thread.sleep(2000);

		if (userRepository.findFirstByUsernameIgnoreCase(createUserRequest.getUsername()).isPresent()
				|| userRepository.findFirstByEmailIgnoreCase(createUserRequest.getEmail()).isPresent()) {
			model.addAttribute("registerError", true);
			return "register";
		}
		User user = new User();
		user.setUsername(createUserRequest.getUsername());
		user.setEmail(createUserRequest.getEmail());
		user.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));
		user.setIsValid(true);
		user = userRepository.save(user);

		if (errors.hasErrors()) {
			return "register";
		}
		
		return "login";
	}
}
