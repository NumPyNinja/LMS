package com.numpyninja.lms.entity;

import java.sql.Timestamp;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name="tbl_lms_skill_master")
public class SkillMaster {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "skill_seq_id")
	@SequenceGenerator(name="skill_seq_id", sequenceName = "tbl_lms_skill_master_skill_id_seq", allocationSize = 1)
	@Column(name="skill_id")
	private Long skillId;
	
	@Column(name="skill_name")
	private String skillName;
	
	@Column(name="creation_time")
	private Timestamp creationTime;
	
	@Column(name="last_mod_time")
	private Timestamp lastModTime;
	
}