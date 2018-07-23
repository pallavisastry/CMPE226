package com.readALoud.client;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;

import com.readALoud.entities.Book;

public class AuthorBookPanel extends Panel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5289563347222345048L;
	static List<Book> bList;
	static List<Book> authoredBooks;
 	
	@SuppressWarnings({ "unchecked", "rawtypes", "serial" })
	public AuthorBookPanel(String id)
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
				view.add(new Label("bookTitle", new PropertyModel(item.getModel(),"bookTitle")));
				item.add(view);
				
				Book b=(Book)item.getModelObject();
			}
		});
		
		List authoredBookList=getAuthoredBooks();
		
		
		add(new ListView("booksWritten",authoredBookList){
			@Override
			protected void populateItem(ListItem item) {
				
				Link view = new Link("view",item.getModel()){
					@Override
					public void onClick() {
						Book b = (Book) getModelObject();
						setResponsePage(new BookPage(b.getEbookID()));						
					}
					
				};
				view.add(new Label("bookTitle", new PropertyModel(item.getModel(),"bookTitle")));
				item.add(view);				
				Book b=(Book)item.getModelObject();
			}
		});
		
	}	
	public static void setListofBooks(List books){
		System.err.println("boksize inside set(..)>>"+books.size());
		bList=books;
	}
	@SuppressWarnings("static-access")
	public List<Book> getListofBooks(){
		return this.bList;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void setAuthoredBooks(List authBooks){
		System.err.println("boksize inside set(..)>>"+authBooks.size());
		
		authoredBooks=authBooks;
	}
	@SuppressWarnings("static-access")
	public List<Book> getAuthoredBooks(){
		return this.authoredBooks;
	}
}
