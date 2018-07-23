package com.readALoud.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;


import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;


import com.readALoud.beans.LoginBean;
import com.readALoud.beans.UserDetailsBean;
import com.readALoud.beans.UserRegistrationBean;
import com.readALoud.entities.Book;
import com.readALoud.entities.User;
import com.readALoud.utils.Constants;
import com.readALoud.utils.HibernateUtil;

public class UserDAOTest {

	//@Test
	public void testRegister() throws Exception {
				
		UserDAO rs=new UserDAO();
		
		UserRegistrationBean u=new UserRegistrationBean();
		u.setDateOfBirth(new Date("10/02/1984"));
		u.setLastName("prakash");
		u.setEmail("sp@gmail.com");
		u.setPassword("pals");
		u.setFirstName("shashi");
		u.setRepeatPassword("pals");
		u.setUserName("shashi");
		
		String res=rs.register(u);
		System.out.println("res===>"+res);
		Assert.assertNotNull(res);
		//Assert.assertEquals(Constants.REGISTEREDUSER || Constants.SUCCESS, res);
	}
	
	//@Test
	public void testReadSave(){

	Session s=HibernateUtil.getSessionFactory().getCurrentSession();
	Transaction t=s.beginTransaction();

	LoginBean lb=new LoginBean();
	lb.setEmail("ps@gmail.com");
	lb.setPassword("pals");

	
	
	UserDAO ud = new UserDAO();
	
	User user =ud.getUserByEmail(lb.getEmail());
	System.err.println("##############TESTuser id:"+user.getUid());
	long i=1;
	ud.saveReadBook(user, i);
	ud.saveReadBook(user, i);
	ud.saveReadBook(user, i);
	ud.saveReadBook(user, i);

	}
	
	@Test
	public void bookListTest()
	{
		List<Book> retBook;
		UserDAO ud = new UserDAO();
		List<Integer> ebookID = new ArrayList<Integer>();
		ebookID.add(11);
		ebookID.add(12);
		
		retBook=ud.getBookListByID(ebookID);
		
		System.out.println("back to book list");
		
		for (Book b:retBook)
		{
			System.out.println("retBook: "+b.getEbookID());
		}
	}

}
