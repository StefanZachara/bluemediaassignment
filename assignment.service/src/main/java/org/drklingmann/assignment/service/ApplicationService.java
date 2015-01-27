package org.drklingmann.assignment.service;

import java.util.List;

import org.drklingmann.assignment.domain.entities.ApplicationEntity;
import org.drklingmann.assignment.domain.entities.State;

public interface ApplicationService {

	void submitApplication(ApplicationEntity application);

	List<ApplicationEntity> getApplications(State state);

	List<ApplicationEntity> getApplications(State state, String name);

	boolean isRejectable(ApplicationEntity application);

	boolean isNew(ApplicationEntity application);

	boolean isContentUpdateable(ApplicationEntity application);

	boolean isReasonVisable(ApplicationEntity application);

	String submitButtonLabel(ApplicationEntity application);

	boolean isSubmitVisable(ApplicationEntity application);

	String rejectButtonLabel(ApplicationEntity application);

	boolean isRejectVisable(ApplicationEntity application);

	void rejectApplication(ApplicationEntity application);

}
