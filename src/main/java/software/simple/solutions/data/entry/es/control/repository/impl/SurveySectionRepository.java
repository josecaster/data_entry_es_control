package software.simple.solutions.data.entry.es.control.repository.impl;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Repository;

import software.simple.solutions.data.entry.es.control.entities.SurveySection;
import software.simple.solutions.data.entry.es.control.repository.ISurveySectionRepository;
import software.simple.solutions.data.entry.es.control.valueobjects.SurveySectionVO;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.pojo.ComboItem;
import software.simple.solutions.framework.core.repository.impl.GenericRepository;

@Repository
public class SurveySectionRepository extends GenericRepository implements ISurveySectionRepository {

	@Override
	public List<ComboItem> findBySurvey(Long surveyId) throws FrameworkException {
		String query = "select new software.simple.solutions.framework.core.pojo.ComboItem(sg.id,sg.name) from SurveySection sg where sg.survey.id=:surveyId";
		ConcurrentMap<String, Object> paramMap = createParamMap();
		paramMap.put("surveyId", surveyId);
		return createListQuery(query, paramMap);
	}

	@Override
	public List<SurveySection> findAllBySurveyId(Long surveyId) throws FrameworkException {
		String query = "from SurveySection sg where sg.survey.id=:surveyId order by id";
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

	@Override
	public Boolean isSectionCodeUniqueForSurvey(Long surveyId, Long surveySectionId, String sectionCode)
			throws FrameworkException {
		ConcurrentMap<String, Object> paramMap = createParamMap();
		String query = "select count(ss) from SurveySection ss where ss.survey.id=:surveyId and lower(ss.code) = lower(:code) ";
		paramMap.put("surveyId", surveyId);
		paramMap.put("code", sectionCode);
		if (surveySectionId != null) {
			query += " and ss.id !=:surveySectionId ";
			paramMap.put("surveySectionId", surveySectionId);
		}
		Long count = getByQuery(query, paramMap);
		return (count == null || count.compareTo(0L) == 0);
	}

	@Override
	public List<ComboItem> getForListing(SurveySectionVO vo) throws FrameworkException {
		ConcurrentMap<String, Object> paramMap = createParamMap();
		String query = "select new software.simple.solutions.framework.core.pojo.ComboItem(id,name) from SurveySection where 1=1 ";
		if (vo.getSurveyId() != null) {
			query += "and survey.id=:surveyId ";
			paramMap.put("surveyId", vo.getSurveyId());
		}
		if (vo.getActive() != null) {
			query += "and active=:active ";
			paramMap.put("active", vo.getActive());
		}
		query += " order by id ";

		return createListQuery(query, paramMap);
	}

}
