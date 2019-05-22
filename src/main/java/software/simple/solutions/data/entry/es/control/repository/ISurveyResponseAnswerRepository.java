package software.simple.solutions.data.entry.es.control.repository;

import java.util.List;

import software.simple.solutions.data.entry.es.control.entities.SurveyResponseAnswer;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.repository.IGenericRepository;

public interface ISurveyResponseAnswerRepository extends IGenericRepository {

	SurveyResponseAnswer getByUniqueId(String uniqueId) throws FrameworkException;

	List<SurveyResponseAnswer> getSurveyResponseAnswers(Long surveyResponseId) throws FrameworkException;

	void removeAllBySurveyResponse(Long surveyResponseId) throws FrameworkException;

}
