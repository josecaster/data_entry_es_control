package software.simple.solutions.data.entry.es.control.rest.model;

import java.io.Serializable;

import software.simple.solutions.data.entry.es.control.entities.SurveyQuestion;

public class SurveyQuestionModel implements Serializable {

	private Long id;
	private Boolean active;
	private Long surveyId;
	private String question;
	private String questionDescription;
	private Long order;
	private String questionType;
	private Boolean required;
	private String requiredError;
	private Boolean multipleSelection;
	private Long sectionId;
	private Long groupId;

	public SurveyQuestionModel() {
		super();
	}

	public SurveyQuestionModel(SurveyQuestion surveyQuestion) {
		this();
		this.active = surveyQuestion.getActive();
		this.id = surveyQuestion.getId();
		this.multipleSelection = surveyQuestion.getMultipleSelection();
		this.order = surveyQuestion.getOrder();
		this.question = surveyQuestion.getQuestion();
		this.questionDescription = surveyQuestion.getQuestionDescription();
		this.questionType = surveyQuestion.getQuestionType();
		this.required = surveyQuestion.getRequired();
		this.requiredError = surveyQuestion.getRequiredError();
		this.groupId = surveyQuestion.getSurveyGroup() == null ? null : surveyQuestion.getSurveyGroup().getId();
		this.surveyId = surveyQuestion.getSurvey() == null ? null : surveyQuestion.getSurvey().getId();
		this.sectionId = surveyQuestion.getSurveySection() == null ? null : surveyQuestion.getSurveySection().getId();
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

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getQuestionDescription() {
		return questionDescription;
	}

	public void setQuestionDescription(String questionDescription) {
		this.questionDescription = questionDescription;
	}

	public Long getOrder() {
		return order;
	}

	public void setOrder(Long order) {
		this.order = order;
	}

	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	public Boolean getRequired() {
		return required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

	public String getRequiredError() {
		return requiredError;
	}

	public void setRequiredError(String requiredError) {
		this.requiredError = requiredError;
	}

	public Boolean getMultipleSelection() {
		return multipleSelection;
	}

	public void setMultipleSelection(Boolean multipleSelection) {
		this.multipleSelection = multipleSelection;
	}

	public Long getSectionId() {
		return sectionId;
	}

	public void setSectionId(Long sectionId) {
		this.sectionId = sectionId;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

}
