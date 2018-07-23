package com.readALoud.entities;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Genre")
public class Genre {
	Genre(){
		super();
	}
	
	public Genre(String type){
		this.setGenreType(type);
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="GID",updatable=false,nullable=false)
	private Long id;
	
	@Column(name="genreType")
	private String genreType;
	
	@ManyToMany
	@JoinTable(name="Room2Genre_joinTable",joinColumns={@JoinColumn(name="roomID")},//book_id
			inverseJoinColumns={@JoinColumn(name="GID")})//uid //1 book many authors
	private Collection<User> users = new HashSet();

	
	@OneToMany(mappedBy = "GID")
	private Set<Book> bookList = new HashSet<Book>();
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Collection<User> getUsers() {
		return users;
	}

	public void setUsers(Collection<User> users) {
		this.users = users;
	}

	public Set<Book> getBookList() {
		return bookList;
	}

	public void setBookList(Set<Book> bookList) {
		this.bookList = bookList;
	}

	public String getGenreType() {
		return genreType;
	}

	public void setGenreType(String genreType) {
		this.genreType = genreType;
	} 
}
