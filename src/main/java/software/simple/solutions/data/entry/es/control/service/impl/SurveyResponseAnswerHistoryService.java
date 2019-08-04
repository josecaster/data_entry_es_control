package software.simple.solutions.data.entry.es.control.service.impl;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

import software.simple.solutions.data.entry.es.control.entities.SurveyResponseAnswerHistory;
import software.simple.solutions.data.entry.es.control.repository.ISurveyResponseAnswerHistoryRepository;
import software.simple.solutions.data.entry.es.control.service.ISurveyResponseAnswerHistoryService;
import software.simple.solutions.framework.core.annotations.ServiceRepository;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.service.impl.SuperService;

@Transactional(propagation=Propagation.REQUIRED, rollbackFor = Exception.class)
@Service
@ServiceRepository(claz = ISurveyResponseAnswerHistoryRepository.class)
public class SurveyResponseAnswerHistoryService extends SuperService implements ISurveyResponseAnswerHistoryService {

	@Autowired
	private ISurveyResponseAnswerHistoryRepository surveyResponseAnswerHistoryRepository;

	@Override
	public SurveyResponseAnswerHistory getSurveyResponseAnswerHistoryByResponseAndQuestion(Long surveyResponseId,
			Long surveyQuestionId) throws FrameworkException {
		return surveyResponseAnswerHistoryRepository
				.getSurveyResponseAnswerHistoryByResponseAndQuestion(surveyResponseId, surveyQuestionId);
	}
}
