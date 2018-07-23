package com.readALoud.client;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Session;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.readALoud.beans.BookListBean;
import com.readALoud.entities.Book;
import com.readALoud.index.SearchFiles;
import com.readALoud.services.UserDAO;

public class ListGenreBooks extends BasePage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7480582445697025000L;
	BookListBean blb=new BookListBean();
	
	public ListGenreBooks(String genre){
		super();
		
		final String genreVal = genre;

		LoadableDetachableModel bookModel = new LoadableDetachableModel() {
			protected Object load()
			{
				return getBookList(genreVal);
			}
		};
		
		@SuppressWarnings("unchecked")
		ListView books = new ListView("books", bookModel){
			protected void populateItem(ListItem item){
				Link view = new Link("view",item.getModel()){
					@Override
					public void onClick() {
						Book b = (Book) getModelObject();
						setResponsePage(new BookPage(b.getEbookID()));						
					}					
				};						
				view.add(new Label("title", new PropertyModel(item.getModel(),"bookTitle")));
				item.add(view);				
				item.add(new Label("ebookID", new PropertyModel(item.getModel(), "ebookID")));				
			}
		};
		
		add(books);
	}
	
	public List<Book> getBookList(String genreVal)
	{
		UserDAO ud = new UserDAO();
		List<Book> booksList=ud.getBookListByGenre(genreVal);
		return booksList;
	}
	

}
