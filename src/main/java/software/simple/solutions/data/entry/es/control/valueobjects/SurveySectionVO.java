package software.simple.solutions.data.entry.es.control.valueobjects;

import software.simple.solutions.data.entry.es.control.properties.SurveySectionProperty;
import software.simple.solutions.data.entry.es.control.properties.SurveyProperty;
import software.simple.solutions.framework.core.annotations.FilterFieldProperty;
import software.simple.solutions.framework.core.pojo.StringInterval;
import software.simple.solutions.framework.core.valueobjects.SuperVO;

public class SurveySectionVO extends SuperVO {

	private static final long serialVersionUID = -2008276944089219114L;

	private Long id;
	private Boolean active;
	private String name;
	private String description;

	@FilterFieldProperty(fieldProperty = SurveyProperty.ID)
	private Long surveyId;
	@FilterFieldProperty(fieldProperty = SurveySectionProperty.NAME)
	private StringInterval nameInterval;
	@FilterFieldProperty(fieldProperty = SurveySectionProperty.DESCRIPTION)
	private StringInterval descriptionInterval;
	@FilterFieldProperty(fieldProperty = SurveySectionProperty.PINNED)
	private Boolean pinned;
	@FilterFieldProperty(fieldProperty = SurveySectionProperty.ENABLE_APPLICABILITY)
	private Boolean enableApplicability;

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

	public Boolean getPinned() {
		return pinned;
	}

	public void setPinned(Boolean pinned) {
		this.pinned = pinned;
	}

	public Boolean getEnableApplicability() {
		return enableApplicability;
	}

	public void setEnableApplicability(Boolean enableApplicability) {
		this.enableApplicability = enableApplicability;
	}

}
