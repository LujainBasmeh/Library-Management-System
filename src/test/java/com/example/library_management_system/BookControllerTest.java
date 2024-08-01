package com.example.library_management_system;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.Year;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.example.library_management_system.controller.BookController;
import com.example.library_management_system.entity.Book;
import com.example.library_management_system.repository.BookRepository;
import com.example.library_management_system.service.BookService;
import com.jayway.jsonpath.JsonPath;

@WebMvcTest(BookController.class)
public class BookControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Mock
	private BookRepository bookRepository;
	@MockBean
	private BookService bookService;

	@Test
	public void testFindAll_success() throws Exception {
		// Arrange
		List<Book> books = Arrays.asList(new Book(1L, "Book 1", "Author 1",Year.of(2023), "1234"),
				new Book(2L, "Book 2", "Author 2", Year.of(2024), "5678"));
		when(bookService.findAll()).thenReturn(books);

		// Act and Assert
		mockMvc.perform(get("/api/books")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json(
						"[{\"id\":1,\"title\":\"Book 1\",\"author\":\"Author 1\",\"publicationYear\":\"2023\",\"isbn\":\"1234\"},"
								+ "{\"id\":2,\"title\":\"Book 2\",\"author\":\"Author 2\",\"publicationYear\":\"2024\",\"isbn\":\"5678\"}]"));
	}

	@Test
	public void testFindById_success() throws Exception {
		// Arrange
		Book book = new Book(1L, "Book 1", "Author 1", Year.of(2023), "1234");
		when(bookService.findById(1L)).thenReturn(book);

		// Act and Assert
		mockMvc.perform(get("/api/books/1"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(content().json(
						"{\"id\":1,\"title\":\"Book 1\",\"author\":\"Author 1\",\"publicationYear\":\"2023\",\"isbn\":\"1234\"}"));

	}

	@Test
	public void testFindById_failure() throws Exception { /*Book id not found*/
		// Arrange
		Book book = new Book(1L, "Book 1", "Author 1", Year.of(2023), "1234");
		when(bookService.findById(1L)).thenReturn(book);
		when(bookService.findById(2L)).thenThrow(new NoSuchElementException("Book with id: 2 not found"));

		// Act and Assert
	    assertThrows(NoSuchElementException.class, () -> bookService.findById(2L));
	}

	@Test
	public void testUpdateById_success() throws Exception {
		// Arrange
		long id = 1L;
		Book book = new Book(id, "Book 1", "Author 1", Year.of(2023), "1234");
		Book updatedBook = new Book(id, "Book 1", "Author 1", Year.of(2020), "1111");
		when(bookService.updateById(id, updatedBook)).thenReturn(updatedBook);
		// Act and Assert
		 mockMvc.perform(put("/api/books/" + id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(
				"{\"title\":\"Book 1\",\"author\":\"Author 1\",\"publicationYear\":\"2020\",\"isbn\":\"1111\"}"))
				.andExpect(status().isOk());
	}

	@Test
	public void testUpdateById_failure() throws Exception {/*Book title is required*/
		// Arrange
		long id = 1L;

		// Act & Assert
		mockMvc.perform(put("/api/books/" + id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(
				"{\"title\":\"\",\"author\":\"Author 1\",\"publicationYear\":\"2020\",\"isbn\":\"1111\"}"))
				.andExpect(status().isBadRequest())
				.andExpect(result -> assertEquals("The Title value is required.",
						JsonPath.read(result.getResponse().getContentAsString(),"$.error")));

	}

	@Test
	public void testDeleteById_success() throws Exception {
		// Arrange
		long id = 1L;
		Book book = new Book(id, "Book 1", "Author 1", Year.of(2023), "1234");
		when(bookRepository.findById(id)).thenReturn(Optional.of(book));

		doNothing().when(bookRepository).deleteById(id);
		// Act and Assert

		mockMvc.perform(delete("/api/books/" + id))
				.andExpect(status().isOk());
	}

	@Test
	public void testDeleteById_failure() throws Exception {/*Book id not found*/
		// Arrange
		long id = 99L;
		doThrow(new NoSuchElementException("Book with id: " + id + " not found")).when(bookService).deleteById(id);

		// Act & Assert
	    assertThrows(NoSuchElementException.class, () -> bookService.deleteById(id));
	}

}