package com.readALoud.client;

import org.apache.wicket.Session;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;

public class LogoutPage extends Panel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8829683779061296096L;

	@SuppressWarnings({ "unchecked", "rawtypes", "serial" })
	public LogoutPage(String id){
		super(id);
		
		add(new Link<Void>("actionLink") {
            public void onClick() {
            		((MySession)Session.get()).invalidate();
                    setResponsePage(LoginPage.class);

            }
		});
		
	}
}
