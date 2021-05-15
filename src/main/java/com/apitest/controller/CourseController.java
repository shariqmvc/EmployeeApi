package com.apitest.controller;

import static org.springframework.http.ResponseEntity.ok;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apitest.entities.Course;
import com.apitest.entities.Employee;
import com.apitest.exception.ApiTestException;
import com.apitest.exception.InvalidRequestParameterException;
import com.apitest.model.CourseDto;
import com.apitest.model.EmployeeDto;
import com.apitest.service.CourseDaoService;

@RestController
@RequestMapping("/api")
public class CourseController {
	
	@Autowired
	CourseDaoService courseDaoService;
    
	@PostMapping("/Course")
	public ResponseEntity<?> createCourse(@RequestBody CourseDto courseDto){
		if(courseDto.getCourseName() == null || courseDto.getCourseName().isEmpty()) {
			throw new InvalidRequestParameterException("Course name is missing");
		}
		
		if(courseDto.getWeekDays().size() == 0) {
			throw new InvalidRequestParameterException("Week day is missing");
		}
		
		CourseDto savedCourse = courseDaoService.createCourse(courseDto);
		
		savedCourse.setStatusDetail("Course added successfully");
		savedCourse.setSuccess(true);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(savedCourse);
	}
	
	@PutMapping("/Course/{courseNo}")
	public ResponseEntity<?> updateCourse(@RequestBody CourseDto courseDto, @PathVariable("courseNo") Long courseNo){
		
		Course course = courseDaoService.getCourseByNo(courseNo);
		if(course == null){
		  throw new ApiTestException("Course doesnot exist");	
		}
		
		if(courseDto.getCourseName() == null || courseDto.getCourseName().isEmpty()) {
			throw new InvalidRequestParameterException("Course name is missing");
		}
		
		course.setCourseName(courseDto.getCourseName());
		courseDaoService.updateCourse(course);
		
		
		courseDto.setStatusDetail("Course updated successfully");
		courseDto.setSuccess(true);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(courseDto);
	}
	
	@DeleteMapping("/Course/{courseNo}")
	public ResponseEntity<Map<String, Object>> deleteEmployee(@PathVariable("courseNo") Long courseNo){
		Course course = courseDaoService.getCourseByNo(courseNo);
		if(course == null){
			  throw new ApiTestException("Course doesnot exist");	
	    }
		
		List<Employee> employees = courseDaoService.getEmployeesByCourses(course);
		
		List<EmployeeDto> employeeDtos = new ArrayList<EmployeeDto>();
		EmployeeDto employeeDto;
		for(Employee employee : employees) {
			employeeDto = new EmployeeDto();
			employeeDto.setEmployeeId(employee.getEmployeeId());
			employeeDto.setEmployeeName(employee.getEmployeeName());
			employeeDtos.add(employeeDto);
		}
		
		
		courseDaoService.deleteCourse(course);
		
		Map<String, Object> model = new HashMap<>();
		model.put("statusDetails", "Course deleted successfully");
		model.put("success", "true");
		model.put("affectEmployees", employeeDtos);

		return ok(model);
	}
	
}
