package software.simple.solutions.data.entry.es.control.valueobjects;

import software.simple.solutions.data.entry.es.control.properties.SurveyProperty;
import software.simple.solutions.data.entry.es.control.properties.SurveyResponseProperty;
import software.simple.solutions.framework.core.annotations.FilterFieldProperty;
import software.simple.solutions.framework.core.pojo.StringInterval;
import software.simple.solutions.framework.core.properties.ApplicationUserProperty;
import software.simple.solutions.framework.core.valueobjects.SuperVO;

public class SurveyResponseVO extends SuperVO {

	private static final long serialVersionUID = -2008276944089219114L;

	private Long id;
	private Boolean active;
	private String formName;
	private Long surveyId;

	@FilterFieldProperty(fieldProperty = SurveyProperty.NAME)
	private StringInterval surveyNameInterval;
	@FilterFieldProperty(fieldProperty = SurveyProperty.DESCRIPTION)
	private StringInterval surveyDescriptionInterval;
	@FilterFieldProperty(fieldProperty = SurveyResponseProperty.FORM_NAME)
	private StringInterval formNameInterval;
	@FilterFieldProperty(fieldProperty = ApplicationUserProperty.USERNAME)
	private StringInterval userNameInInterval;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public Long getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(Long surveyId) {
		this.surveyId = surveyId;
	}

	public StringInterval getSurveyNameInterval() {
		return surveyNameInterval;
	}

	public void setSurveyNameInterval(StringInterval surveyNameInterval) {
		this.surveyNameInterval = surveyNameInterval;
	}

	public StringInterval getSurveyDescriptionInterval() {
		return surveyDescriptionInterval;
	}

	public void setSurveyDescriptionInterval(StringInterval surveyDescriptionInterval) {
		this.surveyDescriptionInterval = surveyDescriptionInterval;
	}

	public StringInterval getFormNameInterval() {
		return formNameInterval;
	}

	public void setFormNameInterval(StringInterval formNameInterval) {
		this.formNameInterval = formNameInterval;
	}

	public StringInterval getUserNameInInterval() {
		return userNameInInterval;
	}

	public void setUserNameInInterval(StringInterval userNameInInterval) {
		this.userNameInInterval = userNameInInterval;
	}

}
