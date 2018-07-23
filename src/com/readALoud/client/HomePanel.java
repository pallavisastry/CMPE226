package com.readALoud.client;

import org.apache.wicket.Session;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.readALoud.entities.Author;
import com.readALoud.entities.User;

public class HomePanel extends Panel {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6618982436250428774L;
	User user = ((MySession)Session.get()).getSessionUser();
	public HomePanel(String id) {
		super(id);
		Object o=user;
		PageParameters params=new PageParameters();
		params.add("email", user.getEmail());
		params.add("password", user.getPassword());
		if(o instanceof Author)
			add(new BookmarkablePageLink("actionLink",AuthorProfilePage.class,params));
		else
			add(new BookmarkablePageLink("actionLink",SuccessPage.class,params));
	}

}
