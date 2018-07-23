package com.readALoud.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name="RATINGS")
public class Ratings implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	Ratings(){

	}
	public Ratings(double rating,User user,Book book,Date currentDate){ //parameterized constructor
		this.setRating(rating);
		currentDate=new Date();
		this.setCreationDate(currentDate);
		this.setUser(user);
		this.setBook(book);
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="RID",updatable=false,nullable=false)
	private Long id;//primary key generated by hibernate
	
	@OneToOne
	@JoinColumn(name="book_id",nullable=false)
	private Book book;
	
	@Column(name="rating",nullable=false)
	private double rating;
	
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="creation_date",nullable=false)
	private Date creationDate;
		
	@OneToOne
	@JoinColumn(name="uid",nullable=false) 
	private User user; //one comment by one user
		

	
	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}
	
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	public Date getCreationDate() {
		Date returnDate=this.creationDate;
		return returnDate;
	}	
	@Override
	public String toString(){	
		StringBuilder sb = new StringBuilder();
		sb.append("(id=");
		sb.append(id);
		sb.append(",comments= ");
		sb.append(rating);
		sb.append(",creation date= ");
		sb.append(creationDate);
		sb.append(",Commentors=");
		sb.append(this.getUser()+")");
		return super.toString();		
	}
	@Override
	public boolean equals(Object obj) {
			
		return super.equals(obj);
	}
	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@SuppressWarnings("unused")
	private void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		Long returnId;
		returnId=this.id;
		return returnId;
	}
	public void setUser(User user) {
		this.user=user;	
	}

	public User getUser() {
		return user;
	}
	public void setBook(Book bookComments) {
		this.book = bookComments;
	}

	public Book getBook() {
		return book;
	}	
}