package software.simple.solutions.data.entry.es.control.rest.model;

import java.util.List;

public class SurveyRestModel {

	private SurveyModel survey;
	private List<SurveySectionModel> surveySections;
	private List<SurveyGroupModel> surveyGroups;
	private List<SurveyQuestionModel> surveyQuestions;
	private List<SurveyQuestionAnswerChoiceModel> surveyQuestionAnswerChoices;
	private List<SurveyQuestionAnswerChoiceSelectionModel> surveyQuestionAnswerChoiceSelections;

	public SurveyRestModel() {
		super();
	}

	public SurveyModel getSurvey() {
		return survey;
	}

	public void setSurvey(SurveyModel survey) {
		this.survey = survey;
	}

	public List<SurveySectionModel> getSurveySections() {
		return surveySections;
	}

	public void setSurveySections(List<SurveySectionModel> surveySections) {
		this.surveySections = surveySections;
	}

	public List<SurveyGroupModel> getSurveyGroups() {
		return surveyGroups;
	}

	public void setSurveyGroups(List<SurveyGroupModel> surveyGroups) {
		this.surveyGroups = surveyGroups;
	}

	public List<SurveyQuestionModel> getSurveyQuestions() {
		return surveyQuestions;
	}

	public void setSurveyQuestions(List<SurveyQuestionModel> surveyQuestions) {
		this.surveyQuestions = surveyQuestions;
	}

	public List<SurveyQuestionAnswerChoiceModel> getSurveyQuestionAnswerChoices() {
		return surveyQuestionAnswerChoices;
	}

	public void setSurveyQuestionAnswerChoices(List<SurveyQuestionAnswerChoiceModel> surveyQuestionAnswerChoices) {
		this.surveyQuestionAnswerChoices = surveyQuestionAnswerChoices;
	}

	public List<SurveyQuestionAnswerChoiceSelectionModel> getSurveyQuestionAnswerChoiceSelections() {
		return surveyQuestionAnswerChoiceSelections;
	}

	public void setSurveyQuestionAnswerChoiceSelections(
			List<SurveyQuestionAnswerChoiceSelectionModel> surveyQuestionAnswerChoiceSelections) {
		this.surveyQuestionAnswerChoiceSelections = surveyQuestionAnswerChoiceSelections;
	}

}
