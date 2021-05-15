package com.apitest.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apitest.entities.WeekDayMapping;

@Repository
public interface WeekDayMapRepo extends JpaRepository<WeekDayMapping, Long>{

}
