package software.simple.solutions.data.entry.es.control.repository.impl;

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

}
