package com.readALoud.client;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.readALoud.entities.User;
import com.readALoud.services.UserDAO;

public class AuthorPanel extends Panel{
	List<User> authorLst;


	@SuppressWarnings("unchecked")
	public AuthorPanel(String id) {
		super(id);
		// TODO Auto-generated constructor stub
//		
//		LoadableDetachableModel genreModel = new LoadableDetachableModel() {
//			protected Object load()
//			{
//				return getGenreList();
//			}
//		};
		//setGenreLst(getGenreList());
		//genreLst=getGenreList();
		//this.setGenreLst(getGenreList());
		
		authorLst = getAuthorList();
		
		add(new ListView("authors",authorLst){
			@Override
			protected void populateItem(ListItem item) {
				@SuppressWarnings("unchecked")
				//IModel s = item.getModel();
				Link view = new Link("view",item.getModel()){
					@Override
					public void onClick() {
						User u =(User)getModelObject();
//						PageParameters p = new PageParameters();
//						p.set("authors", "Art");
						setResponsePage(new AuthorRoomPage(u));						
					}
					
				};	
				System.err.println("stageN");
				//view.add(new Label("roomTitle", new PropertyModel(item.getModel(),"genre")));
//				List<String> st = (List<String>)getModelObject();
				view.add(new Label("authorName", new PropertyModel(item.getModel(),"fname")));
				
				item.add(view);
				//item.add(new Label("roomTitle",g.getGenreType()));
			//	item.add(new Label("roomDesc",g.getGenreDesc()));
			}
		});	
	}

	protected List<User> getAuthorList() {
		// TODO Auto-generated method stub
		UserDAO ud = new UserDAO();
		return ud.getAuthorList();
	}
	
	public List<User> getAuthorLst() {
		return authorLst;
	}

	public void setGenreLst(List<User> authorLst) {
		this.authorLst = authorLst;
	}
}
