package software.simple.solutions.data.entry.es.control.repository.impl;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import software.simple.solutions.data.entry.es.control.entities.SurveyQuestion;
import software.simple.solutions.data.entry.es.control.repository.ISurveyQuestionRepository;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.pojo.ComboItem;
import software.simple.solutions.framework.core.repository.impl.GenericRepository;

@Repository
public class SurveyQuestionRepository extends GenericRepository implements ISurveyQuestionRepository {

	@Override
	public List<SurveyQuestion> getQuestionList(Long surveyId) throws FrameworkException {
		return getQuestionList(surveyId, null);
	}

	@Override
	public List<SurveyQuestion> getQuestionList(Long surveyId, String queryText) throws FrameworkException {
		return getQuestionList(surveyId, queryText, null);
	}

	@Override
	public List<SurveyQuestion> getQuestionList(Long surveyId, String queryText, Long surveySectionId)
			throws FrameworkException {
		String query = "from SurveyQuestion sq where sq.survey.id = :surveyId";
		ConcurrentMap<String, Object> paramMap = createParamMap();
		paramMap.put("surveyId", surveyId);
		if (StringUtils.isNotBlank(queryText)) {
			query += " and lower(sq.question) like :queryText";
			paramMap.put("queryText", "%" + queryText + "%");
		}
		if (surveySectionId != null) {
			query += " and sq.surveySection.id=:surveySectionId ";
			paramMap.put("surveySectionId", surveySectionId);
		}
		query += " order by sq.order";
		return createListQuery(query, paramMap);
	}

	@Override
	public Long getNextOrder(Long surveyId) throws FrameworkException {
		String query = "select max(sq.order) from SurveyQuestion sq where sq.survey.id=:surveyId";
		ConcurrentMap<String, Object> paramMap = createParamMap();
		paramMap.put("surveyId", surveyId);
		return getByQuery(query, paramMap);
	}

	@Override
	public SurveyQuestion getSurveyQuestionByOrder(Long surveyId, Long order) throws FrameworkException {
		String query = "select sq from SurveyQuestion sq where sq.survey.id=:surveyId and sq.order=:order";
		ConcurrentMap<String, Object> paramMap = createParamMap();
		paramMap.put("surveyId", surveyId);
		paramMap.put("order", order);
		return getByQuery(query, paramMap);
	}

	@Override
	public List<ComboItem> getQuestionListForOrder(Long surveyId, Long surveyQuestionId) throws FrameworkException {
		String query = "select new software.simple.solutions.framework.core.pojo.ComboItem(sq.order,str(sq.order),sq.question) from SurveyQuestion sq "
				+ "where sq.survey.id=:surveyId and sq.id!=:surveyQuestionId order by sq.order";
		ConcurrentMap<String, Object> paramMap = createParamMap();
		paramMap.put("surveyId", surveyId);
		paramMap.put("surveyQuestionId", surveyQuestionId);
		return createListQuery(query, paramMap);
	}

	@Override
	public void updateOrderAfterDelete(Long order) throws FrameworkException {
		String update = "update SurveyQuestion sq set sq.order=sq.order-1 where sq.order>:order";
		ConcurrentMap<String, Object> paramMap = createParamMap();
		paramMap.put("order", order);
		updateByHql(update, paramMap);
	}

	@Override
	public void cleanUpSurveyQuestionAnswerChoices(Long id, String questionType) throws FrameworkException {
		String delete = "delete from SurveyQuestionAnswerChoice where surveyQuestion.id=:surveyQuestionId and questionType!=:questionType";
		ConcurrentMap<String, Object> paramMap = createParamMap();
		paramMap.put("surveyQuestionId", id);
		paramMap.put("questionType", questionType);
		updateByHql(delete, paramMap);
	}

	@Override
	public List<ComboItem> getNextQuestions(Long order) throws FrameworkException {
		String query = "select new software.simple.solutions.framework.core.pojo.ComboItem(sq.id,str(sq.order),sq.question) from SurveyQuestion sq "
				+ "where sq.order>:order order by sq.order";
		ConcurrentMap<String, Object> paramMap = createParamMap();
		paramMap.put("order", order);
		return createListQuery(query, paramMap);
	}

	@Override
	public void removeUsersFromQuestions(Long surveyId, Long userId) throws FrameworkException {
		String delete = "delete from SurveyQuestionUser squ where squ.survey.id=:surveyId and squ.applicationUser.id=:userId";
		ConcurrentMap<String, Object> paramMap = createParamMap();
		paramMap.put("surveyId", surveyId);
		paramMap.put("userId", userId);
		deleteByHql(delete, paramMap);
	}

}
