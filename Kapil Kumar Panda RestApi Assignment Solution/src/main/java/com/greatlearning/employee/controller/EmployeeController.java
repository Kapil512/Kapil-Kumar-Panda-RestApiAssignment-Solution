package com.greatlearning.employee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greatlearning.employee.dao.EmployeeServiceImpl;
import com.greatlearning.employee.entity.Employee;

@RestController
@RequestMapping("/employee")


public class EmployeeController {
	
	
	@Autowired
	private EmployeeServiceImpl employeeService;
	

	
	@GetMapping("/list")
	public List<Employee> listEmployees() {
		return employeeService.findAll();
	}
	
	
	@GetMapping("/sortedList")
	public List<Employee> getSortedEmployeeName(String direction){
		
		return employeeService.getSortedEmployeeName(direction);
		
	}
	
	
	@GetMapping("/findById")
	public Employee findById(int id) {
		Employee emp = new Employee();
		try {
			emp = employeeService.findById(id);
		} catch (Exception e) {
			System.out.println(emp);
		}
		return emp;
	}

	@GetMapping("/findByName")
	public List<Employee> findByName(String firstName) {
		return employeeService.findByName(firstName);
	}

	@PostMapping("/addEmployee")
	public Employee addEmployee(String firstName, String lastName, String email) {
		Employee employee = new Employee(firstName, lastName, email);
		employeeService.save(employee);
		return employee;
	}

	@PutMapping("/updateEmployee")
	public Employee updateEmployee(Employee employee) {
		employeeService.updateEmployee(employee);
		return employee;
	}

	@DeleteMapping("/deleteEmployeeByid")
	public String deleteEmployeeByid(int id) {
		employeeService.deleteById(id);
		return "Employee Deleted";
	}
	
	

}
