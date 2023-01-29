package com.noteapp.pwlab.noteapp.user.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.noteapp.pwlab.noteapp.user.entities.records.UserActivateRecord;

public interface UserActivateRecordRepository extends JpaRepository<UserActivateRecord, String> {

}
