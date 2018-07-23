package com.readALoud.client;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;

public class ExamplePage extends BasePage{

	public ExamplePage(PageParameters params){
		StringValue searchString=params.get("searchString");
		
		System.err.println("ExamplePAge with string="+searchString);
	}
}
