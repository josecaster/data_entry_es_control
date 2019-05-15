package software.simple.solutions.data.entry.es.control.repository;

import java.util.List;

import software.simple.solutions.data.entry.es.control.entities.SurveyQuestionAnswerChoice;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.repository.IGenericRepository;

public interface ISurveyQuestionAnswerChoiceRepository extends IGenericRepository {

	List<SurveyQuestionAnswerChoice> findBySurveyQuestion(Long surveyQuestionId) throws FrameworkException;

	List<SurveyQuestionAnswerChoice> findBySurveyQuestion(Long surveyQuestionId, String axis) throws FrameworkException;

	public void updateIndexes(Long surveyQuestionId, String axisType, Integer index) throws FrameworkException;

	void deleteBySurveyQuestion(Long surveyQuestionId) throws FrameworkException;

	List<SurveyQuestionAnswerChoice> findBySurvey(Long surveyId) throws FrameworkException;

	void updateIndexesForDelete(Long surveyQuestionId, String axis, Integer componentIndex) throws FrameworkException;

}
