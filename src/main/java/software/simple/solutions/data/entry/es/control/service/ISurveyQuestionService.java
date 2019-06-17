package software.simple.solutions.data.entry.es.control.service;

import java.util.List;

import software.simple.solutions.data.entry.es.control.entities.SurveyQuestion;
import software.simple.solutions.data.entry.es.control.valueobjects.SurveyQuestionVO;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.pojo.ComboItem;
import software.simple.solutions.framework.core.service.ISuperService;

public interface ISurveyQuestionService extends ISuperService {

	List<ComboItem> getQuestionListForOrder(Long surveyId, Long surveyQuestionId) throws FrameworkException;

	SurveyQuestion updateOrder(SurveyQuestionVO vo) throws FrameworkException;

	List<SurveyQuestion> getQuestionList(Long surveyId) throws FrameworkException;

	List<SurveyQuestion> getQuestionList(Long surveyId, String queryText) throws FrameworkException;

	List<SurveyQuestion> getQuestionList(Long surveyId, String queryText, Long surveySectionId)
			throws FrameworkException;

	SurveyQuestion updateOptions(SurveyQuestionVO vo) throws FrameworkException;

	/**
	 * 
	 * Updates the question to support giving more than one answer or not.
	 * Turning a multiple choice question, where only one answer can be
	 * selected, to a question where more than one answer can be selected and
	 * vice versa.
	 * 
	 * @param vo
	 * @throws FrameworkException
	 */
	void updateMultipleSelection(SurveyQuestionVO vo) throws FrameworkException;

	List<ComboItem> getNextQuestions(Long order) throws FrameworkException;

	SurveyQuestion updateSurveyQuestionSection(Long surveyQuestionId, Long sectionId) throws FrameworkException;

	SurveyQuestion updateSurveyQuestionGroup(Long surveyQuestionId, Long groupId) throws FrameworkException;

	SurveyQuestion updateDescription(Long surveyQuestionId, String description) throws FrameworkException;

	SurveyQuestion updateRequired(Long surveyQuestionId, Boolean required) throws FrameworkException;

	SurveyQuestion updateRequiredError(Long surveyQuestionId, String requiredError) throws FrameworkException;

	void removeUsersFromQuestions(Long surveyId, Long userId) throws FrameworkException;

}
