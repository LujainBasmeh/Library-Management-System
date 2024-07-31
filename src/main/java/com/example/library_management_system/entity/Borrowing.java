package com.example.library_management_system.entity;

import java.sql.Date;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "BORROWING")
public class Borrowing{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
    private Long id;

    @NotNull(message = "The Borrow Date value is required.")
	@Column(name = "BORROW_DATE")
	private java.sql.Date borrowDate;
	
	@Column(name = "RETURN_DATE")
	private java.sql.Date returnDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name ="PATRON_ID", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Patron patron;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name ="BOOK_ID", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Book book;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public java.sql.Date getBorrowDate() {
		return borrowDate;
	}

	public void setBorrowDate(java.sql.Date borrowDate) {
		this.borrowDate = borrowDate;
	}

	public java.sql.Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(java.sql.Date returnDate) {
		this.returnDate = returnDate;
	}

	public Patron getPatron() {
		return patron;
	}

	public void setPatron(Patron patron) {
		this.patron = patron;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}
	public Borrowing() {}
	public Borrowing(Long id, Date borrowDate, Date returnDate, Patron patron, Book book) {
		super();
		this.id = id;
		this.borrowDate = borrowDate;
		this.returnDate = returnDate;
		this.patron = patron;
		this.book = book;
	}

 }