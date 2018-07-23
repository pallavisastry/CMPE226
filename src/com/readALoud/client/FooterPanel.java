package com.readALoud.client;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;

import com.readALoud.client.HeaderPanel.HeaderForm;

public class FooterPanel extends Panel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6377431084588423826L;

	public FooterPanel(String id) {
		super(id);
		add(new FooterForm("footerpanel"));
	}

	public class FooterForm extends Form
	{

		public FooterForm(String id) {
			super(id);
			add(new Label("footer", "Copyright 2012 Quintessential Koders"));
		}
		
	}
}
