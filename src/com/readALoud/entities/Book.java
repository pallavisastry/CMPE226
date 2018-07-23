package com.readALoud.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="BOOK")
public class Book implements Serializable//Comparable<Books>
{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Book(String bookTitle){//,String isbn){ //this is only called when a new book is not there in DB
		this.setBookTitle(bookTitle);		
	}
	public Book(){
		
	}
	@Id
	@GeneratedValue
	@Column(name="book_id")
	private Long bookid;
		
	@Column(name="book_title",nullable=false)
	private String bookTitle;

	@Column(name="book_desc")
	private String bookDesc;

	@Column(name="isbn",nullable=true)
	private String isbn;	

	@Column(name="genre")
	private String genre;
	
	@ManyToMany
	@JoinTable(name="User2Books_joinTable",joinColumns={@JoinColumn(name="uid")},//book_id
			inverseJoinColumns={@JoinColumn(name="book_id")})//uid //1 book many authors 
	private Collection<User> users=new HashSet();
	
	@ManyToMany
	@JoinTable(name="Author2Books_joinTable",joinColumns={@JoinColumn(name="uid")},//book_id
			inverseJoinColumns={@JoinColumn(name="book_id")})//uid //1 book many authors
	private Collection<User> authors=new HashSet(); 

	@OneToMany(mappedBy="book",targetEntity=Comments.class,
			cascade=CascadeType.ALL)
	private Collection<Comments> commentsList;

	@Column(name="language")
	private String language;

	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="posting_date")
	private Date postingDate;

	@Column(name="release_date")
	private Date releaseDate;

	@Column(name="age_group_type")
	private String ageGroupType;

	@Column(name="age_group")
	private int ageGroup;
	
	@Column(name="ebook_id",nullable=true)
	private int ebookID;
	
	@Column(name="last_update_date",nullable=true)	
	private Date lastUpdated;

	@Column(name="filename",nullable=true)
	private String filename;
	
	@Column(name="location",nullable=true)
	private String location;
	
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getEbookID() {
		return ebookID;
	}
	public void setEbookID(int ebookID) {
		this.ebookID = ebookID;
	}
	public Date getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	public void setBookid(Long bookid) {
		this.bookid = bookid;
	}	
	 //auto generated num	
	public Long getBookid() {
		Long bid=bookid;
		return bid;
	}	
	public void setBookDesc(String bookDesc) {
		this.bookDesc = bookDesc;
	}
	public String getBookDesc() {
		return bookDesc;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public String getGenre() {
		return genre;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getLanguage() {
		return language;
	}
	public void setPostingDate(Date postingDate) {
		this.postingDate = postingDate;
	}	
	public Date getPostingDate() {
		Date returnPostingDate=postingDate;
		return returnPostingDate;
	}
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	public Date getReleaseDate() {
		Date returnReleaseDate=releaseDate;
		return returnReleaseDate;
	}
	public void setAgeGroupType(String ageGroupType) {
		this.ageGroupType = ageGroupType;
	}
	public String getAgeGroupType() {
		return ageGroupType;
	}
	public void setAgeGroup(int ageGroup) {
		this.ageGroup = ageGroup;
	}
	public int getAgeGroup() {
		int returnAgeGroup=ageGroup;
		return returnAgeGroup;
	}
	
	//1 book by many authors/1 author many books
	public void setAuthors(Collection<User> authors) {
		this.authors = authors;
	}	
	public Collection<User> getAuthors() {
		return authors;
	}
	public void addAuthor(User anAuthor){
		this.authors.add(anAuthor);
	}
	
	public void setBookComments(Collection<Comments> bookComments) {
		this.commentsList = bookComments;
	}
	//1 book many comments/1 comment on 1 book
	public Collection<Comments> getBookComments() {
		return commentsList;
	}
	
	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}
	public String getBookTitle() {
		return bookTitle;
	}
	
	//for readers
	public void setUsers(Collection<User> users) {
		this.users = users;
	}
	
	public Collection<User> getUsers() {
		return this.users;
	}
	public void addUser(User aReader){
		this.users.add(aReader);
	}
	
	@Override
	public String toString(){	
		StringBuilder sb = new StringBuilder();
		sb.append("(id=");
		sb.append(bookid);
		sb.append(",Book Title= ");
		sb.append(bookTitle);
		sb.append(",Author=");
		sb.append(this.getUsers()+")");//getAuthors
		return super.toString();		
	}	
}
