package software.simple.solutions.data.entry.es.control.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

import software.simple.solutions.data.entry.es.control.entities.SurveyQuestionAnswerChoice;
import software.simple.solutions.data.entry.es.control.entities.SurveyQuestionAnswerChoiceSelection;
import software.simple.solutions.data.entry.es.control.repository.ISurveyQuestionAnswerChoiceSelectionRepository;
import software.simple.solutions.data.entry.es.control.service.ISurveyQuestionAnswerChoiceSelectionService;
import software.simple.solutions.data.entry.es.control.valueobjects.SurveyQuestionAnswerChoiceSelectionVO;
import software.simple.solutions.framework.core.annotations.ServiceRepository;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.service.impl.SuperService;
import software.simple.solutions.framework.core.valueobjects.SuperVO;

@Transactional(propagation=Propagation.REQUIRED, rollbackFor = Exception.class)
@Service
@ServiceRepository(claz = ISurveyQuestionAnswerChoiceSelectionRepository.class)
public class SurveyQuestionAnswerChoiceSelectionService extends SuperService
		implements ISurveyQuestionAnswerChoiceSelectionService {

	@Autowired
	private ISurveyQuestionAnswerChoiceSelectionRepository surveyQuestionAnswerChoiceSelectionRepository;

	@Override
	public <T, R extends SuperVO> T updateSingle(R valueObject) throws FrameworkException {
		SurveyQuestionAnswerChoiceSelectionVO vo = (SurveyQuestionAnswerChoiceSelectionVO) valueObject;

		SurveyQuestionAnswerChoiceSelection surveyQuestionAnswerChoiceSelection = new SurveyQuestionAnswerChoiceSelection();
		if (!vo.isNew()) {
			surveyQuestionAnswerChoiceSelection = get(SurveyQuestionAnswerChoiceSelection.class, vo.getId());
		}
		surveyQuestionAnswerChoiceSelection.setSurveyQuestionAnswerChoice(
				get(SurveyQuestionAnswerChoice.class, vo.getSurveyQuestionAnswerChoiceId()));
		surveyQuestionAnswerChoiceSelection.setLabel(vo.getLabel());
		surveyQuestionAnswerChoiceSelection.setIndex(vo.getIndex());

		return (T) saveOrUpdate(surveyQuestionAnswerChoiceSelection, false);
	}

	@Override
	public List<SurveyQuestionAnswerChoiceSelection> getBySurveyQuestionAnswerChoice(Long surveyQuestionAnswerChoiceId)
			throws FrameworkException {
		return surveyQuestionAnswerChoiceSelectionRepository
				.getBySurveyQuestionAnswerChoice(surveyQuestionAnswerChoiceId);
	}

	@Override
	public SurveyQuestionAnswerChoiceSelection create(Long surveyQuestionAnswerChoiceId, Integer index)
			throws FrameworkException {
		SurveyQuestionAnswerChoiceSelectionVO vo = new SurveyQuestionAnswerChoiceSelectionVO();
		vo.setSurveyQuestionAnswerChoiceId(surveyQuestionAnswerChoiceId);
		vo.setIndex(index);
		vo.setNew(true);
		return updateSingle(vo);
	}

	@Override
	public void updateLabel(Long id, String value) throws FrameworkException {
		SurveyQuestionAnswerChoiceSelection surveyQuestionAnswerChoiceSelection = get(
				SurveyQuestionAnswerChoiceSelection.class, id);
		surveyQuestionAnswerChoiceSelection.setLabel(value);
		saveOrUpdate(surveyQuestionAnswerChoiceSelection, false);
	}

	@Override
	public List<SurveyQuestionAnswerChoiceSelection> findBySurvey(Long surveyId) throws FrameworkException {
		return surveyQuestionAnswerChoiceSelectionRepository.findBySurvey(surveyId);
	}

	@Override
	public void deleteAndUpdateIndex(
			Class<SurveyQuestionAnswerChoiceSelection> surveyQuestionAnswerChoiceSelectionClass,
			Long surveyQuestionAnswerChoiceSelectionId, Long surveyQuestionAnswerChoiceId, Integer componentIndex)
			throws FrameworkException {
		delete(surveyQuestionAnswerChoiceSelectionClass, surveyQuestionAnswerChoiceSelectionId);
		surveyQuestionAnswerChoiceSelectionRepository.updateIndexes(surveyQuestionAnswerChoiceId, componentIndex);
	}
}
