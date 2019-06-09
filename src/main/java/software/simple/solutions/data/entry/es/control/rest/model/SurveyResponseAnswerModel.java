package software.simple.solutions.data.entry.es.control.rest.model;

import java.io.Serializable;

import software.simple.solutions.data.entry.es.control.entities.SurveyResponseAnswer;

public class SurveyResponseAnswerModel implements Serializable {

	Long id;
	Boolean active;
	String uniqueId;
	String surveyResponseUniqueId;
	Long surveyQuestionId;
	Long surveyQuestionAnswerChoiceRowId;
	Long surveyQuestionAnswerChoiceColumnId;
	Long surveyQuestionAnswerChoiceSelectionId;
	String matrixColumnType;
	String responseText;
	String otherValue;
	Boolean selected;
	String state;

	public SurveyResponseAnswerModel() {
		super();
	}

	public SurveyResponseAnswerModel(SurveyResponseAnswer surveyResponseAnswer) {
		this();
		this.active = surveyResponseAnswer.getActive();
		this.id = surveyResponseAnswer.getId();
		this.matrixColumnType = surveyResponseAnswer.getMatrixColumnType();
		this.otherValue = surveyResponseAnswer.getOtherValue();
		this.responseText = surveyResponseAnswer.getResponseText();
		this.selected = surveyResponseAnswer.getSelected();
		this.surveyQuestionAnswerChoiceColumnId = surveyResponseAnswer.getSurveyQuestionAnswerChoiceColumn() == null
				? null : surveyResponseAnswer.getSurveyQuestionAnswerChoiceColumn().getId();
		this.surveyQuestionAnswerChoiceRowId = surveyResponseAnswer.getSurveyQuestionAnswerChoiceRow() == null ? null
				: surveyResponseAnswer.getSurveyQuestionAnswerChoiceRow().getId();
		this.surveyQuestionAnswerChoiceSelectionId = surveyResponseAnswer
				.getSurveyQuestionAnswerChoiceSelection() == null ? null
						: surveyResponseAnswer.getSurveyQuestionAnswerChoiceSelection().getId();
		this.surveyQuestionId = surveyResponseAnswer.getSurveyQuestion().getId();
		this.surveyResponseUniqueId = surveyResponseAnswer.getSurveyResponse().getUniqueId();
		this.uniqueId = surveyResponseAnswer.getUniqueId();
		this.state = surveyResponseAnswer.getState();
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

	public Long getSurveyQuestionId() {
		return surveyQuestionId;
	}

	public void setSurveyQuestionId(Long surveyQuestionId) {
		this.surveyQuestionId = surveyQuestionId;
	}

	public Long getSurveyQuestionAnswerChoiceRowId() {
		return surveyQuestionAnswerChoiceRowId;
	}

	public void setSurveyQuestionAnswerChoiceRowId(Long surveyQuestionAnswerChoiceRowId) {
		this.surveyQuestionAnswerChoiceRowId = surveyQuestionAnswerChoiceRowId;
	}

	public Long getSurveyQuestionAnswerChoiceColumnId() {
		return surveyQuestionAnswerChoiceColumnId;
	}

	public void setSurveyQuestionAnswerChoiceColumnId(Long surveyQuestionAnswerChoiceColumnId) {
		this.surveyQuestionAnswerChoiceColumnId = surveyQuestionAnswerChoiceColumnId;
	}

	public Long getSurveyQuestionAnswerChoiceSelectionId() {
		return surveyQuestionAnswerChoiceSelectionId;
	}

	public void setSurveyQuestionAnswerChoiceSelectionId(Long surveyQuestionAnswerChoiceSelectionId) {
		this.surveyQuestionAnswerChoiceSelectionId = surveyQuestionAnswerChoiceSelectionId;
	}

	public String getMatrixColumnType() {
		return matrixColumnType;
	}

	public void setMatrixColumnType(String matrixColumnType) {
		this.matrixColumnType = matrixColumnType;
	}

	public String getResponseText() {
		return responseText;
	}

	public void setResponseText(String responseText) {
		this.responseText = responseText;
	}

	public String getOtherValue() {
		return otherValue;
	}

	public void setOtherValue(String otherValue) {
		this.otherValue = otherValue;
	}

	public Boolean getSelected() {
		return selected;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "SurveyResponseAnswerModel [id=" + id + ", active=" + active + ", uniqueId=" + uniqueId
				+ ", surveyResponseUniqueId=" + surveyResponseUniqueId + ", surveyQuestionId=" + surveyQuestionId
				+ ", surveyQuestionAnswerChoiceRowId=" + surveyQuestionAnswerChoiceRowId
				+ ", surveyQuestionAnswerChoiceColumnId=" + surveyQuestionAnswerChoiceColumnId
				+ ", surveyQuestionAnswerChoiceSelectionId=" + surveyQuestionAnswerChoiceSelectionId
				+ ", matrixColumnType=" + matrixColumnType + ", responseText=" + responseText + ", otherValue="
				+ otherValue + ", selected=" + selected + ", state=" + state + "]";
	}

}
