package com.green.dto;

import com.green.entity.Article;

public class ArticleForm {
	// Field
	
	private Long   id;
	private String title;
	private String content;
	
	//Getter & Setter
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	//Constructor
	public ArticleForm(Long id, String title, String content) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
	}
	
	//toString
	@Override
	public String toString() {
		return "ArticleForm [id=" + id + ", title=" + title + ", content=" + content + "]";
	}
	
	// toEntity
	// Method
	public Article toEntity() {
		
		return new Article(id,title,content);
	}
	
	
	
	
}