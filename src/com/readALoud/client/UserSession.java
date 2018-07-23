package com.readALoud.client;

import org.apache.wicket.Application;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.request.cycle.RequestCycle;

public class UserSession extends AbstractReadOnlyModel<String>{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7721225619323306460L;
	@Override
	public String getObject()
	{
		final String msg;
		String sessionId = Application.get()
			.getSessionStore()
			.getSessionId(RequestCycle.get().getRequest(), false);
		if (sessionId == null)
		{
			msg = "no concrete session is created yet (only a volatile one)";
		}
		else
		{
			msg = "a session exists for this client, with session id " + sessionId;
		}
		return msg;
	}
	
}
