package com.readALoud.client;

import java.util.Date;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;
import org.apache.wicket.markup.html.link.Link;

import com.readALoud.beans.AuthorDetailsBean;
import com.readALoud.beans.LoginBean;
import com.readALoud.client.SuccessPage.RoomForm;
import com.readALoud.entities.Book;
import com.readALoud.services.UserDAO;

public class AuthorProfilePage extends BasePage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3039724555431161535L;
	AuthorDetailsBean adb=new AuthorDetailsBean();
	
	public AuthorProfilePage(PageParameters params) {
	
		System.err.println("#####INSIDE Athor profile PAGE#######");
		
		
		FeedbackPanel fp=new FeedbackPanel("feedback");
		adb=getADB(params);
		
		String fname=adb.getFname();
		String lname=adb.getLname();
		
		
		
		Date prevLoginDate=adb.getLoginDate();		
		System.err.println("Date>>>>>>"+prevLoginDate);
		
		
		List<Book> bookList=(List<Book>) adb.getBooksRead();
		//System.err.println("read books>>>"+bookList.size());		
		
		List<Book> writtenBookList=(List<Book>) adb.getBooksWritten();				
		//System.err.println("written books>>>"+writtenBookList.size());
	
		Integer totalComments=adb.getTotalCommentsCount();
		String totalComm=totalComments.toString();
		
		Integer totalBooks=adb.getTotalBooksRead();
		String allBooks=totalBooks.toString();
		
		Integer totalBooksWritten=adb.getTotalBooksWritten();
		String writtenBooks=totalBooksWritten.toString();
		//System.err.println("Written books APP>>"+writtenBooks);
			
		
		AuthorBookPanel.setListofBooks(bookList);	
		AuthorBookPanel.setAuthoredBooks(writtenBookList);		
		AuthorBookPanel bp=new AuthorBookPanel("authorBookPanel");
		
		//GENRE>>>>NEWWWW
		Form roomForm=new RoomForm("roomForm");
				
		/*add(new BookmarkablePageLink("commentsLink", ExamplePage.class));//add Comments PAGE!!!
*/		
		Link roomsPanelLink=new Link("roomsPanelLink"){
			@Override
			public void onClick() {
				System.err.println("INSDIE ONSCLICK OF APPPPPP");
				setResponsePage(new RoomsPage(adb.getGenreRooms()));			
			}			
		};
		
		/*add(new BookmarkablePageLink("commentsLink", ExamplePage.class));*///add Comments PAGE!!!
		add(fp);
		
		add(new Label("firstName",fname));
		add(new Label("lastName",lname));
		add(new Label("totalComments",totalComm));
		add(new Label("totalBooks",allBooks));
		add(new Label("totalBooksWritten",writtenBooks));
		add(new Label("loginTime",prevLoginDate.toString().trim()));
		
		add(roomsPanelLink);
		add(roomForm);

		add(new Label("firstName1",fname));
		add(new Label("lastName1",lname));
		
		add(bp);
		RecommendationPanel rp = new RecommendationPanel("recommendationPanel");
		add(rp);
		add(new GenrePanel("genrePanel"));
		add(new AuthorPanel("authorPanel"));
	}
	//GENRE>>>NEWWW
	class RoomForm extends Form{

		public RoomForm(String id) {
			super(id);
	
		}
		@Override
		public void onSubmit(){
			System.err.println("INSIDE SUBBBBMITTTT of rromm");
			
			setResponsePage(new GenreRoomPage());
		}
	}
	
	public static AuthorDetailsBean getADB(PageParameters parameters){
		
		System.err.println("#####INSIDE getADB#######");
		StringValue email=parameters.get("email");
		StringValue pwd=parameters.get("password");	
		
		AuthorDetailsBean ab=new AuthorDetailsBean();
		LoginBean lbean=new LoginBean();
		
		String em=email.toOptionalString();
		String pw=pwd.toOptionalString();
		
		lbean.setEmail(em);
		lbean.setPassword(pw);
		
		UserDAO ud=new UserDAO();
		try{
			ab=ud.getAuthorDetails(lbean);
		}catch (Exception e) {
		System.err.println("!!!!!!!!!!!EXCEPTION IN Author profile PAGE!!!!!!!");
			e.printStackTrace();
		}
	//	info("user name="+udb.getFname());
	//	info("user Last name="+udb.getLname());
		return ab;
		
	}
}
