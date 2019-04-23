package software.simple.solutions.data.entry.es.control.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import software.simple.solutions.data.entry.es.control.constants.Position;
import software.simple.solutions.data.entry.es.control.entities.Survey;
import software.simple.solutions.data.entry.es.control.entities.SurveyGroup;
import software.simple.solutions.data.entry.es.control.entities.SurveyQuestion;
import software.simple.solutions.data.entry.es.control.properties.SurveyQuestionProperty;
import software.simple.solutions.data.entry.es.control.repository.ISurveyQuestionAnswerChoiceRepository;
import software.simple.solutions.data.entry.es.control.repository.ISurveyQuestionGroupRepository;
import software.simple.solutions.data.entry.es.control.repository.ISurveyQuestionRepository;
import software.simple.solutions.data.entry.es.control.service.ISurveyQuestionService;
import software.simple.solutions.data.entry.es.control.valueobjects.SurveyQuestionVO;
import software.simple.solutions.framework.core.annotations.ServiceRepository;
import software.simple.solutions.framework.core.exceptions.Arg;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.pojo.ComboItem;
import software.simple.solutions.framework.core.properties.SystemMessageProperty;
import software.simple.solutions.framework.core.service.impl.SuperService;
import software.simple.solutions.framework.core.util.NumberUtil;
import software.simple.solutions.framework.core.valueobjects.SuperVO;

@Transactional
@Service
@ServiceRepository(claz = ISurveyQuestionRepository.class)
public class SurveyQuestionService extends SuperService implements ISurveyQuestionService {

	@Autowired
	private ISurveyQuestionRepository surveyQuestionRepository;

	@Autowired
	private ISurveyQuestionAnswerChoiceRepository surveyQuestionAnswerChoiceRepository;

	@Autowired
	private ISurveyQuestionGroupRepository surveyQuestionGroupRepository;

	@Override
	public <T, R extends SuperVO> T updateSingle(R valueObject) throws FrameworkException {
		SurveyQuestionVO vo = (SurveyQuestionVO) valueObject;

		if (StringUtils.isBlank(vo.getQuestion())) {
			throw new FrameworkException(SystemMessageProperty.FIELD_IS_REQUIRED,
					new Arg().key(SurveyQuestionProperty.QUESTION));
		}

		SurveyQuestion surveyQuestion = new SurveyQuestion();
		if (vo.isNew()) {
			Long nextOrder = getNextOrder(vo.getSurveyId());
			surveyQuestion.setOrder(nextOrder);
		} else {
			surveyQuestion = get(SurveyQuestion.class, vo.getId());
		}
		surveyQuestion.setQuestion(vo.getQuestion());
		surveyQuestion.setQuestionDescription(vo.getQuestionDescription());
		surveyQuestion.setQuestionType(vo.getQuestionType());
		surveyQuestion.setSurvey(get(Survey.class, vo.getSurveyId()));
		surveyQuestion.setActive(vo.getActive());

		if (vo.isNew()) {
			updateGroupIfPinned(surveyQuestion);
		}

		if (!vo.isNew()) {
			cleanUpSurveyQuestionAnswerChoices(surveyQuestion, vo.getUpdatedBy());
		}

		return (T) saveOrUpdate(surveyQuestion, vo.isNew());
	}

	private SurveyQuestion updateGroupIfPinned(SurveyQuestion surveyQuestion) throws FrameworkException {
		SurveyGroup surveyGroup = surveyQuestionGroupRepository
				.getPinnedGroupBySurvey(surveyQuestion.getSurvey().getId());
		surveyQuestion.setSurveyGroup(surveyGroup);
		return surveyQuestion;
	}

	private void cleanUpSurveyQuestionAnswerChoices(SurveyQuestion surveyQuestion, Long userId)
			throws FrameworkException {
		surveyQuestionRepository.cleanUpSurveyQuestionAnswerChoices(surveyQuestion.getId(),
				surveyQuestion.getQuestionType());
	}

	public Long getNextOrder(Long surveyId) throws FrameworkException {
		Long nextOrder = surveyQuestionRepository.getNextOrder(surveyId);
		if (nextOrder == null) {
			nextOrder = 0L;
		}
		return nextOrder + 1;
	}

	@Override
	public List<ComboItem> getQuestionListForOrder(Long surveyId, Long surveyQuestionId) throws FrameworkException {
		return surveyQuestionRepository.getQuestionListForOrder(surveyId, surveyQuestionId);
	}

	@Override
	public SurveyQuestion updateOrder(SurveyQuestionVO vo) throws FrameworkException {
		SurveyQuestion surveyQuestion = get(SurveyQuestion.class, vo.getId());

		String position = vo.getPosition();
		String[] positionArguments = position.split("-");

		String situation = positionArguments[0];
		Long undecidedQuestionOrder = NumberUtil.getLong(positionArguments[1]);
		Long order = surveyQuestion.getOrder();

		if (order < undecidedQuestionOrder) {
			switch (situation) {
			case Position.BEFORE:
				undecidedQuestionOrder = undecidedQuestionOrder - 1;
				surveyQuestion = processSurveyQuestionsOrder(surveyQuestion, undecidedQuestionOrder, false);
				break;
			case Position.AFTER:
				surveyQuestion = processSurveyQuestionsOrder(surveyQuestion, undecidedQuestionOrder, false);
				break;
			}
		} else {
			switch (situation) {
			case Position.BEFORE:
				surveyQuestion = processSurveyQuestionsOrder(surveyQuestion, undecidedQuestionOrder, true);
				break;
			case Position.AFTER:
				undecidedQuestionOrder = undecidedQuestionOrder + 1;
				surveyQuestion = processSurveyQuestionsOrder(surveyQuestion, undecidedQuestionOrder, true);
				break;
			}
		}

		return surveyQuestion;
	}

	private SurveyQuestion processSurveyQuestionsOrder(SurveyQuestion surveyQuestion, Long undecidedQuestionOrder,
			boolean reverse) throws FrameworkException {
		if (reverse) {
			for (long i = surveyQuestion.getOrder() - 1; i >= undecidedQuestionOrder; i--) {
				SurveyQuestion sq = getSurveyQuestionByOrder(surveyQuestion.getSurvey().getId(), i);
				sq.setOrder(sq.getOrder() + 1);
				saveOrUpdate(sq, false);
			}
		} else {
			for (long i = surveyQuestion.getOrder() + 1; i <= undecidedQuestionOrder; i++) {
				SurveyQuestion sq = getSurveyQuestionByOrder(surveyQuestion.getSurvey().getId(), i);
				sq.setOrder(sq.getOrder() - 1);
				saveOrUpdate(sq, false);
			}
		}
		surveyQuestion.setOrder(undecidedQuestionOrder);
		return saveOrUpdate(surveyQuestion, false);
	}

	@Override
	public List<SurveyQuestion> getQuestionList(Long surveyId) throws FrameworkException {
		return surveyQuestionRepository.getQuestionList(surveyId);
	}

	@Override
	public List<SurveyQuestion> getQuestionList(Long surveyId, String queryText) throws FrameworkException {
		return surveyQuestionRepository.getQuestionList(surveyId, queryText);
	}
	
	@Override
	public List<SurveyQuestion> getQuestionList(Long surveyId, String queryText, Long surveyGroupId) throws FrameworkException {
		return surveyQuestionRepository.getQuestionList(surveyId, queryText, surveyGroupId);
	}

	private SurveyQuestion getSurveyQuestionByOrder(Long surveyId, Long order) throws FrameworkException {
		return surveyQuestionRepository.getSurveyQuestionByOrder(surveyId, order);
	}

	@Override
	public SurveyQuestion updateOptions(SurveyQuestionVO vo) throws FrameworkException {
		SurveyQuestion surveyQuestion = get(SurveyQuestion.class, vo.getId());
		surveyQuestion.setRequired(vo.getRequired());
		surveyQuestion.setRequiredError(vo.getRequiredMessage());
		return saveOrUpdate(surveyQuestion, false);
	}

	@Override
	public <T> Integer delete(Class<T> cl, Long id, Long userId) throws FrameworkException {
		surveyQuestionAnswerChoiceRepository.deleteBySurveyQuestion(id);

		SurveyQuestion surveyQuestion = get(SurveyQuestion.class, id);
		Long order = surveyQuestion.getOrder();
		Integer deleted = super.delete(cl, id, userId);
		updateOrderAfterDelete(order);
		return deleted;
	}

	private void updateOrderAfterDelete(Long order) throws FrameworkException {
		surveyQuestionRepository.updateOrderAfterDelete(order);
	}

	@Override
	public void updateMultipleSelection(SurveyQuestionVO vo) throws FrameworkException {
		SurveyQuestion surveyQuestion = get(SurveyQuestion.class, vo.getId());
		surveyQuestion.setMultipleSelection(vo.getMultipleSelection());
		saveOrUpdate(surveyQuestion, false);
	}

	@Override
	public List<ComboItem> getNextQuestions(Long order) throws FrameworkException {
		return surveyQuestionRepository.getNextQuestions(order);
	}

	@Override
	public SurveyQuestion updateSurveyQuestionGroup(Long surveyQuestionId, Long groupId) throws FrameworkException {
		SurveyQuestion surveyQuestion = get(SurveyQuestion.class, surveyQuestionId);
		surveyQuestion.setSurveyGroup(get(SurveyGroup.class, groupId));
		return saveOrUpdate(surveyQuestion, false);
	}

	@Override
	public SurveyQuestion updateDescription(Long surveyQuestionId, String description) throws FrameworkException {
		SurveyQuestion surveyQuestion = get(SurveyQuestion.class, surveyQuestionId);
		surveyQuestion.setQuestionDescription(description);
		return saveOrUpdate(surveyQuestion, false);
	}

	@Override
	public SurveyQuestion updateRequired(Long surveyQuestionId, Boolean required) throws FrameworkException {
		SurveyQuestion surveyQuestion = get(SurveyQuestion.class, surveyQuestionId);
		surveyQuestion.setRequired(required);
		return saveOrUpdate(surveyQuestion, false);
	}

	@Override
	public SurveyQuestion updateRequiredError(Long surveyQuestionId, String requiredError) throws FrameworkException {
		SurveyQuestion surveyQuestion = get(SurveyQuestion.class, surveyQuestionId);
		surveyQuestion.setRequiredError(requiredError);
		return saveOrUpdate(surveyQuestion, false);
	}

}
