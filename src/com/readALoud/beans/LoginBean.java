package com.readALoud.beans;

import java.io.Serializable;
import java.util.Date;

import com.readALoud.utils.Constants;

public class LoginBean implements Serializable{
	 
	private static final long serialVersionUID = -1176081512443811118L;
	
	private String email="";
	private String password="";
	private Date loginTime;
	
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmail() {
		return email;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassword() {
		return password;
	}
	//basic check-should be done in wicket!
	public String validate(){
		System.out.println("inside validate() of LogInBean");
		if(email.equals("")){
			return "Enter Email"+Constants.LOGINFAILURE;
		}
		if(password.equals("")){
			return "Enter Password"+Constants.LOGINFAILURE;
			
		}
		return com.readALoud.utils.Constants.SUCCESS;
	}
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	public Date getLoginTime() {
		return loginTime;
	}
	
}
