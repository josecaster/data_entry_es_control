package software.simple.solutions.data.entry.es.control.pojo;

import java.util.List;

import software.simple.solutions.data.entry.es.control.entities.Survey;
import software.simple.solutions.data.entry.es.control.entities.SurveySection;
import software.simple.solutions.data.entry.es.control.entities.SurveyQuestion;
import software.simple.solutions.data.entry.es.control.entities.SurveyQuestionAnswerChoice;
import software.simple.solutions.data.entry.es.control.entities.SurveyQuestionAnswerChoiceSelection;

public class SurveyPojo {

	private Survey survey;
	private List<SurveySection> surveySections;
	private List<SurveyQuestion> surveyQuestions;
	private List<SurveyQuestionAnswerChoice> surveyQuestionAnswerChoices;
	private List<SurveyQuestionAnswerChoiceSelection> surveyQuestionAnswerChoiceSelections;

	public SurveyPojo() {
		super();
	}

	public Survey getSurvey() {
		return survey;
	}

	public void setSurvey(Survey survey) {
		this.survey = survey;
	}

	public List<SurveySection> getSurveySections() {
		return surveySections;
	}

	public void setSurveySections(List<SurveySection> surveySections) {
		this.surveySections = surveySections;
	}

	public List<SurveyQuestion> getSurveyQuestions() {
		return surveyQuestions;
	}

	public void setSurveyQuestions(List<SurveyQuestion> surveyQuestions) {
		this.surveyQuestions = surveyQuestions;
	}

	public List<SurveyQuestionAnswerChoice> getSurveyQuestionAnswerChoices() {
		return surveyQuestionAnswerChoices;
	}

	public void setSurveyQuestionAnswerChoices(List<SurveyQuestionAnswerChoice> surveyQuestionAnswerChoices) {
		this.surveyQuestionAnswerChoices = surveyQuestionAnswerChoices;
	}

	public List<SurveyQuestionAnswerChoiceSelection> getSurveyQuestionAnswerChoiceSelections() {
		return surveyQuestionAnswerChoiceSelections;
	}

	public void setSurveyQuestionAnswerChoiceSelections(
			List<SurveyQuestionAnswerChoiceSelection> surveyQuestionAnswerChoiceSelections) {
		this.surveyQuestionAnswerChoiceSelections = surveyQuestionAnswerChoiceSelections;
	}

}
