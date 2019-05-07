package software.simple.solutions.data.entry.es.control.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import software.simple.solutions.data.entry.es.control.entities.SurveyResponseAnswer;
import software.simple.solutions.data.entry.es.control.repository.ISurveyResponseAnswerRepository;
import software.simple.solutions.data.entry.es.control.service.ISurveyResponseAnswerService;
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

}
