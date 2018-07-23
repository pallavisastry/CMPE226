package com.readALoud.client;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;


import com.readALoud.entities.Book;
import com.readALoud.entities.GenreRoom;

public class RoomsPanel extends Panel {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1934619223463500575L;
	static List<GenreRoom> gList;
	
	RoomsPanel(String id) {
		super(id);
		
		List genreRoomList=getListofRooms();	
	
		add(new ListView("rooms",genreRoomList){
			@Override
			protected void populateItem(ListItem item) {
				GenreRoom g=(GenreRoom)item.getModelObject();
				System.err.println("stage1");
				//link for room
				Link view = new Link("view",item.getModel()){
					@Override
					public void onClick() {
						GenreRoom g=(GenreRoom)getModelObject();
					/*	PageParameters p = new PageParameters();
						p.set("genre", g.getGenreType());*/
						setResponsePage(new ListGenreBooks(g.getGenreType()));						
					}
					
				};	
				System.err.println("stageN");
				view.add(new Label("roomTitle", g.getRoomName()));			
				item.add(view);
			}
		});		
	}	
	public static void setListofRooms(List rooms){
		gList=rooms;
	}
	@SuppressWarnings("static-access")
	public List<GenreRoom> getListofRooms(){
		return this.gList;
	}
	

}
