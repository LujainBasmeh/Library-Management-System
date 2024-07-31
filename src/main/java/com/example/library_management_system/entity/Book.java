package com.example.library_management_system.entity;
import java.time.Year;
import org.hibernate.annotations.ColumnDefault;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "BOOK",uniqueConstraints = {@UniqueConstraint(columnNames = {"ISBN"}, name = "isbn_unique_constraint")})
public class Book{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
    private Long id;

    @NotBlank(message = "The Title value is required.")
	@Column(name = "TITLE")
	private String title;

    @NotBlank(message = "The Author value is required.")
	@Column(name = "AUTHOR")
    private String author;

    @NotNull(message = "The Year value is required.")
	@Column(name = "PUBLICATION_YEAR")
    private Year publicationYear;


	@NotBlank(message = "The ISBN value is required.")
	@Column(name = "ISBN", unique = true)
    private String isbn;
	
	@Column(name = "IS_BORROWED")
	@ColumnDefault("false")
	private boolean borrowed;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Year getPublicationYear() {
		return publicationYear;
	}

	public void setPublicationYear(Year publicationYear) {
		this.publicationYear = publicationYear;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public boolean isBorrowed() {
		return borrowed;
	}

	public void setBorrowed(boolean borrowed) {
		this.borrowed = borrowed;
	}
	public Book() {}
	public Book(Long id, String title,String author,Year publicationYear,String isbn) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.publicationYear = publicationYear;
		this.isbn = isbn;
	}

 }
