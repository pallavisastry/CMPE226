package com.readALoud.client;

import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;

import com.readALoud.entities.User;

public final class MySession extends WebSession {

	private String email;
	private User sessionUser;

	public User getSessionUser() {
		return sessionUser;
	}



	public void setSessionUser(User sessionUser) {
		this.sessionUser = sessionUser;
	}



	public MySession(Request request) {
		super(request);
	}

	

	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}

	public static MySession get() {
		return (MySession)Session.get();
	}
}
