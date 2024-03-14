package com.numpyninja.lms.controller;

import java.util.List;
import javax.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.numpyninja.lms.dto.AttendanceDto;
import com.numpyninja.lms.services.AttendanceServices;

@RestController
@RequestMapping("/attendance")
@Tag(name = "Attendance Controller",description = "Attendance CRUD Operations")
//@Api(tags="Attendance Controller", description="Attendance CRUD Operations")
public class AttendanceController{

	@Autowired
	private AttendanceServices attendanceServices;
	// get all attendances
	@GetMapping("")
	@Operation(summary = "Get all Attendance records")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_STAFF')")
	protected ResponseEntity<List<AttendanceDto>> getAllAssignments() {
		return ResponseEntity.ok(this.attendanceServices.getAllAttendances());
	}

	// get attendance By id
	@GetMapping("/{id}")
	@Operation(summary = "Get Attendance by ID")
	public ResponseEntity<AttendanceDto> findById(@PathVariable(value = "id") Long id) {
		return ResponseEntity.ok(this.attendanceServices.getAttendanceById(id));
	}

	// get all Attendance of a Student
	@GetMapping("/student/{studentId}")
	@Operation(summary = "Get Attendance for Student by Student ID")
	public ResponseEntity<List<AttendanceDto>> getAttendancesForStudent(
			@PathVariable(value = "studentId") String studentId) {
		return ResponseEntity.ok(this.attendanceServices.getAttendanceForStudent(studentId));
	}

	// get all Attendance of a Class
	@GetMapping("/class/{classId}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_STAFF')")
	@Operation(summary = "Get Attendance by Class ID")
	public ResponseEntity<List<AttendanceDto>> getAttendancesbyClass(@PathVariable(value = "classId") Long classId) {
		return ResponseEntity.ok(this.attendanceServices.getAttendanceByClass(classId));
	}

	// get all Attendance of a Batch
	@GetMapping("/batch/{batchId}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_STAFF')")
	@Operation(summary = "Get Attendance by Batch ID")
	public ResponseEntity<List<AttendanceDto>> getAttendancesbyBatch(@PathVariable(value = "batchId") Integer batchId) {
		return ResponseEntity.ok(this.attendanceServices.getAttendanceByBatch(batchId));
	}

	@DeleteMapping(path = "/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Operation(summary = "Delete Attendance")
	public ResponseEntity<String> deleteAttendance(@PathVariable Long id) {
		attendanceServices.deleteAttendance(id);
		String message = "Message:" + " Attendance Id-" + id + " deleted Successfully!";
		return ResponseEntity.ok(message);
	}

	@PostMapping(path = "", consumes = "application/json", produces = "application/json")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_STAFF')")
	@Operation(summary = "Create new Attendance")
	public ResponseEntity<AttendanceDto> createAttendance(@Valid @RequestBody AttendanceDto attendanceDto) {
		AttendanceDto newAttendance = attendanceServices.createAttendance(attendanceDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(newAttendance);
	}

	// update an Attendance
	@PutMapping(path="/{id}",consumes = "application/json", produces = "application/json")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_STAFF')")
	@Operation(summary = "Update Attendance")
	public ResponseEntity<AttendanceDto> updateAttendance(@RequestBody AttendanceDto attendanceDto,
			@PathVariable Long id) {
		AttendanceDto updatedAttendanceDto = this.attendanceServices.updateAttendance(attendanceDto, id);
		return ResponseEntity.ok(updatedAttendanceDto);
	}

}
