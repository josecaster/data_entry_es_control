package software.simple.solutions.data.entry.es.control.repository.impl;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Repository;

import software.simple.solutions.data.entry.es.control.entities.SurveyResponse;
import software.simple.solutions.data.entry.es.control.repository.ISurveyResponseRepository;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.repository.impl.GenericRepository;

@Repository
public class SurveyResponseRepository extends GenericRepository implements ISurveyResponseRepository {

	@Override
	public SurveyResponse getByUniqueId(String uniqueId) throws FrameworkException {
		ConcurrentMap<String, Object> paramMap = createParamMap();
		String query = "from SurveyResponse where uniqueId=:uniqueId";
		paramMap.put("uniqueId", uniqueId);
		return getByQuery(query, paramMap);
	}

	@Override
	public List<SurveyResponse> findAllSurveyResponsesByUser(String username) throws FrameworkException {
		String query = "select sr from SurveyResponse sr " + "left join Survey surv on surv.id=sr.survey.id "
				+ "left join SurveyApplicationUser sau on sau.applicationUser.id = sr.applicationUser.id and sau.survey.id=surv.id "
				+ "where lower(sr.applicationUser.username)=lower(:username)";
		ConcurrentMap<String, Object> paramMap = createParamMap();
		paramMap.put("username", username);
		return createListQuery(query, paramMap);
	}

}
