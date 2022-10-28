package com.pdafr.computer.science.library.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.pdafr.computer.science.library.enums.Category;

@Entity
@Table(name="VIDEOS")
public class Video {

    // Fields
	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(name="TITLE")
	private String title;
	
	@Column(name="CHANNEL")
	private String channel;
	
	@Column(name="CATEGORY")
	private Category category;
	
	@Column(name="LENGTH")
	private Integer length;
	
	@Column(name="LINK")
	private String link;
	
	// Constructors
	public Video() {}
	
	public Video(Integer id, String title, String channel, Category category, Integer length, String link) {
	    this.id = id;
	    this.title = title;
	    this.channel = channel;
	    this.category = category;
	    this.length = length;
	    this.link = link;
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
	
	public String getChannel() {
	    return channel;
	}
	
	public void setChannel(String channel) {
	    this.channel = channel;
	}
	
	public Category getCategory() {
	    return category;
	}
	
	public void setCategory(Category category) {
	    this.category = category;
	}
	
	public Integer getLength() {
	    return length;
	}
	
	public void setLength(Integer length) {
	    this.length = length;
	}
	
	public String getLink() {
	    return link;
	}
	
	public void setLink(String link) {
	    this.link = link;
	}
	
}
