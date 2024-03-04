package com.numpyninja.lms.dto;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClassRecordingDTO{

	
	//private Integer recordid;
	@Id
	private Long csId;
	
	private String classRecordingPath;
	// private Integer batchid;
	 //private Integer batchid;
	 
}
