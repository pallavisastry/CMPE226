package com.readALoud.loader;

import com.readALoud.entities.Author;
import com.readALoud.entities.Book;
import com.readALoud.entities.User;
import com.readALoud.services.UserDAO;
import com.readALoud.utils.HibernateUtil;



//import gash.indexing.inverted.BookLoader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Collection;
import java.util.Random;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;


public class BookLoader {
	
	private List<File> list = new ArrayList<File>();

	public BookLoader() throws Exception {
	}

	public List<Book> load(File f) throws Exception {
		if (f == null)
			return null;
		if (f.isFile())
			list.add(f);
		else {
			discoverFiles(f);
		}
		return gatherBookdetails();
	}

	public List<File> files() {
		return list;
	}
	
	private List<Book> gatherBookdetails() throws Exception {
		ArrayList<Book> bks = new ArrayList<Book>(list.size());
		for (File f : list) {
			BufferedReader rdr = null;
			
			//----------------book details------------
			//Will not work if security manager is in place!
			Book b = null;
			try{
				Constructor[] c=Book.class.getDeclaredConstructors();
				c[0].setAccessible(true);
				b=new Book();//(Book)c[0].newInstance(null);
			}catch(SecurityException se){
				System.err.println("Security exception:acessing non public constructor");
				se.printStackTrace();
			}
			String fn="", ln="";
			Collection<User> ul = new ArrayList<User>();
			Set<User> userSet = new HashSet<User>();
			Date randomdate = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse("January 1, 2000");
			String authpass = "password";
			try {
				rdr = new BufferedReader(new FileReader(f));
				String raw = rdr.readLine();

				while (raw != null) {
					String[] parts = raw.trim().split("[:#\\[\\]]");
					for (String p : parts)
					{
						//for author-----------------------
						if(p.equalsIgnoreCase("author"))
						{
							String auth = parts[1].trim();
							if (auth.contains(","))
							{
								String name[] = auth.split(",");
								for (String n : name)
								{
									String fname[] =n.trim().split(" ");
									if(fname.length > 2)
									{
										fn = fname[0]+" "+fname[1];
										ln="";
										for(int i=2; i<fname.length; i++)
										{
											ln = ln.concat(fname[i]).concat(" ");											
										}
									}else
									{
										fn=fname[0];
										ln=fname[1];
									}
									userSet.add(new Author(fn,ln,fn+"@"+"na.com",authpass,randomdate));
								}
							}
							else if (auth.contains("and"))
							{
								String name[] = auth.split("and");
								for (String n : name)
								{
									String fname[] = n.trim().split(" ");
									if(fname.length > 2)
									{
										fn = fname[0]+" "+fname[1];	
										ln="";
										for(int i=2; i<fname.length; i++)
										{
											ln = ln.concat(fname[i]).concat(" ");										
										}									
									}
									else
									{
										fn=fname[0];
										ln=fname[1];
									}
									userSet.add(new Author(fn,ln,fn+"@"+"na.com",authpass, randomdate));

								}
							}else
							{
								String fname[] = auth.split(" ");
								fn=fname[0];
								ln=fname[1];
								userSet.add(new Author(fn,ln,fn+"@"+"na.com",authpass, randomdate));

							}
							//set the author list to book
							b.setAuthors(userSet);
							
						}
										
						//---------------author------------------
						
						if(p.equalsIgnoreCase("title"))
						{
							if(parts.length == 3)
							{
							b.setBookTitle(parts[1]+" : "+parts[2]);
							b.setBookDesc(parts[1]+" : "+parts[2]);
							}
							else
							{
								b.setBookTitle(parts[1]);
								b.setBookDesc(parts[1]);
							}
						}
						if(p.equalsIgnoreCase("language"))
						{
							b.setLanguage(parts[1]);
						}
						
						//ebook_id and posting date
						if(p.equalsIgnoreCase("posting date"))
						{
							if(parts.length == 3)
							{
								Date date = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse(parts[1].trim());
								b.setPostingDate(date);
								b.setEbookID(Integer.parseInt(parts[3].trim()));								
							}
							if(parts.length == 2)
							{
								Date date = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse(parts[1].trim());
								b.setPostingDate(date);								
							}
							
						}

						if(p.equalsIgnoreCase("Last updated"))
						{
							if(parts.length == 3)
							{

								Date date = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse(parts[2].trim());
								b.setLastUpdated(date);
							}
							else{
								Date date = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse(parts[1].trim());
								b.setLastUpdated(date);								
							}							

						}
						
						if(p.equalsIgnoreCase("release date"))
						{

							if(parts.length > 3)
							{
								b.setReleaseDate(null);
								b.setEbookID(Integer.parseInt(parts[3].trim()));
							}
							else
							{
								b.setReleaseDate(null);	
							}							
						}
						
					}
					raw = rdr.readLine();
				}
				String[] ids = f.getName().split("[\\-\\.]");
				b.setEbookID(Integer.parseInt(ids[0]));
				b.setFilename(f.getName());
				b.setLocation(f.getAbsolutePath());				
				b.setGenre(generateGenre());
				
				Session session = HibernateUtil.getSessionFactory().openSession();		
				Transaction transaction=session.beginTransaction();

				for(User a:b.getAuthors())
				{
					session.save(a);	
				}				
				session.save(b);
				transaction.commit();
				session.close();
				
				bks.add(b);			
				
			}
			catch (Exception ex) 
			{
				ex.printStackTrace();
			} 
			finally {
				try {
					if (rdr != null)
						rdr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return bks;
	}

	/**
	 * depth search
	 * 
	 * @param dir
	 */
	private void discoverFiles(File dir) {
		if (dir == null || dir.isFile())
			return;

		File[] dirs = dir.listFiles(new FileFilter() {

			@Override
			public boolean accept(File f) {
				if (f.isFile())
					list.add(f);
				else if (f.getName().startsWith("."))
					; // ignore
				else if (f.isDirectory()) {
					discoverFiles(f);
				}

				return false;
			}
		});
	}
	
	public void dump(Book b) throws Exception
	{
		Session session = HibernateUtil.getSessionFactory().openSession();		
		Transaction transaction=session.beginTransaction();

		session.save(b);
		
		for (User a: b.getAuthors())
		{
			boolean isUserRegistered=true;
			int CheckCount;

			try{

				Query CheckUser= session.createQuery("select count(*) from User u where u.fname=:fname and u.lname=:lname and u.email=:email");
				CheckUser.setParameter("fname", a.getFname());
				CheckUser.setParameter("lname", a.getLname());
				CheckUser.setParameter("email", a.getEmail());
				CheckCount= ((Long)CheckUser.uniqueResult()).intValue();
				if(CheckCount > 0)
				{
					//return true; //user acc exists
					isUserRegistered=true;
				}
				else
				{
					//return false; //registration is successful	
					isUserRegistered=false;
				}
			}catch(HibernateException he){
				System.out.println("Exception in DAO");
				he.printStackTrace();
			}
			
			
			
			if(!isUserRegistered)
			{
				session.save(a);	
			}			
		}
		transaction.commit();
		session.close();
		
	}
	
	public boolean isUserExisting(User u)throws Exception
	{
		int CheckCount;

		try{
			Session session=HibernateUtil.getSessionFactory().getCurrentSession();

			Query CheckUser= session.createQuery("select count(*) from User u where u.lname=:lname and u.dob=:dob and u.email=:email");
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
	
	private Random ran = new Random(System.currentTimeMillis());
	private static final String[] genreList = { "Art","Biography","Business","Chick-lit","Children's","Christian","Classics","Comics","Contemporary","Cookbooks","Crime","Ebooks","Fantasy","Fiction","Gay and Lesbian","Graphic novels","Historical fiction","History","Horror","Humor and Comedy","Manga","Memoir","Music","Mystery","Non-fiction","Paranormal","Philosophy","Poetry","Psychology","Religion","Romance","Science","Science fiction","Self help","Suspense","Spirituality","Sports","Thriller","Travel","Young-adult" };
	//to generate random genre
	public String generateGenre()
	{
		int ind = ran.nextInt(genreList.length);
		return genreList[ind];
	}
	
	
	public static void main(String[] args)
	{
		File dir = new File(args[0]);
		BookLoader ldr;
		try {
			ldr = new BookLoader();
			List<Book> books = ldr.load(dir);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
