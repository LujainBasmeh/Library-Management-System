package com.example.library_management_system;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.Calendar;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.CsrfRequestPostProcessor;
import org.springframework.test.web.servlet.MockMvc;
import com.example.library_management_system.controller.BorrowingController;
import com.example.library_management_system.entity.Book;
import com.example.library_management_system.entity.Borrowing;
import com.example.library_management_system.entity.Patron;
import com.example.library_management_system.repository.BorrowingRepository;
import com.example.library_management_system.service.BookService;
import com.example.library_management_system.service.BorrowingService;
import com.example.library_management_system.service.PatronService;

@WebMvcTest(BorrowingController.class)
public class BorrowingControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Mock
	private BorrowingRepository borrowingRepository;
	
	@MockBean
	private BorrowingService borrowingService;
	
	@MockBean
	private BookService bookService;
	
	@MockBean
	private PatronService patronService;
	
	private CsrfRequestPostProcessor csrf = SecurityMockMvcRequestPostProcessors.csrf();

	@Test
	public void testBorrowBook_success() throws Exception {
		// Arrange
		long bookId = 1L;
		long patronId = 1L;
		Book book = new Book();
		Patron patron = new Patron();
		java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		Borrowing borrowing = new Borrowing(1L, date, null, patron, book); 
		
		when(bookService.findById(bookId)).thenReturn(book);
		when(patronService.findById(patronId)).thenReturn(patron);
		when(borrowingService.borrowBook(bookId,patronId)).thenReturn(borrowing);

		// Act and Assert
		mockMvc.perform(post("/api/borrows/borrow/"+bookId+"/patron/"+patronId)
				.with(csrf)
				.with(SecurityMockMvcRequestPostProcessors.user("user").password("password"))
                .contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	public void testBorrowBook_failure() throws Exception {
		// Arrange
		long bookId = 1L;
		long patronId = 999L;
		Book book = new Book();
		Patron patron = new Patron();
		
		when(bookService.findById(bookId)).thenReturn(book);
		doThrow(new NoSuchElementException("Patron with id: " + patronId + " not found")).when(patronService).findById(patronId);
		doThrow(new NoSuchElementException("Patron with id: " + patronId + " not found")).when(borrowingService).borrowBook(bookId, patronId);
		
		// Act and Assert
	    assertThrows(NoSuchElementException.class, () -> borrowingService.borrowBook(bookId, patronId));
	}
	
	@Test
	public void testReturnBook() throws Exception {
		// Arrange
		long bookId = 1L;
		long patronId = 1L;
		Book book = new Book();
		Patron patron = new Patron();
		java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		Borrowing borrowing = new Borrowing(1L, date, date, patron, book); 
		
		when(bookService.findById(bookId)).thenReturn(book);
		when(patronService.findById(patronId)).thenReturn(patron);
		when(borrowingService.returnBook(bookId,patronId,date)).thenReturn(borrowing);

		// Act and Assert
		mockMvc.perform(put("/api/borrows/return/"+bookId+"/patron/"+patronId)
				.with(csrf)
				.with(SecurityMockMvcRequestPostProcessors.user("user").password("password"))
				 .contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		}
	
	@Test
	public void testReturnBook_failure() throws Exception {
		// Arrange
		long bookId = 1L;
		long patronId = 1L;
		Book book = new Book();
		Patron patron = new Patron();
		java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		
		when(bookService.findById(bookId)).thenReturn(book);
		when(patronService.findById(patronId)).thenReturn(patron);
		doThrow(new NoSuchElementException("Borrowing record for book with id: " + bookId
				+ " and patron with id " + patronId + " not found")).when(borrowingService).returnBook(bookId, patronId, date);
		
		// Act and Assert
	    assertThrows(NoSuchElementException.class, () -> borrowingService.returnBook(bookId, patronId, date));
		}

}