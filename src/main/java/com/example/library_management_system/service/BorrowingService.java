package com.example.library_management_system.service;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.library_management_system.entity.Book;
import com.example.library_management_system.entity.Borrowing;
import com.example.library_management_system.entity.Patron;
import com.example.library_management_system.repository.BorrowingRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BorrowingService {
	
	@Autowired
	private BorrowingRepository borrowingRepository;
	
	@Autowired
	private PatronService patronService;
	
	@Autowired
	private BookService bookService;

	// @Override
	public Borrowing borrowBook(Long bookId, Long patronId) {
		Book book = bookService.findByIdAndNotBorrowd(bookId);

		Patron patron = patronService.findById(patronId);

		book.setBorrowed(true);
		bookService.save(book);

		Borrowing borrowing = new Borrowing();
		borrowing.setBook(book);
		borrowing.setPatron(patron);
		borrowing.setBorrowDate(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
		borrowing.setReturnDate(null);

		return borrowingRepository.save(borrowing);

	}

	public Borrowing returnBook(Long bookId, Long patronId, java.sql.Date returnDate) {
		Book book = bookService.findById(bookId);
		
		Patron patron = patronService.findById(patronId);

		Borrowing borrowing = borrowingRepository.findBorrowingByPatronAndBook(patron, book)
				.orElseThrow(() -> new RuntimeException("Borrowing record for book with id: " + bookId
						+ " and patron with id " + patronId + " not found"));
		

		book.setBorrowed(false);
		bookService.save(book);

		borrowing.setReturnDate(returnDate);
		return borrowingRepository.save(borrowing);
	}
}