package com.apitest.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apitest.entities.Course;
import com.apitest.entities.Employee;
import com.apitest.entities.Enrollment;

@Repository
public interface EnrollmentRepo extends JpaRepository<Enrollment, Long>{

	List<Enrollment> findByEmployee(Employee employee);
	List<Enrollment> findByCourse(Course course);
	
	Enrollment findByEmployeeAndCourse(Employee emp, Course course);
}
