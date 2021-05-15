package com.apitest.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apitest.entities.Course;
import com.apitest.entities.Employee;
import com.apitest.entities.Enrollment;
import com.apitest.exception.ApiTestException;
import com.apitest.model.EmployeeDto;
import com.apitest.repo.EmployeeRepo;
import com.apitest.repo.EnrollmentRepo;

@Service
public class EmployeeDaoService {
	
	@Autowired
    EmployeeRepo employeeRepo;
    @Autowired
	EnrollmentRepo enrollmentRepo;
    
	public EmployeeDto createEmployee(EmployeeDto empDto) {
		
		Employee emp = new Employee();
		emp.setEmployeeName(empDto.getEmployeeName());
		emp.setCreatedAt(new Date());
		emp.setStatus("acive");
		
		Employee savedEmp;
		try {
			savedEmp = employeeRepo.save(emp);
		}catch(Exception exc) {
			throw new ApiTestException("Error in saving employee");
		}
		
		empDto.setEmployeeId(savedEmp.getEmployeeId());
		return empDto;
	}
	
	public Employee updateEmployee(Employee employee) {
		return employeeRepo.save(employee);
	}
	
	public Boolean enrollEmployee(Course course, Employee employee, List<String> weekDays) {
	
		boolean flag = true;
		Enrollment enroll = new Enrollment();
		enroll.setCourse(course);
		enroll.setEmployee(employee);
		String weekDaysStr = weekDays.toString().replace(", ", ",").replaceAll("[\\[.\\]]", "");
		enroll.setWeekDays(weekDaysStr);
		Enrollment createdEnrollment = enrollmentRepo.save(enroll);
		if(createdEnrollment == null) {
			flag = false;
		}
		
		return flag;
	}
	
	
	public Employee getEmployeeById(Long empId) {
		Optional<Employee> employee = employeeRepo.findById(empId);
	    if(!employee.isPresent()) {
	    	return employee.get();
	    }else {
	    	return employee.get();
	    }
	}
	
	public void deleteEmployee(Employee employee) {
		try {
			employeeRepo.delete(employee);
		}catch(Exception exc) {
			throw new ApiTestException("Error in deleting employee");
		}
		
	}
}
