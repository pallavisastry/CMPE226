package com.readALoud.client;

import java.util.Date;
import java.util.List;

import org.apache.wicket.IClusterable;
import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.rating.RatingPanel;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.value.ValueMap;

import com.readALoud.beans.BookListBean;
import com.readALoud.beans.BookPageBean;
import com.readALoud.beans.UserDetailsBean;
import com.readALoud.client.SearchPanel.SearchForm;
import com.readALoud.entities.Book;
import com.readALoud.entities.Comments;
import com.readALoud.entities.User;
import com.readALoud.services.UserDAO;

public class BookPage extends BasePage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7572373749132714487L;
	BookListBean blb=new BookListBean();
	BookPageBean bpb=new BookPageBean();
	private Boolean hasVoted = Boolean.FALSE;
	private static RatingModel rating1 = new RatingModel();

	private static long bookId;
	@SuppressWarnings({ "unchecked", "serial" })
	public BookPage(final Integer ebookID)
	{
		super();
		System.out.println("Book Page book returned: "+ebookID);
		blb = getBook(ebookID);
		bpb = getBookBean(ebookID);
		add(new ReadForm("readForm"));
		System.err.println("#####################BookPAGESession user Id from MySession is " + ((MySession)Session.get()).getSessionUser().getUid());
		
		for (Comments c: bpb.getCommentsList())
			System.out.println("comments received:" + c.getCommentsDesc());
		
		List<Comments> commentList = (List<Comments>) bpb.getCommentsList();
		add(new ListView("comments",commentList){
			@Override
			protected void populateItem(ListItem item) {
				Comments c=(Comments)item.getModelObject();
				item.add(new Label("comment",c.getCommentsDesc()));
			}
		});
		
		String authorName="";
		for(User auth: bpb.getAuthor())
		{
			System.out.println("Disp Author"+auth.getFname() + " " + auth.getLname());
			authorName = authorName + auth.getFname() + " " + auth.getLname() + " , ";
		}
				
		
		Integer id = bpb.getEbookID();
		String eid = id.toString();
		add(new Label("ebookID", eid));
		add(new Label("title", bpb.getTitle()));
		add(new Label("title_book", bpb.getTitle()));
		add(new Label("author", authorName));
		add(new Label("language", bpb.getLanguage()));
		add(new Label("genre", bpb.getGenre()));		
		add(new Label("postingdate", (bpb.getPostingDate()==null)? "Information Not Available" : bpb.getPostingDate().toString()));
		add(new Label("releasedate", (bpb.getReleaseDate()==null)? "Information Not Available" : bpb.getReleaseDate().toString()));
		add(new Label("lastupdated", (bpb.getLastUpdated()==null)? "Information Not Available" : bpb.getLastUpdated().toString()));
		
		add(new CommentForm("commentForm"));

      	bookId = bpb.getBookid();
		System.err.println("Adding Rating Pannel");
		add(new RatingPanel("rating1", new PropertyModel<Double>(rating1, "rating"), 5,
            new PropertyModel<Integer>(rating1,"nrOfVotes"), true)
        {
			
            @Override
            public boolean onIsStarActive(int star)
            {
                return BookPage.rating1.isActive(star);
            }

            @Override
            public void onRated(int rating, AjaxRequestTarget target)
            {
            	BookPage.rating1.addRating(rating, bookId);
            }

        });


		
	}
	
	public BookListBean getBook(int ebookID)
	{
		UserDAO ud = new UserDAO();
		return ud.findBook(ebookID);
	}
	
	public BookPageBean getBookBean(int ebookID)
	{
		UserDAO ud = new UserDAO();
		return ud.findBookBean(ebookID);
	}
	
	public class ReadForm extends Form
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = -7120826920158205111L;

		public ReadForm(String id) {
			super(id);
			System.err.println("########readform: "+id);
			setMarkupId("readForm");
		}
		
		public void onSubmit()
		{
			PageParameters params = new PageParameters();
			System.err.println("########value:"+params.get("readForm"));
			UserDAO ud = new UserDAO();
			User user = ((MySession)Session.get()).getSessionUser();
			ud.saveReadBook(user, bpb.getBookid());
			
		}
		
	}
	
	public final class CommentForm extends Form<ValueMap> {
        public CommentForm(final String id) {
            // Construct form with no validation listener
            super(id, new CompoundPropertyModel<ValueMap>(new ValueMap()));
            setMarkupId("commentForm");

            // Add text entry widget
            add(new TextArea<String>("text").setType(String.class));

        }

        /*
         * Show the resulting valid edit
         */
        @Override
        public final void onSubmit() {        	
            ValueMap values = getModelObject();
            System.err.println("Value is the textbox is " + (String)values.get("text"));
            
            UserDAO rDao=new UserDAO();
            
            Date currentDate=new Date();
           
            
            User user = ((MySession)Session.get()).getSessionUser();            
            Book book = rDao.getBook(bpb.getBookid());
            Date curDate = new Date();
                  
            String result = rDao.insertComment((String)values.get("text"),user, book, curDate);
	    	
	    	String dateStr = String.valueOf(curDate.getTime());
            values.put("text", "");
        }
    }

    /**
     * Clears the comments.
     */
	
	public static class RatingModel implements IClusterable
    {
        private int nrOfVotes = 0;
        private long sumOfRatings = 0;
        private double rating = 0;

        /**
         * Returns whether the star should be rendered active.
         * 
         * @param star
         *            the number of the star
         * @return true when the star is active
         */
        public boolean isActive(int star)
        {
            return star < ((int)(getRating() + 0.5));
        }

        /**
         * Gets the number of cast votes.
         * 
         * @return the number of cast votes.
         */
        public Integer getNrOfVotes()
        {
            return nrOfVotes;
        }

               
        /**
         * Adds the vote from the user to the total of votes, and calculates the rating.
         * 
         * @param nrOfStars
         *            the number of stars the user has cast
         */
        public void addRating(int nrOfStars, long bookId)
        {
            nrOfVotes++;
            sumOfRatings += nrOfStars;
            rating = sumOfRatings / (1.0 * nrOfVotes);
            System.err.println("No. of Votes " + nrOfVotes);
            System.err.println("Sum of ratings is " + sumOfRatings);
            System.err.println("Rating is " + rating);
            UserDAO rDao = new UserDAO();
            User user = ((MySession)Session.get()).getSessionUser();
            Book book = rDao.getBook(bookId);
            Date curDate = new Date();
            String result = rDao.insertRatings(rating, user, book, curDate);
            System.err.println("Result of inserting ratings is " + result);
        }

        /**
         * Gets the rating.
         * 
         * @return the rating
         */
        public Double getRating()
        {
        	UserDAO rDao = new UserDAO();
        	User user = ((MySession)Session.get()).getSessionUser();
        	System.err.println("Book ID is : " + bookId);
            Book book = rDao.getBook(bookId);
        	rating = rDao.getRating(user, book);
            return rating;
        }
        
       
        /**
         * Returns the sum of the ratings.
         * 
         * @return the sum of the ratings.
         */
        public long getSumOfRatings()
        {
            return sumOfRatings;
        }
    }
	  public Boolean getHasVoted()
	    {
	        return hasVoted;
	    }

	    /**
	     * @see org.apache.wicket.Component#isVersioned()
	     */
	    @Override
	    public boolean isVersioned()
	    {
	        return false;
	    }
}
