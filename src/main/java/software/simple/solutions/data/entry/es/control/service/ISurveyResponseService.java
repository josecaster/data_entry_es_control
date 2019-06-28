package software.simple.solutions.data.entry.es.control.service;

import java.util.List;

import software.simple.solutions.data.entry.es.control.entities.SurveyResponse;
import software.simple.solutions.data.entry.es.control.rest.model.SurveyResponseRestModel;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.service.ISuperService;

public interface ISurveyResponseService extends ISuperService {

	SurveyResponse updateFromRest(SurveyResponseRestModel surveyResponseRestModel) throws FrameworkException;

	List<SurveyResponse> findAllSurveyResponsesByUser(String username) throws FrameworkException;

	List<String> findAllActiveSurveyResponseUuIdsByUser(String username) throws FrameworkException;

	Boolean removeAllFormData(Long surveyResponseId) throws FrameworkException;

}
