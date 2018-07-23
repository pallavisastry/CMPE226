package com.readALoud.client;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;

import com.readALoud.utils.HibernateUtil;


public class UserRegistrationApplication extends WebApplication 
{
	public UserRegistrationApplication(){
		//This should not be use since Open Session In View pattern is applied
		/*org.hibernate.Session myHibSession=
			HibernateUtil.getSessionFactory().getCurrentSession();
		myHibSession.beginTransaction();*/		
		
	}

	@Override
	public Class<? extends Page> getHomePage() {
		return UserRegistrationPage.class;
	}


}
