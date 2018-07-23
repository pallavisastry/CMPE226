package com.readALoud.loader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.readALoud.entities.Author;
import com.readALoud.entities.Book;
import com.readALoud.entities.Comments;
import com.readALoud.entities.GenreRoom;
import com.readALoud.entities.Ratings;
import com.readALoud.entities.Reader;
import com.readALoud.entities.User;
import com.readALoud.utils.HibernateTablesCreator;
import com.readALoud.utils.HibernateUtil;

public class EntityLoader {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		EntityLoader loader = new EntityLoader();
		loader.testTableCreation();
		
	}

	public void testTableCreation(){
		
		HibernateTablesCreator.createTables(GenreRoom.class);
		HibernateTablesCreator.createTables(User.class);
		HibernateTablesCreator.createTables(Reader.class); 
		HibernateTablesCreator.createTables(Author.class);
		HibernateTablesCreator.createTables(Book.class); 
		HibernateTablesCreator.createTables(Comments.class);
		HibernateTablesCreator.createTables(Ratings.class);
		
		Session session=HibernateUtil.getSessionFactory().openSession();		
		Transaction transaction=session.beginTransaction();
		
		
		//Authors and books will already exist in the table;this is for new ppl only		
		Author author1=new Author("Ayn", "Rand", "ar@gmail.com", "pals",new Date("2/2/1905"));
		Author author2=new Author("Isaac", "Asimov", "ia@gmail.com", "pals",new Date("2/1/1920"));
		
		session.save(author1);
		session.save(author2);
		
		User reader1=new Reader("Pallavi","Sastry","ps@gmail.com","pals",new Date("05/10/1984"));
		User reader2=new Reader("Vishnu","Vardhan","vv@gmail.com","pals",new Date("03/10/1954"));
		User reader3=new Reader("Raj","Kumar","rk@gmail.com","pals",new Date("05/06/1944"));
			
		
		session.save(reader1);
		session.save(reader2);
		session.save(reader3);

		
		Book book1=new Book("The FountainHead");
		book1.addAuthor(author1);		
		book1.addUser(reader1);book1.addUser(reader1);book1.addUser(reader1);
		book1.addUser(reader2);		
		book1.setPostingDate(new Date());
		book1.setEbookID(1);
		book1.setGenre("Art");
		
		Book book2=new Book("Atlas Shrugged");
		book2.addAuthor(author1);
		book2.addUser(reader2);
		book2.setPostingDate(new Date());
		book2.setEbookID(2);
		book2.setGenre("History");
		
		Book book3=new Book("The Foundation");
		book3.addAuthor(author2);
		book3.addUser(author1);
		book3.addUser(reader3);
		book3.setPostingDate(new Date());
		book3.setEbookID(3);
		book3.setGenre("Art");
		
		Book book4=new Book("The Time Machine");
		book4.addAuthor(author2);
		book4.setPostingDate(new Date());
		book4.setEbookID(4);
		book4.setGenre("Art");
		
		Collection<Book> readBooks=new ArrayList();
		readBooks.add(book3);readBooks.add(book4);
		
		session.save(book1);
		session.save(book2);
		session.save(book3);
		session.save(book4);
		reader2.addComment(new Comments("Too ambitios :(", reader2,book2,new Date()));
		session.update(reader2);
		//readers
		//comments by readers
		Comments comm1=new Comments("Best book in the world!!", reader1,book1,new Date());
		Comments comm2=new Comments("Too ambitios :(", reader2,book2,new Date());
		Comments comm3=new Comments("All about integrity", reader2,book1,new Date());
		Comments comm4=new Comments("inspiring", reader2,book2,new Date());
		Comments comm5=new Comments("Hmmmm", reader3,book2,new Date());	
		comm4.setUser(reader2);
		
		session.save(comm1);
		session.save(comm2);
		session.save(comm3);
		session.save(comm4);
		session.save(comm5);
		
		long rate1no = 5;
		Ratings rate1=new Ratings(rate1no, reader1,book1,new Date());
		Ratings rate2=new Ratings(rate1no, reader2,book2,new Date());
		Ratings rate5=new Ratings(rate1no, reader3,book2,new Date());	
		
		session.save(rate1);
		session.save(rate2);
		session.save(rate5);
				
		transaction.commit();
		session.close();
		
		Session secondSession=HibernateUtil.getSessionFactory().openSession();
		Transaction secondTransaction=secondSession.beginTransaction();
		
		List<Comments> comments=secondSession.createSQLQuery("select * from comments").list();
		System.out.println("list size:"+comments.size());

		
		List<User> users=secondSession.createSQLQuery("select * from user").list();
		System.out.println("user table list size:"+users.size());
						
	
	}
	
}
