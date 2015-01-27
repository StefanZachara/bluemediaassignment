package org.drklingmann.assignment.web.component;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.drklingmann.assignment.domain.entities.History;

public class HistoryListPanel extends Panel {
	
	private static final long serialVersionUID = 4508230950017484264L;
	
	private HistoryListView list;

	public HistoryListPanel(String id, List<History> history) {
		super(id);
		
		list = new HistoryListView("appsList", history);
				
		add(list);
	}
	
	public class HistoryListView extends ListView<History> {

		private static final long serialVersionUID = -2340984929583551212L;

		public HistoryListView(String id, List<? extends History> list) {
			super(id, list);
		}

		@Override
		protected void populateItem(ListItem<History> item) {
			final History app = item.getModelObject();
			item.add(new Label("state",app.getState().getName()));
			item.add(new Label("date",app.getDate()));
			
		}
		


	}
}
