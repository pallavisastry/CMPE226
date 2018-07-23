/**This is a DAO class that corresponds with database and classes that use hibernate
 * This class is a part of Open Session In View pattern
 * This forms a data access object layer that detaches DB logic from presentation 
 * layer
 * **/
package com.readALoud.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.readALoud.beans.AuthorDetailsBean;
import com.readALoud.beans.BookListBean;
import com.readALoud.beans.BookPageBean;
import com.readALoud.beans.GenreRoomBean;
import com.readALoud.beans.LoginBean;
import com.readALoud.beans.UserDetailsBean;
import com.readALoud.beans.UserRegistrationBean;
import com.readALoud.client.MySession;

import com.readALoud.entities.Author;
import com.readALoud.entities.Book;

import com.readALoud.entities.Book;

import com.readALoud.entities.Comments;
import com.readALoud.entities.Genre;
import com.readALoud.entities.GenreRoom;
import com.readALoud.entities.Ratings;
import com.readALoud.entities.Reader;
import com.readALoud.entities.User;
import com.readALoud.utils.Constants;
import com.readALoud.utils.HibernateUtil;


public class UserDAO extends GenericDAO<User, Long>
{
	private Session session;
	private Query CheckUser;
	private Query query;
	static String resValidation;

	private static final ArrayList<String> genreList = new ArrayList<String>(Arrays.asList("Art","Biography","Business","Chick-lit","Children's","Christian","Classics","Comics","Contemporary","Cookbooks","Crime","Ebooks","Fantasy","Fiction","Gay and Lesbian","Graphic novels","Historical fiction","History","Horror","Humor and Comedy","Manga","Memoir","Music","Mystery","Non-fiction","Paranormal","Philosophy","Poetry","Psychology","Religion","Romance","Science","Science fiction","Self help","Suspense","Spirituality","Sports","Thriller","Travel","Young-adult"));
	
	public String register(UserRegistrationBean urb)throws Exception{

		System.err.println("####INSIDE register####");
		int CheckCount;

		resValidation=urb.validate();
		if(!resValidation.equals(Constants.SUCCESS)){ //this validation is on the bean
			return resValidation;
		}		
		//getting the current session thread
		try{
			session=HibernateUtil.getSessionFactory().getCurrentSession();
			
			CheckUser= session.createQuery("select count(*) from User u where u.email=:email");
			CheckUser.setParameter("email", urb.getEmail());
			CheckCount= ((Long)CheckUser.uniqueResult()).intValue();
			if(CheckCount > 0)
			{
				return Constants.REGISTEREDUSER; //user acc exists

			}
			else
			{
				User newUser=new Reader(urb.getFirstName(),urb.getLastName(),
						urb.getEmail(),urb.getPassword(),urb.getDateOfBirth());		
				session.save(newUser);
				return Constants.REGRISTRATIONSUCCESSFUL; //registration is successful	
			}
		}catch(HibernateException he){
			System.out.println("Exception in DAO");
			he.printStackTrace();
			return he.getMessage();
		}
	}
	
	public String authenticate(LoginBean lbean) throws Exception{

		session=HibernateUtil.getSessionFactory().getCurrentSession();

		boolean validUser;
		Iterator iter;
		List<User> luser;
		Date prevLoggedTime;
		resValidation=lbean.validate(); 
		System.err.println("resValidation="+resValidation);
		if(!(resValidation.equals(Constants.SUCCESS))){
			return resValidation;
		}

		try{
			CheckUser= session.createQuery("select count(*) from User u where u.email=:email and u.password=:password");
			CheckUser.setParameter("email", lbean.getEmail());
			CheckUser.setParameter("password", lbean.getPassword());
			int count= ((Long)CheckUser.uniqueResult()).intValue();

			System.err.println("COUNT!!!!"+count);
			if(count>0){
				CheckUser= session.createQuery("from User u where u.email=:email and u.password=:password");
				CheckUser.setParameter("email", lbean.getEmail());
				CheckUser.setParameter("password", lbean.getPassword());	
				luser=CheckUser.list();

				for(iter=luser.iterator();iter.hasNext();){
					User thisUser=(User)iter.next();
				}
				
				return Constants.LOGGEDUSER;
			}
			else
			{
				return Constants.UNREGISTEREDUSER;
			}		
		}catch(HibernateException he){
			System.out.println("Exception in DAO-authenticate");
			he.printStackTrace();
			return he.getMessage();
		}
	}
	public String getForgottenPassword(LoginBean lb) throws Exception{
		//SHOUDL PUT A CHECK ON EMAIL-if it exists, else throw excception and send to Registrationpage!!!!
		session=HibernateUtil.getSessionFactory().getCurrentSession();
		List pwd;
		String passwd = null;
		Iterator iter;
		try{
			CheckUser= session.createQuery("select password from User u where u.email=:email");
			CheckUser.setParameter("email", lb.getEmail());
			pwd=CheckUser.list();
			if(pwd.isEmpty())
				return Constants.UNREGISTEREDUSER;
			for(iter=pwd.iterator();iter.hasNext();){			
				passwd=(String) iter.next();				
				System.err.println("pswd="+passwd);
			}
			return passwd;
		}catch(HibernateException he){
			System.out.println("Exception in DAO-authenticate");
			he.printStackTrace();
			return he.getMessage();
		}
	}
	//to get user type and call respective method
	public String getUserType(LoginBean lbean)throws Exception{		
		
		session=HibernateUtil.getSessionFactory().getCurrentSession();
		
		Query userType;
		String dtype;
		try{
			userType=session.createSQLQuery("select DTYPE from User u where u.email=:email and u.password=:password");
			userType.setParameter("email", lbean.getEmail());
			userType.setParameter("password", lbean.getPassword());
		
			List userTypeEntity=userType.list();
			dtype=(String) userTypeEntity.get(0);
			
		}catch(HibernateException he){
			System.out.println("Exception in DAO-authenticate");
			he.printStackTrace();
			return null; 		
		}
		return dtype;

	}
	//This method is used to get user details
	public UserDetailsBean getUserDetails(LoginBean lbean, UserDetailsBean udb)throws Exception{

		session=HibernateUtil.getSessionFactory().getCurrentSession();

		Reader curUser = null;
		int commListSize;
		Collection<Comments> commList;
		Collection<Book> readerBookList;
		Collection<GenreRoom> gRoomsList;
		
		List<User> userEntity;	

		try{						
			CheckUser= session.createQuery("from User u where u.email=:email and u.password=:password");
			CheckUser.setParameter("email", lbean.getEmail());
			CheckUser.setParameter("password", lbean.getPassword());

			userEntity=CheckUser.list();

			//since only one obj with the respective values,getting the 0th obj
			curUser=(Reader)userEntity.get(0);

			//constructing a userdetailsbean for login page
			String fName=curUser.getFname();			
			String lName=curUser.getLname();		
			String email=curUser.getEmail();		
			
			
			Date prevDate=curUser.getLoggedTime();
			if(prevDate==null)prevDate=new Date();
			curUser.setLoggedTime(new Date());
			
			
			commList=curUser.getComments();			
			for(Comments c:commList)
				System.err.println("my commetns>>"+c.getCommentsDesc());
			commListSize=commList.size();
			readerBookList=curUser.getBooks();
			
			udb.setFname(fName);
			udb.setLname(lName);
			udb.setComments(commList);
			
			gRoomsList=curUser.getRooms();
			System.err.println("Rooms>>>"+gRoomsList.size());
			for(GenreRoom r:gRoomsList)
				System.err.println("Rooms are>>"+r.getGenreType());
			
			udb.setGenreRooms(gRoomsList);

			udb.setBooksRead(readerBookList);

			udb.setTotalCommentsCount(commListSize);
			
			udb.setLoginDate(prevDate);
			return udb;

		}catch(HibernateException he){
			System.out.println("Exception in DAO-authenticate");
			he.printStackTrace();
			return null; 		
		}
	}
	//This method is exclusively used to get author details
	public AuthorDetailsBean getAuthorDetails(LoginBean lbean)throws Exception{

		session=HibernateUtil.getSessionFactory().getCurrentSession();

		Author curAuthor = null;
		int commListSize,bookListSize,bookWrittenListSize;
		Collection<Comments> commList;
		Collection<Book> bookReadList= new ArrayList();
		Collection<Book> allBooks;
		Collection<Book> bookWrittenList= new ArrayList();
		Collection<GenreRoom> gRoomsList;
		
		AuthorDetailsBean adb=new AuthorDetailsBean();
		List<User> userEntity;	

		try{						
			CheckUser= session.createQuery("from User u where u.email=:email and u.password=:password");
			CheckUser.setParameter("email", lbean.getEmail());
			CheckUser.setParameter("password", lbean.getPassword());

			userEntity=CheckUser.list();

			//since only one obj with the respective values,getting the 0th obj
			curAuthor=(Author)userEntity.get(0);

			//constructing a AuthorDetailsBean for Auth profile page
			String fName=curAuthor.getFname();			
			String lName=curAuthor.getLname();		
			String email=curAuthor.getEmail();			
			
			Date prevDate=curAuthor.getLoggedTime();
			if(prevDate==null)prevDate=new Date();
			curAuthor.setLoggedTime(new Date());
			
			commList=curAuthor.getComments();			
			commListSize=commList.size();
			
			bookReadList=curAuthor.getBooks();

			for(Book b:bookReadList)
				System.err.println(b.getBookTitle());
			
			
			bookWrittenList=curAuthor.getMyBooks();

			for(Book b:bookWrittenList)
				System.err.println(b.getBookTitle());
			
			bookListSize=bookReadList.size();

			bookWrittenListSize=bookWrittenList.size();

			gRoomsList=curAuthor.getRooms();

			for(GenreRoom r:gRoomsList)
				System.err.println("Rooms are>>"+r.getGenreType());
			
			adb.setFname(fName);
			adb.setLname(lName);
			
			adb.setComments(commList);			
			adb.setBooksRead(bookReadList);
			adb.setBooksWritten(bookWrittenList);

			adb.setTotalCommentsCount(commListSize);		
			adb.setTotalBooksRead(bookListSize);
			adb.setTotalBooksWritten(bookWrittenListSize);
			
			adb.setLoginDate(prevDate);
			
			adb.setGenreRooms(gRoomsList);
			
			return adb;

		}catch(HibernateException he){
			System.out.println("Exception in DAO-authenticate");
			he.printStackTrace();
			return null; 		
		}
		
	}
	
	public AuthorDetailsBean getAuthorDetails(User u)throws Exception{

		System.err.println("####INSIDE getAuthorDetails####");

		session=HibernateUtil.getSessionFactory().getCurrentSession();

		Author curAuthor = null;
		int commListSize,bookListSize,bookWrittenListSize;
		Collection<Comments> commList;
		Collection<Book> bookReadList= new ArrayList();
		Collection<Book> allBooks;
		Collection<Book> bookWrittenList= new ArrayList();
		Collection<GenreRoom> gRoomsList;
		
		AuthorDetailsBean adb=new AuthorDetailsBean();
		List<User> userEntity;	

		try{						
			CheckUser= session.createQuery("from User u where u.email=:email and u.password=:password");
			CheckUser.setParameter("email", u.getEmail());
			CheckUser.setParameter("password", u.getPassword());

			userEntity=CheckUser.list();

			//since only one obj with the respective values,getting the 0th obj
			curAuthor=(Author)userEntity.get(0);

			//constructing a AuthorDetailsBean for Auth profile page
			String fName=curAuthor.getFname();			
			String lName=curAuthor.getLname();		
			String email=curAuthor.getEmail();			
			
			Date prevDate=curAuthor.getLoggedTime();
			if(prevDate==null)prevDate=new Date();
			curAuthor.setLoggedTime(new Date());
			
			commList=curAuthor.getComments();			
			commListSize=commList.size();
			
			bookReadList=curAuthor.getBooks();
			System.err.println("My read books>>>");
			for(Book b:bookReadList)
				System.err.println(b.getBookTitle());
			
			
			bookWrittenList=curAuthor.getMyBooks();
			System.err.println("My written books>>>");
			for(Book b:bookWrittenList)
				System.err.println(b.getBookTitle());
			
			bookListSize=bookReadList.size();
			System.err.println("Size inside userDAO>>"+bookListSize);
			bookWrittenListSize=bookWrittenList.size();
			System.err.println("written Size inside userDAO>>"+bookWrittenListSize);
			
			//GENRE>>>NEWWWW
			gRoomsList=curAuthor.getRooms();
			System.err.println("Rooms>>>"+gRoomsList.size());
			for(GenreRoom r:gRoomsList)
				System.err.println("Rooms are>>"+r.getGenreType());
			
			adb.setFname(fName);
			adb.setLname(lName);
			
			adb.setComments(commList);			
			adb.setBooksRead(bookReadList);
			adb.setBooksWritten(bookWrittenList);

			adb.setTotalCommentsCount(commListSize);		
			adb.setTotalBooksRead(bookListSize);
			adb.setTotalBooksWritten(bookWrittenListSize);
			
			adb.setLoginDate(prevDate);
			
			//GENRE>>>NEWWWW
			adb.setGenreRooms(gRoomsList);
			
			return adb;

		}catch(HibernateException he){
			System.out.println("Exception in DAO-authenticate");
			he.printStackTrace();
			return null; 		
		}
		
	}
	
	@Override
	public List findAll() {
		return null;
	}	
	
	
	public List<BookListBean> searchBookByEbookID(List<Integer> ebookID)
	{
		session=HibernateUtil.getSessionFactory().getCurrentSession();
		BookListBean blbean = new BookListBean();
		List<BookListBean> blbeanList = new ArrayList<BookListBean>();
		//instead of Book, use bean that can be used to display on screen
		List<Book> books = new ArrayList<Book>();
		List<Book> l;
		for (int id: ebookID)
		{
			CheckUser= session.createQuery("from Book b where b.ebookID = :ebookID");
			CheckUser.setParameter("ebookID", id);
			l = CheckUser.list();
			
			if(l.size()!=0)
			{
			
			blbean.setEbookID(l.get(0).getEbookID());
			blbean.setLanguage(l.get(0).getLanguage());
			blbean.setTitle(l.get(0).getBookTitle());
			blbean.setComment(l.get(0).getBookComments());
			blbeanList.add(blbean);
			}
		}
		return blbeanList;
	}
	
	public BookListBean findBook(int ebookID)
	{
		session=HibernateUtil.getSessionFactory().getCurrentSession();
		BookListBean blbean = new BookListBean();
		//instead of Book, use bean that can be used to display on screen
		List<Book> l;

			CheckUser= session.createQuery("from Book b where b.ebookID = :ebookID");
			CheckUser.setParameter("ebookID", ebookID);
			l = CheckUser.list();
			
			blbean.setEbookID(l.get(0).getEbookID());
			blbean.setLanguage(l.get(0).getLanguage());
			blbean.setTitle(l.get(0).getBookTitle());
			blbean.setComment(l.get(0).getBookComments());
		
		return blbean;
	}
	
	public BookPageBean findBookBean(int ebookID)
	{
		session=HibernateUtil.getSessionFactory().getCurrentSession();
		
		BookPageBean bpbean = new BookPageBean();
		//instead of Book, use bean that can be used to display on screen
		List<Book> l;

		CheckUser= session.createQuery("from Book b where b.ebookID = :ebookID");
		CheckUser.setParameter("ebookID", ebookID);
		l = CheckUser.list();
		
		bpbean.setBookid(l.get(0).getBookid());
		bpbean.setEbookID(l.get(0).getEbookID());
		bpbean.setLanguage(l.get(0).getLanguage());
		bpbean.setTitle(l.get(0).getBookTitle());
		bpbean.setCommentsList(l.get(0).getBookComments());
		bpbean.setGenre(l.get(0).getGenre());
		bpbean.setLastUpdated(l.get(0).getLastUpdated());
		bpbean.setPostingDate(l.get(0).getPostingDate());
		bpbean.setReleaseDate(l.get(0).getReleaseDate());
		
		bpbean.setAuthor(l.get(0).getAuthors());

		return bpbean;
	}
	
	public String insertComment(String commentText, User user, Book book, Date date){
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();		
		Comments newComment=new Comments(commentText, user, book, date);	
		session.save(newComment);
		return Constants.SUCCESS;	
	}
	
	public Book getBook(long bookid){
		
		session=HibernateUtil.getSessionFactory().getCurrentSession();
		List<Book> bookList;

		try{
			query = session.createQuery("from Book b where b.bookid=:bookId");
			query.setParameter("bookId", bookid);
			bookList = query.list();
		
			Iterator iter = bookList.iterator();
			if(iter.hasNext()){
				Book book = (Book) iter.next();
				return book;
			}
			return null;
		}catch(HibernateException he){
			System.out.println("Exception in DAO-authenticate");
			he.printStackTrace();
			return null; 
		}

	}
	
	public User getUserByEmail(String email){
		
		session=HibernateUtil.getSessionFactory().getCurrentSession();

		List<User> userList;

		try{
			query = session.createQuery("from User u where u.email=:email");
			query.setParameter("email", email);
			userList = query.list();

			return userList.get(0);
		}catch(HibernateException he){
			System.out.println("Exception in DAO-authenticate");
			he.printStackTrace();
			return null; 
		}
	}
	
	@SuppressWarnings("unchecked")
	public String insertRatings(double rating, User user, Book book, Date date){
		System.err.println("Inserting the rating to DB");
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		
		int checkCount =0;
		List<Ratings> ratingList;
		
		query = session.createQuery("select count(*) from Ratings r where r.user=:user and r.book=:book");
		query.setParameter("user", user);
		query.setParameter("book", book);
		
		checkCount= ((Long)query.uniqueResult()).intValue();
		System.err.println("Insert Rating: Check Count = " + checkCount);
		if(checkCount > 0)
		{
			String hql = "update Ratings r set r.rating = :rating where r.user=:user and r.book=:book";
			query = session.createQuery(hql);
			query.setParameter("rating",rating);
	        query.setParameter("user",user);
	        query.setParameter("book",book);
	        int rowCount = query.executeUpdate();
		}
		else
		{
			Ratings userRating =new Ratings(rating, user, book, date);		
			session.save(userRating);
		}
		
		System.err.println("Finished inserting the rating to DB");
		return Constants.SUCCESS;	
	}

	public User getUserById(long userid){
		
		session=HibernateUtil.getSessionFactory().getCurrentSession();
		

		List<User> userList;

		try{
			query = session.createQuery("from User u where u.uid=:userid");
			query.setParameter("userid", userid);
			userList = query.list();
			return userList.get(0);
		}catch(HibernateException he){
			System.out.println("Exception in DAO-authenticate");
			he.printStackTrace();
			return null; 
		}
		
	}

	/**GENRE>>>NEWWW**/
	public String createGenreRoom(User u,GenreRoomBean gb){
		
		session=HibernateUtil.getSessionFactory().getCurrentSession();

		int CheckCount;
		String desc=gb.getGenreDesc();
		String roomName=gb.getRoomName();
		String genreType = gb.getGenreType();
		
		if(!genreList.contains(genreType)){
			return Constants.GENREDOESNOTEXISTS;
		}
		
		GenreRoom gr=new GenreRoom(u,roomName,desc,genreType);

		CheckUser= session.createQuery("select count(*) from GenreRoom g where g.roomName=:roomName");
		CheckUser.setParameter("roomName", roomName);
		CheckCount= ((Long)CheckUser.uniqueResult()).intValue();
		if(CheckCount > 0)
		{
			return Constants.ROOMEXISTS;
		}
		else
		{
			session.save(gr);
			return Constants.SUCCESS;
		}

	}
	
	public void saveReadBook(User user, Long bookid)
	{
		session=HibernateUtil.getSessionFactory().getCurrentSession();
		Book b = getBook(bookid);
		
		query = session.createQuery("from Book b where b.bookid=:bookId");
		query.setParameter("bookId", bookid);
		List<Book> l = query.list();
		boolean alreadyRead = false;
		for(User u:l.get(0).getUsers())
		{
			if(u.getUid()==user.getUid())
			{
				alreadyRead = true;
			}
		}
		
		if(!alreadyRead)
		{			
			b.addUser(user);
			session.save(b);
		}	
		
	}
	
	
	public boolean isUserExisting(User u)throws Exception
	{
		int CheckCount;

		try{
			session=HibernateUtil.getSessionFactory().getCurrentSession();

			CheckUser= session.createQuery("select count(*) from User u where u.lname=:lname and u.dob=:dob and u.email=:email");
			CheckUser.setParameter("lname", u.getLname());
			CheckUser.setParameter("dob", u.getDob());
			CheckUser.setParameter("email", u.getEmail());
			CheckCount= ((Long)CheckUser.uniqueResult()).intValue();
			if(CheckCount > 0)
			{
				return true; //user acc exists
			}
			else
			{
				return false; //registration is successful	
			}
		}catch(HibernateException he){
			System.out.println("Exception in DAO");
			he.printStackTrace();
			return false;
		}
	}
	
	
	//gets the list of books
	public List<Book> getBookListByID(List<Integer> ebookID)
	{
		session=HibernateUtil.getSessionFactory().getCurrentSession();
	
		List<Book> l;
		System.err.println("######################## EbookID in searchBookByEbookID: "+ebookID);
		
		try{
		CheckUser= session.createQuery("from Book b where b.ebookID in (:ebookID)");
		CheckUser.setParameterList("ebookID", ebookID);
		l = CheckUser.list();
		
		System.out.println("in bookllist USERDAO");
		
		return l;
		}catch(HibernateException he){
			System.out.println("Exception getBookListByID");
			he.printStackTrace();
			return null; 
		}
		
	}
	
	
	//gets the list of books by genre...later change it to genreID
	public List<Book> getBookListByGenre(String genre)
	{
		session=HibernateUtil.getSessionFactory().getCurrentSession();
	
		List<Book> l;
		
		try{
		CheckUser= session.createQuery("from Book b where b.genre =:genre");
		CheckUser.setParameter("genre", genre);
		l = CheckUser.list();
		
		return l;
		}catch(HibernateException he){
			System.out.println("Exception getBookListByID");
			he.printStackTrace();
			return null; 
		}
		
	}
	
	//method to return the list of genres
	public List<String> getGenreList()
	{
		session=HibernateUtil.getSessionFactory().getCurrentSession();
	
		List<Book> bookList;
		List<String> glist;
		Iterator iter;
		
		try{
		CheckUser= session.createQuery("from Book b");
		bookList = CheckUser.list();

		List<String> genreList = new ArrayList<String>();
		
		for(iter=bookList.iterator();iter.hasNext();){
			Book thisBook=(Book)iter.next();
			if(!genreList.contains((String)thisBook.getGenre())){
				genreList.add(thisBook.getGenre());
			}
		}

		return genreList;
		}catch(HibernateException he){
			System.out.println("Exception getBookListByID");
			he.printStackTrace();
			return null; 
		}
		
	}

	public List<Book> getRecommendedBooks(User u) {
        
        session=HibernateUtil.getSessionFactory().getCurrentSession();
        
        List<String> genlist = new ArrayList<String>();
        List<Book> l;
        List<Book> recList;
        
        try{
        CheckUser= session.createQuery("from Book b");
        l = CheckUser.list();
        
        Collection<User> userColl=new ArrayList<User>();
        
        for (int i=0;i<l.size();i++)
        {
            userColl=l.get(i).getUsers();
            
            for(User us:userColl){                
                if(us.getEmail().equals(u.getEmail())){                   
                    if(l.get(i).getGenre()!= null)
                    {                        
                        genlist.add(l.get(i).getGenre());
                    }        
                }
            }
        
          
        }
        
        CheckUser= session.createQuery("from Book b where b.genre in (:genlist)");
        CheckUser.setParameterList("genlist", genlist);
        recList = CheckUser.list();                      
        
        return recList;
        
        }catch(HibernateException he){
            System.out.println("Exception getBookListByID");
            he.printStackTrace();
            return null; 
        }
    }   
	
	public List<Book> getBooksByGenreList(List<String> genList){
		session=HibernateUtil.getSessionFactory().getCurrentSession();
		List<Book> genBookList;
		System.err.println("getBooksByGenreList USERDAO>>>>");
		try{
			CheckUser= session.createQuery("from Book b where b.genre in (:genList)");
	        CheckUser.setParameterList("genList", genList);
	        genBookList = CheckUser.list();
	        
	        System.err.println("reclist USERDAO>>>>"+genBookList.size());
	        
	        return genBookList;
		}catch(Exception he){
            System.out.println("Exception getBookListByID");
            he.printStackTrace();
            return null; 
        }
	}
	

	
	//method to return the list of genres
	public List<String> getGenreListBySelect()
	{
		session=HibernateUtil.getSessionFactory().getCurrentSession();
	
		List<Book> l;
		List<String> glist;
		
		try{
		CheckUser= session.createQuery("select b.genre from Book b");
		glist = CheckUser.list();
		
		return glist;
		}catch(HibernateException he){
			System.out.println("Exception getBookListByID");
			he.printStackTrace();
			return null; 
		}
		
	}
	
	
	public List<User> getAuthorList()
	{
		session=HibernateUtil.getSessionFactory().getCurrentSession();
	
		//List<Book> l;
		List<User> authorList = new ArrayList<User>();
		List<User> userList;
		System.err.println("USERDAO getAuthorList");
//		System.err.println("######################## EbookID in searchBookByEbookID: "+genre);
		String author = "author";
		try{
		CheckUser= session.createQuery("from User u");
		System.err.println("USERDAO getAuthorList: After getAuthorList");
		//CheckUser.setParameter("author", author);
		userList = CheckUser.list();
				
		Iterator iter;
		User auth;
		for(iter=userList.iterator();iter.hasNext();){			
			auth=(User) iter.next();
			try {
				if(isUserAuthor(auth)){
					authorList.add(auth);
					System.err.println("Author="+auth.getFname());
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return authorList;
		}catch(HibernateException he){
			System.out.println("Exception getBookListByID");
			he.printStackTrace();
			return null; 
		}
		
	}
	
public boolean isUserAuthor(User u)throws Exception{		
		
		System.err.println("####INSIDE getUserType####");
		
		session=HibernateUtil.getSessionFactory().getCurrentSession();
		
		Query userType;
		String dtype;
		boolean flag = false;
		//String email = u.getEmail();
		try{
			userType=session.createSQLQuery("select DTYPE from User u where u.email=:email");
			userType.setParameter("email", u.getEmail());
			//userType.setParameter("password", u.getPassword());
		
			List userTypeEntity=userType.list();
			dtype=(String) userTypeEntity.get(0);
			if(dtype.equalsIgnoreCase("author")){
				flag = true;
				return flag;
			}
			return flag;
			//System.err.println("DTYPE>>>>>>"+dtype);
		}catch(HibernateException he){
			System.out.println("Exception in DAO-authenticate");
			he.printStackTrace();
			return flag; 		
		}

	}
	
	
	public double getRating(User user, Book book){
		session=HibernateUtil.getSessionFactory().getCurrentSession();
		
		double ratings = 0;
		List<Ratings> ratingList;
		try{
			query = session.createQuery("from Ratings r where r.user=:user and r.book=:book");
			query.setParameter("user", user);
			query.setParameter("book", book);
			
			ratingList = query.list();
			if(ratingList.size()>0)
			ratings = ratingList.get(0).getRating();
			
		}catch(Exception e){
			System.out.println("Exception getBookListByID");
			e.printStackTrace();

		}
		return ratings;
	}
}
