package com.readALoud.client;

import java.util.Date;
import java.util.List;

import org.apache.wicket.Session;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.util.value.ValueMap;

import com.readALoud.beans.AuthorDetailsBean;
import com.readALoud.client.BookPage.CommentForm;
import com.readALoud.entities.Book;
import com.readALoud.entities.User;
import com.readALoud.services.UserDAO;

public class AuthorRoomPage extends BasePage{
	private static final long serialVersionUID = 7572373749132714487L;
	
	public AuthorRoomPage(User u){
		
		System.err.println("#####INSIDE Athor Room PAGE#######");
		
		UserDAO rDAO = new UserDAO();
		
		try {
			
			FeedbackPanel fp=new FeedbackPanel("feedback");
			AuthorDetailsBean adb = rDAO.getAuthorDetails(u);
			
			String fname=adb.getFname();
			String lname=adb.getLname();
			
			List<Book> writtenBookList=(List<Book>) adb.getBooksWritten();	
			
			Integer totalBooksWritten=adb.getTotalBooksWritten();
			String writtenBooks=totalBooksWritten.toString();
			
			BooksWrittenPanel.setAuthoredBooks(writtenBookList);	
			
			BooksWrittenPanel bp=new BooksWrittenPanel("booksWrittenPanel");
			
			add(fp);
			add(new Label("firstName",fname));
			add(new Label("lastName",lname));
			//add(new CommentForm("commentForm"));
			
			add(new Label("totalBooksWritten",writtenBooks));
			
			add(bp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/*public final class CommentForm extends Form<ValueMap> {
        public CommentForm(final String id) {
            // Construct form with no validation listener
            super(id, new CompoundPropertyModel<ValueMap>(new ValueMap()));

            // this is just to make the unit test happy
            setMarkupId("commentForm");

            // Add text entry widget
            add(new TextArea<String>("text").setType(String.class));

            // Add simple automated spam prevention measure.
            //add(new TextField<String>("comment").setType(String.class));
        }*/

        /*
         * Show the resulting valid edit
         */
        //@Override
     //   public final void onSubmit() {
//        	System.err.println("onSubmit");
//            ValueMap values = getModelObject();
//            System.err.println("onSubmit1");
//            System.err.println("Value is the textbox is " + (String)values.get("text"));
//
//            // check if the honey pot is filled
////            if (StringUtils.isNotBlank((String)values.get("comment"))) {
////                error("Caught a spammer!!!");
////                return;
////            }
//            // Construct a copy of the edited comment
//            UserDAO rDao=new UserDAO();
//            System.out.println("stage1");
//            Date currentDate=new Date();
//            System.out.println("stage2");
//           
//            //System.out.println("User ID is " + udb.getUserId());
//            
//            User user = ((MySession)Session.get()).getSessionUser();
//            //System.out.println("User name is " + user.getFname());
//            System.out.println("stage3");
//           // System.out.println("User ID String is " + udb.getUser().getUid().toString());
//          //  System.err.println("Book id from bean is " + bpb.getBookid());
//            //Book book = rDao.getBook(bpb.getBookid());
//           // System.err.println("Book id from bean is " + bpb.getBookid());
//            System.err.println("Book id from book object is " + book.getBookid());
//            System.out.println("stage4");
//            Date curDate = new Date();
//                  
//            String result = rDao.insertComment((String)values.get("text"),user, book, curDate);
//            System.out.println("stage5");
//	    	
//	    	String dateStr = String.valueOf(curDate.getTime());
//           
////            System.err.println("Date is : " + cBean.getCreationDate());
////            System.err.println("Description is : " + cBean.getCommentText());
//	    	//commentList.add(0, "text");
//	    	//commentList.add(index, element)
//            // Clear out the text component
//            values.put("text", "");
    //    }
}
