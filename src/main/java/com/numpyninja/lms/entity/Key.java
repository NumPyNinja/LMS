package com.numpyninja.lms.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

public class Key {
	@Id
	@Column
	private Integer id;

	@Column
	//@Type(type = "org.hibernate.type.BinaryType")
	private byte[] key;
}
