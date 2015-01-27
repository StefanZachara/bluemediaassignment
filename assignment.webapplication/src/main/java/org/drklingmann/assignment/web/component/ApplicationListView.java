package org.drklingmann.assignment.web.component;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.drklingmann.assignment.domain.entities.ApplicationEntity;
import org.drklingmann.assignment.web.view.ApplicationPage;

import com.googlecode.wicket.jquery.ui.form.button.Button;


public class ApplicationListView extends ListView<ApplicationEntity> {

	private static final long serialVersionUID = -2340984929583551212L;

	public ApplicationListView(String id, List<? extends ApplicationEntity> list) {
		super(id, list);
	}

	@Override
	protected void populateItem(ListItem<ApplicationEntity> item) {
		final ApplicationEntity app = item.getModelObject();
		item.add(new Label("name",app.getName()));
		item.add(new Label("state",app.getState().getName()));
		item.add(new WorkOnThisApplication("selectApp", app));
		
	}
	
	private class WorkOnThisApplication extends Form<ApplicationEntity> {

		private static final long serialVersionUID = -458996296485110127L;
		private ApplicationEntity app;
		
		public WorkOnThisApplication(String id, ApplicationEntity app) {
			super(id);
			add(new Button("submit", Model.of("Details")));
			this.app = app;
		}
		@Override
		protected void onSubmit() {
			setResponsePage(new ApplicationPage(app));
		}
		
	}

}
