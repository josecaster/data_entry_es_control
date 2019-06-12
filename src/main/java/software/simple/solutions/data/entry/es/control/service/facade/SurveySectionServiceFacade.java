package software.simple.solutions.data.entry.es.control.service.facade;

import java.util.List;

import com.vaadin.ui.UI;

import software.simple.solutions.data.entry.es.control.entities.SurveySection;
import software.simple.solutions.data.entry.es.control.service.ISurveySectionService;
import software.simple.solutions.data.entry.es.control.valueobjects.SurveySectionVO;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.pojo.ComboItem;
import software.simple.solutions.framework.core.service.ISuperService;
import software.simple.solutions.framework.core.service.facade.SuperServiceFacade;

public class SurveySectionServiceFacade extends SuperServiceFacade<ISurveySectionService>
		implements ISurveySectionService {

	public SurveySectionServiceFacade(UI ui, Class<? extends ISuperService> s) {
		super(ui, s);
	}

	public static SurveySectionServiceFacade get(UI ui) {
		return new SurveySectionServiceFacade(ui, ISurveySectionService.class);
	}

	@Override
	public List<ComboItem> findBySurvey(Long surveyId) throws FrameworkException {
		return service.findBySurvey(surveyId);
	}

	@Override
	public List<SurveySection> findAllBySurveyId(Long id) throws FrameworkException {
		return service.findAllBySurveyId(id);
	}

	@Override
	public SurveySection getPinnedSectionBySurvey(Long id) throws FrameworkException {
		return service.getPinnedSectionBySurvey(id);
	}

	@Override
	public List<ComboItem> getForListing(SurveySectionVO vo) throws FrameworkException {
		return service.getForListing(vo);
	}
}
