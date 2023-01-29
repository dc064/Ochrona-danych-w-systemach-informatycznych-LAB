package com.noteapp.pwlab.noteapp.user.entities.records;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.noteapp.pwlab.noteapp.user.entities.User;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class ResetPasswdRecord {
	@Id
	@Column(length = 36, unique = true, nullable = false)
	private String id = UUID.randomUUID().toString();

	private Date validTime;

	@ManyToOne
	private User user;
}
