package com.noteapp.pwlab.noteapp.user.services;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.noteapp.pwlab.noteapp.user.entities.User;
import com.noteapp.pwlab.noteapp.user.repositories.UserRepository;

@RequiredArgsConstructor
public class UserService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	private final String notFound = "Username not found";

	@SneakyThrows
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepository.findFirstByUsernameIgnoreCase(username).orElse(null);

		if (user == null) {
			throw new UsernameNotFoundException(notFound);
		}

		return user;
	}


}
