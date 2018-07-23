package com.readALoud.client;

import java.util.List;

import org.apache.wicket.Session;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;

import com.readALoud.entities.Book;
import com.readALoud.services.UserDAO;

public class RecommendationPanel extends Panel {

	public RecommendationPanel(String id) {
		super(id);
		
		List<Book> bookList=getRecommendedBooks();
		
		add(new ListView("books",bookList){
			@Override
			protected void populateItem(ListItem item) {
				
				Link view = new Link("view",item.getModel()){
					@Override
					public void onClick() {
						Book b = (Book) getModelObject();
						setResponsePage(new BookPage(b.getEbookID()));						
					}
					
				};
				
				Book b=(Book)item.getModelObject();
				view.add(new Label("bookTitle", new PropertyModel(item.getModel(),"bookTitle")));
				item.add(view);
			}
		});
		
	}

	private List<Book> getRecommendedBooks() {

		UserDAO ud = new UserDAO();
		return ud.getRecommendedBooks(((MySession)Session.get()).getSessionUser());
	}



}
