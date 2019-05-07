package software.simple.solutions.data.entry.es.control.rest.model;

import java.io.Serializable;

import software.simple.solutions.data.entry.es.control.entities.SurveySection;

public class SurveySectionModel implements Serializable {

	private Long id;
	private Boolean active;
	private Long surveyId;
	private String name;
	private String description;
	private Boolean enableApplicability;

	public SurveySectionModel() {
		super();
	}

	public SurveySectionModel(SurveySection surveySection) {
		this();
		this.active = surveySection.getActive();
		this.description = surveySection.getDescription();
		this.enableApplicability = surveySection.getEnableApplicability();
		this.id = surveySection.getId();
		this.name = surveySection.getName();
		this.surveyId = surveySection.getSurvey().getId();
	}

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

	public Long getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(Long surveyId) {
		this.surveyId = surveyId;
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

	public Boolean getEnableApplicability() {
		return enableApplicability;
	}

	public void setEnableApplicability(Boolean enableApplicability) {
		this.enableApplicability = enableApplicability;
	}

}
