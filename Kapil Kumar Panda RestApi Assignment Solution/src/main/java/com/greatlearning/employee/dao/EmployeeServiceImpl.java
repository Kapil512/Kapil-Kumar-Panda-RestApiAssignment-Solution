package com.greatlearning.employee.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.greatlearning.employee.entity.Employee;
import com.greatlearning.employee.repository.EmployeeRepository;
import com.greatlearning.employee.service.EmployeeService;



@Service
public class EmployeeServiceImpl implements EmployeeService {

	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	
	
	
	
	@Override
	@Transactional
	public List<Employee> findAll() {
		List<Employee> employee = employeeRepository.findAll();
		return employee;
	}
	
	
	@Override
	@Transactional
	public List<Employee> getSortedEmployeeName(String direction) {
		if (direction.equals("ASC"))
			return employeeRepository.findAll(Sort.by(Direction.ASC, "firstName"));
		else if (direction.equals("DESC"))
			return employeeRepository.findAll(Sort.by(Direction.DESC, "firstName"));
		return null;
	}

	@Override
	@Transactional
	public Employee findById(int id) {
		
		return employeeRepository.findById(id).get();
	}

	@Override
	@Transactional
	public void save(Employee employee) {
		employeeRepository.saveAndFlush(employee);
		
	}
	
	@Override
	public List<Employee> findByName(String name) {
		Employee emp = new Employee();
		emp.setFirstName(name);
		ExampleMatcher exampleMatcher = ExampleMatcher.matching()
				.withMatcher("firstName", ExampleMatcher.GenericPropertyMatchers.exact())
				.withIgnorePaths("id", "lastName", "email");
		Example<Employee> example = Example.of(emp, exampleMatcher);
		return employeeRepository.findAll(example, Sort.by("firstName"));
	}
	
	
	@Override
	@Transactional
	public void updateEmployee(Employee employee) {
		boolean exist = employeeRepository.existsById(employee.getId());
		if (exist) {
			employeeRepository.saveAndFlush(employee);
		} else {
			throw new RuntimeException("There is no employee with id " + employee.getId());
		}
	}

	@Override
	public void deleteById(int id) {
	  employeeRepository.deleteById(id);
		
	}

}
