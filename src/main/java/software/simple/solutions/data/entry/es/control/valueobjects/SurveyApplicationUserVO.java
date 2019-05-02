package software.simple.solutions.data.entry.es.control.valueobjects;

import software.simple.solutions.data.entry.es.control.properties.SurveyProperty;
import software.simple.solutions.framework.core.annotations.FilterFieldProperty;
import software.simple.solutions.framework.core.properties.ApplicationUserProperty;
import software.simple.solutions.framework.core.valueobjects.SuperVO;

public class SurveyApplicationUserVO extends SuperVO {

	private static final long serialVersionUID = -2008276944089219114L;

	private Long id;
	private Boolean active;

	@FilterFieldProperty(fieldProperty = SurveyProperty.ID)
	private Long surveyId;
	@FilterFieldProperty(fieldProperty = ApplicationUserProperty.ID)
	private Long applicationUserId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public Boolean getActive() {
		return active;
	}

	@Override
	public void setActive(Boolean active) {
		this.active = active;
	}

	public Long getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(Long surveyId) {
		this.surveyId = surveyId;
	}

	public Long getApplicationUserId() {
		return applicationUserId;
	}

	public void setApplicationUserId(Long applicationUserId) {
		this.applicationUserId = applicationUserId;
	}

}
