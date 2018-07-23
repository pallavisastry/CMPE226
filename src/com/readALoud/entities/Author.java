package com.readALoud.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="AUTHOR")
public class Author extends Reader implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@ManyToMany(fetch=FetchType.LAZY,mappedBy="authors")
	private Collection<Book> myBooks=new HashSet<Book>();
	
	Author(){
		super();
	}	

	public Author(String fname, String lname, String email,
			String pwd, Date date) {
		super(fname,lname,email,pwd,date);
	}
	//An author sets books he writes using the methods below	
	public void setMyBooks(Collection<Book> books) {
		this.myBooks = books;
	}	
	public Collection<Book> getMyBooks() {
		return myBooks;
	}
	//Scaffolding code:adding only one book
	public void addMyBook(Book newBook){
		this.myBooks.add(newBook);
	}	
}
