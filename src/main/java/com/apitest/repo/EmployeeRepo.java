package com.apitest.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.apitest.entities.Employee;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long>{

	@Query(value = "select distinct(emp.employee_id) from employee emp join enrollment enroll on emp.employee_id = enroll.employee_id inner join course cs on enroll.course_no = cs.course_no where emp.employee_name like %:searchTerm% or cs.course_no = :searchTerm or emp.employee_id = :searchTerm", nativeQuery = true)
	List<Long> getEmployeeBySearch(@Param("searchTerm") String searchTerm);
	
}
