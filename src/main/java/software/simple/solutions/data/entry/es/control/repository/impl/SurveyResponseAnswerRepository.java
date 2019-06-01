package software.simple.solutions.data.entry.es.control.repository.impl;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Repository;

import software.simple.solutions.data.entry.es.control.entities.SurveyResponseAnswer;
import software.simple.solutions.data.entry.es.control.repository.ISurveyResponseAnswerRepository;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.repository.impl.GenericRepository;

@Repository
public class SurveyResponseAnswerRepository extends GenericRepository implements ISurveyResponseAnswerRepository {

	@Override
	public SurveyResponseAnswer getByUniqueId(String uniqueId) throws FrameworkException {
		ConcurrentMap<String, Object> paramMap = createParamMap();
		String query = "from SurveyResponseAnswer where uniqueId=:uniqueId";
		paramMap.put("uniqueId", uniqueId);
		return getByQuery(query, paramMap);
	}

	@Override
	public List<SurveyResponseAnswer> getSurveyResponseAnswers(Long surveyResponseId) throws FrameworkException {
		ConcurrentMap<String, Object> paramMap = createParamMap();
		String query = "select sra from SurveyResponseAnswer sra where sra.surveyResponse.id=:surveyResponseId";
		paramMap.put("surveyResponseId", surveyResponseId);
		return createListQuery(query, paramMap);
	}

	@Override
	public void removeAllBySurveyResponse(Long surveyResponseId) throws FrameworkException {
		ConcurrentMap<String, Object> paramMap = createParamMap();
		String delete = "delete from SurveyResponseAnswer where surveyResponse.id=:surveyResponseId";
		paramMap.put("surveyResponseId", surveyResponseId);
		deleteByHql(delete, paramMap);

	}

	@Override
	public SurveyResponseAnswer getSurveyResponse(Long surveyResponseId, Long surveyQuestionId)
			throws FrameworkException {
		ConcurrentMap<String, Object> paramMap = createParamMap();
		String query = "from SurveyResponseAnswer where surveyResponse.id=:surveyResponseId and surveyQuestion.id=:surveyQuestionId";
		paramMap.put("surveyResponseId", surveyResponseId);
		paramMap.put("surveyQuestionId", surveyQuestionId);
		return getByQuery(query, paramMap);
	}

	@Override
	public List<SurveyResponseAnswer> getSurveyResponseAnswers(Long surveyResponseId, Long surveyQuestionId)
			throws FrameworkException {
		ConcurrentMap<String, Object> paramMap = createParamMap();
		String query = "from SurveyResponseAnswer where surveyResponse.id=:surveyResponseId and surveyQuestion.id=:surveyQuestionId";
		paramMap.put("surveyResponseId", surveyResponseId);
		paramMap.put("surveyQuestionId", surveyQuestionId);
		return createListQuery(query, paramMap);
	}

}
