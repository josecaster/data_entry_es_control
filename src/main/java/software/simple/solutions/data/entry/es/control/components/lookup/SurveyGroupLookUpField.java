package software.simple.solutions.data.entry.es.control.components.lookup;

import software.simple.solutions.data.entry.es.control.entities.SurveyGroup;
import software.simple.solutions.data.entry.es.control.web.view.SurveyGroupView;
import software.simple.solutions.framework.core.components.LookUpField;

public class SurveyGroupLookUpField extends LookUpField {

	private static final long serialVersionUID = 994848491488378790L;

	public SurveyGroupLookUpField() {
		super();
		setEntityClass(SurveyGroup.class);
		setViewClass(SurveyGroupView.class);
	}

}
