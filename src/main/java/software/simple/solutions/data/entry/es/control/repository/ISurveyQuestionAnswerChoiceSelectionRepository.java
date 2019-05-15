package software.simple.solutions.data.entry.es.control.repository;

import java.util.List;

import software.simple.solutions.data.entry.es.control.entities.SurveyQuestionAnswerChoiceSelection;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.repository.IGenericRepository;

public interface ISurveyQuestionAnswerChoiceSelectionRepository extends IGenericRepository {

	List<SurveyQuestionAnswerChoiceSelection> getBySurveyQuestionAnswerChoice(Long surveyQuestionAnswerChoiceId)
			throws FrameworkException;

	void deleteBySurveyQuestionAnswerChoice(Long surveyQuestionAnswerChoiceId) throws FrameworkException;

	List<SurveyQuestionAnswerChoiceSelection> findBySurvey(Long surveyId) throws FrameworkException;

	void updateIndexes(Long surveyQuestionAnswerChoiceId, Integer index) throws FrameworkException;

}
