package com.wpm.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnTransformer;

import lombok.Data;

@Entity
@Data
public class Password {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;


	private String companyName;
	@ColumnTransformer(write = "HEX(AES_ENCRYPT(?, 'secret-key'))", read = "CAST(AES_DECRYPT(UNHEX(password), 'secret-key') as CHAR)")
	private String password;

}
