package com.cyberguardians.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cyberguardians.entity.Javas_Question;

@Repository
public interface QuestionRepository extends JpaRepository<Javas_Question, Long>{
	
}
