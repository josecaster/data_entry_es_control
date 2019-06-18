package software.simple.solutions.data.entry.es.control.web.view;

import software.simple.solutions.data.entry.es.control.entities.SurveyQuestion;
import software.simple.solutions.data.entry.es.control.entities.SurveyResponse;
import software.simple.solutions.data.entry.es.control.entities.SurveyResponseAnswerHistory;
import software.simple.solutions.data.entry.es.control.web.view.question.QuestionPreviewLayout;
import software.simple.solutions.framework.core.components.SessionHolder;

public class SurveyResponseHistoryPreviewGenerator {

	private final QuestionPreviewLayout questionPreviewLayout;

	public SurveyResponseHistoryPreviewGenerator(SessionHolder sessionHolder, SurveyQuestion surveyQuestion,
			SurveyResponse surveyResponse, SurveyResponseAnswerHistory surveyResponseAnswerHistory) {
		questionPreviewLayout = new QuestionPreviewLayout(sessionHolder);
		questionPreviewLayout.setShowInfo(false);
		questionPreviewLayout.setShowSection(false);
		questionPreviewLayout.setEditable(false);
		questionPreviewLayout.setAuditView(true);
		questionPreviewLayout.setPreviewData(surveyQuestion, surveyResponse, surveyResponseAnswerHistory);
	}

	public QuestionPreviewLayout getQuestionPreviewLayout() {
		return questionPreviewLayout;
	}

}
