package com.example.library_management_system.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.library_management_system.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
    Optional<Book> findOneByIdAndBorrowedFalse(Long id);

}