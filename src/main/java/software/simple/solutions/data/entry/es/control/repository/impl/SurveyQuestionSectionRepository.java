package software.simple.solutions.data.entry.es.control.repository.impl;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Repository;

import software.simple.solutions.data.entry.es.control.entities.SurveySection;
import software.simple.solutions.data.entry.es.control.repository.ISurveyQuestionSectionRepository;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.pojo.ComboItem;
import software.simple.solutions.framework.core.repository.impl.GenericRepository;

@Repository
public class SurveyQuestionSectionRepository extends GenericRepository implements ISurveyQuestionSectionRepository {

	@Override
	public List<ComboItem> findBySurvey(Long surveyId) throws FrameworkException {
		String query = "select new software.simple.solutions.framework.core.pojo.ComboItem(sg.id,sg.name) from SurveySection sg where sg.survey.id=:surveyId";
		ConcurrentMap<String, Object> paramMap = createParamMap();
		paramMap.put("surveyId", surveyId);
		return createListQuery(query, paramMap);
	}

	@Override
	public List<SurveySection> findAllBySurveyId(Long surveyId) throws FrameworkException {
		String query = "from SurveySection sg where sg.survey.id=:surveyId";
		ConcurrentMap<String, Object> paramMap = createParamMap();
		paramMap.put("surveyId", surveyId);
		return createListQuery(query, paramMap);
	}

	@Override
	public void updateSectionsToUnPinned(Long surveyId, Long surveySectionId) throws FrameworkException {
		String query = "update SurveySection set pinned=false where survey.id=:surveyId";
		ConcurrentMap<String, Object> paramMap = createParamMap();
		paramMap.put("surveyId", surveyId);
		if (surveySectionId != null) {
			query += " and id!=:surveySectionId";
			paramMap.put("surveySectionId", surveySectionId);
		}
		updateByHql(query, paramMap);
	}

	@Override
	public SurveySection getPinnedSectionBySurvey(Long surveyId) throws FrameworkException {
		String query = "from SurveySection where pinned=true and survey.id=:surveyId";
		ConcurrentMap<String, Object> paramMap = createParamMap();
		paramMap.put("surveyId", surveyId);
		return getByQuery(query, paramMap);
	}

	@Override
	public <T, R> List<R> getForListing(Class<T> cl, List<Long> ids, Boolean active) throws FrameworkException {
		ConcurrentMap<String, Object> paramMap = createParamMap();
		String query = "select new software.simple.solutions.framework.core.pojo.ComboItem(id,name) from SurveySection where 1=1 ";
		query += " order by id ";

		return createListQuery(query, paramMap);
	}

}
