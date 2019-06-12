package software.simple.solutions.data.entry.es.control.service.facade;

import com.vaadin.ui.UI;

import software.simple.solutions.data.entry.es.control.entities.SurveyResponseAnswerHistory;
import software.simple.solutions.data.entry.es.control.service.ISurveyResponseAnswerHistoryService;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.service.ISuperService;
import software.simple.solutions.framework.core.service.facade.SuperServiceFacade;

public class SurveyResponseAnswerHistoryServiceFacade extends SuperServiceFacade<ISurveyResponseAnswerHistoryService>
		implements ISurveyResponseAnswerHistoryService {

	public SurveyResponseAnswerHistoryServiceFacade(UI ui, Class<? extends ISuperService> s) {
		super(ui, s);
	}

	public static SurveyResponseAnswerHistoryServiceFacade get(UI ui) {
		return new SurveyResponseAnswerHistoryServiceFacade(ui, ISurveyResponseAnswerHistoryService.class);
	}

	@Override
	public SurveyResponseAnswerHistory getSurveyResponseAnswerHistoryByResponseAndQuestion(Long surveyResponseId,
			Long surveyQuestionId) throws FrameworkException {
		return service.getSurveyResponseAnswerHistoryByResponseAndQuestion(surveyResponseId, surveyQuestionId);
	}
}
