package com.readALoud.client;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.readALoud.entities.Book;
import com.readALoud.entities.GenreRoom;
import com.readALoud.services.UserDAO;

public class GenrePanel extends Panel {

	List<Book> genreLst;


	@SuppressWarnings("unchecked")
	public GenrePanel(String id) {
		super(id);
		
		genreLst = getGenreList();
		
		add(new ListView("genre",genreLst){
			@Override
			protected void populateItem(ListItem item) {
				@SuppressWarnings("unchecked")
				//IModel s = item.getModel();
				Link view = new Link("view",item.getModel()){
					@Override
					public void onClick() {
						Book b = (Book) getModelObject();
						//GenreRoom g=(GenreRoom)getModelObject();
						//PageParameters p = new PageParameters();
						//p.set("genre", "Art");
						setResponsePage(new ListGenreBooks(b.getGenre()));						
					}
					
				};	
				
				view.add(new Label("genreName", new PropertyModel(item.getModel(), "genre")));
				
				item.add(view);
			}
		});	
	}

	protected List<Book> getGenreList() {
		// TODO Auto-generated method stub
		UserDAO ud = new UserDAO();
		List<String> genList = ud.getGenreList();
		List<Book> genBookList = ud.getBooksByGenreList(genList);
		return genBookList;
	}
	
	public List<Book> getGenreLst() {
		return genreLst;
	}

	public void setGenreLst(List<Book> genreLst) {
		this.genreLst = genreLst;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -4909442165954875598L;

	
	
}
