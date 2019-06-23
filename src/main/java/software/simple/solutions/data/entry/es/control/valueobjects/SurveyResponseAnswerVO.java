package software.simple.solutions.data.entry.es.control.valueobjects;

import software.simple.solutions.framework.core.valueobjects.SuperVO;

public class SurveyResponseAnswerVO extends SuperVO {

	private static final long serialVersionUID = -2008276944089219114L;

	private Long id;
	private Boolean active;
	private String uniqueId;
	private Long surveyId;
	private Long surveyResponseId;
	private Long surveyQuestionId;
	private String responseText;
	private Long questionAnswerChoiceRowId;
	private Long questionAnswerChoiceColumnId;
	private Long questionAnswerChoiceSelectionId;
	private Boolean selected;
	private String otherValue;
	private String matrixColumnType;

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

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public Long getSurveyResponseId() {
		return surveyResponseId;
	}

	public void setSurveyResponseId(Long surveyResponseId) {
		this.surveyResponseId = surveyResponseId;
	}

	public Long getSurveyQuestionId() {
		return surveyQuestionId;
	}

	public void setSurveyQuestionId(Long surveyQuestionId) {
		this.surveyQuestionId = surveyQuestionId;
	}

	public String getResponseText() {
		return responseText;
	}

	public void setResponseText(String responseText) {
		this.responseText = responseText;
	}

	public Long getQuestionAnswerChoiceRowId() {
		return questionAnswerChoiceRowId;
	}

	public void setQuestionAnswerChoiceRowId(Long questionAnswerChoiceRowId) {
		this.questionAnswerChoiceRowId = questionAnswerChoiceRowId;
	}

	public Long getQuestionAnswerChoiceColumnId() {
		return questionAnswerChoiceColumnId;
	}

	public void setQuestionAnswerChoiceColumnId(Long questionAnswerChoiceColumnId) {
		this.questionAnswerChoiceColumnId = questionAnswerChoiceColumnId;
	}

	public Boolean getSelected() {
		return selected;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
	}

	public String getOtherValue() {
		return otherValue;
	}

	public void setOtherValue(String otherValue) {
		this.otherValue = otherValue;
	}

	public Long getQuestionAnswerChoiceSelectionId() {
		return questionAnswerChoiceSelectionId;
	}

	public void setQuestionAnswerChoiceSelectionId(Long questionAnswerChoiceSelectionId) {
		this.questionAnswerChoiceSelectionId = questionAnswerChoiceSelectionId;
	}

	public String getMatrixColumnType() {
		return matrixColumnType;
	}

	public void setMatrixColumnType(String matrixColumnType) {
		this.matrixColumnType = matrixColumnType;
	}

}
