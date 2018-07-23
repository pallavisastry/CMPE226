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
@Table(name="READER")
public class Reader extends User implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	
	@ManyToMany(fetch=FetchType.LAZY,mappedBy="users")
	private Collection<Book> books=new HashSet<Book>();
	
	Reader(){
		super();
	}		
	public Reader(String fname, String lname, String email,
			String pwd, Date date) {
		super(fname,lname,email,pwd,date);
	}
	//A reader(could be an author too sets the book read using these methods)
	public void setBooks(Collection<Book> books) {
		this.books = books;
	}	
	public Collection<Book> getBooks() {
		return books;
	}
	//adding only one book
	public void addBook(Book newBook){
		this.books.add(newBook);
	}
	
	
	
}
