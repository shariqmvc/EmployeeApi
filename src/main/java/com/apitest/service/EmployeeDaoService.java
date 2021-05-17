package com.apitest.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apitest.entities.Course;
import com.apitest.entities.Employee;
import com.apitest.entities.Enrollment;
import com.apitest.exception.ApiTestException;
import com.apitest.exception.ResourceNotFoundException;
import com.apitest.model.EmployeeDto;
import com.apitest.model.Status;
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
		emp.setStatus(Status.Active.name());
		
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
		enroll.setStatus(Status.Active.name());
		Enrollment createdEnrollment = enrollmentRepo.save(enroll);
		if(createdEnrollment == null) {
			flag = false;
		}
		
		return flag;
	}
	
	
	public Employee getEmployeeById(Long empId) {
		Optional<Employee> employee = employeeRepo.findById(empId);
	    if(employee.isPresent()) {
	    	return employee.get();
	    }else {
	    	return null;
	    }
	}
	
	public void deleteEmployee(Employee employee) {
		try {
			employeeRepo.delete(employee);
		}catch(Exception exc) {
			throw new ApiTestException("Error in deleting employee");
		}
		
	}
	
	public List<EmployeeDto> searchEmployee(String searchTerm) {
		List<Long> employeeids = employeeRepo.getEmployeeBySearch(searchTerm);
		List<Employee> employees = new ArrayList<Employee>();
		Employee employee;
		for(Long empId : employeeids) {
			employee = employeeRepo.findById(empId).get();
			if(employee != null) {
				employees.add(employee);
			}
			
		}
		
		List<EmployeeDto> empDtos = new ArrayList<EmployeeDto>();
		EmployeeDto empDto;
		if(employees.size() > 0) {
			for(Employee emp : employees) {
				empDto = new EmployeeDto();
				empDto.setEmployeeId(emp.getEmployeeId());
				empDto.setEmployeeName(emp.getEmployeeName());
				empDtos.add(empDto);
			}
			
		}
		
		return empDtos;
	}
	
	public Boolean removeEmployeeFromCourse(Course course, Employee employee) {
		Enrollment enrollment = enrollmentRepo.findByEmployeeAndCourse(employee, course);
		if(enrollment == null) {
			throw new ResourceNotFoundException("No such enrollment found");
		}
		
		try {
			enrollmentRepo.delete(enrollment);
		}catch(Exception exc) {
			throw new ApiTestException("Unable to delete enrollment");
		}
		
		return true;
	}
	
	public Boolean suspendCourse(Course course, Employee employee) {
		Enrollment enrollment = enrollmentRepo.findByEmployeeAndCourse(employee, course);
		if(enrollment == null) {
			throw new ResourceNotFoundException("No such enrollment found");
		}
		
		try {
			enrollment.setStatus(Status.Suspended.name());
			enrollmentRepo.save(enrollment);
		}catch(Exception exc) {
			throw new ApiTestException("Unable to suspend course");
		}
		
		return true;
	}
	
}
