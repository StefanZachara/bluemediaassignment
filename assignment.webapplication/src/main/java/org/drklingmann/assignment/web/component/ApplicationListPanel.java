package org.drklingmann.assignment.web.component;

import java.util.List;

import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.drklingmann.assignment.domain.entities.ApplicationEntity;

public class ApplicationListPanel extends Panel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7885034416828739877L;
	
	private ApplicationListView listview;

	public ApplicationListPanel(String id, List<ApplicationEntity> application) {
		super(id);
		
		WebMarkupContainer datacontainer = new WebMarkupContainer("data");
		datacontainer.setOutputMarkupId(true);
		add(datacontainer);

		
		listview = new ApplicationListView("appsList", application);
				
		datacontainer.add(listview);
	    datacontainer.add(new AjaxPagingNavigator("navigator", listview));
	    datacontainer.setVersioned(false);
	}

}
