package com.numpyninja.lms.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.numpyninja.lms.dto.AssignmentDto;
import com.numpyninja.lms.dto.AttendanceDto;
import com.numpyninja.lms.dto.BatchDTO;
import com.numpyninja.lms.entity.Attendance;
import com.numpyninja.lms.mappers.AttendanceMapper;
import com.numpyninja.lms.repository.AttendanceRepository;
import com.numpyninja.lms.services.AttendanceServices;

@RestController
@RequestMapping("/attendance")
public class AttendanceController{

	@Autowired
	private AttendanceServices attendanceServices;
	// get all attendances
	@GetMapping("")
	protected ResponseEntity<List<AttendanceDto>> getAllAssignments() {
		return ResponseEntity.ok(this.attendanceServices.getAllAttendances());
	}

	// get attendance By id
	@GetMapping("/{id}")
	public ResponseEntity<AttendanceDto> findById(@PathVariable(value = "id") Long id) {
		return ResponseEntity.ok(this.attendanceServices.getAttendanceById(id));
	}

	// get all Attendance of a Student
	@GetMapping("/student/{studentId}")
	public ResponseEntity<List<AttendanceDto>> getAttendancesForStudent(
			@PathVariable(value = "studentId") String studentId) {
		return ResponseEntity.ok(this.attendanceServices.getAttendanceForStudent(studentId));
	}

	// get all Attendance of a Class
	@GetMapping("/class/{classId}")
	public ResponseEntity<List<AttendanceDto>> getAttendancesbyClass(@PathVariable(value = "classId") Long classId) {
		return ResponseEntity.ok(this.attendanceServices.getAttendanceByClass(classId));
	}

	// get all Attendance of a Batch
	@GetMapping("/batch/{batchId}")
	public ResponseEntity<List<AttendanceDto>> getAttendancesbyBatch(@PathVariable(value = "batchId") Integer batchId) {
		return ResponseEntity.ok(this.attendanceServices.getAttendanceByBatch(batchId));
	}

	@DeleteMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<String> deleteAttendance(@PathVariable Long id) {
		attendanceServices.deleteAttendance(id);
		String message = "Message:" + " Attendance Id-" + id + " deleted Successfully!";
		return ResponseEntity.ok(message);
	}

	@PostMapping(path = "", consumes = "application/json", produces = "application/json")
	public ResponseEntity<AttendanceDto> createAttendance(@Valid @RequestBody AttendanceDto attendanceDto) {
		AttendanceDto newAttendance = attendanceServices.createAttendance(attendanceDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(newAttendance);
	}

	// update an Attendance
	@PutMapping("/{id}")
	public ResponseEntity<AttendanceDto> updateAttendance(@RequestBody AttendanceDto attendanceDto,
			@PathVariable Long id) {
		AttendanceDto updatedAttendanceDto = this.attendanceServices.updateAttendance(attendanceDto, id);
		return ResponseEntity.ok(updatedAttendanceDto);
	}

}
