package software.simple.solutions.data.entry.es.control.service;

import software.simple.solutions.data.entry.es.control.entities.SurveyResponseAnswerHistory;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.service.ISuperService;

public interface ISurveyResponseAnswerHistoryService extends ISuperService {

	SurveyResponseAnswerHistory getSurveyResponseAnswerHistoryByResponseAndQuestion(Long surveyResponseId,
			Long surveyQuestionId) throws FrameworkException;
}
