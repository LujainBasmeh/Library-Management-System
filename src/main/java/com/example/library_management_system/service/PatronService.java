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

	// @Cacheable(cacheNames = CachingConfig.PATRONS_CACHE_NAME, key = "#id")
	public Patron findById(Long id) {
		// log.info("findPatronById(id:{})", id);
		return patronRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Patron with id: " + id + " not found"));
	}

	// @Cacheable(cacheNames = CachingConfig.PATRONS_CACHE_NAME, key =
	// "#root.methodName")
	public List<Patron> findAll() {
		// log.info("findAllPAtron()");
		return patronRepository.findAll();
	}

	// @CacheEvict(cacheNames = CachingConfig.PATRONS_CACHE_NAME, key = "#id")
	public void deleteById(Long id) {
		// log.info("deletePatronById(id:{})", id);

		Patron patron = patronRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Patron with id: " + id + " not found"));
		patronRepository.deleteById(patron.getId());
	}

	// @CachePut(cacheNames = CachingConfig.PATRONS_CACHE_NAME, key = "#id")
	public Patron updateById(Long id, Patron updatedPatron) {
		// log.info("updatePatronById(id:{}, updatedPatron:{})", id, patron);

		Patron patron = patronRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Patron with id: " + id + " not found"));

		patron.setName(updatedPatron.getName());
		patron.setEmail(updatedPatron.getEmail());
		patron.setPhone(updatedPatron.getPhone());
		patron.setAddress(updatedPatron.getAddress());
		
		return patronRepository.save(patron);
	}
}
