package com.apitest.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apitest.entities.Employee;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long>{

}
