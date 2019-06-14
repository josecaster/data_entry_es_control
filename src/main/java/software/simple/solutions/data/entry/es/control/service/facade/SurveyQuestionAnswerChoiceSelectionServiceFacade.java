package software.simple.solutions.data.entry.es.control.service.facade;

import java.util.List;

import com.vaadin.ui.UI;

import software.simple.solutions.data.entry.es.control.entities.SurveyQuestionAnswerChoiceSelection;
import software.simple.solutions.data.entry.es.control.service.ISurveyQuestionAnswerChoiceSelectionService;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.service.facade.SuperServiceFacade;

public class SurveyQuestionAnswerChoiceSelectionServiceFacade
		extends SuperServiceFacade<ISurveyQuestionAnswerChoiceSelectionService>
		implements ISurveyQuestionAnswerChoiceSelectionService {

	public SurveyQuestionAnswerChoiceSelectionServiceFacade(UI ui, Class<ISurveyQuestionAnswerChoiceSelectionService> s) {
		super(ui, s);
	}

	public static SurveyQuestionAnswerChoiceSelectionServiceFacade get(UI ui) {
		return new SurveyQuestionAnswerChoiceSelectionServiceFacade(ui,
				ISurveyQuestionAnswerChoiceSelectionService.class);
	}

	@Override
	public List<SurveyQuestionAnswerChoiceSelection> getBySurveyQuestionAnswerChoice(Long surveyQuestionAnswerChoiceId)
			throws FrameworkException {
		return service.getBySurveyQuestionAnswerChoice(surveyQuestionAnswerChoiceId);
	}

	@Override
	public void updateLabel(Long id, String value) throws FrameworkException {
		service.updateLabel(id, value);
	}

	@Override
	public SurveyQuestionAnswerChoiceSelection create(Long surveyQuestionAnswerChoiceId, Integer index)
			throws FrameworkException {
		return service.create(surveyQuestionAnswerChoiceId, index);
	}

	@Override
	public List<SurveyQuestionAnswerChoiceSelection> findBySurvey(Long surveyId) throws FrameworkException {
		return service.findBySurvey(surveyId);
	}

	@Override
	public void deleteAndUpdateIndex(
			Class<SurveyQuestionAnswerChoiceSelection> surveyQuestionAnswerChoiceSelectionClass,
			Long surveyQuestionAnswerChoiceSelectionId, Long surveyQuestionAnswerChoiceId, Integer componentIndex)
			throws FrameworkException {
		service.deleteAndUpdateIndex(surveyQuestionAnswerChoiceSelectionClass, surveyQuestionAnswerChoiceSelectionId,
				surveyQuestionAnswerChoiceId, componentIndex);
	}
}
