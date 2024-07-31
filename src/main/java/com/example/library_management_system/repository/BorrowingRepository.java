package com.example.library_management_system.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.library_management_system.entity.Book;
import com.example.library_management_system.entity.Borrowing;
import com.example.library_management_system.entity.Patron;

@Repository
public interface BorrowingRepository extends JpaRepository<Borrowing, Long> {

    Optional<Borrowing> findBorrowingByPatronAndBook(Patron patron, Book book);
}