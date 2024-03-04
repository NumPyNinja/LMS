package com.numpyninja.lms.controller;

import java.util.List;

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

import com.numpyninja.lms.config.ApiResponse;
import com.numpyninja.lms.dto.AssignmentDto;
import com.numpyninja.lms.dto.AssignmentSubmitDTO;
import com.numpyninja.lms.services.AssignmentService;
import com.numpyninja.lms.services.AssignmentSubmitService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;

@RestController
@RequestMapping("/assignments")
//@Tag(name = "Assignment Controller", description = "Assignment CRUD Operations")
@Api(tags="Assignment Controller", description="Assignment CRUD Operations")
public class AssignmentController {
	
	@Autowired
	private AssignmentService assignmentService;
	
	@Autowired
	public AssignmentSubmitService assignmentSubmitService;
	
	//create an assignment
	@PostMapping("")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_STAFF')")
	//@Operation(description = "Create New Assignment")
	@ApiOperation("Create New Assignment")
	public ResponseEntity<AssignmentDto> createAssignment(@Valid @RequestBody AssignmentDto assignmentDto) {
		AssignmentDto createdAssignmentDto =  this.assignmentService.createAssignment(assignmentDto);
		return new ResponseEntity<>(createdAssignmentDto, HttpStatus.CREATED);
	}
	
	//update an assignment
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_STAFF')")
	//@Operation(description = "Update existing Assignment")
	@ApiOperation("Update existing Assignment")
	public ResponseEntity<AssignmentDto> updateAssignment(@Valid @RequestBody AssignmentDto assignmentDto, @PathVariable Long id) {
		AssignmentDto updatedAssignmentDto =  this.assignmentService.updateAssignment(assignmentDto, id);
		return ResponseEntity.ok(updatedAssignmentDto);
	}
	
	//delete an assignment
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_STAFF')")
	//@Operation(description = "Delete existing Assignment")
	@ApiOperation("Delete existing Assignment")
	public ResponseEntity<ApiResponse> deleteAssignment(@PathVariable Long id) {
		
		List<AssignmentSubmitDTO> AssignmentsubmissionDTOs = assignmentSubmitService.getSubmissionsByAssignment(id); 
		
		if (AssignmentsubmissionDTOs.isEmpty())  {
			this.assignmentService.deleteAssignment(id);
		    return new ResponseEntity<ApiResponse>(new ApiResponse("Assignment deleted successfully", true), HttpStatus.OK);
		}
		else {
			return new ResponseEntity<ApiResponse>(new ApiResponse("Cannot delete assignment after submission", true), HttpStatus.FORBIDDEN);

		}
	}
	
	//get all assignments
    @GetMapping("")
	//@Operation(description = "Get list of all Assignments")
    @ApiOperation("Get list of all Assignments")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_STAFF')")
    public ResponseEntity<List<AssignmentDto>> getAllAssignments() {
        return ResponseEntity.ok(this.assignmentService.getAllAssignments());  
    }
    
    //get assignment by id
  	@GetMapping("/{id}")
	//@Operation(description = "Get Assignment details by Assignment Id")
  	@ApiOperation("Get Assignment details by Assignment Id")
  	public ResponseEntity<AssignmentDto> getAssignmentById(@PathVariable(value="id") Long id) {
  		return ResponseEntity.ok(this.assignmentService.getAssignmentById(id));
  	}
  	
  	//get all batches of a program
  	@GetMapping("/batch/{batchId}")
	//@Operation(description = "Get all Assignments for Batch")
  	@ApiOperation("Get all Assignments for Batch")
  	public ResponseEntity<List<AssignmentDto>> getAssignmentsForBatch(@PathVariable(value="batchId") Integer batchId) {
  		return ResponseEntity.ok(this.assignmentService.getAssignmentsForBatch(batchId));
  	}
	
}
