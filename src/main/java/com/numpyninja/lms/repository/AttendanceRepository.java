package com.numpyninja.lms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.numpyninja.lms.entity.Attendance;
import com.numpyninja.lms.entity.Batch;
import com.numpyninja.lms.entity.User;
import com.numpyninja.lms.entity.Class;
@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long>{
	//Get Attendance by Student
	//List<Attendance> findByUser(User user);
	List<Attendance> findByuser_userId(String studentId);
	//List<Attendance> findByClass(Integer cs);
	List<Attendance> findByobjClass_csId(Long csId); 
	List<Attendance> findByobjClass_csIdIn(List<Long> csIds); 
}
