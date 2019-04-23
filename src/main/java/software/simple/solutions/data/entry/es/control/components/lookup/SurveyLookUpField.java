package software.simple.solutions.data.entry.es.control.components.lookup;

import software.simple.solutions.data.entry.es.control.entities.Survey;
import software.simple.solutions.data.entry.es.control.web.view.SurveyView;
import software.simple.solutions.framework.core.components.LookUpField;

public class SurveyLookUpField extends LookUpField {

	private static final long serialVersionUID = 994848491488378790L;

	public SurveyLookUpField() {
		super();
		setEntityClass(Survey.class);
		setViewClass(SurveyView.class);
	}

}
