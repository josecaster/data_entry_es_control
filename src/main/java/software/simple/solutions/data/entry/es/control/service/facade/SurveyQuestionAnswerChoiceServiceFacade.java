package software.simple.solutions.data.entry.es.control.service.facade;

import java.math.BigDecimal;
import java.util.List;

import com.vaadin.ui.UI;

import software.simple.solutions.data.entry.es.control.entities.SurveyQuestionAnswerChoice;
import software.simple.solutions.data.entry.es.control.service.ISurveyQuestionAnswerChoiceService;
import software.simple.solutions.data.entry.es.control.valueobjects.SurveyQuestionAnswerChoiceVO;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.service.ISuperService;
import software.simple.solutions.framework.core.service.facade.SuperServiceFacade;

public class SurveyQuestionAnswerChoiceServiceFacade extends SuperServiceFacade<ISurveyQuestionAnswerChoiceService>
		implements ISurveyQuestionAnswerChoiceService {

	public SurveyQuestionAnswerChoiceServiceFacade(UI ui, Class<? extends ISuperService> s) {
		super(ui, s);
	}

	public static SurveyQuestionAnswerChoiceServiceFacade get(UI ui) {
		return new SurveyQuestionAnswerChoiceServiceFacade(ui, ISurveyQuestionAnswerChoiceService.class);
	}

	@Override
	public List<SurveyQuestionAnswerChoice> findBySurveyQuestion(Long surveyQuestionId) throws FrameworkException {
		return service.findBySurveyQuestion(surveyQuestionId);
	}

	@Override
	public List<SurveyQuestionAnswerChoice> findBySurveyQuestion(Long surveyQuestionId, String axis)
			throws FrameworkException {
		return service.findBySurveyQuestion(surveyQuestionId, axis);
	}

	@Override
	public SurveyQuestionAnswerChoice createNewRow(Long surveyQuestionId, String questionType, Integer index)
			throws FrameworkException {
		return service.createNewRow(surveyQuestionId, questionType, index);
	}

	@Override
	public SurveyQuestionAnswerChoice createNewColumn(Long surveyQuestionId, String questionType, Integer index)
			throws FrameworkException {
		return service.createNewColumn(surveyQuestionId, questionType, index);
	}

	@Override
	public void updateLabel(SurveyQuestionAnswerChoiceVO surveyQuestionAnswerChoiceVO) throws FrameworkException {
		service.updateLabel(surveyQuestionAnswerChoiceVO);
	}

	@Override
	public void updateMatrixColumnType(SurveyQuestionAnswerChoiceVO surveyQuestionAnswerChoiceVO)
			throws FrameworkException {
		service.updateMatrixColumnType(surveyQuestionAnswerChoiceVO);
	}

	@Override
	public void updateQuestionType(Long id, String questionType) throws FrameworkException {
		service.updateQuestionType(id, questionType);
	}

	@Override
	public void updateMinLength(Long id, Long minLength) throws FrameworkException {
		service.updateMinLength(id, minLength);
	}

	@Override
	public void updateMaxLength(Long id, Long maxLength) throws FrameworkException {
		service.updateMaxLength(id, maxLength);
	}

	@Override
	public void updateMinValue(Long id, BigDecimal minValue) throws FrameworkException {
		service.updateMinValue(id, minValue);
	}

	@Override
	public void updateMaxValue(Long id, BigDecimal maxValue) throws FrameworkException {
		service.updateMaxValue(id, maxValue);
	}

	@Override
	public void updateIsOther(Long id, Boolean isOther) throws FrameworkException {
		service.updateIsOther(id, isOther);
	}

	@Override
	public void updateMakeSelectedQuestionRequired(Long id, Long selectedQuestionId) throws FrameworkException {
		service.updateMakeSelectedQuestionRequired(id, selectedQuestionId);
	}

	@Override
	public void updateMakeSelectedGroupRequired(Long id, Long selectedGroupId) throws FrameworkException {
		service.updateMakeSelectedGroupRequired(id, selectedGroupId);
	}

	@Override
	public List<SurveyQuestionAnswerChoice> findBySurvey(Long surveyId) throws FrameworkException {
		return service.findBySurvey(surveyId);
	}

	@Override
	public void deleteAndUpdateIndex(Class<SurveyQuestionAnswerChoice> surveyQuestionAnswerChoiceClass,
			Long surveyQuestionAnswerChoiceId, Long surveyQuestionId, String axis, Integer componentIndex)
			throws FrameworkException {
		service.deleteAndUpdateIndex(surveyQuestionAnswerChoiceClass, surveyQuestionAnswerChoiceId, surveyQuestionId,
				axis, componentIndex);
	}

	@Override
	public List<SurveyQuestionAnswerChoice> findBySurveyQuestionChoiceIds(List<Long> ids, String axis)
			throws FrameworkException {
		return service.findBySurveyQuestionChoiceIds(ids, axis);
	}
}
