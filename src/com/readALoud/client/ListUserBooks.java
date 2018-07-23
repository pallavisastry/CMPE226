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

public class ListUserBooks extends BasePage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7480582445697025000L;
	BookListBean blb=new BookListBean();
	
	public ListUserBooks(PageParameters params){
		super();
		final String searchStr = params.get("searchString").toString();
		LoadableDetachableModel bookModel = new LoadableDetachableModel() {
			protected Object load()
			{
				return getBookList(searchStr);
			}
		};
		
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
	
	public List<Book> getBookList(String kw)
	{
		UserDAO ud = new UserDAO();
		List<Integer> foundBooks = new ArrayList<Integer>();
		SearchFiles s = new SearchFiles();
		try {
			foundBooks = s.searchWord(kw);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		List<Book> booksList=ud.getBookListByID(foundBooks);	
		return booksList;
	}

}
