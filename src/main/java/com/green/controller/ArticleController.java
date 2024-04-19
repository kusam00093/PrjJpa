package com.green.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.green.dto.ArticleDto;
import com.green.dto.ArticleForm;
import com.green.entity.Article;
import com.green.repository.ArticleRepository;

import lombok.extern.slf4j.Slf4j;
@Slf4j
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
		
		// 수정할 데이터를 조회한다
		Article articleEntity = articleRepository.findById(id).orElse(null);
		
		// 조회한 데이터를 model에 저장
		model.addAttribute("article",articleEntity);
		
		// 수정페이지로 이동		
		System.out.println("수정 조회결과:"+articleEntity);
		return "/articles/edit";
	}
	
	@PostMapping("/articles/Edit")
	public String edit(ArticleForm articleForm) {
		
		log.info("수정용 데이터:"+articleForm.toString());
		
		
		//db 수정
		// 1.Dto -> Entity로 변환
//		
//		Long   id      = articleForm.getId();
//		String title   = articleForm.getTitle();
//		String content = articleForm.getContent();
//				
//		Article articleEntity = new Article(id,title,content);
		
		Article articleEntity = articleForm.toEntity();
		
		// 2. entity 를 db에 수정한다
		// 2-1. 수정할 데이터를 찾아서(db의 data를 가져온다)
		Article article = articleRepository.findById(articleForm.getId()).orElse(null);
		// 2-2. 필요한 데이터를 변경한다
		if(article != null) { // 자료가 있으면 저장(수정)한다
			articleRepository.save(articleEntity);
			
		}

		//return "redirect:/articles/View/"+id;    //articles/view.nustache
		return "redirect:/articles/List";    //articles/view.nustache
		
	}
	
	
	@GetMapping("articles/{id}/Delete")
	public String delete(@PathVariable Long id, RedirectAttributes rttr) {
		
		// 삭제할 대상을 검색한다
		Article target = articleRepository.findById(id).orElse(null);
		
		// 대상 entity를 삭제한다
		if(target != null) {
			articleRepository.delete(target);
			
			// RedirectAttributes: 리다이렉트 할 페이지에서 사용할 데이터를 넘겨줌
			// 한번 쓰면 사라지는 휘발성 데이터
			// 삭제 후 임시메세지를 lsit.mustache가 출력한다
			rttr.addFlashAttribute("msg",id+"번 게시글이 삭제되었습니다");
			// header.mustache 에 출력
		}
		
		
		
		return "redirect:/articles/List";
	}
	
}
