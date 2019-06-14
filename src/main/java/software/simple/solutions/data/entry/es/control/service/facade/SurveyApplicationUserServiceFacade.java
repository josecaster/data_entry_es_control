package software.simple.solutions.data.entry.es.control.service.facade;

import java.util.List;

import com.vaadin.ui.UI;

import software.simple.solutions.data.entry.es.control.service.ISurveyApplicationUserService;
import software.simple.solutions.framework.core.entities.ApplicationUser;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.service.facade.SuperServiceFacade;

public class SurveyApplicationUserServiceFacade extends SuperServiceFacade<ISurveyApplicationUserService>
		implements ISurveyApplicationUserService {

	public SurveyApplicationUserServiceFacade(UI ui, Class<ISurveyApplicationUserService> s) {
		super(ui, s);
	}

	public static SurveyApplicationUserServiceFacade get(UI ui) {
		return new SurveyApplicationUserServiceFacade(ui, ISurveyApplicationUserService.class);
	}

	@Override
	public List<ApplicationUser> findApplicationUserBySurvey(Long surveyId) throws FrameworkException {
		return service.findApplicationUserBySurvey(surveyId);
	}
}
