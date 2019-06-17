package software.simple.solutions.data.entry.es.control.service;

import java.util.List;

import software.simple.solutions.data.entry.es.control.entities.SurveyQuestionUser;
import software.simple.solutions.framework.core.entities.ApplicationUser;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.service.ISuperService;

public interface ISurveyQuestionUserService extends ISuperService {

	void addUserToSurveyQuestion(Long surveyId, Long questionId, Long userId) throws FrameworkException;

	List<ApplicationUser> findBySurveyAndQuestion(Long surveyId, Long questionId) throws FrameworkException;

	void removeUserFromSurveyQuestion(Long surveyId, Long questionId, Long userId) throws FrameworkException;

	List<SurveyQuestionUser> getSurveyQuestionUserList(Long id) throws FrameworkException;
}
