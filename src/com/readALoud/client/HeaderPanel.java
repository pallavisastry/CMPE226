package com.readALoud.client;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;


public class HeaderPanel extends Panel {

	public HeaderPanel(String id) {
		super(id);
		add(new HeaderForm("headerpanel"));
		
	}

	public class HeaderForm extends Form
	{

		public HeaderForm(String id) {
			super(id);
			add(new Label("header", "Read-a-Loud Portal"));
			add(new SearchPanel("searchPanel"));
			add(new LogoutPage("logoutPanel"));
			add(new HomePanel("homePanel"));

		}
		
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2903085637681411965L;

}
