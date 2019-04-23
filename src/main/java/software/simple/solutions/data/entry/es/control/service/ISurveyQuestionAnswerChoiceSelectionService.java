package software.simple.solutions.data.entry.es.control.service;

import java.util.List;

import software.simple.solutions.data.entry.es.control.entities.SurveyQuestionAnswerChoiceSelection;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.service.ISuperService;

public interface ISurveyQuestionAnswerChoiceSelectionService extends ISuperService {

	List<SurveyQuestionAnswerChoiceSelection> getBySurveyQuestionAnswerChoice(Long surveyQuestionAnswerChoiceId)
			throws FrameworkException;

	void updateLabel(Long id, String value) throws FrameworkException;

	SurveyQuestionAnswerChoiceSelection create(Long surveyQuestionAnswerChoiceId) throws FrameworkException;

	List<SurveyQuestionAnswerChoiceSelection> findBySurvey(Long surveyId) throws FrameworkException;

}
