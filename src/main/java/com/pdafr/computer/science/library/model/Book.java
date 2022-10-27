package com.pdafr.computer.science.library.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.pdafr.computer.science.library.enums.Category;

@Entity
@Table(name="BOOKS")
public class Book {

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
	
	@Column(name="NUM_PAGES")
	private Integer numPages;
	
	// Constructors
	public Book() {}
	
    public Book(Integer id, String title, String author, Category category, Integer numPages) {
      this.id = id;
      this.title = title;
      this.author = author;
      this.category = category;
      this.numPages = numPages;
  }
	
	
	// Getters and Setters methods
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
	
	public Integer getNumPages() {
	    return numPages;
	}
	
	public void setNumPages(Integer numPages) {
	    this.numPages = numPages;
	}
	
}
