package software.simple.solutions.data.entry.es.control.service;

import software.simple.solutions.data.entry.es.control.entities.SurveyResponse;
import software.simple.solutions.data.entry.es.control.rest.model.SurveyResponseModel;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.service.ISuperService;

public interface ISurveyResponseService extends ISuperService {

	SurveyResponse updateFromRest(SurveyResponseModel surveyResponseModel) throws FrameworkException;

}
