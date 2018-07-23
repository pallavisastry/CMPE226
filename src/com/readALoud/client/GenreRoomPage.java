package com.readALoud.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteTextField;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;
import org.apache.wicket.util.string.Strings;

import com.readALoud.beans.GenreRoomBean;
import com.readALoud.beans.LoginBean;
import com.readALoud.beans.UserDetailsBean;
import com.readALoud.client.UserRegistrationPage.UserRegistrationForm;
import com.readALoud.entities.User;
import com.readALoud.services.UserDAO;
import com.readALoud.utils.Constants;
import com.readALoud.client.GenreTypesPanel;

public class GenreRoomPage extends BasePage 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2573393898225711819L;
	private String[] genreList = {"Art","Biography","Business","Chick-lit","Children's","Christian","Classics","Comics","Contemporary","Cookbooks","Crime","Ebooks","Fantasy","Fiction","Gay and Lesbian","Graphic novels","Historical fiction","History","Horror","Humor and Comedy","Manga","Memoir","Music","Mystery","Non-fiction","Paranormal","Philosophy","Poetry","Psychology","Religion","Romance","Science","Science fiction","Self help","Suspense","Spirituality","Sports","Thriller","Travel","Young-adult"};
	private String selectedGenreType = null;
	
	GenreRoomBean grb=new GenreRoomBean();
	CompoundPropertyModel GenreRoomModel;
	CompoundPropertyModel GenreTypeModel;
	
	
	User user = ((MySession)Session.get()).getSessionUser();
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public GenreRoomPage(){

		GenreRoomModel=new CompoundPropertyModel(grb);
		GenreTypeModel = new CompoundPropertyModel(grb);
		
		Form genreRoomForm=new GenreRoomForm("genreRoomForm",GenreRoomModel);
				
		Form genreTypeForm = new GenreTypeForm("genreTypeForm", GenreTypeModel);
		TextArea genreDesc=new TextArea("genreDesc");
		genreDesc.setType(String.class);
		genreDesc.setRequired(true);			
		
		RequiredTextField roomName=new RequiredTextField("roomName");
		roomName.setType(String.class);
		
		FeedbackPanel feedback=new FeedbackPanel("feedback");
		feedback.setOutputMarkupId(true);
		
		
		final AutoCompleteTextField<String> field = new AutoCompleteTextField<String>("genreType", new Model<String>(""))
	        {
	            @Override
	            protected Iterator<String> getChoices(String input)
	            {
	                if (Strings.isEmpty(input))
	                {
	                    List<String> emptyList = Collections.emptyList();
	                    return emptyList.iterator();
	                }

	                UserDAO r = new UserDAO();
	                List<String> choices = new ArrayList<String>(10);

	                List<String> genreTypesList = r.getGenreList();

	                for (final String genre : genreTypesList)
	                {
	                    if (genre.toUpperCase().startsWith(input.toUpperCase()))
	                    {
	                        choices.add(genre);
	                        if (choices.size() == 10)
	                        {
	                            break;
	                        }
	                    }
	                }

	                return choices.iterator();
	            }
	        };
	        genreTypeForm.add(field);

	        final Label label = new Label("selectedValue", field.getDefaultModel());
	        label.setOutputMarkupId(true);
	        genreTypeForm.add(label);

	        field.add(new AjaxFormSubmitBehavior(genreTypeForm, "onchange")
	        {
	            @Override
	            protected void onSubmit(AjaxRequestTarget target)
	            {
	            	selectedGenreType = label.getDefaultModelObjectAsString();
	            	grb.setGenreType(selectedGenreType);
	                target.add(label);
	                
	            }

	            @Override
	            protected void onError(AjaxRequestTarget target)
	            {
	            }
	        });

		genreRoomForm.add(genreDesc);
		genreRoomForm.add(roomName);
		add(genreRoomForm);
		add(genreTypeForm);
		add(new GenrePanel("genrePanel"));		
		add(feedback);		
	}
	
	
	
	class GenreRoomForm extends Form{
		
		GenreRoomForm(String id, IModel model){			
			super(id,model);			
		}
		@Override
		public void onSubmit(){
			UserDAO rDao=new UserDAO();			
			String result=rDao.createGenreRoom(user,grb);		
			info(result);
		}
	}
	
	
	class GenreTypeForm extends Form{
		
		
		public GenreTypeForm(String id, IModel model) {
			super(id);
		}
		
		@Override
		public void onSubmit(){
			
		}
	}

}
		


