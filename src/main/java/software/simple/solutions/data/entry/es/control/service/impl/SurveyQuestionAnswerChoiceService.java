package software.simple.solutions.data.entry.es.control.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

import software.simple.solutions.data.entry.es.control.constants.Axis;
import software.simple.solutions.data.entry.es.control.constants.MatrixColumnType;
import software.simple.solutions.data.entry.es.control.entities.SurveyQuestion;
import software.simple.solutions.data.entry.es.control.entities.SurveyQuestionAnswerChoice;
import software.simple.solutions.data.entry.es.control.properties.SurveyQuestionAnswerChoiceProperty;
import software.simple.solutions.data.entry.es.control.repository.ISurveyQuestionAnswerChoiceRepository;
import software.simple.solutions.data.entry.es.control.repository.ISurveyQuestionAnswerChoiceSelectionRepository;
import software.simple.solutions.data.entry.es.control.repository.ISurveyRepository;
import software.simple.solutions.data.entry.es.control.service.ISurveyQuestionAnswerChoiceService;
import software.simple.solutions.data.entry.es.control.valueobjects.SurveyQuestionAnswerChoiceVO;
import software.simple.solutions.framework.core.annotations.ServiceRepository;
import software.simple.solutions.framework.core.exceptions.Arg;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.SystemMessageProperty;
import software.simple.solutions.framework.core.service.impl.SuperService;
import software.simple.solutions.framework.core.valueobjects.SuperVO;

@Transactional(propagation=Propagation.REQUIRED, rollbackFor = Exception.class)
@Service
@ServiceRepository(claz = ISurveyRepository.class)
public class SurveyQuestionAnswerChoiceService extends SuperService implements ISurveyQuestionAnswerChoiceService {

	@Autowired
	private ISurveyQuestionAnswerChoiceRepository surveyQuestionAnswerChoiceRepository;

	@Autowired
	private ISurveyQuestionAnswerChoiceSelectionRepository surveyQuestionAnswerChoiceSelectionRepository;

	@Override
	public <T, R extends SuperVO> T updateSingle(R valueObject) throws FrameworkException {
		SurveyQuestionAnswerChoiceVO vo = (SurveyQuestionAnswerChoiceVO) valueObject;

		if (StringUtils.isBlank(vo.getLabel())) {
			throw new FrameworkException(SystemMessageProperty.FIELD_IS_REQUIRED,
					new Arg().key(SurveyQuestionAnswerChoiceProperty.LABEL));
		}

		SurveyQuestionAnswerChoice surveyQuestionAnswerChoice = new SurveyQuestionAnswerChoice();
		if (!vo.isNew()) {
			surveyQuestionAnswerChoice = get(SurveyQuestionAnswerChoice.class, vo.getId());
		}
		surveyQuestionAnswerChoice.setIndex(vo.getIndex());
		surveyQuestionAnswerChoice.setLabel(vo.getLabel());
		surveyQuestionAnswerChoice.setQuestionType(vo.getQuestionType());
		surveyQuestionAnswerChoice.setSurveyQuestion(get(SurveyQuestion.class, vo.getSurveyQuestionId()));

		return (T) saveOrUpdate(surveyQuestionAnswerChoice, vo.isNew());
	}

	@Override
	public List<SurveyQuestionAnswerChoice> findBySurveyQuestion(Long surveyQuestionId) throws FrameworkException {
		return surveyQuestionAnswerChoiceRepository.findBySurveyQuestion(surveyQuestionId);
	}

	@Override
	public List<SurveyQuestionAnswerChoice> findBySurveyQuestion(Long surveyQuestionId, String axis)
			throws FrameworkException {
		return surveyQuestionAnswerChoiceRepository.findBySurveyQuestion(surveyQuestionId, axis);
	}

	@Override
	public List<SurveyQuestionAnswerChoice> findBySurveyQuestionChoiceIds(List<Long> ids, String axis)
			throws FrameworkException {
		return surveyQuestionAnswerChoiceRepository.findBySurveyQuestionChoiceIds(ids, axis);
	}

	@Override
	public SurveyQuestionAnswerChoice createNewRow(Long surveyQuestionId, String questionType, Integer index)
			throws FrameworkException {
		surveyQuestionAnswerChoiceRepository.updateIndexes(surveyQuestionId, Axis.ROW, index);
		SurveyQuestionAnswerChoice surveyQuestionAnswerChoice = new SurveyQuestionAnswerChoice();
		surveyQuestionAnswerChoice.setAxis(Axis.ROW);
		surveyQuestionAnswerChoice.setQuestionType(questionType);
		surveyQuestionAnswerChoice.setIndex(index);
		surveyQuestionAnswerChoice.setSurveyQuestion(get(SurveyQuestion.class, surveyQuestionId));

		return saveOrUpdate(surveyQuestionAnswerChoice, true);
	}

	@Override
	public SurveyQuestionAnswerChoice createNewColumn(Long surveyQuestionId, String questionType, Integer index)
			throws FrameworkException {
		surveyQuestionAnswerChoiceRepository.updateIndexes(surveyQuestionId, Axis.COLUMN, index);
		SurveyQuestionAnswerChoice surveyQuestionAnswerChoice = new SurveyQuestionAnswerChoice();
		surveyQuestionAnswerChoice.setAxis(Axis.COLUMN);
		surveyQuestionAnswerChoice.setQuestionType(questionType);
		surveyQuestionAnswerChoice.setIndex(index);
		surveyQuestionAnswerChoice.setMatrixColumnType(MatrixColumnType.TEXT);
		surveyQuestionAnswerChoice.setSurveyQuestion(get(SurveyQuestion.class, surveyQuestionId));

		return saveOrUpdate(surveyQuestionAnswerChoice, true);
	}

	@Override
	public void updateLabel(SurveyQuestionAnswerChoiceVO vo) throws FrameworkException {
		SurveyQuestionAnswerChoice surveyQuestionAnswerChoice = get(SurveyQuestionAnswerChoice.class, vo.getId());
		surveyQuestionAnswerChoice.setLabel(vo.getLabel());
		saveOrUpdate(surveyQuestionAnswerChoice, false);
	}

	@Override
	public void updateMatrixColumnType(SurveyQuestionAnswerChoiceVO vo) throws FrameworkException {
		SurveyQuestionAnswerChoice surveyQuestionAnswerChoice = get(SurveyQuestionAnswerChoice.class, vo.getId());
		surveyQuestionAnswerChoice.setMatrixColumnType(vo.getMatrixColumnType());
		surveyQuestionAnswerChoice.setMultipleSelection(vo.getMultipleSelection());
		saveOrUpdate(surveyQuestionAnswerChoice, false);
	}

	@Override
	public void updateQuestionType(Long id, String questionType) throws FrameworkException {
		SurveyQuestionAnswerChoice surveyQuestionAnswerChoice = get(SurveyQuestionAnswerChoice.class, id);
		surveyQuestionAnswerChoice.setQuestionType(questionType);
		saveOrUpdate(surveyQuestionAnswerChoice, false);
	}

	@Override
	public void updateMinLength(Long id, Long minLength) throws FrameworkException {
		SurveyQuestionAnswerChoice surveyQuestionAnswerChoice = get(SurveyQuestionAnswerChoice.class, id);
		surveyQuestionAnswerChoice.setMinLength(minLength);
		saveOrUpdate(surveyQuestionAnswerChoice, false);
	}

	@Override
	public void updateMaxLength(Long id, Long maxLength) throws FrameworkException {
		SurveyQuestionAnswerChoice surveyQuestionAnswerChoice = get(SurveyQuestionAnswerChoice.class, id);
		surveyQuestionAnswerChoice.setMaxLength(maxLength);
		saveOrUpdate(surveyQuestionAnswerChoice, false);
	}

	@Override
	public void updateMinValue(Long id, BigDecimal minValue) throws FrameworkException {
		SurveyQuestionAnswerChoice surveyQuestionAnswerChoice = get(SurveyQuestionAnswerChoice.class, id);
		surveyQuestionAnswerChoice.setMinValue(minValue);
		saveOrUpdate(surveyQuestionAnswerChoice, false);
	}

	@Override
	public void updateMaxValue(Long id, BigDecimal maxValue) throws FrameworkException {
		SurveyQuestionAnswerChoice surveyQuestionAnswerChoice = get(SurveyQuestionAnswerChoice.class, id);
		surveyQuestionAnswerChoice.setMaxValue(maxValue);
		saveOrUpdate(surveyQuestionAnswerChoice, false);
	}

	@Override
	public <T> Integer delete(Class<T> cl, Long id) throws FrameworkException {
		surveyQuestionAnswerChoiceSelectionRepository.deleteBySurveyQuestionAnswerChoice(id);
		return super.delete(cl, id);
	}

	@Override
	public void updateIsOther(Long id, Boolean isOther) throws FrameworkException {
		SurveyQuestionAnswerChoice surveyQuestionAnswerChoice = get(SurveyQuestionAnswerChoice.class, id);
		surveyQuestionAnswerChoice.setIsOther(isOther);
		saveOrUpdate(surveyQuestionAnswerChoice, false);
	}

	@Override
	public void updateMakeSelectedQuestionRequired(Long id, Long selectedQuestionId) throws FrameworkException {
		SurveyQuestionAnswerChoice surveyQuestionAnswerChoice = get(SurveyQuestionAnswerChoice.class, id);
		surveyQuestionAnswerChoice.setMakeSelectedQuestionRequired(selectedQuestionId);
		saveOrUpdate(surveyQuestionAnswerChoice, false);
	}

	@Override
	public void updateMakeSelectedGroupRequired(Long id, Long selectedGroupId) throws FrameworkException {
		SurveyQuestionAnswerChoice surveyQuestionAnswerChoice = get(SurveyQuestionAnswerChoice.class, id);
		surveyQuestionAnswerChoice.setMakeSelectedGroupRequired(selectedGroupId);
		saveOrUpdate(surveyQuestionAnswerChoice, false);
	}

	@Override
	public List<SurveyQuestionAnswerChoice> findBySurvey(Long surveyId) throws FrameworkException {
		return surveyQuestionAnswerChoiceRepository.findBySurvey(surveyId);
	}

	@Override
	public void deleteAndUpdateIndex(Class<SurveyQuestionAnswerChoice> surveyQuestionAnswerChoiceClass,
			Long surveyQuestionAnswerChoiceId, Long surveyQuestionId, String axis, Integer componentIndex)
			throws FrameworkException {
		delete(surveyQuestionAnswerChoiceClass, surveyQuestionAnswerChoiceId);
		surveyQuestionAnswerChoiceRepository.updateIndexesForDelete(surveyQuestionId, axis, componentIndex);
	}
}
