package software.simple.solutions.data.entry.es.control.repository.impl;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Repository;

import software.simple.solutions.data.entry.es.control.entities.SurveyGroup;
import software.simple.solutions.data.entry.es.control.repository.ISurveyGroupRepository;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.pojo.ComboItem;
import software.simple.solutions.framework.core.repository.impl.GenericRepository;

@Repository
public class SurveyGroupRepository extends GenericRepository implements ISurveyGroupRepository {

	@Override
	public List<ComboItem> findBySurvey(Long surveyId) throws FrameworkException {
		String query = "select new software.simple.solutions.framework.core.pojo.ComboItem(sg.id,sg.name) from SurveyGroup sg where sg.survey.id=:surveyId";
		ConcurrentMap<String, Object> paramMap = createParamMap();
		paramMap.put("surveyId", surveyId);
		return createListQuery(query, paramMap);
	}

	@Override
	public List<SurveyGroup> findAllBySurveyId(Long surveyId) throws FrameworkException {
		String query = "from SurveyGroup sg where sg.survey.id=:surveyId";
		ConcurrentMap<String, Object> paramMap = createParamMap();
		paramMap.put("surveyId", surveyId);
		return createListQuery(query, paramMap);
	}

	@Override
	public <T, R> List<R> getForListing(Class<T> cl, List<Long> ids, Boolean active) throws FrameworkException {
		ConcurrentMap<String, Object> paramMap = createParamMap();
		String query = "select new software.simple.solutions.framework.core.pojo.ComboItem(id,name) from SurveyGroup where 1=1 ";
		query += " order by id ";

		return createListQuery(query, paramMap);
	}

}
