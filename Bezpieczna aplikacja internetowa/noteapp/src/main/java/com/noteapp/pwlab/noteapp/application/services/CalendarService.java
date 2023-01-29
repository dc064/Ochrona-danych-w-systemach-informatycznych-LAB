package com.noteapp.pwlab.noteapp.application.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class CalendarService {
    public Date getValidDate(int requestedAmount) {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		cal.add(Calendar.HOUR, requestedAmount);
		return cal.getTime();
	}
}
