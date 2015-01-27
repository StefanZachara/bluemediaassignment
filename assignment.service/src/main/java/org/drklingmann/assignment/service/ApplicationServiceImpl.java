package org.drklingmann.assignment.service;

import java.util.Date;
import java.util.List;

import org.drklingmann.assignment.domain.entities.ApplicationEntity;
import org.drklingmann.assignment.domain.entities.History;
import org.drklingmann.assignment.domain.entities.State;
import org.drklingmann.assignment.domain.repository.ApplicationRepository;
import org.drklingmann.assignment.domain.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "applicationService")
@Transactional(rollbackFor = Exception.class)
public class ApplicationServiceImpl implements ApplicationService {

	@Autowired
	ApplicationRepository appRepo;
	
	@Autowired
	HistoryRepository histRepo;

	@Override
	public void submitApplication(ApplicationEntity application) {
		if(State.ACCEPTED.equals(application.getState())) {
			application.setState(State.PUBLISHED);
		} else if(State.VERIFIED.equals(application.getState())) {
			application.setState(State.ACCEPTED);
		} else if(State.CREATED.equals(application.getState())) {
			application.setState(State.VERIFIED);
		} else if(application.getState()==null) {
			application.setState(State.CREATED);
		} 
		saveApplication(application);
	}

	@Override
	public List<ApplicationEntity> getApplications(State state) {
		return appRepo.findAllByState(state);
	}

	@Override
	public List<ApplicationEntity> getApplications(State state, String name) {
		if (name!= null)
			return appRepo.findAllByStateAndName(state, "%" +name.trim() + "%");
		else
			return appRepo.findAllByState(state);
	}

	@Override
	public boolean isRejectable(ApplicationEntity application) {
		if(State.VERIFIED.equals(application.getState()) || State.ACCEPTED.equals(application.getState()))
			return true;
		return false;
	}

	@Override
	public boolean isNew(ApplicationEntity application) {
		if(application.getState()==null)
			return true;
		return false;
	}

	@Override
	public boolean isContentUpdateable(ApplicationEntity application) {
		if(State.VERIFIED.equals(application.getState()) || State.CREATED.equals(application.getState()))
			return true;
		return isNew(application);
	}

	@Override
	public boolean isReasonVisable(ApplicationEntity application) {
		if(State.REJECTED.equals(application.getState()))
			return true;
		return isRejectable(application);
	}

	@Override
	public String submitButtonLabel(ApplicationEntity application) {
		if(State.ACCEPTED.equals(application.getState())) {
			return "Publish";
		} else if(State.VERIFIED.equals(application.getState())) {
			return "Accept";
		} else if(State.CREATED.equals(application.getState())) {
			return "Verify";
		} else if(application.getState()==null) {
			return "Create";
		} 
		return "NOT_VALID";
	}

	@Override
	public boolean isSubmitVisable(ApplicationEntity application) {
		if(State.DELETED.equals(application.getState()) || State.REJECTED.equals(application.getState()) || State.PUBLISHED.equals(application.getState()))
			return false;
		return true;
	}

	@Override
	public boolean isRejectVisable(ApplicationEntity application) {
		if(State.CREATED.equals(application.getState()) || State.VERIFIED.equals(application.getState()) || State.ACCEPTED.equals(application.getState()))
			return true;
		return false;
	}

	@Override
	public String rejectButtonLabel(ApplicationEntity application) {
		if(State.ACCEPTED.equals(application.getState()) || State.VERIFIED.equals(application.getState())) {
			return "Reject";
		} else if(State.CREATED.equals(application.getState())) {
			return "Delete";
		}
		return "NOT_VALID";
	}

	@Override
	public void rejectApplication(ApplicationEntity application) {
		if(State.ACCEPTED.equals(application.getState()) || State.VERIFIED.equals(application.getState())) {
			application.setState(State.REJECTED);
		} else if(State.CREATED.equals(application.getState())) {
			application.setState(State.DELETED);
		} 
		saveApplication(application);
	}
	
	private void saveApplication(ApplicationEntity application) {
		History history = new History();
		history.setState(application.getState());
		history.setDate(new Date());
		history.setApplication(application);
		application.getHistory().add(history);
		appRepo.save(application);
	
	}
}
