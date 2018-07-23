package com.readALoud.beans;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import com.readALoud.entities.Book;
import com.readALoud.entities.Comments;
import com.readALoud.entities.GenreRoom;
import com.readALoud.entities.User;

public class UserDetailsBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8264144620687668415L;
	private User user;
	private long userId;
	private Collection<GenreRoom> genreRooms;
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	private String fname;
	private String lname;
	private Date loginDate;
	
	private Collection<Book> booksRead;
	
	private Collection<Comments> comments;

	private int totalCommentsCount;
	
	
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getFname() {
		return fname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String getLname() {
		return lname;
	}
	public void setComments(Collection<Comments> comms) {
		this.comments = comms;
	}
	public Collection<Comments> getComments() {
		return comments;
	}
	public void setTotalCommentsCount(int totalCommentsCount) {
		this.totalCommentsCount = totalCommentsCount;
	}
	public int getTotalCommentsCount() {
		return totalCommentsCount;
	}
	public void setBooksRead(Collection<Book> booksRead) {
		this.booksRead = booksRead;
	}
	public Collection<Book> getBooksRead() {
		return booksRead;
	}
	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}
	public Date getLoginDate() {
		return loginDate;
	}
	public void setGenreRooms(Collection<GenreRoom> genreRooms) {
		this.genreRooms = genreRooms;
	}
	public Collection<GenreRoom> getGenreRooms() {
		return genreRooms;
	}
	
}
