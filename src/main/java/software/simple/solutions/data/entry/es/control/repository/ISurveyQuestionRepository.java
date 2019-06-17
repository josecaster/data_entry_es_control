package software.simple.solutions.data.entry.es.control.repository;

import java.util.List;

import software.simple.solutions.data.entry.es.control.entities.SurveyQuestion;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.pojo.ComboItem;
import software.simple.solutions.framework.core.repository.IGenericRepository;

public interface ISurveyQuestionRepository extends IGenericRepository {

	List<SurveyQuestion> getQuestionList(Long surveyId) throws FrameworkException;

	List<SurveyQuestion> getQuestionList(Long surveyId, String queryText) throws FrameworkException;

	List<SurveyQuestion> getQuestionList(Long surveyId, String queryText, Long surveySectionId)
			throws FrameworkException;

	Long getNextOrder(Long surveyId) throws FrameworkException;

	SurveyQuestion getSurveyQuestionByOrder(Long surveyId, Long order) throws FrameworkException;

	List<ComboItem> getQuestionListForOrder(Long surveyId, Long surveyQuestionId) throws FrameworkException;

	void updateOrderAfterDelete(Long order) throws FrameworkException;

	void cleanUpSurveyQuestionAnswerChoices(Long id, String questionType) throws FrameworkException;

	List<ComboItem> getNextQuestions(Long order) throws FrameworkException;

	void removeUsersFromQuestions(Long surveyId, Long userId) throws FrameworkException;

}
