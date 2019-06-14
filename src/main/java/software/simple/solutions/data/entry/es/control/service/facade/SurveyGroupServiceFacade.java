package software.simple.solutions.data.entry.es.control.service.facade;

import java.util.List;

import com.vaadin.ui.UI;

import software.simple.solutions.data.entry.es.control.entities.SurveyGroup;
import software.simple.solutions.data.entry.es.control.service.ISurveyGroupService;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.pojo.ComboItem;
import software.simple.solutions.framework.core.service.facade.SuperServiceFacade;

public class SurveyGroupServiceFacade extends SuperServiceFacade<ISurveyGroupService> implements ISurveyGroupService {

	public SurveyGroupServiceFacade(UI ui, Class<ISurveyGroupService> s) {
		super(ui, s);
	}

	public static SurveyGroupServiceFacade get(UI ui) {
		return new SurveyGroupServiceFacade(ui, ISurveyGroupService.class);
	}

	@Override
	public List<ComboItem> findBySurvey(Long surveyId) throws FrameworkException {
		return service.findBySurvey(surveyId);
	}

	@Override
	public List<SurveyGroup> findAllBySurveyId(Long id) throws FrameworkException {
		return findAllBySurveyId(id);
	}
}
