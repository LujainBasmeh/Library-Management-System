package com.example.library_management_system.controller;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.library_management_system.entity.Borrowing;
import com.example.library_management_system.service.BorrowingService;

@RestController
@RequestMapping("/api/borrows")

public class BorrowingController {

	@Autowired
	private BorrowingService borrowingService;

	@PostMapping("/borrow/{bookId}/patron/{patronId}")
	public ResponseEntity<?> borrowBook(@PathVariable Long bookId, @PathVariable Long patronId) {

		Borrowing borrowing = borrowingService.borrowBook(bookId, patronId);
		return new ResponseEntity<>(HttpStatus.OK);

	}

	@PutMapping("/return/{bookId}/patron/{patronId}")
	public ResponseEntity<?> returnBook(@PathVariable Long bookId, @PathVariable Long patronId) {

		java.sql.Date returnDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());

		borrowingService.returnBook(bookId, patronId, returnDate);
		return new ResponseEntity<>(HttpStatus.OK);

	}
}
