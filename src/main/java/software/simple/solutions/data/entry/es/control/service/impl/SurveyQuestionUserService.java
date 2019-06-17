package software.simple.solutions.data.entry.es.control.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import software.simple.solutions.data.entry.es.control.entities.Survey;
import software.simple.solutions.data.entry.es.control.entities.SurveyQuestion;
import software.simple.solutions.data.entry.es.control.entities.SurveyQuestionUser;
import software.simple.solutions.data.entry.es.control.repository.ISurveyQuestionUserRepository;
import software.simple.solutions.data.entry.es.control.service.ISurveyQuestionUserService;
import software.simple.solutions.framework.core.annotations.ServiceRepository;
import software.simple.solutions.framework.core.entities.ApplicationUser;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.service.impl.SuperService;

@Transactional
@Service
@ServiceRepository(claz = ISurveyQuestionUserRepository.class)
public class SurveyQuestionUserService extends SuperService implements ISurveyQuestionUserService {

	@Autowired
	private ISurveyQuestionUserRepository surveyQuestionUserRepository;

	@Override
	public void addUserToSurveyQuestion(Long surveyId, Long questionId, Long userId) throws FrameworkException {
		SurveyQuestionUser surveyQuestionUser = surveyQuestionUserRepository.getBySurveyAndQuestionAndUser(surveyId,
				questionId, userId);
		if (surveyQuestionUser == null) {
			surveyQuestionUser = new SurveyQuestionUser();
			surveyQuestionUser.setActive(true);
			surveyQuestionUser.setApplicationUser(get(ApplicationUser.class, userId));
			surveyQuestionUser.setSurvey(get(Survey.class, surveyId));
			surveyQuestionUser.setSurveyQuestion(get(SurveyQuestion.class, questionId));
			saveOrUpdate(surveyQuestionUser, true);
		}
	}

	@Override
	public List<ApplicationUser> findBySurveyAndQuestion(Long surveyId, Long questionId) throws FrameworkException {
		return surveyQuestionUserRepository.findBySurveyAndQuestion(surveyId, questionId);
	}

	@Override
	public void removeUserFromSurveyQuestion(Long surveyId, Long questionId, Long userId) throws FrameworkException {
		SurveyQuestionUser surveyQuestionUser = surveyQuestionUserRepository.getBySurveyAndQuestionAndUser(surveyId,
				questionId, userId);
		if (surveyQuestionUser != null) {
			delete(SurveyQuestionUser.class, surveyQuestionUser.getId());
		}

	}

	@Override
	public List<SurveyQuestionUser> getSurveyQuestionUserList(Long surveyId) throws FrameworkException {
		return surveyQuestionUserRepository.getSurveyQuestionUserList(surveyId);
	}
}
