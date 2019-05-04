package software.simple.solutions.data.entry.es.control.components.lookup;

import software.simple.solutions.data.entry.es.control.entities.SurveySection;
import software.simple.solutions.data.entry.es.control.web.view.SurveySectionView;
import software.simple.solutions.framework.core.components.LookUpField;

public class SurveySectionLookUpField extends LookUpField {

	private static final long serialVersionUID = 994848491488378790L;

	public SurveySectionLookUpField() {
		super();
		setEntityClass(SurveySection.class);
		setViewClass(SurveySectionView.class);
	}

}
