package software.simple.solutions.data.entry.es.control.repository.impl;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import software.simple.solutions.data.entry.es.control.entities.SurveyQuestionAnswerChoice;
import software.simple.solutions.data.entry.es.control.repository.ISurveyQuestionAnswerChoiceRepository;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.repository.impl.GenericRepository;

@Repository
public class SurveyQuestionAnswerChoiceRepository extends GenericRepository
		implements ISurveyQuestionAnswerChoiceRepository {

	@Override
	public List<SurveyQuestionAnswerChoice> findBySurveyQuestion(Long surveyQuestionId) throws FrameworkException {
		return findBySurveyQuestion(surveyQuestionId, null);
	}

	@Override
	public List<SurveyQuestionAnswerChoice> findBySurveyQuestion(Long surveyQuestionId, String axis)
			throws FrameworkException {
		String query = "from SurveyQuestionAnswerChoice where surveyQuestion.id=:surveyQuestionId";
		ConcurrentMap<String, Object> paramMap = createParamMap();
		paramMap.put("surveyQuestionId", surveyQuestionId);
		if (StringUtils.isNotBlank(axis)) {
			query += " and axis=:axis";
			paramMap.put("axis", axis);
		}
		query += " order by index";

		return createListQuery(query, paramMap);
	}

	@Override
	public void updateIndexes(Long surveyQuestionId, String axisType, Integer index) throws FrameworkException {
		String update = "update SurveyQuestionAnswerChoice set index = index+1 where index>=:index and surveyQuestion.id=:surveyQuestionId and axis=:axis";
		ConcurrentMap<String, Object> paramMap = createParamMap();
		paramMap.put("index", index);
		paramMap.put("surveyQuestionId", surveyQuestionId);
		paramMap.put("axis", axisType);
		updateByHql(update, paramMap);
	}

	@Override
	public void deleteBySurveyQuestion(Long surveyQuestionId) throws FrameworkException {
		String query = "delete from SurveyQuestionAnswerChoiceSelection where surveyQuestionAnswerChoice.id "
				+ "in (select id from SurveyQuestionAnswerChoice where surveyQuestion.id=:surveyQuestionId)";
		ConcurrentMap<String, Object> paramMap = createParamMap();
		paramMap.put("surveyQuestionId", surveyQuestionId);
		updateByHql(query, paramMap);

		query = "delete from SurveyQuestionAnswerChoice where surveyQuestion.id=:surveyQuestionId";
		paramMap.put("surveyQuestionId", surveyQuestionId);
		updateByHql(query, paramMap);
	}

	@Override
	public List<SurveyQuestionAnswerChoice> findBySurvey(Long surveyId) throws FrameworkException {
		String query = "from SurveyQuestionAnswerChoice where surveyQuestion.survey.id=:surveyId";
		ConcurrentMap<String, Object> paramMap = createParamMap();
		paramMap.put("surveyId", surveyId);
		return createListQuery(query, paramMap);
	}

}
