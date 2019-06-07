package software.simple.solutions.data.entry.es.control.repository;

import java.util.List;

import software.simple.solutions.framework.core.entities.ApplicationUser;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.repository.IGenericRepository;

public interface ISurveyApplicationUserRepository extends IGenericRepository {

	List<ApplicationUser> findApplicationUserBySurvey(Long surveyId) throws FrameworkException;

}
