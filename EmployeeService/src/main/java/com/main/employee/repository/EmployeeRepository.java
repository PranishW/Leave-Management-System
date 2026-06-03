package com.main.employee.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.main.employee.models.Employee;

public interface EmployeeRepository extends CrudRepository<Employee,Long>{
	public Employee findByEmail(String emailId);
	public Employee findByEmployeeIdAndManagerId(long empId,String managerId);
	public List<Employee> findAllByManagerId(String managerId);
}
