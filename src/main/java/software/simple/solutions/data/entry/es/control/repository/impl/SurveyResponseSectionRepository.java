package software.simple.solutions.data.entry.es.control.repository.impl;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Repository;

import software.simple.solutions.data.entry.es.control.entities.SurveyResponseSection;
import software.simple.solutions.data.entry.es.control.repository.ISurveyResponseSectionRepository;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.repository.impl.GenericRepository;

@Repository
public class SurveyResponseSectionRepository extends GenericRepository implements ISurveyResponseSectionRepository {

	@Override
	public SurveyResponseSection getByUniqueId(String uniqueId) throws FrameworkException {
		ConcurrentMap<String, Object> paramMap = createParamMap();
		String query = "from SurveyResponseSection where uniqueId=:uniqueId";
		paramMap.put("uniqueId", uniqueId);
		return getByQuery(query, paramMap);
	}

	@Override
	public List<SurveyResponseSection> getSurveyResponseSections(Long surveyResponseId) throws FrameworkException {
		ConcurrentMap<String, Object> paramMap = createParamMap();
		String query = "select srs from SurveyResponseSection srs where srs.surveyResponse.id=:surveyResponseId";
		paramMap.put("surveyResponseId", surveyResponseId);
		return createListQuery(query, paramMap);
	}

	@Override
	public void removeAllBySurveyResponse(Long surveyResponseId) throws FrameworkException {
		ConcurrentMap<String, Object> paramMap = createParamMap();
		String delete = "delete from SurveyResponseSection where surveyResponse.id=:surveyResponseId";
		paramMap.put("surveyResponseId", surveyResponseId);
		deleteByHql(delete, paramMap);
	}

}
