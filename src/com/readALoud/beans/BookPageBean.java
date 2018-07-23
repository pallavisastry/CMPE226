package com.readALoud.beans;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import com.readALoud.entities.Book;
import com.readALoud.entities.Comments;
import com.readALoud.entities.User;

public class BookPageBean implements Serializable{

	private static final long serialVersionUID = -5112916586115617629L;
	/**
	 * 
	 */
	private Long bookid;
	public Long getBookid() {
		return bookid;
	}
	public void setBookid(Long bookid) {
		this.bookid = bookid;
	}
	private String title;
	private int ebookID;
	private String Language;
	private String genre;
	private Date postingDate;
	private Date releaseDate;
	private Date lastUpdated;
	private Collection<User> Author;
	
	public Collection<User> getAuthor() {
		return Author;
	}
	public void setAuthor(Collection<User> author) {
		Author = author;
	}
	private Collection<Comments> commentsList;
	
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
	public Collection<Comments> getCommentsList() {
		return commentsList;
	}
	public void setCommentsList(Collection<Comments> commentsList) {
		this.commentsList = commentsList;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public Date getPostingDate() {
		return postingDate;
	}
	public void setPostingDate(Date postingDate) {
		this.postingDate = postingDate;
	}
	public Date getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	public Date getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	

}
