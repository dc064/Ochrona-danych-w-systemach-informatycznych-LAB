package com.noteapp.pwlab.noteapp.note.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import com.noteapp.pwlab.noteapp.user.entities.User;
import com.noteapp.pwlab.noteapp.user.entities.UserIdentity;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
public class Note {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Length(max = 64)
	private String name;

	@Length(max = 10000)
	private String note;

	private Boolean encrypted;

	private Boolean isPublic;

	@ManyToOne
	private User user;

	@ManyToMany
	private List<UserIdentity> usersWithAccess;
}
