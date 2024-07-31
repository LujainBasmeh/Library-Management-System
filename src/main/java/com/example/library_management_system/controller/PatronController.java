package com.example.library_management_system.controller;

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

import com.example.library_management_system.entity.Patron;
import com.example.library_management_system.service.PatronService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/patrons")
public class PatronController {
	@Autowired
	private PatronService patronService;

	@GetMapping
	public ResponseEntity<?> findAll() {
		List<Patron> patrons = patronService.findAll();
		return new ResponseEntity<>(patrons, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id) {
		Patron patron = patronService.findById(id);
		return new ResponseEntity<>(patron, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<?> create(@Valid @RequestBody Patron patron) {
		Patron newPattron = patronService.save(patron);
		return new ResponseEntity<>(newPattron, HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateById(@PathVariable Long id,@Valid @RequestBody Patron patron) {
		patronService.updateById(id, patron);
		return new ResponseEntity<>(HttpStatus.OK);

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Long id) {
		patronService.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);

	}

}