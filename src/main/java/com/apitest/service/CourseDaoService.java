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
import com.apitest.entities.WeekDayMapping;
import com.apitest.exception.ApiTestException;
import com.apitest.model.CourseDto;
import com.apitest.model.Status;
import com.apitest.repo.CourseRepo;
import com.apitest.repo.EnrollmentRepo;
import com.apitest.repo.WeekDayMapRepo;

@Service
public class CourseDaoService {

	@Autowired
	CourseRepo courseRepo;
	@Autowired
	WeekDayMapRepo weekDayMapRepo;
	@Autowired
	EnrollmentRepo enrollmentRepo;
	
	public CourseDto createCourse(CourseDto courseDto) {
		
	   Course course = new Course();
	   course.setCourseName(courseDto.getCourseName());
	   course.setCreatedAt(new Date());
	   course.setStatus(Status.Active.name());
	   
	   Course savedCourse;
	   try {
		   savedCourse = courseRepo.save(course);
	   }catch(Exception exc) {
		   throw new ApiTestException("Error in persisting course");
	   }
	   
	   
	   
	   WeekDayMapping mapping;
	   
	   for(String weekDay : courseDto.getWeekDays()) {
		   mapping =  new WeekDayMapping();
		   mapping.setCourse(savedCourse);
		   mapping.setWeekDay(weekDay);
		   try {
			   weekDayMapRepo.save(mapping);
		   }catch(Exception exc) {
			   throw new ApiTestException("Error in persisting Weekdays");
		   }
		   
	   }
	  
	   courseDto.setCourseNo(savedCourse.getCourseNo());
	   
	   return courseDto;
	   
	}
	
	public Course getCourseByNo(Long courseNo) {
		Optional<Course> course = courseRepo.findById(courseNo);
	    if(course.isPresent()) {
	    	return course.get();
	    }else {
	    	return null;
	    }
	}
	
	public Course updateCourse(Course course) {
		return courseRepo.save(course);
	}
	
	public void deleteCourse(Course course) {
		try {
			courseRepo.delete(course);
		}catch(Exception exc) {
			throw new ApiTestException("Error in deleting course");
		}
		
	}
	
	public List<Employee> getEmployeesByCourses(Course course){
		List<Employee> employeeList = new ArrayList<Employee>();
		List<Enrollment> enrollmentList = enrollmentRepo.findByCourse(course);
		for(Enrollment enrollment : enrollmentList) {
			employeeList.add(enrollment.getEmployee());
		}
		
		return employeeList;
	}
	
	public List<Course> getCoursesByKeyword(String searchTerm){
		List<Course> courseList = new ArrayList<Course>();
		List<Long> courseNos = courseRepo.getCourseBySearch(searchTerm);
		for(Long courseNo : courseNos) {
			courseList.add(getCourseByNo(courseNo));
		}
		
		return courseList;
	}
}
