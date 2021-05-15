package com.apitest.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apitest.entities.Course;

@Repository
public interface CourseRepo extends JpaRepository<Course, Long>{

}
