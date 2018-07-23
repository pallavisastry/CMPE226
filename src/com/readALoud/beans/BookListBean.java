package com.readALoud.beans;

import java.io.Serializable;
import java.util.Collection;

import com.readALoud.entities.Book;
import com.readALoud.entities.Comments;

public class BookListBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1456691985349541698L;
	
	private String title;
	private int ebookID;
	private String Language;
	private Collection<Comments> comment;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getEbookID() {
		return ebookID;
	}
	public void setEbookID(int ebookID) {
		this.ebookID = ebookID;
	}
	public String getLanguage() {
		return Language;
	}
	public void setLanguage(String language) {
		Language = language;
	}
	public Collection<Comments> getComment() {
		return comment;
	}
	public void setComment(Collection<Comments> comment) {
		this.comment = comment;
	}

	

}
