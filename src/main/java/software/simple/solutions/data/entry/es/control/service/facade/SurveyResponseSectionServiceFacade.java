package software.simple.solutions.data.entry.es.control.service.facade;

import java.util.List;

import com.vaadin.ui.UI;

import software.simple.solutions.data.entry.es.control.entities.SurveyResponseSection;
import software.simple.solutions.data.entry.es.control.service.ISurveyResponseSectionService;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.service.facade.SuperServiceFacade;

public class SurveyResponseSectionServiceFacade extends SuperServiceFacade<ISurveyResponseSectionService>
		implements ISurveyResponseSectionService {

	public SurveyResponseSectionServiceFacade(UI ui, Class<ISurveyResponseSectionService> s) {
		super(ui, s);
	}

	public static SurveyResponseSectionServiceFacade get(UI ui) {
		return new SurveyResponseSectionServiceFacade(ui, ISurveyResponseSectionService.class);
	}

	@Override
	public List<SurveyResponseSection> getSurveyResponseSections(Long surveyResponseId) throws FrameworkException {
		return service.getSurveyResponseSections(surveyResponseId);
	}
}
