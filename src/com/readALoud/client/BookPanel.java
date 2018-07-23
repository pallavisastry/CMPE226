package com.readALoud.client;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;

import com.readALoud.entities.Book;

public class BookPanel extends Panel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3438378199758724210L;
	static List<Book> bList;
 	
	@SuppressWarnings({ "unchecked", "rawtypes", "serial" })
	public BookPanel(String id)
	{	
		super(id);
		List bookList=getListofBooks();
		System.err.println("INSIDE BP>>>booklist>>>"+bookList);
	
		
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
	public static void setListofBooks(List books){
		bList=books;
	}
	@SuppressWarnings("static-access")
	public List<Book> getListofBooks(){
		return this.bList;
	}
}
