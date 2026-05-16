package com.main.employee.repository;

import org.springframework.data.repository.CrudRepository;
import com.main.employee.models.Employee;

public interface EmployeeRepository extends CrudRepository<Employee,Long>{
	public Employee findByEmail(String emailId);
}
