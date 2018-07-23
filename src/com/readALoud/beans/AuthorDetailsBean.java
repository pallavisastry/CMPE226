package com.readALoud.beans;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import com.readALoud.entities.Book;
import com.readALoud.entities.Comments;
import com.readALoud.entities.GenreRoom;

public class AuthorDetailsBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2081080448148885193L;
	private String fname;
	private String lname;
	private Collection<Book> booksRead;
	private Collection<Book> booksWritten;
	private Collection<Comments> comments;
	private int totalBooksRead;
	private int totalBooksWritten;
	private int totalCommentsCount;
	private Date loginDate;
	private Collection<GenreRoom> genreRooms;
	
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
	public void setBooksWritten(Collection<Book> booksWritten) {
		this.booksWritten = booksWritten;
	}
	public Collection<Book> getBooksWritten() {
		return booksWritten;
	}
	public void setTotalBooksRead(int totalBooksRead) {
		this.totalBooksRead = totalBooksRead;
	}
	public int getTotalBooksRead() {
		return totalBooksRead;
	}
	public void setTotalBooksWritten(int totalBooksWritten) {
		this.totalBooksWritten = totalBooksWritten;
	}
	public int getTotalBooksWritten() {
		return totalBooksWritten;
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
