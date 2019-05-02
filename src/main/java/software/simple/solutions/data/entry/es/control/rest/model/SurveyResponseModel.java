package software.simple.solutions.data.entry.es.control.rest.model;

import java.io.Serializable;

public class SurveyResponseModel implements Serializable {

	Long id;
	String uniqueId;
	Long surveyId;
	String formName;
	String createdOn;
	Boolean active;
	String username;
	Boolean uploaded;

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
