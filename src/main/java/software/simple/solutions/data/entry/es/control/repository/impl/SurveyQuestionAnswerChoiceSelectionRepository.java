package software.simple.solutions.data.entry.es.control.repository.impl;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Repository;

import software.simple.solutions.data.entry.es.control.entities.SurveyQuestionAnswerChoiceSelection;
import software.simple.solutions.data.entry.es.control.repository.ISurveyQuestionAnswerChoiceSelectionRepository;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.repository.impl.GenericRepository;

@Repository
public class SurveyQuestionAnswerChoiceSelectionRepository extends GenericRepository
		implements ISurveyQuestionAnswerChoiceSelectionRepository {

	@Override
	public List<SurveyQuestionAnswerChoiceSelection> getBySurveyQuestionAnswerChoice(Long surveyQuestionAnswerChoiceId)
			throws FrameworkException {
		String query = "from SurveyQuestionAnswerChoiceSelection where surveyQuestionAnswerChoice.id=:surveyQuestionAnswerChoiceId";
		ConcurrentMap<String, Object> paramMap = createParamMap();
		paramMap.put("surveyQuestionAnswerChoiceId", surveyQuestionAnswerChoiceId);
		return createListQuery(query, paramMap);
	}

	@Override
	public void deleteBySurveyQuestionAnswerChoice(Long surveyQuestionAnswerChoiceId) throws FrameworkException {
		String query = "delete from SurveyQuestionAnswerChoiceSelection where surveyQuestionAnswerChoice.id=:surveyQuestionAnswerChoiceId";
		ConcurrentMap<String, Object> paramMap = createParamMap();
		paramMap.put("surveyQuestionAnswerChoiceId", surveyQuestionAnswerChoiceId);
		updateByHql(query, paramMap);
	}

	@Override
	public List<SurveyQuestionAnswerChoiceSelection> findBySurvey(Long surveyId) throws FrameworkException {
		String query = "from SurveyQuestionAnswerChoiceSelection where surveyQuestionAnswerChoice.surveyQuestion.survey.id=:surveyId";
		ConcurrentMap<String, Object> paramMap = createParamMap();
		paramMap.put("surveyId", surveyId);
		return createListQuery(query, paramMap);
	}
}
