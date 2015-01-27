package org.drklingmann.assignment.web.component;

import java.util.List;

import org.apache.wicket.markup.html.panel.Panel;
import org.drklingmann.assignment.domain.entities.ApplicationEntity;

public class ApplicationListPanel extends Panel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7885034416828739877L;
	
	private ApplicationListView list;

	public ApplicationListPanel(String id, List<ApplicationEntity> application) {
		super(id);
		
		list = new ApplicationListView("appsList", application);
				
		add(list);
	}
	
	public void setCardList(List<ApplicationEntity> applications) {
		list.setModelObject(applications);
	}

}
