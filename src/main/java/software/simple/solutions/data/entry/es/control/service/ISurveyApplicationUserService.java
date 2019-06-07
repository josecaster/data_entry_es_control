package software.simple.solutions.data.entry.es.control.service;

import java.util.List;

import software.simple.solutions.framework.core.entities.ApplicationUser;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.service.ISuperService;

public interface ISurveyApplicationUserService extends ISuperService {

	List<ApplicationUser> findApplicationUserBySurvey(Long surveyId) throws FrameworkException;

}
