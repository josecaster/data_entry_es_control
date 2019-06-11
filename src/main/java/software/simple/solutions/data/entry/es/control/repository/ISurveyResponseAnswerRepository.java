package software.simple.solutions.data.entry.es.control.repository;

import java.util.List;

import software.simple.solutions.data.entry.es.control.entities.SurveyResponseAnswer;
import software.simple.solutions.data.entry.es.control.entities.SurveyResponseAnswerHistory;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.repository.IGenericRepository;

public interface ISurveyResponseAnswerRepository extends IGenericRepository {

	SurveyResponseAnswer getByUniqueId(String uniqueId) throws FrameworkException;

	List<SurveyResponseAnswer> getSurveyResponseAnswers(Long surveyResponseId) throws FrameworkException;

	SurveyResponseAnswer getSurveyResponse(Long surveyResponseId, Long surveyQuestionId) throws FrameworkException;

	List<SurveyResponseAnswer> getSurveyResponseAnswers(Long surveyResponseId, Long surveyQuestionId)
			throws FrameworkException;

	SurveyResponseAnswer getSurveyResponseAnswer(String surveyResponseUniqueId, Long surveyQuestionId)
			throws FrameworkException;

	SurveyResponseAnswer getSurveyResponseAnswer(Long surveyResponseId, Long surveyQuestionId,
			Long surveyQuestionAnswerChoiceRowId) throws FrameworkException;

	SurveyResponseAnswer getSurveyResponseAnswer(Long surveyResponseId, Long surveyQuestionId,
			Long surveyQuestionAnswerChoiceRowId, Long surveyQuestionAnswerChoiceColumnId) throws FrameworkException;

	SurveyResponseAnswer getSurveyResponseAnswer(Long surveyResponseId, Long surveyQuestionId,
			Long surveyQuestionAnswerChoiceRowId, Long surveyQuestionAnswerChoiceColumnId,
			Long surveyQuestionAnswerChoiceSelectionId) throws FrameworkException;

	SurveyResponseAnswerHistory getAnswerHistory(SurveyResponseAnswer surveyResponseAnswer) throws FrameworkException;

	void deleteFromSurveyResponseAnswerByResponse(Long surveyResponseId, Long surveyQuestionId)
			throws FrameworkException;

	void removeAllBySurveyResponse(Long surveyResponseId) throws FrameworkException;

	void removeByResponseUniqueIdAndQuestion(String first, Long second) throws FrameworkException;

}
