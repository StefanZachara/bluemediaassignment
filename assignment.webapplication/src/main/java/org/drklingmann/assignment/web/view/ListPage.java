package org.drklingmann.assignment.web.view;

import java.util.List;

import org.apache.wicket.spring.injection.annot.SpringBean;
import org.drklingmann.assignment.domain.entities.ApplicationEntity;
import org.drklingmann.assignment.domain.entities.State;
import org.drklingmann.assignment.service.ApplicationService;
import org.drklingmann.assignment.web.BasePage;
import org.drklingmann.assignment.web.component.ApplicationListPanel;

public class ListPage extends BasePage {
	
	private static final long serialVersionUID = 1L;
	
	@SpringBean
	private ApplicationService applicationService;

	private ApplicationListPanel appsTable;
	

	public ListPage() {
		this(State.ACCEPTED);
	}
	public ListPage(List<ApplicationEntity> appsList) {
		super();
		getListPanel(appsList);
		initGui();
	}
	public ListPage(State state) {
		super();
		List<ApplicationEntity> appsList = applicationService.getApplications(state);
		getListPanel(appsList);
		initGui();
	}
	private void initGui() {

		appsTable.setOutputMarkupId(true);
		add(appsTable);

	}

	protected ApplicationListPanel getListPanel(List<ApplicationEntity> list) {
		appsTable = new ApplicationListPanel("appsList", list);
		return appsTable;
	}

}
