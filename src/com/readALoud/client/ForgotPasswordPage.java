package com.readALoud.client;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

import com.readALoud.beans.LoginBean;
//import com.readALoud.client.LoginPage.LoginForm;
import com.readALoud.services.UserDAO;
import com.readALoud.utils.Constants;

public class ForgotPasswordPage extends WebPage {

	LoginBean lBean=new LoginBean();
	CompoundPropertyModel lBeanModel;
	
	public ForgotPasswordPage(){
		
		lBeanModel=new CompoundPropertyModel(lBean);
		Form forgotPasswordForm=new ForgotPasswordForm("forgotForm",lBeanModel);
		RequiredTextField email=new RequiredTextField("email");
		
		FeedbackPanel feedback=new FeedbackPanel("feedback");
		feedback.setOutputMarkupId(true);
		
		forgotPasswordForm.add(email);
		add(forgotPasswordForm);
		add(feedback);
	}
	class ForgotPasswordForm extends Form{
	
		ForgotPasswordForm(String id,IModel model){
			super(id,model);
		}
		
		@Override
		public void onSubmit(){
			
			String result = null;
			UserDAO rDao=new UserDAO();
			try{
				result=rDao.getForgottenPassword(lBean);
				if(result.equals(Constants.UNREGISTEREDUSER)){
					info(Constants.UNREGISTEREDUSER);
					setResponsePage(LoginPage.class);
				}
				else
					info("Password>>"+result);
			}
			catch(Exception e) {
				System.out.println("exception in wicket-LoginPage");
				e.printStackTrace();
			}

		}
	}
}
