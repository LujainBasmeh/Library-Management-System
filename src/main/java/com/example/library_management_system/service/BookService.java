package com.example.library_management_system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.library_management_system.entity.Book;
import com.example.library_management_system.repository.BookRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BookService{
    @Autowired
    private BookRepository bookRepository;

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Cacheable(cacheNames = "books", key = "#id")
    public Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book with id: " + id + " not found"));
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }
    
    @CacheEvict(cacheNames = "books", key = "#id")
    public void deleteById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book with id: " + id + " not found"));

        bookRepository.deleteById(book.getId());
    }

    @CachePut(cacheNames = "books", key = "#id")
    public Book updateById(Long id, Book updatedBook) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book with id: " + id + " not found"));

        book.setTitle(updatedBook.getTitle());
        book.setIsbn(updatedBook.getIsbn());
        book.setAuthor(updatedBook.getAuthor());
        book.setPublicationYear(updatedBook.getPublicationYear());

        return bookRepository.save(book);
    }
    
    public Book findByIdAndNotBorrowd(Long id) {
    	return bookRepository.findOneByIdAndBorrowedFalse(id)
    			.orElseThrow(() -> new RuntimeException("Book with id: " + id + " is already borrowed"));
    }


}