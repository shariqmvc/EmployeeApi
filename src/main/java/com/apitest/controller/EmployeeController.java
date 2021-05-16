package com.apitest.controller;

import static org.springframework.http.ResponseEntity.ok;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.relation.InvalidRelationIdException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apitest.entities.Course;
import com.apitest.entities.Employee;
import com.apitest.entities.Enrollment;
import com.apitest.exception.ApiTestException;
import com.apitest.exception.ResourceNotFoundException;
import com.apitest.model.EmployeeDto;
import com.apitest.repo.EnrollmentRepo;
import com.apitest.service.CourseDaoService;
import com.apitest.service.EmployeeDaoService;

@RestController
@RequestMapping("/api")
public class EmployeeController {

	@Autowired
	EmployeeDaoService employeeDaoService;
	@Autowired
	CourseDaoService courseDaoService;
	@Autowired
	EnrollmentRepo enrollmentRepo;

	@PostMapping("/employee")
	public ResponseEntity<?> createEmployee(@RequestBody EmployeeDto employeeDto) throws InvalidRelationIdException {
		if (employeeDto.getEmployeeName() == null || employeeDto.getEmployeeName().isEmpty()) {
			throw new InvalidRelationIdException("Employee name is missing");
		}

		EmployeeDto savedEmployee = employeeDaoService.createEmployee(employeeDto);

		savedEmployee.setStatusDetail("Employee saved successfully");
		savedEmployee.setSuccess(true);

		return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
	}
	@PostMapping("/employee/enroll")
	public ResponseEntity<Map<String, Object>> enrollEmployee(@RequestBody EmployeeDto employeeDto)
			throws InvalidRelationIdException {
		if (employeeDto.getEmployeeId() == null) {
			throw new ApiTestException("Employee id is missing");
		}
		if (employeeDto.getCourseNo() == null) {
			throw new ApiTestException("Course no is missing");
		}

		if (employeeDto.getWeekDays().size() < 1) {
			throw new ApiTestException("Weekdays are missing");
		}

		Employee employee = employeeDaoService.getEmployeeById(employeeDto.getEmployeeId());
		if (employee == null) {
			throw new ApiTestException("Invalid employee id");
		}

		Course course = courseDaoService.getCourseByNo(employeeDto.getCourseNo());
		if (course == null) {
			throw new ApiTestException("Invalid course no.");
		}

		// logic to check if already enrolled. //Can opt for mutiple courses to be confirmed
		List<Enrollment> alreadyEnrolled = enrollmentRepo.findByEmployee(employee);
		List<String> weekDays = new ArrayList<String>();
		for(Enrollment enrollment : alreadyEnrolled) {
			if (enrollment != null) {

				if (enrollment.getWeekDays().split(",").length > 1) {
					weekDays = Arrays.asList(enrollment.getWeekDays().split(","));
				} else {
					weekDays.add(enrollment.getWeekDays());
				}

				for (String exisitingWeekDay : weekDays) {
					for (String weekDay : employeeDto.getWeekDays()) {
						if (weekDay.equals(exisitingWeekDay)) {
							throw new ApiTestException("Already enrolled with a course at this day");
						}
					}

				}

			}
		}
		

		Boolean newEnrollment = employeeDaoService.enrollEmployee(course, employee, employeeDto.getWeekDays());

		Map<String, Object> model = new HashMap<>();
		model.put("statusDetails", newEnrollment == true ? "Enrollment successfull" : "Failed to enroll");
		model.put("success", newEnrollment == true ? "success" : "false");

		return ok(model);
	}
	
	@PutMapping("/employee/{employeeId}")
	public ResponseEntity<?> updateEmployee(@RequestBody EmployeeDto employeeDto, @PathVariable("employeeId") Long employeeId) throws InvalidRelationIdException{
		if (employeeDto.getEmployeeName() == null || employeeDto.getEmployeeName().isEmpty()) {
			throw new InvalidRelationIdException("Employee name is missing");
		}
		
		Employee alreadyEmp = employeeDaoService.getEmployeeById(employeeId);
		if(alreadyEmp == null) {
			throw new ResourceNotFoundException("Invalid employee id");
		}
		
		alreadyEmp.setEmployeeName(employeeDto.getEmployeeName());
		employeeDaoService.updateEmployee(alreadyEmp);
		

		employeeDto.setStatusDetail("Employee updated successfully");
		employeeDto.setSuccess(true);

		return ResponseEntity.status(HttpStatus.CREATED).body(employeeDto);
	}
	@DeleteMapping("/employee/{employeeId}")
	public ResponseEntity<Map<String, Object>> deleteEmployee(@PathVariable("employeeId") Long employeeId){
		Employee employee = employeeDaoService.getEmployeeById(employeeId);
		if(employee == null) {
			throw new ApiTestException("Invalid employee Id");
		}
		
		employeeDaoService.deleteEmployee(employee);
		
		Map<String, Object> model = new HashMap<>();
		model.put("statusDetails", "Employee deleted successfully");
		model.put("success", "true");

		return ok(model);
	}
	@GetMapping("/employee/{searchTerm}")
	public ResponseEntity<?> searchEmployee(@PathVariable("searchTerm") String searchTerm) throws InvalidRelationIdException {
		if (searchTerm == null || searchTerm.isEmpty()) {
			throw new InvalidRelationIdException("searchTerm is missing");
		}

		List<EmployeeDto> fetchedEmployees = employeeDaoService.searchEmployee(searchTerm);

		Map<String, Object> model = new HashMap<>();
		model.put("statusDetails", "Found " +fetchedEmployees.size()+ " employees");
		model.put("success", "true");
		model.put("employees", fetchedEmployees);

		return ok(model);
	}
	
	

}
