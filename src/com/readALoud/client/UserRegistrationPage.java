package com.readALoud.client;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.convert.IConverter;

import com.readALoud.beans.UserRegistrationBean;
import com.readALoud.services.UserDAO;
import com.readALoud.utils.Constants;

public class UserRegistrationPage extends WebPage
{
	private static final long serialVersionUID = 1L;
	
	UserRegistrationBean urb=new UserRegistrationBean();
		
	@SuppressWarnings("rawtypes")
	BookmarkablePageLink loginLink;
		
   	@SuppressWarnings({ "rawtypes", "unchecked", "serial" })
	public UserRegistrationPage(){

		System.err.println("INSIDE UserRegistrationPage");
		CompoundPropertyModel userRegistrationModel=new CompoundPropertyModel(urb);
		Form regForm=new UserRegistrationForm("regForm",userRegistrationModel);
		
		FeedbackPanel feedback=new FeedbackPanel("feedback");
		feedback.setOutputMarkupId(true);
		loginLink=new BookmarkablePageLink("loginLink",LoginPage.class);
		
		RequiredTextField firstName=new RequiredTextField("firstName");
		RequiredTextField lastName=new RequiredTextField("lastName");
		RequiredTextField email=new RequiredTextField("email");
		PasswordTextField password=new PasswordTextField("password");
		PasswordTextField repeatPassword=new PasswordTextField("repeatPassword");
		RequiredTextField userName=new RequiredTextField("userName");
		RequiredTextField date=new RequiredTextField("date",new PropertyModel(urb,"dateOfBirth")){
			@SuppressWarnings("unused")
			public IConverter getConverter(){
				return new IConverter() {
					public Object convert(Object value,Class c){
						try{
							if(c==String.class){
								return new SimpleDateFormat("dd-MM-yyyy").format((Date)value);
							}
							else
								return new SimpleDateFormat("dd-MM-yyyy").parse((String)value);
						}	catch(ParseException e){
							e.printStackTrace();
						}
						return c;
					}
					public Locale getLocale(){
						return getLocale();
					}
					public void setLocale(Locale locale){
						
					}
					@Override
					public Object convertToObject(String value, Locale locale) {

						return null;
					}
					@Override
					public String convertToString(Object value, Locale locale) {

						return null;
					}
				};
			}
		};
		
		regForm.add(firstName);
		regForm.add(lastName);
		regForm.add(email);
		regForm.add(password);
		regForm.add(repeatPassword);
		regForm.add(userName);
		regForm.add(date);
		regForm.add(new EqualPasswordInputValidator(password,repeatPassword));
		regForm.add(loginLink);
		add(regForm);
		add(feedback);	

	}

	@SuppressWarnings("rawtypes")
	class UserRegistrationForm extends Form{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		@SuppressWarnings("unchecked")
		public UserRegistrationForm(String id,IModel model) {
			super(id,model);
		}
		@Override
		public void onSubmit(){
			String result = null;
			System.out.println("inside onSubmit");
			loginLink.setVisible(false);
			//Calling the DAO's
			UserDAO rDao=new UserDAO();
			try {
				result=rDao.register(urb);
				
				if(result.equals(Constants.REGRISTRATIONSUCCESSFUL)){
					info("Registered:"+result+"..Please click on login");//ask user to go back/provide link to login class				
					loginLink.setVisible(true); 
				}
				else if(result.equals(Constants.REGISTEREDUSER)){
					info("Already Registered"+result+"..directing to login page");//ask user to go back/provide link to login class				
					loginLink.setVisible(true);
				}
			} catch (Exception e) {

				System.out.println("exception in wicket-userREgPage");
				e.printStackTrace();
			}
		}
	}	
}
