package test.readALoud.client;

import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;

import com.readALoud.client.FAQ;
import com.readALoud.client.ForgotPasswordPage;
import com.readALoud.client.LoginApplication;
import com.readALoud.client.LoginPage;
import com.readALoud.client.UserRegistrationPage;


public class LoginPageTest {

	WicketTester tester;
	
	@Before
	public void setUp(){
		tester=new WicketTester(new LoginApplication());
	}
	
	@Test
	public void testLoginPageBasicRender() {
		
		tester.startPage(LoginPage.class);
		tester.assertRenderedPage(LoginPage.class);
	
	}
	@Test
	public void testLoginPageComponents(){
	
		tester.startPage(LoginPage.class);
		tester.assertRenderedPage(LoginPage.class);
		
		tester.assertComponent("loginForm:email",RequiredTextField.class);
		tester.assertComponent("loginForm:password",PasswordTextField.class);
		tester.assertComponent("loginForm:fpLink",BookmarkablePageLink.class);
		tester.assertComponent("loginForm:regLink",BookmarkablePageLink.class);
		tester.assertComponent("loginForm:faq",BookmarkablePageLink.class);
	}
	
	@Test
	public void testOnForgotPasswordClickAction(){
		
		tester.startPage(LoginPage.class);
		tester.assertRenderedPage(LoginPage.class);
		tester.assertBookmarkablePageLink("loginForm:fpLink", ForgotPasswordPage.class, new PageParameters());
		
		
	}
	
	@Test
	public void testOnRegisterClickAction(){
		
		tester.startPage(LoginPage.class);
		tester.assertRenderedPage(LoginPage.class);
		tester.assertBookmarkablePageLink("loginForm:regLink", UserRegistrationPage.class, new PageParameters());
	}
	
	@Test
	public void testOnFAQClickAction(){
		tester.startPage(LoginPage.class);
		tester.assertRenderedPage(LoginPage.class);
		tester.assertBookmarkablePageLink("loginForm:faq", FAQ.class, new PageParameters());
		
	}

}
