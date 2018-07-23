package com.readALoud.client;

import java.util.Collection;
import java.util.List;

import org.apache.wicket.Session;

import com.readALoud.entities.GenreRoom;
import com.readALoud.entities.User;

public class RoomsPage extends BasePage 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1407011785221820508L;
	
	public RoomsPage(Collection<GenreRoom> genreType){
	
		add(new GenrePanel("genrePanel"));

		if(genreType.size()>0){			
			List gr=(List) genreType;
			RoomsPanel.setListofRooms(gr);
			RoomsPanel rp=new RoomsPanel("roomsPanel");			
			add(rp);
		}else
		{
			RoomsPanel rp=new RoomsPanel("roomsPanel");
			add(rp);			
		}

	}

}
