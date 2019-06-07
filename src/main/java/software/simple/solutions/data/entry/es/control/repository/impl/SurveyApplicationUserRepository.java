package software.simple.solutions.data.entry.es.control.repository.impl;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Repository;

import software.simple.solutions.data.entry.es.control.repository.ISurveyApplicationUserRepository;
import software.simple.solutions.framework.core.entities.ApplicationUser;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.repository.impl.GenericRepository;

@Repository
public class SurveyApplicationUserRepository extends GenericRepository implements ISurveyApplicationUserRepository {

	@Override
	public List<ApplicationUser> findApplicationUserBySurvey(Long surveyId) throws FrameworkException {
		String query = "select sau.applicationUser from SurveyApplicationUser sau where sau.survey.id = :id";
		ConcurrentMap<String, Object> paramMap = createParamMap();
		paramMap.put("id", surveyId);
		return createListQuery(query, paramMap);
	}

}
