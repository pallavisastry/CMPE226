package test.readALoud.client;

import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;

import com.readALoud.client.LoginPage;
import com.readALoud.client.UserRegistrationApplication;
import com.readALoud.client.UserRegistrationPage;

public class UserRegistrationPageTest {

	WicketTester tester;
	
	@Before
	public void setUp(){
		tester=new WicketTester(new UserRegistrationApplication());
	}
	
	@Test
	public void testUserRegistrationPageBasicRender() {
		
		tester.startPage(UserRegistrationPage.class);
		tester.assertRenderedPage(UserRegistrationPage.class);
	
	}
	@Test
	public void testUserRegistrationPageComponents(){
	
		tester.startPage(UserRegistrationPage.class);
		tester.assertRenderedPage(UserRegistrationPage.class);
		
		tester.assertComponent("regForm:firstName",RequiredTextField.class);
		tester.assertComponent("regForm:lastName",RequiredTextField.class);
		tester.assertComponent("regForm:email",RequiredTextField.class);
		tester.assertComponent("regForm:password",PasswordTextField.class);
		tester.assertComponent("regForm:date",RequiredTextField.class);//??
	}
	@Test
	public void testUserRegistrationFormSubmit(){
		
		tester.startPage(UserRegistrationPage.class);
		tester.assertRenderedPage(UserRegistrationPage.class);
		
		FormTester ft=tester.newFormTester("regForm");
		
		ft.setValue("firstName","mickey");
		ft.setValue("lastName","mouse");
		ft.setValue("email","ex@gmail.com");
		ft.setValue("password","mouse");
		ft.setValue("repeatPassword","mouse");
		ft.setValue("date","09/09/2012");
		
		ft.submit();
		
		tester.assertRenderedPage(UserRegistrationPage.class);
	}
	@Test
	public void testOnLoginClickAction(){
		
		tester.startPage(UserRegistrationPage.class);
		tester.assertRenderedPage(UserRegistrationPage.class);
		PageParameters p=new PageParameters();
		tester.assertBookmarkablePageLink("regForm:loginLink",LoginPage.class,p);
		
		
	}

}
