package com.apitest.model;

import java.util.ArrayList;
import java.util.List;

public class CourseDto extends BaseDto{

	private Long courseNo;
	private String courseName;
	private List<String> weekDays = new ArrayList<String>();
	
	public Long getCourseNo() {
		return courseNo;
	}
	public void setCourseNo(Long courseNo) {
		this.courseNo = courseNo;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public List<String> getWeekDays() {
		return weekDays;
	}
	public void setWeekDays(List<String> weekDays) {
		this.weekDays = weekDays;
	}
	
	
}
