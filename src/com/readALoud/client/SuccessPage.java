package com.readALoud.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.wicket.Session;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;

import com.readALoud.beans.LoginBean;
import com.readALoud.beans.UserDetailsBean;
import com.readALoud.entities.Book;
import com.readALoud.entities.GenreRoom;
import com.readALoud.entities.User;
import com.readALoud.services.UserDAO;

public class SuccessPage extends BasePage {	
	/**
	 * 
	 */
	private static final long serialVersionUID = 622461867530453758L;
	UserDetailsBean udb=new UserDetailsBean();
	User user = ((MySession)Session.get()).getSessionUser();

	PageParameters parameters=new PageParameters();
	
	ArrayList groom=new ArrayList();
	
	@SuppressWarnings({ "rawtypes", "serial" })
	public SuccessPage(PageParameters params){
		
		udb=getUDB(params);
		
		String fname=udb.getFname();
		String lname=udb.getLname(); 
		Integer totalComments=udb.getTotalCommentsCount();
		String totalComm=totalComments.toString();
		
		
		Date prevLoginDate=udb.getLoginDate();		
		
		List<Book> readBookList=(List<Book>) udb.getBooksRead();
		
		FeedbackPanel fp=new FeedbackPanel("feedback");
		BookPanel.setListofBooks(readBookList);
		
		BookPanel bp=new BookPanel("bookPanel");

		Form roomForm=new RoomForm("roomForm");
		Link roomsPanelLink = null;
		
		roomsPanelLink=new Link("roomsPanelLink")
		{			
			@Override
				public void onClick() {		
				
				System.err.println("INSDIE ONSCLICK OF SPPPPP");
				if(udb.getGenreRooms()==null)
				{
					info("Sorry no rooms>>add please");
				}
				else{
					setResponsePage(new RoomsPage(udb.getGenreRooms()));
				}	
							
			}
				
		};
		add(roomsPanelLink);
		
		add(fp);
		add(new Label("firstName",fname));
		add(new Label("lastName",lname));
		add(new Label("totalComments",totalComm));
		add(new Label("loginTime",prevLoginDate.toString().trim()));
		
		add(roomForm);
		add(bp);
		
		//changing for genre panel
		add(new GenrePanel("genrePanel"));
		add(new AuthorPanel("authorPanel"));
		RecommendationPanel rp = new RecommendationPanel("recommendationPanel");
		add(rp);
	}
	
	class RoomForm extends Form{

		public RoomForm(String id) {
			super(id);
		}
		@Override
		public void onSubmit(){
			setResponsePage(new GenreRoomPage());
		}
	}
	
	public static UserDetailsBean getUDB(PageParameters parameters){
	
		StringValue email=parameters.get("email");
		StringValue pwd=parameters.get("password");
		
		UserDetailsBean db=new UserDetailsBean();
		LoginBean lbean=new LoginBean();

		String em=email.toOptionalString();
		String pw=pwd.toOptionalString();
		
		lbean.setEmail(em);
		lbean.setPassword(pw);
	
		UserDAO ud=new UserDAO();
		
		try {

			db=ud.getUserDetails(lbean, db);
			
		} catch (Exception e) {
		System.err.println("!!!!!!!!!!!EXCEPTION IN SUCCESSPAGE!!!!!!!");
			e.printStackTrace();
		}
		return db;
		
	}
}
