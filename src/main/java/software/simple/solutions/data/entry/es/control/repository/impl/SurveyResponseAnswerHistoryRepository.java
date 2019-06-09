package software.simple.solutions.data.entry.es.control.repository.impl;

import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Repository;

import software.simple.solutions.data.entry.es.control.entities.SurveyResponseAnswerHistory;
import software.simple.solutions.data.entry.es.control.repository.ISurveyResponseAnswerHistoryRepository;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.repository.impl.GenericRepository;

@Repository
public class SurveyResponseAnswerHistoryRepository extends GenericRepository
		implements ISurveyResponseAnswerHistoryRepository {

	@Override
	public SurveyResponseAnswerHistory getSurveyResponseAnswerHistoryByResponseAndQuestion(Long surveyResponseId,
			Long surveyQuestionId) throws FrameworkException {
		ConcurrentMap<String, Object> paramMap = createParamMap();
		String query = "from SurveyResponseAnswerHistory where surveyResponse.id=:surveyResponseId "
				+ "and surveyQuestion.id=:surveyQuestionId";
		paramMap.put("surveyResponseId", surveyResponseId);
		paramMap.put("surveyQuestionId", surveyQuestionId);
		return getByQuery(query, paramMap);
	}

}
