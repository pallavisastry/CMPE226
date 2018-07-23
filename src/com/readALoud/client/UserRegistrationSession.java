package com.readALoud.client;

import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;

public class UserRegistrationSession extends WebSession
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserRegistrationSession(Request request) {
		super(request);
	}

}
