package software.simple.solutions.data.entry.es.control.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

import software.simple.solutions.data.entry.es.control.entities.Survey;
import software.simple.solutions.data.entry.es.control.entities.SurveyApplicationUser;
import software.simple.solutions.data.entry.es.control.entities.SurveyQuestion;
import software.simple.solutions.data.entry.es.control.entities.SurveyQuestionUser;
import software.simple.solutions.data.entry.es.control.properties.SurveyApplicationUserProperty;
import software.simple.solutions.data.entry.es.control.repository.ISurveyApplicationUserRepository;
import software.simple.solutions.data.entry.es.control.service.ISurveyApplicationUserService;
import software.simple.solutions.data.entry.es.control.service.ISurveyQuestionService;
import software.simple.solutions.data.entry.es.control.valueobjects.SurveyApplicationUserVO;
import software.simple.solutions.framework.core.annotations.ServiceRepository;
import software.simple.solutions.framework.core.entities.ApplicationUser;
import software.simple.solutions.framework.core.exceptions.Arg;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.SystemMessageProperty;
import software.simple.solutions.framework.core.service.impl.SuperService;
import software.simple.solutions.framework.core.valueobjects.SuperVO;

@Transactional(propagation=Propagation.REQUIRED, rollbackFor = Exception.class)
@Service
@ServiceRepository(claz = ISurveyApplicationUserRepository.class)
public class SurveyApplicationUserService extends SuperService implements ISurveyApplicationUserService {

	@Autowired
	private ISurveyApplicationUserRepository surveyApplicationUserRepository;

	@Autowired
	private ISurveyQuestionService surveyQuestionService;

	@Override
	public <T, R extends SuperVO> T updateSingle(R valueObject) throws FrameworkException {
		SurveyApplicationUserVO vo = (SurveyApplicationUserVO) valueObject;

		if (vo.getApplicationUserId() == null) {
			throw new FrameworkException(SystemMessageProperty.FIELD_IS_REQUIRED,
					new Arg().key(SurveyApplicationUserProperty.APPLICATION_USER));
		}

		if (vo.getSurveyId() == null) {
			throw new FrameworkException(SystemMessageProperty.FIELD_IS_REQUIRED,
					new Arg().key(SurveyApplicationUserProperty.SURVEY));
		}

		SurveyApplicationUser surveyApplicationUser = new SurveyApplicationUser();
		if (!vo.isNew()) {
			surveyApplicationUser = get(SurveyApplicationUser.class, vo.getId());
		}
		Survey survey = get(Survey.class, vo.getSurveyId());
		surveyApplicationUser.setSurvey(survey);
		surveyApplicationUser.setApplicationUser(get(ApplicationUser.class, vo.getApplicationUserId()));

		if (vo.isNew()) {
			addUsersToQuestion(surveyApplicationUser);
		}

		return (T) saveOrUpdate(surveyApplicationUser, vo.isNew());
	}

	private void addUsersToQuestion(SurveyApplicationUser surveyApplicationUser) throws FrameworkException {
		List<SurveyQuestion> questions = surveyQuestionService
				.getQuestionList(surveyApplicationUser.getSurvey().getId());
		for (SurveyQuestion question : questions) {
			SurveyQuestionUser surveyQuestionUser = new SurveyQuestionUser();
			surveyQuestionUser.setActive(true);
			surveyQuestionUser.setApplicationUser(surveyApplicationUser.getApplicationUser());
			surveyQuestionUser.setSurvey(surveyApplicationUser.getSurvey());
			surveyQuestionUser.setSurveyQuestion(question);
			saveOrUpdate(surveyQuestionUser, true);
		}
	}

	@Override
	public List<ApplicationUser> findApplicationUserBySurvey(Long surveyId) throws FrameworkException {
		return surveyApplicationUserRepository.findApplicationUserBySurvey(surveyId);
	}

	@Override
	public <T> Integer delete(List<T> entities) throws FrameworkException {
		List<SurveyApplicationUser> surveyApplicationUsers = (List<SurveyApplicationUser>) entities;
		if (surveyApplicationUsers != null) {
			for (SurveyApplicationUser surveyApplicationUser : surveyApplicationUsers) {
				Long surveyId = surveyApplicationUser.getSurvey().getId();
				Long userId = surveyApplicationUser.getApplicationUser().getId();
				surveyQuestionService.removeUsersFromQuestions(surveyId, userId);
			}
		}
		return super.delete(entities);
	}

}
