package software.simple.solutions.data.entry.es.control.rest.model;

import java.io.Serializable;

import software.simple.solutions.data.entry.es.control.entities.SurveyResponse;
import software.simple.solutions.framework.core.constants.DateConstant;

public class SurveyResponseModel implements Serializable {

	Long id;
	Boolean active;
	String uniqueId;
	String formName;
	String createdOn;
	Long surveyId;
	String username;
	Boolean uploaded;

	public SurveyResponseModel() {
		super();
	}

	public SurveyResponseModel(SurveyResponse surveyResponse) {
		this();
		this.active = surveyResponse.getActive();
		this.createdOn = surveyResponse.getCreatedOn().format(DateConstant.DATE_TIME_FORMAT);
		this.formName = surveyResponse.getFormName();
		this.id = surveyResponse.getId();
		this.surveyId = surveyResponse.getSurvey().getId();
		this.uniqueId = surveyResponse.getUniqueId();
		this.uploaded = true;
		this.username = surveyResponse.getApplicationUser().getUsername();
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

	public Long getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(Long surveyId) {
		this.surveyId = surveyId;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Boolean getUploaded() {
		return uploaded;
	}

	public void setUploaded(Boolean uploaded) {
		this.uploaded = uploaded;
	}

	@Override
	public String toString() {
		return "SurveyResponseModel [id=" + id + ", uniqueId=" + uniqueId + ", surveyId=" + surveyId + ", formName="
				+ formName + ", createdOn=" + createdOn + ", active=" + active + ", username=" + username
				+ ", uploaded=" + uploaded + "]";
	}

}
