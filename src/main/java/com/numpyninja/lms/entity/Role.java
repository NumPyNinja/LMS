package com.numpyninja.lms.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table( name = "tbl_lms_role" )
public class Role {
	@Id
	@Column( name = "role_id" )
	private String roleId;
	
	@Column( name = "role_name" )
	private String roleName; 
	
	@Column( name = "role_desc" )
	private String roleDesc;
	
	@Column( name = "creation_time" )
    private Timestamp creationTime ;
	
	@Column( name = "last_mod_time" )
    private Timestamp lastModTime ;
	

}
