package software.simple.solutions.data.entry.es.control.rest.model;

import java.io.Serializable;

import software.simple.solutions.data.entry.es.control.entities.SurveyResponseSection;

public class SurveyResponseSectionModel implements Serializable {

	Long id;
	String uniqueId;
	Boolean active;
	String surveyResponseUniqueId;
	Long surveySectionId;
	Boolean notApplicable;

	public SurveyResponseSectionModel() {
		super();
	}

	public SurveyResponseSectionModel(SurveyResponseSection surveyResponseSection) {
		this();
		this.active = surveyResponseSection.getActive();
		this.id = surveyResponseSection.getId();
		this.notApplicable = surveyResponseSection.getNotApplicable();
		this.surveyResponseUniqueId = surveyResponseSection.getSurveyResponse().getUniqueId();
		this.surveySectionId = surveyResponseSection.getSurveySection().getId();
		this.uniqueId = surveyResponseSection.getUniqueId();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getSurveyResponseUniqueId() {
		return surveyResponseUniqueId;
	}

	public void setSurveyResponseUniqueId(String surveyResponseUniqueId) {
		this.surveyResponseUniqueId = surveyResponseUniqueId;
	}

	public Long getSurveySectionId() {
		return surveySectionId;
	}

	public void setSurveySectionId(Long surveySectionId) {
		this.surveySectionId = surveySectionId;
	}

	public Boolean getNotApplicable() {
		return notApplicable;
	}

	public void setNotApplicable(Boolean notApplicable) {
		this.notApplicable = notApplicable;
	}

}
