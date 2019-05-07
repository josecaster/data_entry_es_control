package software.simple.solutions.data.entry.es.control.rest.model;

import java.io.Serializable;

import software.simple.solutions.data.entry.es.control.entities.SurveyGroup;

public class SurveyGroupModel implements Serializable {

	private Long id;
	private Boolean active;
	private Long surveyId;
	private String name;
	private String description;

	public SurveyGroupModel() {
		super();
	}

	public SurveyGroupModel(SurveyGroup surveyGroup) {
		this();
		this.active = surveyGroup.getActive();
		this.description = surveyGroup.getDescription();
		this.id = surveyGroup.getId();
		this.name = surveyGroup.getName();
		this.surveyId = surveyGroup.getSurvey().getId();
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

}
