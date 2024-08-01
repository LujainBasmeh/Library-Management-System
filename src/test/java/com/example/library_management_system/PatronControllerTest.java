package com.example.library_management_system;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.example.library_management_system.controller.PatronController;
import com.example.library_management_system.entity.Patron;
import com.example.library_management_system.repository.PatronRepository;
import com.example.library_management_system.service.PatronService;
import com.jayway.jsonpath.JsonPath;

@WebMvcTest(PatronController.class)
public class PatronControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Mock
	private PatronRepository patronRepository;
	@MockBean
	private PatronService patronService;

	@Test
	public void testFindAll_success() throws Exception {
		// Arrange
		List<Patron> patrons = Arrays.asList(new Patron(1L, "User 1", "user1@gmail.com", "1234567", "address1"),
											new Patron(2L, "User 2", "user2@gmail.com", "1234567", "address2"));
		when(patronService.findAll()).thenReturn(patrons);

		// Act and Assert
		mockMvc.perform(get("/api/patrons"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json(
						"[{\"id\":1,\"name\":\"User 1\",\"email\":\"user1@gmail.com\",\"phone\":\"1234567\",\"address\":\"address1\"},"
								+ "{\"id\":2,\"name\":\"User 2\",\"email\":\"user2@gmail.com\",\"phone\":\"1234567\",\"address\":\"address2\"}]"));
	}

	@Test
	public void testFindById_success() throws Exception {
		// Arrange
		Patron patron = new Patron(1L, "User 1", "user1@gmail.com", "1234567", "address1");
		when(patronService.findById(1L)).thenReturn(patron);

		// Act and Assert
		mockMvc.perform(get("/api/patrons/1"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(content().json(
						"{\"id\":1,\"name\":\"User 1\",\"email\":\"user1@gmail.com\",\"phone\":\"1234567\",\"address\":\"address1\"}"));

	}

	@Test
	public void testFindById_failure() throws Exception {/*Patron id not found*/
		// Arrange
		Patron patron = new Patron(1L, "User 1", "user1@gmail.com", "1234567", "address1");
		when(patronService.findById(1L)).thenReturn(patron);
		when(patronService.findById(2L)).thenThrow(new NoSuchElementException("Patron with id: 2 not found"));

		// Act and Assert
	    assertThrows(NoSuchElementException.class, () -> patronService.findById(2L));
	}

	@Test
	public void testUpdateById_success() throws Exception {
		// Arrange
		long id = 1L;
		Patron patron = new Patron(id, "User 1", "user1@gmail.com", "201501506090", "address1");
		Patron updatedPatron = new Patron(id, "User 1", "user1@gmail.com", "963930918983", "new address1");
		when(patronService.updateById(id, updatedPatron)).thenReturn(updatedPatron);
		// Act and Assert
		mockMvc.perform(put("/api/patrons/" + id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(
				"{\"name\":\"User 1\",\"email\":\"user1@gmail.com\",\"phone\":\"963930918983\",\"address\":\"new address1\"}"))
				.andExpect(status().isOk());

	}

	@Test
	public void testUpdateById_failure() throws Exception {/*Patron phone number should be numbers*/
		// Arrange
		long id = 999L;
		Patron updatedPatron = new Patron();
		doThrow(new RuntimeException("Patron with id: " + id + " not found")).when(patronService).updateById(id,
				updatedPatron);

		// Act & Assert
		mockMvc.perform(put("/api/patrons/" + id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(
				"{\"name\":\"User 1\",\"email\":\"user1@gmail.com\",\"phone\":\"k1234\",\"address\":\"new address1\"}"))
				.andExpect(status().isBadRequest())
				.andExpect(result -> assertEquals("The Phone number should be 12 numbers. (country code + number)",
						JsonPath.read(result.getResponse().getContentAsString(), "$.error")));
				
	}

	@Test
	public void testDeleteById_success() throws Exception {
		// Arrange
		long id = 1L;
		Patron patron = new Patron(id, "User 1", "user1@gmail.com", "1234567", "address1");
		when(patronRepository.findById(id)).thenReturn(Optional.of(patron));

		doNothing().when(patronRepository).deleteById(id);
		// Act and Assert

		mockMvc.perform(delete("/api/patrons/" + id))
				.andExpect(status().isOk());
	}

	@Test
	public void testDeleteById_failure() throws Exception {/*Patron id not found*/
		// Arrange
		long id = 99L;
		doThrow(new NoSuchElementException("Patron with id: " + id + " not found")).when(patronService).deleteById(id);

		// Act & Assert
	    assertThrows(NoSuchElementException.class, () -> patronService.deleteById(id));
	}

}