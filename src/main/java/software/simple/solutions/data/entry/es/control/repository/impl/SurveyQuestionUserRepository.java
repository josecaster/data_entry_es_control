package software.simple.solutions.data.entry.es.control.repository.impl;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Repository;

import software.simple.solutions.data.entry.es.control.entities.SurveyQuestionUser;
import software.simple.solutions.data.entry.es.control.repository.ISurveyQuestionUserRepository;
import software.simple.solutions.framework.core.entities.ApplicationUser;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.repository.impl.GenericRepository;

@Repository
public class SurveyQuestionUserRepository extends GenericRepository implements ISurveyQuestionUserRepository {

	@Override
	public SurveyQuestionUser getBySurveyAndQuestionAndUser(Long surveyId, Long questionId, Long userId)
			throws FrameworkException {
		ConcurrentMap<String, Object> paramMap = createParamMap();
		String query = "from SurveyQuestionUser squ where squ.survey.id=:surveyId "
				+ "and squ.surveyQuestion.id=:surveyQuestionId " + "and squ.applicationUser.id=:userId";
		paramMap.put("surveyId", surveyId);
		paramMap.put("surveyQuestionId", questionId);
		paramMap.put("userId", userId);
		return getByQuery(query, paramMap);
	}

	@Override
	public List<ApplicationUser> findBySurveyAndQuestion(Long surveyId, Long questionId) throws FrameworkException {
		ConcurrentMap<String, Object> paramMap = createParamMap();
		String query = "select squ.applicationUser from SurveyQuestionUser squ where squ.survey.id=:surveyId "
				+ "and squ.surveyQuestion.id=:surveyQuestionId ";
		paramMap.put("surveyId", surveyId);
		paramMap.put("surveyQuestionId", questionId);
		return createListQuery(query, paramMap);
	}

	@Override
	public List<SurveyQuestionUser> getSurveyQuestionUserList(Long surveyId) throws FrameworkException {
		ConcurrentMap<String, Object> paramMap = createParamMap();
		String query = "from SurveyQuestionUser squ where squ.survey.id=:surveyId ";
		paramMap.put("surveyId", surveyId);
		return createListQuery(query, paramMap);
	}
}
