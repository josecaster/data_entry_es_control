package software.simple.solutions.data.entry.es.control.valueobjects;

import software.simple.solutions.data.entry.es.control.entities.SurveyQuestion;
import software.simple.solutions.framework.core.valueobjects.SuperVO;

public class SurveyQuestionVO extends SuperVO {

	private static final long serialVersionUID = -2008276944089219114L;

	private Long id;
	private Boolean active;
	private String question;
	private String questionDescription;
	private Long surveyId;
	private Long order;
	private String position;
	private Boolean required;
	private String requiredMessage;
	private String questionType;
	private Boolean multipleSelection;
	private Long requiredBy;
	private Long requiredByChoice;

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

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public Long getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(Long surveyId) {
		this.surveyId = surveyId;
	}

	public Long getOrder() {
		return order;
	}

	public void setOrder(Long order) {
		this.order = order;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Boolean getRequired() {
		return required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

	public String getRequiredMessage() {
		return requiredMessage;
	}

	public void setRequiredMessage(String requiredMessage) {
		this.requiredMessage = requiredMessage;
	}

	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	public String getQuestionDescription() {
		return questionDescription;
	}

	public void setQuestionDescription(String questionDescription) {
		this.questionDescription = questionDescription;
	}

	/**
	 * @see SurveyQuestion#getMultipleSelection()
	 */
	public Boolean getMultipleSelection() {
		return multipleSelection;
	}

	/**
	 * @see SurveyQuestion#getMultipleSelection()
	 */
	public void setMultipleSelection(Boolean multipleSelection) {
		this.multipleSelection = multipleSelection;
	}

	public Long getRequiredBy() {
		return requiredBy;
	}

	public void setRequiredBy(Long requiredBy) {
		this.requiredBy = requiredBy;
	}

	public Long getRequiredByChoice() {
		return requiredByChoice;
	}

	public void setRequiredByChoice(Long requiredByChoice) {
		this.requiredByChoice = requiredByChoice;
	}

}
