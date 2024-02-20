package com.cyberguardians.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Javas_Question {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long question_index;
	
	@Column(length = 100, nullable = false)
	private String question_title;
	
	@Column(length = 1000, nullable = false)
	private String question_content;
	
	@Column(length = 100, nullable = false)
	private String question_email;
	
}
