package software.simple.solutions.data.entry.es.control.rest.model;

import java.io.Serializable;

import software.simple.solutions.data.entry.es.control.entities.SurveyQuestionUser;

public class SurveyQuestionUserModel implements Serializable {

	private Long id;
	private Boolean active;
	private Long surveyId;
	private Long questionId;
	private String username;

	public SurveyQuestionUserModel() {
		super();
	}

	public SurveyQuestionUserModel(SurveyQuestionUser surveyQuestionUser) {
		this();
		this.active = surveyQuestionUser.getActive();
		this.id = surveyQuestionUser.getId();
		this.surveyId = surveyQuestionUser.getSurvey() == null ? null : surveyQuestionUser.getSurvey().getId();
		this.questionId = surveyQuestionUser.getSurveyQuestion() == null ? null
				: surveyQuestionUser.getSurveyQuestion().getId();
		this.username = surveyQuestionUser.getApplicationUser() == null ? null
				: surveyQuestionUser.getApplicationUser().getUsername();
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

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
