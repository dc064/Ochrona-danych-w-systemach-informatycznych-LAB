package com.noteapp.pwlab.noteapp.user.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.noteapp.pwlab.noteapp.user.entities.records.ResetPasswdRecord;

import java.util.List;

public interface ResetPasswordRecordRepository extends JpaRepository<ResetPasswdRecord, String> {
	List<ResetPasswdRecord> findAllByUserId(Long id);
}
