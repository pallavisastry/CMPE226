package com.readALoud.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="GenreRoom")
public class GenreRoom implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5365226857176470263L;

	GenreRoom(){
		super();
	}
	public GenreRoom(User u,String name, String desc, String type){
		this.setGenreType(type);
		this.setGenreDesc(desc);
		this.setRoomName(name);
		this.setUser(u);
	}
	
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="GID",updatable=false,nullable=false)
	private Long id;	
	
	
	@Column(name="roomName", unique=true)
	private String roomName;
	
	@Column(name="genreDesc")
	private String genreDesc;
	
	@Column(name="genreType")
	private String genreType;
	
	@ManyToOne
	@JoinColumn(name="uid",nullable=false) 
	private User user; //one comment by one user

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setGenreType(String genreType) {
		this.genreType = genreType;
	}

	public String getGenreType() {
		return genreType;
	}
	public void setGenreDesc(String genreDesc) {
		this.genreDesc = genreDesc;
	}
	public String getGenreDesc() {
		return genreDesc;
	}
	
}
