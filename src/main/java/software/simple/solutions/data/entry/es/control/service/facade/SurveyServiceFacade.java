package software.simple.solutions.data.entry.es.control.service.facade;

import java.util.List;

import com.vaadin.ui.UI;

import software.simple.solutions.data.entry.es.control.entities.Survey;
import software.simple.solutions.data.entry.es.control.service.ISurveyService;
import software.simple.solutions.framework.core.entities.Configuration;
import software.simple.solutions.framework.core.entities.EntityFile;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.service.facade.SuperServiceFacade;
import software.simple.solutions.framework.core.valueobjects.EntityFileVO;

public class SurveyServiceFacade extends SuperServiceFacade<ISurveyService> implements ISurveyService {

	public SurveyServiceFacade(UI ui, Class<ISurveyService> s) {
		super(ui, s);
	}

	public static SurveyServiceFacade get(UI ui) {
		return new SurveyServiceFacade(ui, ISurveyService.class);
	}

	@Override
	public EntityFile upLoadFile(EntityFileVO vo) throws FrameworkException {
		return service.upLoadFile(vo);
	}

	@Override
	public List<Survey> findAllSurveys() throws FrameworkException {
		return service.findAllSurveys();
	}

	@Override
	public List<Survey> findAllSurveysByUser(String username) throws FrameworkException {
		return service.findAllSurveysByUser(username);
	}

	@Override
	public List<Configuration> getEsControlConfigurations() throws FrameworkException {
		return service.getEsControlConfigurations();
	}
}
