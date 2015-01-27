package org.drklingmann.assignment.web.view;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.drklingmann.assignment.domain.entities.ApplicationEntity;
import org.drklingmann.assignment.domain.entities.State;
import org.drklingmann.assignment.service.ApplicationService;
import org.drklingmann.assignment.web.BasePage;
import org.drklingmann.assignment.web.component.HistoryListPanel;

import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;

public class ApplicationPage extends BasePage {
	private static final long serialVersionUID = 1L;
	@SpringBean
	private ApplicationService applicationService;

	public ApplicationPage(final ApplicationEntity application) {
		super();
		
		Form<ApplicationEntity> appForm = new Form<ApplicationEntity>("appForm", new CompoundPropertyModel<ApplicationEntity>(application));

		final FeedbackPanel feedback = new JQueryFeedbackPanel("feedback");
		appForm.add(feedback.setOutputMarkupId(true));
		
		final TextField<String> nameTextField = new TextField<String>("name");
		if(!applicationService.isNew(application)) {
			nameTextField.setEnabled(false);
		} else {
			nameTextField.setRequired(true);
		}
		appForm.add(nameTextField);
		
		final TextField<String> contentTextField = new TextField<String>("content");
		if(!applicationService.isContentUpdateable(application)) {
			contentTextField.setEnabled(false);
		} else {
			contentTextField.setRequired(true);
		}
		appForm.add(contentTextField);
		
		final TextField<String> reasonTextField = new TextField<String>("rejectionReason");
		if(!applicationService.isRejectable(application)) {
			reasonTextField.setEnabled(false);
		} else {
			reasonTextField.setRequired(true);
		}
		if(!applicationService.isReasonVisable(application)) {
			reasonTextField.setVisible(false);
		}
		appForm.add(reasonTextField);
		
		AjaxButton submitButton = new AjaxButton("submitButton", Model.of(applicationService.submitButtonLabel(application)), appForm) {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void onError(AjaxRequestTarget target, Form<?> form) {
				target.add(feedback);
			}
			@Override
			public void onSubmit(AjaxRequestTarget target, Form<?> form) {
				applicationService.submitApplication(application);
				setResponsePage(new ListPage(State.CREATED));
				target.add(feedback);
				super.onSubmit();
			}
		};
		if(!applicationService.isSubmitVisable(application)) {
			submitButton.setVisible(false);
			submitButton.setEnabled(false);
		}
		appForm.add(submitButton);
		
		AjaxButton rejectButton = new AjaxButton("rejectButton", Model.of(applicationService.rejectButtonLabel(application)), appForm) {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void onError(AjaxRequestTarget target, Form<?> form) {
				target.add(feedback);
			}
			@Override
			public void onSubmit(AjaxRequestTarget target, Form<?> form) {
				applicationService.rejectApplication(application);
				setResponsePage(new ListPage(State.CREATED));
				target.add(feedback);
				super.onSubmit();
			}
		};
		if(!applicationService.isRejectVisable(application)) {
			rejectButton.setVisible(false);
			rejectButton.setEnabled(false);
		}
		appForm.add(rejectButton);
		
		HistoryListPanel historyTable = new HistoryListPanel("historyList", application.getHistory());
		add(appForm);
		add(historyTable);
		initGui();
	}
	private void initGui() {

	}


}
