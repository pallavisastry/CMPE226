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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="USER")
public class User implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	User(){
		super();
	}
	public User(String userFirstName,String userLastName,String email,String pwd,Date dob){
		this.setFname(userFirstName);
		this.setLname(userLastName);
		this.setEmail(email);
		this.setPassword(pwd);
		this.setDob(dob);
	}
	
	@Id
	@GeneratedValue
	private Long uid;
	
	@Column(name="birthDate")
	protected Date dob;
	
	@Column(name="first_name",nullable=false)
	protected String fname;
	
	@Column(name="last_name",nullable=false)
	protected String lname;
	
	@Column(name="Email",nullable=false)
	protected String email;
	
	@Column(name="password",nullable=false)
	protected String password;
	
	@Column(name="loginTime")
	protected Date loggedTime;
	
	@Column(name="CID")	
	@OneToMany(mappedBy="user",targetEntity=Comments.class,cascade=CascadeType.ALL)
	protected Collection<Comments> comments;	
	
	//GENRE-NEWWW
	@Column(name="GID")							
	@OneToMany(mappedBy="user",targetEntity=GenreRoom.class,cascade=CascadeType.ALL)
	protected Collection<GenreRoom> rooms;
	
	public void setFname(String name){
		this.fname=name;
	}	
	public String getFname(){
		return this.fname;
	}
	public void setEmail(String email) {
		this.email = email;
	}	
	public String getEmail() {
		return email;
	}
	public void setPassword(String password) {
		this.password = password;
	}	
	public String getPassword() {
		return password;
	}
	public void setComments(Collection<Comments> uComments) {
		comments=uComments;		
	}
	public Collection<Comments> getComments() {
		return comments;
	}
	//Scaffolding code:current user adds a comment:connecting both ends since its bidirectional
	public void addComment(Comments comm){
		comments=new HashSet<Comments>();
		comments.add(comm);
		comm.setUser(this);
	}
	@SuppressWarnings("unused")
	private void setUid(Long uid) {
		this.uid = uid;
	}
	public Long getUid() {
		Long id=this.uid;
		return id;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String getLname() {
		return lname;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public Date getDob() {
		return dob;
	}
	public void setLoggedTime(Date curDate){
		this.loggedTime=curDate;
	}
	public Date getLoggedTime(){
		return this.loggedTime;
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(id=");
		sb.append(uid);
		sb.append(") ");
		sb.append(fname);
		sb.append(" ");
		sb.append(lname);
			
		return sb.toString();
	}

	public Collection<GenreRoom> getRooms(){
		return this.rooms;
	}
	public void setRooms(Collection<GenreRoom> gr){
		this.rooms=gr;
	}
	public void addRoom(GenreRoom groom){
		
		this.rooms.add(groom);
	}
}
