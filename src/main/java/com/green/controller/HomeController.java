package com.green.controller;

import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	@GetMapping("/hi")
	public String hi() {
		return "greetings";  // greetings.mustache화면을 보여줄 template 이름
							// resources/template package 에 생성		
	}
	
	@GetMapping("/hi2")
	public String hi2(Model model) {
		model.addAttribute("username","페이커");
		
		return "greetings2";
	}

	@GetMapping("/hi3")
	public String hi3(Model model) {
		model.addAttribute("username","박지성");
		
		return "greetings3";
	}
	
	@GetMapping("/hi4")
	public String hi4(Model model) {
		model.addAttribute("username","조승우");
		
		return "greetings4";
	}
	
	
}
