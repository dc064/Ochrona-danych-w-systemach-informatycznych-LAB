package com.noteapp.pwlab.noteapp.user.entities.records;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.noteapp.pwlab.noteapp.user.entities.User;

import java.util.Date;
import java.util.UUID;
@Entity
@Getter
@Setter
public class UserActivateRecord {
	@Id
	@Column(length = 36, unique = true, nullable = false)
	private String id = UUID.randomUUID().toString();

	private Date validTime;

	@OneToOne
	private User user;
}

