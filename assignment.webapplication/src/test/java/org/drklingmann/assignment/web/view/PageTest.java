package org.drklingmann.assignment.web.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.drklingmann.assignment.domain.entities.ApplicationEntity;
import org.drklingmann.assignment.domain.entities.History;
import org.drklingmann.assignment.domain.entities.State;
import org.drklingmann.assignment.web.Application;
import org.drklingmann.assignment.web.component.ApplicationListPanel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:WEB-INF/applicationContext.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class PageTest {
	
	private WicketTester tester;
	private List<ApplicationEntity> appsList = new ArrayList<ApplicationEntity>();
	private ApplicationEntity aEntity = new ApplicationEntity();
	private List<History> historyList = new ArrayList<History>();
	private History history = new History();
	private FormTester formTester;

	
	@Autowired
	private ApplicationContext ctx;
	
	@Autowired
	private Application application;
	
	@Before
	public void setUp() {
		tester = new WicketTester(application);
		
		aEntity.setContent("content");
		aEntity.setName("name");
		aEntity.setRejectionReason("reason");
		history.setDate(new Date());
		history.setState(State.ACCEPTED);
		history.setApplication(aEntity);
		historyList.add(history);
		aEntity.setHistory(historyList);
		appsList.add(aEntity);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void renderListPageTest() {

		
		
		tester.startPage(ListPage.class);
		tester.assertRenderedPage(ListPage.class);
		
		tester.clickLink("newApplication");
		tester.assertRenderedPage(ApplicationPage.class);
		formTester = tester.newFormTester("appForm", true);
		tester.executeAjaxEvent("appForm:submitButton", "onclick");
		tester.assertRenderedPage(ApplicationPage.class);
		formTester = tester.newFormTester("appForm", true);
		formTester.setValue("name", "name");
		formTester.setValue("content", "content");
		tester.executeAjaxEvent("appForm:submitButton", "onclick");
		tester.assertRenderedPage(ListPage.class);
		
		tester.clickLink("accepted");
		tester.assertRenderedPage(ListPage.class);
		
		formTester = tester.newFormTester("filterForm", true);
		formTester.select("state", 2);
		formTester.setValue("name", " aa ");
		formTester.submit();
		formTester = tester.newFormTester("filterForm", false);
		formTester.select("state", 2);
		formTester.submit();
		tester.assertRenderedPage(ListPage.class);
		for (State state : State.values()) {
			aEntity.setState(state);
			tester.startPage(new ListPage(appsList));
			tester.assertRenderedPage(ListPage.class);
			tester.startPage(new ApplicationPage(aEntity));
			tester.assertRenderedPage(ApplicationPage.class);
			if(state.equals(State.ACCEPTED) || state.equals(State.CREATED) || state.equals(State.VERIFIED)) {
				formTester = tester.newFormTester("appForm");
				formTester.setValue("name", "name");
				formTester.setValue("content", "content");
				tester.executeAjaxEvent("appForm:submitButton", "onclick");
				tester.assertRenderedPage(ListPage.class);
			}
		}
		for (State state : State.values()) {
			aEntity.setState(state);
			tester.startPage(new ApplicationPage(aEntity));
			tester.assertRenderedPage(ApplicationPage.class);
			if(state.equals(State.ACCEPTED) || state.equals(State.CREATED) || state.equals(State.VERIFIED)) {
				formTester = tester.newFormTester("appForm");
				formTester.setValue("rejectionReason", "rejectionReason");
				formTester.setValue("name", "name");
				formTester.setValue("content", "content");
				tester.executeAjaxEvent("appForm:rejectButton", "onclick");
				tester.assertRenderedPage(ListPage.class);
			}
		}
		ApplicationListPanel listView = new ApplicationListPanel("listPanel", appsList);
		tester.startComponentInPage(listView);
		formTester = tester.newFormTester("listPanel:data:appsList:0:selectApp");
		formTester.submit();
		tester.assertRenderedPage(ApplicationPage.class);
	}
}
