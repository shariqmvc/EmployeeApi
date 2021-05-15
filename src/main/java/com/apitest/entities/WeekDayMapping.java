package com.apitest.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="week_day_mapping")
public class WeekDayMapping {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long mapId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "course_no", nullable = false)
	private Course course;
	
	private String weekDay;

	public Long getMapId() {
		return mapId;
	}

	public void setMapId(Long mapId) {
		this.mapId = mapId;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public String getWeekDay() {
		return weekDay;
	}

	public void setWeekDay(String weekDay) {
		this.weekDay = weekDay;
	}
	
	
}
