package software.simple.solutions.data.entry.es.control.service.impl;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import software.simple.solutions.data.entry.es.control.entities.SurveyQuestion;
import software.simple.solutions.data.entry.es.control.entities.SurveyQuestionAnswerChoice;
import software.simple.solutions.data.entry.es.control.entities.SurveyQuestionAnswerChoiceSelection;
import software.simple.solutions.data.entry.es.control.entities.SurveyResponse;
import software.simple.solutions.data.entry.es.control.entities.SurveyResponseAnswer;
import software.simple.solutions.data.entry.es.control.repository.ISurveyResponseAnswerRepository;
import software.simple.solutions.data.entry.es.control.service.ISurveyResponseAnswerService;
import software.simple.solutions.data.entry.es.control.valueobjects.SurveyResponseAnswerVO;
import software.simple.solutions.framework.core.annotations.ServiceRepository;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.service.impl.SuperService;

@Transactional
@Service
@ServiceRepository(claz = ISurveyResponseAnswerRepository.class)
public class SurveyResponseAnswerService extends SuperService implements ISurveyResponseAnswerService {

	@Autowired
	private ISurveyResponseAnswerRepository surveyResponseAnswerRepository;

	@Override
	public List<SurveyResponseAnswer> getSurveyResponseAnswers(Long surveyResponseId) throws FrameworkException {
		return surveyResponseAnswerRepository.getSurveyResponseAnswers(surveyResponseId);
	}

	@Override
	public SurveyResponseAnswer getSurveyResponseAnswer(Long surveyResponseId, Long surveyQuestionId)
			throws FrameworkException {
		return surveyResponseAnswerRepository.getSurveyResponse(surveyResponseId, surveyQuestionId);
	}

	@Override
	public List<SurveyResponseAnswer> getSurveyResponseAnswers(Long surveyResponseId, Long surveyQuestionId)
			throws FrameworkException {
		return surveyResponseAnswerRepository.getSurveyResponseAnswers(surveyResponseId, surveyQuestionId);
	}

	@Override
	public SurveyResponseAnswer getSurveyResponseAnswer(Long surveyResponseId, Long surveyQuestionId,
			Long surveyQuestionAnswerChoiceRowId) throws FrameworkException {
		return surveyResponseAnswerRepository.getSurveyResponseAnswer(surveyResponseId, surveyQuestionId,
				surveyQuestionAnswerChoiceRowId);
	}

	@Override
	public SurveyResponseAnswer getSurveyResponseAnswer(Long surveyResponseId, Long surveyQuestionId,
			Long surveyQuestionAnswerChoiceRowId, Long surveyQuestionAnswerChoiceColumnId) throws FrameworkException {
		return surveyResponseAnswerRepository.getSurveyResponseAnswer(surveyResponseId, surveyQuestionId,
				surveyQuestionAnswerChoiceRowId, surveyQuestionAnswerChoiceColumnId);
	}

	@Override
	public SurveyResponseAnswer getSurveyResponseAnswer(Long surveyResponseId, Long surveyQuestionId,
			Long surveyQuestionAnswerChoiceRowId, Long surveyQuestionAnswerChoiceColumnId,
			Long surveyQuestionAnswerChoiceSelectionId) throws FrameworkException {
		return surveyResponseAnswerRepository.getSurveyResponseAnswer(surveyResponseId, surveyQuestionId,
				surveyQuestionAnswerChoiceRowId, surveyQuestionAnswerChoiceColumnId,
				surveyQuestionAnswerChoiceSelectionId);
	}

	@Override
	public SurveyResponseAnswer updateAnswerForSingle(SurveyResponseAnswerVO vo) throws FrameworkException {
		if (vo == null) {
			return null;
		}
		boolean newEntity = false;
		SurveyResponseAnswer surveyResponseAnswer = getSurveyResponseAnswer(vo.getSurveyResponseId(),
				vo.getSurveyQuestionId());
		if (surveyResponseAnswer == null) {
			newEntity = true;
			surveyResponseAnswer = new SurveyResponseAnswer();
			surveyResponseAnswer.setUniqueId(UUID.randomUUID().toString());
		}
		surveyResponseAnswer.setActive(vo.getActive());
		surveyResponseAnswer.setSurveyResponse(get(SurveyResponse.class, vo.getSurveyResponseId()));
		surveyResponseAnswer.setSurveyQuestion(get(SurveyQuestion.class, vo.getSurveyQuestionId()));
		surveyResponseAnswer.setResponseText(vo.getResponseText());
		return saveOrUpdate(surveyResponseAnswer, newEntity);
	}

	@Override
	public SurveyResponseAnswer updateAnswerMultipleSelection(SurveyResponseAnswerVO vo) throws FrameworkException {
		if (vo == null) {
			return null;
		}
		if (vo.getSelected()) {
			boolean newEntity = false;
			SurveyResponseAnswer surveyResponseAnswer = getSurveyResponseAnswer(vo.getSurveyResponseId(),
					vo.getSurveyQuestionId(), vo.getQuestionAnswerChoiceRowId());
			if (surveyResponseAnswer == null) {
				newEntity = true;
				surveyResponseAnswer = new SurveyResponseAnswer();
				surveyResponseAnswer.setUniqueId(UUID.randomUUID().toString());
			}
			surveyResponseAnswer.setActive(vo.getActive());
			surveyResponseAnswer.setSurveyResponse(get(SurveyResponse.class, vo.getSurveyResponseId()));
			surveyResponseAnswer.setSurveyQuestion(get(SurveyQuestion.class, vo.getSurveyQuestionId()));
			surveyResponseAnswer.setSurveyQuestionAnswerChoiceRow(
					get(SurveyQuestionAnswerChoice.class, vo.getQuestionAnswerChoiceRowId()));
			surveyResponseAnswer.setSelected(vo.getSelected());
			return saveOrUpdate(surveyResponseAnswer, newEntity);
		} else {
			delete(SurveyResponseAnswer.class, vo.getId());
			return null;
		}
	}

	@Override
	public SurveyResponseAnswer updateAnswerSingleSelection(SurveyResponseAnswerVO vo) throws FrameworkException {
		if (vo == null) {
			return null;
		}
		if (vo.getSelected()) {
			boolean newEntity = false;
			SurveyResponseAnswer surveyResponseAnswer = getSurveyResponseAnswer(vo.getSurveyResponseId(),
					vo.getSurveyQuestionId(), vo.getQuestionAnswerChoiceRowId());
			if (surveyResponseAnswer == null) {
				surveyResponseAnswerRepository.deleteFromSurveyResponseAnswerByResponse(vo.getSurveyResponseId(),
						vo.getSurveyQuestionId());

				newEntity = true;
				surveyResponseAnswer = new SurveyResponseAnswer();
				surveyResponseAnswer.setUniqueId(UUID.randomUUID().toString());
			}
			surveyResponseAnswer.setActive(vo.getActive());
			surveyResponseAnswer.setSurveyResponse(get(SurveyResponse.class, vo.getSurveyResponseId()));
			surveyResponseAnswer.setSurveyQuestion(get(SurveyQuestion.class, vo.getSurveyQuestionId()));
			surveyResponseAnswer.setSurveyQuestionAnswerChoiceRow(
					get(SurveyQuestionAnswerChoice.class, vo.getQuestionAnswerChoiceRowId()));
			surveyResponseAnswer.setSelected(vo.getSelected());
			return saveOrUpdate(surveyResponseAnswer, newEntity);
		} else {
			surveyResponseAnswerRepository.deleteFromSurveyResponseAnswerByResponse(vo.getSurveyResponseId(),
					vo.getSurveyQuestionId());
			return null;
		}
	}

	@Override
	public SurveyResponseAnswer updateAnswerOtherSelection(SurveyResponseAnswerVO vo) throws FrameworkException {
		if (vo == null) {
			return null;
		}
		boolean newEntity = false;
		SurveyResponseAnswer surveyResponseAnswer = getSurveyResponseAnswer(vo.getSurveyResponseId(),
				vo.getSurveyQuestionId(), vo.getQuestionAnswerChoiceRowId());
		if (surveyResponseAnswer == null) {
			newEntity = true;
			surveyResponseAnswer = new SurveyResponseAnswer();
			surveyResponseAnswer.setUniqueId(UUID.randomUUID().toString());
		}
		surveyResponseAnswer.setActive(vo.getActive());
		surveyResponseAnswer.setSurveyResponse(get(SurveyResponse.class, vo.getSurveyResponseId()));
		surveyResponseAnswer.setSurveyQuestion(get(SurveyQuestion.class, vo.getSurveyQuestionId()));
		surveyResponseAnswer.setSurveyQuestionAnswerChoiceRow(
				get(SurveyQuestionAnswerChoice.class, vo.getQuestionAnswerChoiceRowId()));
		surveyResponseAnswer.setOtherValue(vo.getOtherValue());
		return saveOrUpdate(surveyResponseAnswer, newEntity);
	}

	@Override
	public SurveyResponseAnswer updateAnswerMatrixCellForText(SurveyResponseAnswerVO vo) throws FrameworkException {
		if (vo == null) {
			return null;
		}
		boolean newEntity = false;
		SurveyResponseAnswer surveyResponseAnswer = getSurveyResponseAnswer(vo.getSurveyResponseId(),
				vo.getSurveyQuestionId(), vo.getQuestionAnswerChoiceRowId(), vo.getQuestionAnswerChoiceColumnId());
		if (surveyResponseAnswer == null) {
			newEntity = true;
			surveyResponseAnswer = new SurveyResponseAnswer();
			surveyResponseAnswer.setUniqueId(UUID.randomUUID().toString());
		}
		surveyResponseAnswer.setActive(vo.getActive());
		surveyResponseAnswer.setSurveyResponse(get(SurveyResponse.class, vo.getSurveyResponseId()));
		surveyResponseAnswer.setSurveyQuestion(get(SurveyQuestion.class, vo.getSurveyQuestionId()));
		surveyResponseAnswer.setSurveyQuestionAnswerChoiceRow(
				get(SurveyQuestionAnswerChoice.class, vo.getQuestionAnswerChoiceRowId()));
		surveyResponseAnswer.setSurveyQuestionAnswerChoiceColumn(
				get(SurveyQuestionAnswerChoice.class, vo.getQuestionAnswerChoiceColumnId()));
		surveyResponseAnswer.setResponseText(vo.getResponseText());
		return saveOrUpdate(surveyResponseAnswer, newEntity);
	}

	@Override
	public SurveyResponseAnswer updateAnswerMatrixCellForSelection(SurveyResponseAnswerVO vo)
			throws FrameworkException {
		if (vo == null) {
			return null;
		}
		boolean newEntity = false;
		SurveyResponseAnswer surveyResponseAnswer = getSurveyResponseAnswer(vo.getSurveyResponseId(),
				vo.getSurveyQuestionId(), vo.getQuestionAnswerChoiceRowId(), vo.getQuestionAnswerChoiceColumnId(),
				vo.getQuestionAnswerChoiceSelectionId());
		if (surveyResponseAnswer == null) {
			newEntity = true;
			surveyResponseAnswer = new SurveyResponseAnswer();
			surveyResponseAnswer.setUniqueId(UUID.randomUUID().toString());
		}
		surveyResponseAnswer.setActive(vo.getActive());
		surveyResponseAnswer.setSurveyResponse(get(SurveyResponse.class, vo.getSurveyResponseId()));
		surveyResponseAnswer.setSurveyQuestion(get(SurveyQuestion.class, vo.getSurveyQuestionId()));
		surveyResponseAnswer.setSurveyQuestionAnswerChoiceRow(
				get(SurveyQuestionAnswerChoice.class, vo.getQuestionAnswerChoiceRowId()));
		surveyResponseAnswer.setSurveyQuestionAnswerChoiceColumn(
				get(SurveyQuestionAnswerChoice.class, vo.getQuestionAnswerChoiceColumnId()));
		surveyResponseAnswer.setSurveyQuestionAnswerChoiceSelection(
				get(SurveyQuestionAnswerChoiceSelection.class, vo.getQuestionAnswerChoiceSelectionId()));
		surveyResponseAnswer.setSelected(vo.getSelected());
		return saveOrUpdate(surveyResponseAnswer, newEntity);
	}

}
