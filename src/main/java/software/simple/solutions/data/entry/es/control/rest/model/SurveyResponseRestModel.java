package software.simple.solutions.data.entry.es.control.rest.model;

import java.io.Serializable;
import java.util.List;

public class SurveyResponseRestModel implements Serializable {

	private SurveyResponseModel surveyResponse;
	private List<SurveyResponseAnswerModel> surveyResponseAnswers;
	private List<SurveyResponseSectionModel> surveyResponseSections;

	public SurveyResponseModel getSurveyResponse() {
		return surveyResponse;
	}

	public void setSurveyResponse(SurveyResponseModel surveyResponse) {
		this.surveyResponse = surveyResponse;
	}

	public List<SurveyResponseAnswerModel> getSurveyResponseAnswers() {
		return surveyResponseAnswers;
	}

	public void setSurveyResponseAnswers(List<SurveyResponseAnswerModel> surveyResponseAnswers) {
		this.surveyResponseAnswers = surveyResponseAnswers;
	}

	public List<SurveyResponseSectionModel> getSurveyResponseSections() {
		return surveyResponseSections;
	}

	public void setSurveyResponseSections(List<SurveyResponseSectionModel> surveyResponseSections) {
		this.surveyResponseSections = surveyResponseSections;
	}

}
