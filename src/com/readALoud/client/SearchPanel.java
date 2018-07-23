package com.readALoud.client;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class SearchPanel extends Panel {

	public SearchPanel(String id) {
		super(id);
		add(new SearchForm("searchForm"));
		
	}
	
	public class SearchForm extends Form
	{
		private String searchString;
		public SearchForm(String id) {
			super(id);
			add(new RequiredTextField<String>("searchString", new PropertyModel<String>(this, "searchString")));
			setMarkupId("search-form");
		}
		
		public void onSubmit()
		{
			PageParameters params = new PageParameters();
			params.add("searchString", getSearchString());
			System.out.println("params:"+params.get("searchString"));
			//set the response page
			setResponsePage(ListUserBooks.class, params);
		}
		
		public String getSearchString()
		{
			return searchString;
		}

		public void setSearchString(String searchString) {
			this.searchString = searchString;
		}
		
	}

	
	
}
