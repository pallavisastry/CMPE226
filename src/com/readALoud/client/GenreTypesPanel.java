package com.readALoud.client;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteTextField;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.handler.BookmarkablePageRequestHandler;
import org.apache.wicket.request.handler.PageProvider;
import org.apache.wicket.util.string.Strings;

import com.readALoud.entities.Genre;
import com.readALoud.services.UserDAO;


/**
 * 
 */
public class GenreTypesPanel extends Panel
{
    /**
     * Constructor
     */
    public GenreTypesPanel(String id)
    {
    	super(id);
        Form<Void> form = new Form<Void>(id);
        add(form);

        final AutoCompleteTextField<String> field = new AutoCompleteTextField<String>("genreType",
            new Model<String>(""))
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
        form.add(field);

        final Label label = new Label("selectedValue", field.getDefaultModel());
        label.setOutputMarkupId(true);
        form.add(label);

        field.add(new AjaxFormSubmitBehavior(form, "onchange")
        {
            @Override
            protected void onSubmit(AjaxRequestTarget target)
            {
                target.add(label);
            }

            @Override
            protected void onError(AjaxRequestTarget target)
            {
            }
        });
    }
}


