package software.simple.solutions.data.entry.es.control.rest.model;

import java.io.Serializable;

import software.simple.solutions.data.entry.es.control.entities.SurveyQuestionAnswerChoiceSelection;

public class SurveyQuestionAnswerChoiceSelectionModel implements Serializable {
	private Long id;
	private Long surveyQuestionAnswerChoiceId;
	private String label;

	public SurveyQuestionAnswerChoiceSelectionModel() {
		super();
	}

	public SurveyQuestionAnswerChoiceSelectionModel(
			SurveyQuestionAnswerChoiceSelection surveyQuestionAnswerChoiceSelection) {
		this();
		this.id = surveyQuestionAnswerChoiceSelection.getId();
		this.label = surveyQuestionAnswerChoiceSelection.getLabel();
		this.surveyQuestionAnswerChoiceId = surveyQuestionAnswerChoiceSelection.getSurveyQuestionAnswerChoice() == null
				? null : surveyQuestionAnswerChoiceSelection.getSurveyQuestionAnswerChoice().getId();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSurveyQuestionAnswerChoiceId() {
		return surveyQuestionAnswerChoiceId;
	}

	public void setSurveyQuestionAnswerChoiceId(Long surveyQuestionAnswerChoiceId) {
		this.surveyQuestionAnswerChoiceId = surveyQuestionAnswerChoiceId;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
