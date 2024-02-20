package com.cyberguardians.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cyberguardians.entity.Javas_Question;
import com.cyberguardians.repository.QuestionRepository;

@Controller
@RequestMapping("/question")
public class QuestionController {

	@Autowired
	private QuestionRepository repo;

	@GetMapping("/list")
	public String findAll(Model model) {
		List<Javas_Question> questionList = repo.findAll();
		System.out.println("questionList : " + questionList.get(0).getQuestion_index());
		model.addAttribute("questionList", questionList);
		return "question";
	}

	@PostMapping("/write")
	public String Write(Javas_Question question) {
		repo.save(question);
		return "redirect:/question/list";
	}

}
