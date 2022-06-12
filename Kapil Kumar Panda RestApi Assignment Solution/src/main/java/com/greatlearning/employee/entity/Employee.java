package com.greatlearning.employee.entity;


import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Employee")
@Data
@NoArgsConstructor
public class Employee {
	
	
	
	public Employee(String firstName, String lastName, String email) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	
	
	
}