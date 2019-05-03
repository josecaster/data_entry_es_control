package software.simple.solutions.data.entry.es.control.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Repository;

import software.simple.solutions.data.entry.es.control.entities.Survey;
import software.simple.solutions.data.entry.es.control.repository.ISurveyRepository;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.repository.impl.GenericRepository;

@Repository
public class SurveyRepository extends GenericRepository implements ISurveyRepository {

	@Override
	public List<Survey> findAllSurveys() throws FrameworkException {
		String query = "from Survey";
		return createListQuery(query);
	}

	@Override
	public List<Survey> findAllSurveysByUser(String username) throws FrameworkException {
		if (username == null) {
			return new ArrayList<>();
		}
		String query = "select sau.survey from SurveyApplicationUser sau where lower(sau.applicationUser.username)=lower(:username)";
		ConcurrentMap<String, Object> paramMap = createParamMap();
		paramMap.put("username", username);
		return createListQuery(query, paramMap);
	}

}
