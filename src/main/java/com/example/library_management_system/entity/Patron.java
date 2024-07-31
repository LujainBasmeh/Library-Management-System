package com.example.library_management_system.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "PATRON",uniqueConstraints = {@UniqueConstraint(columnNames = {"email"}, name = "email_unique_constraint")})
public class Patron{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
    private Long id;

    @NotBlank(message = "The Name value is required.")
	@Column(name = "NAME")
	private String name;

    @NotBlank(message = "The Email value is required.")
	@Column(name = "EMAIL",unique = true)
    private String email;

	@Column(name = "PHONE")
    private String phone;
	
	@Column(name = "ADDRESS")
    private String address;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Patron(){}
	public Patron(Long id, String name,String email, String phone, String address) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.address = address;
	}

 }
