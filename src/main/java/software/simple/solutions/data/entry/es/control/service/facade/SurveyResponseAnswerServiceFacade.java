package software.simple.solutions.data.entry.es.control.service.facade;

import java.util.List;

import com.vaadin.ui.UI;

import software.simple.solutions.data.entry.es.control.entities.SurveyResponseAnswer;
import software.simple.solutions.data.entry.es.control.service.ISurveyResponseAnswerService;
import software.simple.solutions.data.entry.es.control.valueobjects.SurveyResponseAnswerVO;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.service.ISuperService;
import software.simple.solutions.framework.core.service.facade.SuperServiceFacade;

public class SurveyResponseAnswerServiceFacade extends SuperServiceFacade<ISurveyResponseAnswerService>
		implements ISurveyResponseAnswerService {

	public SurveyResponseAnswerServiceFacade(UI ui, Class<? extends ISuperService> s) {
		super(ui, s);
	}

	public static SurveyResponseAnswerServiceFacade get(UI ui) {
		return new SurveyResponseAnswerServiceFacade(ui, ISurveyResponseAnswerService.class);
	}

	@Override
	public List<SurveyResponseAnswer> getSurveyResponseAnswers(Long surveyResponseId) throws FrameworkException {
		return service.getSurveyResponseAnswers(surveyResponseId);
	}

	@Override
	public SurveyResponseAnswer getSurveyResponseAnswer(Long surveyResponseId, Long surveyQuestionId)
			throws FrameworkException {
		return service.getSurveyResponseAnswer(surveyResponseId, surveyQuestionId);
	}

	@Override
	public List<SurveyResponseAnswer> getSurveyResponseAnswers(Long surveyResponseId, Long surveyQuestionId)
			throws FrameworkException {
		return service.getSurveyResponseAnswers(surveyResponseId, surveyQuestionId);
	}

	@Override
	public SurveyResponseAnswer updateAnswerForSingle(SurveyResponseAnswerVO surveyResponseAnswerVO)
			throws FrameworkException {
		return service.updateAnswerForSingle(surveyResponseAnswerVO);
	}

	@Override
	public SurveyResponseAnswer updateAnswerMultipleSelection(SurveyResponseAnswerVO surveyResponseAnswerVO)
			throws FrameworkException {
		return service.updateAnswerMultipleSelection(surveyResponseAnswerVO);
	}

	@Override
	public SurveyResponseAnswer updateAnswerSingleSelection(SurveyResponseAnswerVO surveyResponseAnswerVO)
			throws FrameworkException {
		return service.updateAnswerSingleSelection(surveyResponseAnswerVO);
	}

	@Override
	public SurveyResponseAnswer getSurveyResponseAnswer(Long surveyResponseId, Long surveyQuestionId,
			Long surveyQuestionAnswerChoiceRowId) throws FrameworkException {
		return service.getSurveyResponseAnswer(surveyResponseId, surveyQuestionId, surveyQuestionAnswerChoiceRowId);
	}

	@Override
	public SurveyResponseAnswer getSurveyResponseAnswer(Long surveyResponseId, Long surveyQuestionId,
			Long surveyQuestionAnswerChoiceRowId, Long surveyQuestionAnswerChoiceColumnId) throws FrameworkException {
		return service.getSurveyResponseAnswer(surveyResponseId, surveyQuestionId, surveyQuestionAnswerChoiceRowId,
				surveyQuestionAnswerChoiceColumnId);
	}

	@Override
	public SurveyResponseAnswer getSurveyResponseAnswer(Long surveyResponseId, Long surveyQuestionId,
			Long surveyQuestionAnswerChoiceRowId, Long surveyQuestionAnswerChoiceColumnId,
			Long surveyQuestionAnswerChoiceSelectionId) throws FrameworkException {
		return service.getSurveyResponseAnswer(surveyResponseId, surveyQuestionId, surveyQuestionAnswerChoiceRowId,
				surveyQuestionAnswerChoiceColumnId, surveyQuestionAnswerChoiceSelectionId);
	}

	@Override
	public SurveyResponseAnswer updateAnswerOtherSelection(SurveyResponseAnswerVO vo) throws FrameworkException {
		return service.updateAnswerOtherSelection(vo);
	}

	@Override
	public SurveyResponseAnswer updateAnswerMatrixCellForText(SurveyResponseAnswerVO surveyResponseAnswerVO)
			throws FrameworkException {
		return service.updateAnswerMatrixCellForText(surveyResponseAnswerVO);
	}

	@Override
	public SurveyResponseAnswer updateAnswerMatrixCellForSelection(SurveyResponseAnswerVO surveyResponseAnswerVO)
			throws FrameworkException {
		return service.updateAnswerMatrixCellForSelection(surveyResponseAnswerVO);
	}

	@Override
	public void createUpdateAnswerHistory(SurveyResponseAnswer surveyResponseAnswer) throws FrameworkException {
		service.createUpdateAnswerHistory(surveyResponseAnswer);
	}

}
