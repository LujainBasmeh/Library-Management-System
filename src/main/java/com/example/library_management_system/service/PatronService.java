package com.example.library_management_system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.library_management_system.entity.Patron;
import com.example.library_management_system.repository.PatronRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PatronService {

	@Autowired
	private PatronRepository patronRepository;

	// @Override
	public Patron save(Patron patron) {
		return patronRepository.save(patron);
	}

	public Patron findById(Long id) {
		return patronRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Patron with id: " + id + " not found"));
	}

	public List<Patron> findAll() {
		return patronRepository.findAll();
	}

	public void deleteById(Long id) {
	
		Patron patron = patronRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Patron with id: " + id + " not found"));
		patronRepository.deleteById(patron.getId());
	}

	public Patron updateById(Long id, Patron updatedPatron) {
	
		Patron patron = patronRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Patron with id: " + id + " not found"));

		patron.setName(updatedPatron.getName());
		patron.setEmail(updatedPatron.getEmail());
		patron.setPhone(updatedPatron.getPhone());
		patron.setAddress(updatedPatron.getAddress());
		
		return patronRepository.save(patron);
	}
}
