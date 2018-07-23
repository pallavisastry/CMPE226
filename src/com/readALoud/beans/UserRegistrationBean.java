package com.readALoud.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*Bean that gets all the values from wicket*/
public class UserRegistrationBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String firstName="";
	private String lastName="";
	private String email="";
	private String password="";
	private String repeatPassword="";
	private String userName="";
	//private String address=""; //should be a class
	private Date dateOfBirth;

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getLastName() {
		return lastName;
	}
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
	public void setRepeatPassword(String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}
	public String getRepeatPassword() {
		return repeatPassword;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserName() {
		return userName;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public Date getDateOfBirth() {
		Date date=new Date();
		date=dateOfBirth;
		return date;
	}
	public String validate(){
		String result = "";
		System.out.println("inside validate() of RegBean");
		if(firstName==null || firstName.equals("")){
			result += "Enter First Name \n";
		}
		if(lastName==null || lastName.equals("")){
			result += "Enter Last Name \n";
		}
		if(userName==null || userName.equals("")){
			result += "Enter User Name \n";
		}
		EmailValidator ev = new EmailValidator();
		if(email==null || email.equals("")){
			result += "Enter Email \n";
		}else if(!ev.validate(email)) {
			result += "Enter a Valid Email \n";
		}
		if(password==null || password.equals("")){
			result += "Enter Password \n";
		}else if(!(password.equals(repeatPassword))){
			result += "Password do not match.Please enter again \n";
		}
		System.out.println(dateOfBirth + ":DOB");
		if(dateOfBirth==null) {
			result += "Enter Date Of Birth \n";
		}
		if(result.equals("")) {
			return com.readALoud.utils.Constants.SUCCESS;
		}
		return result;
	}
	
}

class EmailValidator{
	 
	  private Pattern pattern;
	  private Matcher matcher;

	  private static final String EMAIL_PATTERN = 
                 "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@"+
                 "[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	  public EmailValidator(){
		  pattern = Pattern.compile(EMAIL_PATTERN);
	  }

	  /**
	   * Validate hex with regular expression
	   * @param hex hex for validation
	   * @return true valid hex, false invalid hex
	   */
	  public boolean validate(final String hex){

		  matcher = pattern.matcher(hex);
		  return matcher.matches();

	  }
}
