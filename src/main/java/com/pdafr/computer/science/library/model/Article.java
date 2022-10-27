package com.pdafr.computer.science.library.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.pdafr.computer.science.library.enums.Category;

@Entity
@Table(name="ARTICLES")
public class Article {

    // Fields
	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(name="TITLE")
	private String title;
	
	@Column(name="AUTHOR")
	private String author;
	
	@Column(name="CATEGORY")
	private Category category;
	
	@Column(name="READ_TIME")
	private Integer readTime;

	@Column(name="LINK")
	private String link;
	
	// Constructors
	public Article() {}
	
	public Article(Integer id, String title, String author, Category category, Integer readTime, String link) {
	    this.id = id;
	    this.title = title;
	    this.author = author;
	    this.category = category;
	    this.readTime = readTime;
	    this.link = link;
	}
	
	// Getters and Setter methos
	public Integer getId() {
	    return id;
	}
	
	public void setId(Integer id) {
	    this.id = id;
	}
	
	public String getTitle() {
	    return title;
	}
	
	public void setTitle(String title) {
	    this.title = title;
	}
	
	public String getAuthor() {
	    return author;
	}
	
	public void setAuthor(String author) {
	    this.author = author;
	}
	
	public Category getCategory() {
	    return category;
	}
	
	public void setCategory(Category category) {
	    this.category = category;
	}
	
	public Integer getReadTime() {
	    return readTime;
	}
	
	public void setReadTime(Integer readTime) {
	    this.readTime = readTime;
	}
	
	public String getLink() {
	    return link;
	}
	
	public void setLink(String link) {
	    this.link = link;
	}
	
}
