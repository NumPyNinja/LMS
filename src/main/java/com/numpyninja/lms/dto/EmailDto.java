package com.numpyninja.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EmailDto {
	
     @NotBlank(message = "EmailId is mandatory")
     private String userLoginEmailId;
     
   }
