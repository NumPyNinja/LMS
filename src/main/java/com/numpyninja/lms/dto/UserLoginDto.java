package com.numpyninja.lms.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.numpyninja.lms.config.ValidateStatus;

import com.numpyninja.lms.util.Constants;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonIgnoreProperties(value = {"roleIds"}, allowGetters = true)
public class UserLoginDto {
    @NotBlank(message = "UserLoginEmail is mandatory")
    @Pattern(regexp = Constants.REGEX_EMAIL_ID , message = "userLoginEmail " + Constants.MSG_INVALID_EMAIL_ID)
    private String userLoginEmail;

    private String password;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ValidateStatus
    private String loginStatus;

  //created custom annotation to validate status( accepts only "Active" and "Inactive")
    @ValidateStatus
    //@JsonIgnore
    private String status;

    private List<String> roleIds;

}
