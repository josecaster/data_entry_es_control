package software.simple.solutions.data.entry.es.control.service.facade;

import java.util.List;

import com.vaadin.ui.UI;

import software.simple.solutions.data.entry.es.control.entities.SurveyResponse;
import software.simple.solutions.data.entry.es.control.rest.model.SurveyResponseRestModel;
import software.simple.solutions.data.entry.es.control.service.ISurveyResponseService;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.service.facade.SuperServiceFacade;

public class SurveyResponseServiceFacade extends SuperServiceFacade<ISurveyResponseService>
		implements ISurveyResponseService {

	public SurveyResponseServiceFacade(UI ui, Class<ISurveyResponseService> s) {
		super(ui, s);
	}

	public static SurveyResponseServiceFacade get(UI ui) {
		return new SurveyResponseServiceFacade(ui, ISurveyResponseService.class);
	}

	@Override
	public SurveyResponse updateFromRest(SurveyResponseRestModel surveyResponseRestModel) throws FrameworkException {
		return service.updateFromRest(surveyResponseRestModel);
	}

	@Override
	public List<SurveyResponse> findAllSurveyResponsesByUser(String username) throws FrameworkException {
		return service.findAllSurveyResponsesByUser(username);
	}

	@Override
	public List<String> findAllActiveSurveyResponseUuIdsByUser(String username) throws FrameworkException {
		return service.findAllActiveSurveyResponseUuIdsByUser(username);
	}

	public Boolean removeAllFormData(Long surveyResponseId) throws FrameworkException {
		return service.removeAllFormData(surveyResponseId);
	}
}
