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
@Table(name="Room")
public class Room implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5365226857176470263L;

	Room(){
		super();
	}
	public Room(User u, String name, String desc){
		this.setUser(u);
		this.setRoomName(name);
		this.setRoomDesc(desc);
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="GID",updatable=false,nullable=false)
	private Long id;	
	
	
	@Column(name="genreType")
	private String roomName;
	
	@Column(name="genreDesc")
	private String roomDesc;
	
	@ManyToOne
	@JoinColumn(name="uid",nullable=false) 
	private User user; 

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
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public String getRoomDesc() {
		return roomDesc;
	}
	public void setRoomDesc(String roomDesc) {
		this.roomDesc = roomDesc;
	}
	
}
