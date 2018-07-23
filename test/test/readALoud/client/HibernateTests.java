package test.readALoud.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import junit.framework.Assert;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.readALoud.entities.Author;
import com.readALoud.entities.Book;
import com.readALoud.entities.Comments;
import com.readALoud.entities.Ratings;
import com.readALoud.entities.Reader;
import com.readALoud.entities.User;
import com.readALoud.utils.HibernateUtil;

public class HibernateTests {
	
	private static Session session;		
	private static Transaction transaction;
	@SuppressWarnings("rawtypes")
	private Iterator iter;
	@SuppressWarnings("rawtypes")
	private List list;
	private Query query;
	
	@BeforeClass
	public static void setup() {
		session=HibernateUtil.getSessionFactory().openSession();		
		transaction=session.beginTransaction();
	}
	
	@AfterClass
	public static void teardown() {
		transaction.rollback();
		session.close();
	}
	
	@Test
	public void users() {
		list = session.createQuery("from User").list();
		for(iter=list.iterator();iter.hasNext();){
			User user=(User)iter.next();
			Assert.assertNotNull(user);
			Assert.assertNotNull(user.getUid());
			Assert.assertNotNull(user.getFname());
			Assert.assertNotNull(user.getLname());
			Assert.assertNotNull(user.getPassword());
			Assert.assertNotNull(user.getEmail());
		}	
		
	}
	
	@Test
	public void books() {
		list = session.createQuery("from Book").list();
		for(iter=list.iterator();iter.hasNext();){
			Book book=(Book)iter.next();
			Assert.assertNotNull(book);
			Assert.assertNotNull(book.getBookid());
			Assert.assertNotNull(book.getBookTitle());
			Assert.assertNotNull(book.getAuthors());
		}	
	}
	
	@Test
	public void comments() {
		list = session.createQuery("from Comments").list();
		for(iter=list.iterator();iter.hasNext();){
			Comments comments=(Comments)iter.next();
			Assert.assertNotNull(comments);
			Assert.assertNotNull(comments.getId());
			Assert.assertNotNull(comments.getBook());
			Assert.assertNotNull(comments.getUser());
			Assert.assertNotNull(comments.getCreationDate());
			Assert.assertNotNull(comments.getCommentsDesc());
		}	
	}
	
	@Test
	public void ratings() {
		list = session.createQuery("from Ratings").list();
		for(iter=list.iterator();iter.hasNext();){
			Ratings rating=(Ratings)iter.next();
			Assert.assertNotNull(rating);
			Assert.assertNotNull(rating.getId());
			Assert.assertNotNull(rating.getBook());
			Assert.assertNotNull(rating.getUser());
			Assert.assertNotNull(rating.getRating());
			Assert.assertNotNull(rating.getCreationDate());
		}	
	}
	
	@Test
	public void create_read_author() {
		String firstName = "John";
		String lastName = "Doe";
		String email = "jda@gmail.com";
		String pwd = "pals";
		@SuppressWarnings("deprecation")
		Date dob = new Date("2/2/1975");
		User author1=new Author(firstName, lastName, email, pwd, dob);
		session.save(author1);
		
		query= session.createQuery("from User where Email=:email and DTYPE=:type");
		query.setParameter("email", email);
		query.setParameter("type", "Author");
		list = query.list();
		iter=list.iterator();
		User author=(User)iter.next();
		Assert.assertNotNull(author);
		Assert.assertNotNull(author.getUid());
		Assert.assertNotNull(author.getFname());
		Assert.assertNotNull(author.getLname());
		Assert.assertNotNull(author.getPassword());
		Assert.assertNotNull(author.getEmail());
		Assert.assertNotNull(author.getDob());
		
		
		Assert.assertEquals(firstName, author.getFname());
		Assert.assertEquals(lastName, author.getLname());
		Assert.assertEquals(pwd, author.getPassword());
		Assert.assertEquals(email, author.getEmail());
		Assert.assertEquals(dob, author.getDob());
		
		
	}
	
	@Test
	public void create_read_reader() {
		String firstName = "Jane";
		String lastName = "Doe";
		String email = "jdr@gmail.com";
		String pwd = "pals";
		@SuppressWarnings("deprecation")
		Date dob = new Date("05/10/1984");
		User reader1=new Reader(firstName, lastName, email, pwd, dob);
		session.save(reader1);
		
		query= session.createQuery("from User where Email=:email and DTYPE=:type");
		query.setParameter("email", email);
		query.setParameter("type", "Reader");
		list = query.list();
		iter=list.iterator();
		User reader=(User)iter.next();
		Assert.assertNotNull(reader);
		Assert.assertNotNull(reader.getUid());
		Assert.assertNotNull(reader.getFname());
		Assert.assertNotNull(reader.getLname());
		Assert.assertNotNull(reader.getPassword());
		Assert.assertNotNull(reader.getEmail());
		Assert.assertNotNull(reader.getDob());
		
		Assert.assertEquals(firstName, reader.getFname());
		Assert.assertEquals(lastName, reader.getLname());
		Assert.assertEquals(pwd, reader.getPassword());
		Assert.assertEquals(email, reader.getEmail());
		Assert.assertEquals(dob, reader.getDob());
	}
	
	@Test
	public void create_read_book() {
		String emailAuthor = "jda@gmail.com";
		query= session.createQuery("from User where Email=:email and DTYPE=:type");
		query.setParameter("email", emailAuthor);
		query.setParameter("type", "Author");
		list = query.list();
		iter=list.iterator();
		User author=(User)iter.next();
		Assert.assertNotNull(author);
		
		String emailReader = "jdr@gmail.com";
		query= session.createQuery("from User where Email=:email and DTYPE=:type");
		query.setParameter("email", emailReader);
		query.setParameter("type", "Reader");
		list = query.list();
		iter=list.iterator();
		User reader=(User)iter.next();
		Assert.assertNotNull(reader);

		String bookName = "Harry Potter";
		Book book1=new Book(bookName);
		book1.addAuthor(author);		
		book1.addUser(reader);
		book1.setPostingDate(new Date());
		session.save(book1);
		
		query= session.createQuery("from Book where book_title=:title");
		query.setParameter("title", bookName);
		list = query.list();
		iter=list.iterator();
		Book book=(Book)iter.next();
		Assert.assertNotNull(book);
		Assert.assertNotNull(book.getBookid());
		Assert.assertNotNull(book.getBookTitle());
		Assert.assertNotNull(book.getAuthors());
		Assert.assertNotNull(book.getUsers());
		
		Collection<User> authors = new ArrayList<User>();
		authors.add(author);
		Collection<User> readers = new ArrayList<User>();
		readers.add(reader);
		
		Assert.assertEquals(authors, book.getAuthors());
		Assert.assertEquals(readers, book.getUsers());
		
	}
	
	@Test
	public void comment_rate() {
		
		String bookName = "Harry Potter";
		query= session.createQuery("from Book where book_title=:title");
		query.setParameter("title", bookName);
		list = query.list();
		iter=list.iterator();
		Book book=(Book)iter.next();
		Assert.assertNotNull(book);
		
		String emailReader = "jdr@gmail.com";
		query= session.createQuery("from User where Email=:email and DTYPE=:type");
		query.setParameter("email", emailReader);
		query.setParameter("type", "Reader");
		list = query.list();
		iter=list.iterator();
		User reader=(User)iter.next();
		Assert.assertNotNull(reader);
		
		
		String commentTest = "Comments Testing";
		Comments comm=new Comments(commentTest, reader,book,new Date());	
		comm.setUser(reader);
		session.save(comm);
		
		query= session.createQuery("from Comments where comments=:comment and book_id=:bookid and uid=:uid");
		query.setParameter("comment", commentTest);
		query.setParameter("bookid", book.getBookid());
		query.setParameter("uid", reader.getUid());
		list = query.list();
		iter=list.iterator();
		Comments comment=(Comments)iter.next();
		Assert.assertNotNull(comment);
		
		double rate1no = 4;
		Ratings rate1=new Ratings(rate1no, reader,book,new Date());
		session.save(rate1);
		
		query= session.createQuery("from Ratings where rating=:rating and book_id=:bookid and uid=:uid");
		query.setParameter("rating", rate1no);
		query.setParameter("bookid", book.getBookid());
		query.setParameter("uid", reader.getUid());
		list = query.list();
		iter=list.iterator();
		Ratings rating=(Ratings)iter.next();
		Assert.assertNotNull(rating);
	}
	
}
