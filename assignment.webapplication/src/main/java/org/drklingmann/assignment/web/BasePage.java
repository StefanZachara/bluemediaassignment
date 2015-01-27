package org.drklingmann.assignment.web;

import java.io.Serializable;
import java.util.Arrays;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.drklingmann.assignment.domain.entities.ApplicationEntity;
import org.drklingmann.assignment.domain.entities.State;
import org.drklingmann.assignment.service.ApplicationService;
import org.drklingmann.assignment.web.view.ApplicationPage;
import org.drklingmann.assignment.web.view.ListPage;

public abstract class BasePage extends WebPage {

	
	@SpringBean
	private ApplicationService applicationService;

	private static final long serialVersionUID = -2653420841066715010L;

	public BasePage() {
		super();
		createBasePage();
	}
	
	private void createBasePage() {
		add(new AjaxLink<Void>("newApplication") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				setResponsePage(new ApplicationPage(new ApplicationEntity()));
			}
			
		});
		Form<FilterResults> filterForm = new Form<FilterResults>("filterForm", new CompoundPropertyModel<FilterResults>(new FilterResults())) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit() {
				FilterResults filter = getModelObject();
				setResponsePage(new ListPage(applicationService.getApplications(filter.state,filter.name)));
				super.onSubmit();
			}
		};
		DropDownChoice<State> state = new DropDownChoice<State>("state", Arrays.asList(State.values()));
		TextField<String> name = new TextField<String>("name");
		filterForm.add(state);
		filterForm.add(name);
		add(filterForm);
		add(new ApplicationListLink("accepted",State.ACCEPTED));
		add(new ApplicationListLink("created",State.CREATED));
		add(new ApplicationListLink("deleted",State.DELETED));
		add(new ApplicationListLink("published",State.PUBLISHED));
		add(new ApplicationListLink("rejected",State.REJECTED));
		add(new ApplicationListLink("verified",State.VERIFIED));
	}
	
	private class FilterResults implements Serializable {
		private static final long serialVersionUID = 1L;
		private String name = "";
		private State state = State.CREATED;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public State getState() {
			return state;
		}
		public void setState(State state) {
			this.state = state;
		}
	}
	
	private class ApplicationListLink extends AjaxLink<Void>{
		private static final long serialVersionUID = 6108146687580321551L;
		State state;
		public ApplicationListLink(String name, State state) {
			super(name);
			this.state = state;
		}
		@Override
		public void onClick(AjaxRequestTarget target) {
			setResponsePage(new ListPage(state));	
			
		}
		
	}
	
	public BasePage(PageParameters parameters) {
		super(parameters);
	}
	
}
