package com.green.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.green.dto.ArticleDto;
import com.green.entity.Article;
import com.green.repository.ArticleRepository;

@Controller
public class ArticleController {
	
	@Autowired
	private ArticleRepository articleRepository;

	@GetMapping("/articles/WriteForm")
	public String writeForm() {
		return "articles/write";  // greetings.mustache화면을 보여줄 template 이름
							// resources/template package 에 생성		
	}
	
	@PostMapping("/articles/Write")
	public String write(ArticleDto articleDto) {
			System.out.println("결과:"+articleDto.toString());
			
			Article article = articleDto.toEntity();
			Article saved = articleRepository.save(article);   // INSERT
			System.out.println("세이브:"+saved);
		return "redirect:/articles/List";
	}
	// 1번 데이터 조회 : PathVariable -> get 방식
	// java.lang.IllegalArgumentException: Name for argument of type [java.lang.Long] not specified, and parameter name information not available via reflection. Ensure that the compiler uses the '-parameters' flag.
	// 1. PathVariable (value="id")
	// 2. sts 설정 추가
	// 프로젝트 - properties -> java compiler -> enable project specific settings 체크
	// store infomation 체크 
	@GetMapping("articles/{id}")
	public String view(@PathVariable(value="id") Long id, Model model) {
		
		
		// 1번 방법
		// Article articleEntity = articleRepository.findById(id);
		// Type mismatch error
		// Optional<Article> articleEntity = articleRepository.findById(id);
		// 값이 있으면 Article 을 return 하고 값이 없으면 null
		
		
		//2번 방법
		Article articleEntity = articleRepository.findById(id).orElse(null);
		System.out.println("1번 조회결과:"+articleEntity);
		model.addAttribute("article",articleEntity); //조회한 결과 -> model 에 담는다
		return "/articles/view";    //articles/view.nustache
		
	}
	@GetMapping("articles/List")
	public String list(Model model) {
		
		//List<Article> articleEntityList =articleRepository.findAll();
		// 오류 처리 1번 방법
		//List<Article> articleEntityList =(List<Article>)articleRepository.findAll();
		// 오류 처리 2번 방법
		// ArticleRepository interface 에 함수를 등록
		
		List<Article> articleEntityList =articleRepository.findAll();
		System.out.println("전체 목록:"+articleEntityList);
		model.addAttribute("articleList",articleEntityList);
		
		
		return "/articles/list";    //articles/view.nustache
		
	}
	
	@GetMapping("/articles/{id}/EditForm")
	public String editForm(@PathVariable(value="id") Long id,Model model) {
		Article articleEntity = articleRepository.findById(id).orElse(null);
		System.out.println("수정 조회결과:"+articleEntity);
		model.addAttribute("article",articleEntity);
		return "/articles/edit";
	}
	
	@GetMapping("/aritcles/{id}/Edit")
	public String edit(@PathVariable(value="id") Long id, ArticleDto articleDto) {
			
		System.out.println("수정결과:"+articleDto.toString());
		Article article = articleDto.toEntity();
		Article saved = articleRepository.save(article); 
		System.out.println("수정세이브:"+saved);
		
		//return "redirect:/articles/View/"+id;    //articles/view.nustache
		return "redirect:/articles/List";    //articles/view.nustache
		
	}
	
}
