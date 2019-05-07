package software.simple.solutions.data.entry.es.control.service;

import java.util.List;

import software.simple.solutions.data.entry.es.control.entities.SurveyResponseAnswer;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.service.ISuperService;

public interface ISurveyResponseAnswerService extends ISuperService {

	List<SurveyResponseAnswer> getSurveyResponseAnswers(Long surveyResponseId) throws FrameworkException;

}
