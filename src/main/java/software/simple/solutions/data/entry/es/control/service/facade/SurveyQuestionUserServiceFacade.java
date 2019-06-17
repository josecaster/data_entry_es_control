package software.simple.solutions.data.entry.es.control.service.facade;

import java.util.List;

import com.vaadin.ui.UI;

import software.simple.solutions.data.entry.es.control.entities.SurveyQuestionUser;
import software.simple.solutions.data.entry.es.control.service.ISurveyQuestionUserService;
import software.simple.solutions.framework.core.entities.ApplicationUser;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.service.facade.SuperServiceFacade;

public class SurveyQuestionUserServiceFacade extends SuperServiceFacade<ISurveyQuestionUserService>
		implements ISurveyQuestionUserService {

	public SurveyQuestionUserServiceFacade(UI ui, Class<ISurveyQuestionUserService> s) {
		super(ui, s);
	}

	public static SurveyQuestionUserServiceFacade get(UI ui) {
		return new SurveyQuestionUserServiceFacade(ui, ISurveyQuestionUserService.class);
	}

	@Override
	public void addUserToSurveyQuestion(Long surveyId, Long questionId, Long userId) throws FrameworkException {
		service.addUserToSurveyQuestion(surveyId, questionId, userId);
	}

	@Override
	public List<ApplicationUser> findBySurveyAndQuestion(Long surveyId, Long questionId) throws FrameworkException {
		return service.findBySurveyAndQuestion(surveyId, questionId);
	}

	@Override
	public void removeUserFromSurveyQuestion(Long surveyId, Long questionId, Long userId) throws FrameworkException {
		service.removeUserFromSurveyQuestion(surveyId, questionId, userId);
	}

	@Override
	public List<SurveyQuestionUser> getSurveyQuestionUserList(Long id) throws FrameworkException {
		return service.getSurveyQuestionUserList(id);
	}

}
