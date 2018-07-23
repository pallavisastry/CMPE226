package com.readALoud.client;

import org.apache.wicket.Session;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.readALoud.beans.LoginBean;
import com.readALoud.entities.User;
import com.readALoud.services.UserDAO;
import com.readALoud.utils.Constants;


public class LoginPage extends WebPage 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9217066109082502384L;

	LoginBean lBean=new LoginBean();
	CompoundPropertyModel lBeanModel;


	@SuppressWarnings({ "rawtypes", "unchecked" })
	public LoginPage(){
		
		super();
		
		((MySession)Session.get()).bind();
		lBeanModel=new CompoundPropertyModel(lBean);
		Form loginForm=new LoginForm("loginForm",lBeanModel);

		RequiredTextField email=new RequiredTextField("email");
		PasswordTextField password=new PasswordTextField("password");

		BookmarkablePageLink fpLink=new BookmarkablePageLink("fpLink",ForgotPasswordPage.class);
		BookmarkablePageLink regLink=new BookmarkablePageLink("regLink",UserRegistrationPage.class);
		BookmarkablePageLink faq=new BookmarkablePageLink("faq",FAQ.class);
		
		FeedbackPanel feedback=new FeedbackPanel("feedback");
		feedback.setOutputMarkupId(true);

		loginForm.add(email);
		loginForm.add(password);
		loginForm.add(fpLink);
		loginForm.add(regLink);
		loginForm.add(faq);
		add(loginForm);
		add(feedback);
	}
	class LoginForm extends Form{

		/**
		 * 
		 */
		private static final long serialVersionUID = -6576128971172557803L;
		@SuppressWarnings({ "unchecked", "rawtypes" })
		LoginForm(String formId,IModel model){
			super(formId,model);
		}

		@Override
		public void onSubmit(){

			String result = null;
			UserDAO rDao=new UserDAO();

			try{				
				result=rDao.authenticate(lBean);	//authenticating user;

				if(result.equals(Constants.UNREGISTEREDUSER)){
					setResponsePage(UserRegistrationPage.class); //if user unauthenticated, send to reg page
				}				
				else if(result.equals(Constants.LOGGEDUSER)){

					String utype=rDao.getUserType(lBean);
					((MySession)Session.get()).setEmail(lBean.getEmail());
					
					User user = rDao.getUserByEmail(lBean.getEmail());
					((MySession)Session.get()).setSessionUser(user);
					
					String email = ((MySession)Session.get()).getEmail();
					if(utype.equalsIgnoreCase("Author")){
					
						PageParameters p=new PageParameters();
						p.add("email",lBean.getEmail());
						p.add("password", lBean.getPassword());
						setResponsePage(new AuthorProfilePage(p));
					}
					else{  //going to user's page
						PageParameters p=new PageParameters();
						p.add("email",lBean.getEmail());
						p.add("password", lBean.getPassword());
						setResponsePage(new SuccessPage(p));
					}

				}
			}catch(Exception e) {
				System.out.println("exception in wicket-LoginPage");
				e.printStackTrace();
			}			
		}		
	}
}

