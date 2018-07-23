package com.readALoud.beans;

import java.io.Serializable;

public class GenreRoomBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3996896079000075989L;
	private String roomName;
	private String genreDesc;
	private String genreType;
	
	public void setGenreDesc(String genreDesc) {
		this.genreDesc = genreDesc;
	}
	public String getGenreDesc() {
		return genreDesc;
	}
	public void setGenreType(String genreType) {
		this.genreType = genreType;
	}
	public String getGenreType() {
		return genreType;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
}
