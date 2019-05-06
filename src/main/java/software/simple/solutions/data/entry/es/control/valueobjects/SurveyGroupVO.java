package software.simple.solutions.data.entry.es.control.valueobjects;

import software.simple.solutions.data.entry.es.control.properties.SurveyGroupProperty;
import software.simple.solutions.data.entry.es.control.properties.SurveyProperty;
import software.simple.solutions.framework.core.annotations.FilterFieldProperty;
import software.simple.solutions.framework.core.pojo.StringInterval;
import software.simple.solutions.framework.core.valueobjects.SuperVO;

public class SurveyGroupVO extends SuperVO {

	private static final long serialVersionUID = -2008276944089219114L;

	@FilterFieldProperty(fieldProperty = SurveyGroupProperty.ID)
	private Long id;
	private Boolean active;
	private String name;
	private String description;

	@FilterFieldProperty(fieldProperty = SurveyProperty.ID)
	private Long surveyId;
	@FilterFieldProperty(fieldProperty = SurveyGroupProperty.NAME)
	private StringInterval nameInterval;
	@FilterFieldProperty(fieldProperty = SurveyGroupProperty.DESCRIPTION)
	private StringInterval descriptionInterval;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public StringInterval getNameInterval() {
		return nameInterval;
	}

	public void setNameInterval(StringInterval nameInterval) {
		this.nameInterval = nameInterval;
	}

	public StringInterval getDescriptionInterval() {
		return descriptionInterval;
	}

	public void setDescriptionInterval(StringInterval descriptionInterval) {
		this.descriptionInterval = descriptionInterval;
	}

	public Long getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(Long surveyId) {
		this.surveyId = surveyId;
	}

}
