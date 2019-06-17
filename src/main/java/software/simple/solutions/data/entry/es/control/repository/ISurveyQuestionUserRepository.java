package software.simple.solutions.data.entry.es.control.repository;

import java.util.List;

import software.simple.solutions.data.entry.es.control.entities.SurveyQuestionUser;
import software.simple.solutions.framework.core.entities.ApplicationUser;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.repository.IGenericRepository;

public interface ISurveyQuestionUserRepository extends IGenericRepository {

	SurveyQuestionUser getBySurveyAndQuestionAndUser(Long surveyId, Long questionId, Long userId)
			throws FrameworkException;

	List<ApplicationUser> findBySurveyAndQuestion(Long surveyId, Long questionId) throws FrameworkException;

	List<SurveyQuestionUser> getSurveyQuestionUserList(Long surveyId) throws FrameworkException;
}
