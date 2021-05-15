package com.apitest.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class EmployeeDto extends BaseDto {

	private Long employeeId;
	private String employeeName;
	private Long courseNo;
	private List<String> weekDays;
	
	public Long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public Long getCourseNo() {
		return courseNo;
	}
	public void setCourseNo(Long courseNo) {
		this.courseNo = courseNo;
	}
	public List<String> getWeekDays() {
		return weekDays;
	}
	public void setWeekDays(List<String> weekDays) {
		this.weekDays = weekDays;
	}
	
}
