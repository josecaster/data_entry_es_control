package software.simple.solutions.data.entry.es.control.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import software.simple.solutions.data.entry.es.control.constants.QuestionType;
import software.simple.solutions.data.entry.es.control.constants.State;
import software.simple.solutions.data.entry.es.control.entities.SurveyQuestion;
import software.simple.solutions.data.entry.es.control.entities.SurveyQuestionAnswerChoice;
import software.simple.solutions.data.entry.es.control.entities.SurveyQuestionAnswerChoiceSelection;
import software.simple.solutions.data.entry.es.control.entities.SurveyResponse;
import software.simple.solutions.data.entry.es.control.entities.SurveyResponseAnswer;
import software.simple.solutions.data.entry.es.control.entities.SurveyResponseAnswerHistory;
import software.simple.solutions.data.entry.es.control.pojo.ResponseJsonCellPojo;
import software.simple.solutions.data.entry.es.control.pojo.ResponseJsonPojo;
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
	public void createUpdateAnswerHistory(SurveyResponseAnswer surveyResponseAnswer) throws FrameworkException {

		SurveyResponseAnswerHistory surveyResponseAnswerHistory = surveyResponseAnswerRepository
				.getAnswerHistory(surveyResponseAnswer);
		boolean isNew = false;
		if (surveyResponseAnswerHistory == null) {
			isNew = true;
			surveyResponseAnswerHistory = new SurveyResponseAnswerHistory();
			surveyResponseAnswerHistory.setSurveyQuestion(surveyResponseAnswer.getSurveyQuestion());
			surveyResponseAnswerHistory.setSurveyResponse(surveyResponseAnswer.getSurveyResponse());
			surveyResponseAnswerHistory.setUniqueId(UUID.randomUUID().toString());
		}
		SurveyQuestion surveyQuestion = surveyResponseAnswer.getSurveyQuestion();
		String questionType = surveyQuestion.getQuestionType();
		switch (questionType) {
		case QuestionType.SINGLE:
		case QuestionType.DATE:
		case QuestionType.LENGTH_FT_INCH:
		case QuestionType.AREA_FT_INCH:
			createUpdateResponseHistoryForSingle(surveyResponseAnswer, surveyResponseAnswerHistory, isNew);
			break;
		case QuestionType.CHOICES:
			createUpdateResponseHistoryForChoices(surveyResponseAnswer, surveyResponseAnswerHistory, isNew,
					surveyQuestion);
			break;
		case QuestionType.MATRIX:
			createUpdateResponseHistoryForMatrix(surveyResponseAnswer, surveyResponseAnswerHistory, isNew,
					surveyQuestion);
			break;
		default:
			break;
		}
	}

	private void createUpdateResponseHistoryForMatrix(SurveyResponseAnswer surveyResponseAnswer,
			SurveyResponseAnswerHistory surveyResponseAnswerHistory, boolean isNew, SurveyQuestion surveyQuestion)
			throws FrameworkException {
		List<SurveyResponseAnswer> surveyResponseAnswers = surveyResponseAnswerRepository.getSurveyResponseAnswers(
				surveyResponseAnswer.getSurveyResponse().getId(), surveyResponseAnswer.getSurveyQuestion().getId());
		List<ResponseJsonCellPojo> cells = null;
		if (surveyResponseAnswers != null) {
			cells = surveyResponseAnswers.stream().filter(p -> p.getSurveyQuestionAnswerChoiceSelection() == null)
					.map(p -> new ResponseJsonCellPojo(p.getSurveyQuestionAnswerChoiceRow().getId(),
							p.getSurveyQuestionAnswerChoiceColumn().getId(), p.getResponseText()))
					.collect(Collectors.toList());
			Map<Pair<Long, Long>, List<SurveyResponseAnswer>> collect = surveyResponseAnswers.stream()
					.filter(p -> p.getSurveyQuestionAnswerChoiceSelection() != null)
					.collect(Collectors.groupingBy(p -> Pair.of(p.getSurveyQuestionAnswerChoiceRow().getId(),
							p.getSurveyQuestionAnswerChoiceColumn().getId())));
			List<ResponseJsonCellPojo> collect2 = collect.entrySet().stream()
					.map(p -> new ResponseJsonCellPojo(p.getKey().getFirst(), p.getKey().getSecond(),
							p.getValue().stream().map(sel -> sel.getSurveyQuestionAnswerChoiceSelection().getId())
									.collect(Collectors.toList())))
					.collect(Collectors.toList());
			cells.addAll(collect2);
		}
		if (cells == null) {
			cells = new ArrayList<ResponseJsonCellPojo>();
		}
		String json = new Gson().toJson(cells);
		surveyResponseAnswerHistory.setResponseJson(json);
		surveyResponseAnswerRepository.updateSingle(surveyResponseAnswerHistory, isNew);
	}

	private void createUpdateResponseHistoryForChoices(SurveyResponseAnswer surveyResponseAnswer,
			SurveyResponseAnswerHistory surveyResponseAnswerHistory, boolean isNew, SurveyQuestion surveyQuestion)
			throws FrameworkException {
		if (surveyQuestion.getMultipleSelection() != null && surveyQuestion.getMultipleSelection()) {
			List<SurveyResponseAnswer> surveyResponseAnswers = surveyResponseAnswerRepository.getSurveyResponseAnswers(
					surveyResponseAnswer.getSurveyResponse().getId(), surveyResponseAnswer.getSurveyQuestion().getId());
			List<Long> ids = null;
			String otherValue = null;
			if (surveyResponseAnswers != null) {
				ids = surveyResponseAnswers.stream().filter(p -> (p.getSelected() != null && p.getSelected()))
						.map(p -> p.getSurveyQuestionAnswerChoiceRow().getId()).collect(Collectors.toList());
				Optional<SurveyResponseAnswer> optional = surveyResponseAnswers.stream()
						.filter(p -> (p.getSelected() != null && p.getSelected()))
						.filter(p -> (p.getSurveyQuestionAnswerChoiceRow().getIsOther() != null
								&& p.getSurveyQuestionAnswerChoiceRow().getIsOther()))
						.findFirst();
				if (optional.isPresent()) {
					SurveyResponseAnswer answer = optional.get();
					otherValue = answer.getOtherValue();
				}
			}
			if (ids == null) {
				ids = new ArrayList<Long>();
			}
			ResponseJsonPojo responseJsonPojo = new ResponseJsonPojo(ids, otherValue);
			String json = new Gson().toJson(responseJsonPojo);
			surveyResponseAnswerHistory.setResponseJson(json);
			surveyResponseAnswerRepository.updateSingle(surveyResponseAnswerHistory, isNew);
		} else {
			List<Long> ids = null;
			String otherValue = null;
			if (surveyResponseAnswer.getSelected() != null && surveyResponseAnswer.getSelected()) {
				Long id = surveyResponseAnswer.getSurveyQuestionAnswerChoiceRow().getId();
				ids = Arrays.asList(id);
				if (surveyResponseAnswer.getSurveyQuestionAnswerChoiceRow().getIsOther() != null
						&& surveyResponseAnswer.getSurveyQuestionAnswerChoiceRow().getIsOther()) {
					otherValue = surveyResponseAnswer.getOtherValue();
				}
			}
			if (ids == null) {
				ids = new ArrayList<Long>();
			}
			ResponseJsonPojo responseJsonPojo = new ResponseJsonPojo(ids, otherValue);
			String json = new Gson().toJson(responseJsonPojo);
			surveyResponseAnswerHistory.setResponseJson(json);
			surveyResponseAnswerRepository.updateSingle(surveyResponseAnswerHistory, isNew);
		}
	}

	private void createUpdateResponseHistoryForSingle(SurveyResponseAnswer surveyResponseAnswer,
			SurveyResponseAnswerHistory surveyResponseAnswerHistory, boolean isNew) throws FrameworkException {
		String responseText = surveyResponseAnswer.getResponseText();
		String json = new Gson().toJson(responseText);
		surveyResponseAnswerHistory.setResponseJson(json);
		surveyResponseAnswerRepository.updateSingle(surveyResponseAnswerHistory, isNew);
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
		surveyResponseAnswer.setState(State.UPDATED);
		surveyResponseAnswer = saveOrUpdate(surveyResponseAnswer, newEntity);
		createUpdateAnswerHistory(surveyResponseAnswer);
		return surveyResponseAnswer;
	}

	@Override
	public SurveyResponseAnswer updateAnswerMultipleSelection(SurveyResponseAnswerVO vo) throws FrameworkException {
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
		surveyResponseAnswer.setSelected(vo.getSelected());
		surveyResponseAnswer.setState(State.UPDATED);
		surveyResponseAnswer = saveOrUpdate(surveyResponseAnswer, newEntity);
		createUpdateAnswerHistory(surveyResponseAnswer);
		return surveyResponseAnswer;
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
			surveyResponseAnswer.setState(State.UPDATED);
			surveyResponseAnswer = saveOrUpdate(surveyResponseAnswer, newEntity);
			createUpdateAnswerHistory(surveyResponseAnswer);
			return surveyResponseAnswer;
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
		surveyResponseAnswer.setState(State.UPDATED);
		surveyResponseAnswer = saveOrUpdate(surveyResponseAnswer, newEntity);
		createUpdateAnswerHistory(surveyResponseAnswer);
		return surveyResponseAnswer;
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
		surveyResponseAnswer.setState(State.UPDATED);
		surveyResponseAnswer = saveOrUpdate(surveyResponseAnswer, newEntity);
		createUpdateAnswerHistory(surveyResponseAnswer);
		return surveyResponseAnswer;
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
		surveyResponseAnswer.setState(State.UPDATED);
		surveyResponseAnswer = saveOrUpdate(surveyResponseAnswer, newEntity);
		createUpdateAnswerHistory(surveyResponseAnswer);
		return surveyResponseAnswer;
	}

}
