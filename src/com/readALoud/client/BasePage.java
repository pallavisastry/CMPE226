package com.readALoud.client;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.ResourceLink;
import org.apache.wicket.request.resource.CssResourceReference;

import com.readALoud.beans.UserDetailsBean;

public class BasePage extends WebPage{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3257238703646673457L;

	public BasePage() {
		
		System.err.println("################################################INSIDE BASEPAGE#######");
		add(new HeaderPanel("headerpanel"));
		add(new FooterPanel("footerpanel"));

		CssResourceReference rrefCSS = new CssResourceReference( 
				   BasePage.class, "styles.css"); 
				  ResourceLink<CssResourceReference> rlnkCSS = 
				   new ResourceLink<CssResourceReference>("stylesheet", rrefCSS); 
				  add(rlnkCSS); 
	}

}
