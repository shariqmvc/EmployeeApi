package com.apitest.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.apitest.entities.Course;

@Repository
public interface CourseRepo extends JpaRepository<Course, Long>{

	@Query(value = "select distinct(cr.course_no) from course cr join week_day_mapping wd on cr.course_no = wd.course_no where wd.week_day = :searchTerm or cr.course_no = :searchTerm", nativeQuery = true)
	List<Long> getCourseBySearch(@Param("searchTerm") String searchTerm);
}
