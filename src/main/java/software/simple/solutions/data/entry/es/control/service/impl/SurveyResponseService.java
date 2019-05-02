package software.simple.solutions.data.entry.es.control.service.impl;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import software.simple.solutions.data.entry.es.control.entities.Survey;
import software.simple.solutions.data.entry.es.control.entities.SurveyResponse;
import software.simple.solutions.data.entry.es.control.repository.ISurveyRepository;
import software.simple.solutions.data.entry.es.control.repository.ISurveyResponseRepository;
import software.simple.solutions.data.entry.es.control.rest.model.SurveyResponseModel;
import software.simple.solutions.data.entry.es.control.service.ISurveyResponseService;
import software.simple.solutions.framework.core.annotations.ServiceRepository;
import software.simple.solutions.framework.core.constants.DateConstant;
import software.simple.solutions.framework.core.entities.ApplicationUser;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.repository.IApplicationUserRepository;
import software.simple.solutions.framework.core.service.impl.SuperService;

@Transactional
@Service
@ServiceRepository(claz = ISurveyResponseRepository.class)
public class SurveyResponseService extends SuperService implements ISurveyResponseService {

	@Autowired
	private ISurveyResponseRepository surveyResponseRepository;

	@Autowired
	private IApplicationUserRepository applicationUserRepository;

	@Autowired
	private ISurveyRepository surveyRepository;

	@Override
	public SurveyResponse updateFromRest(SurveyResponseModel surveyResponseModel) throws FrameworkException {
		String uniqueId = surveyResponseModel.getUniqueId();
		SurveyResponse surveyResponse = surveyResponseRepository.getByUniqueId(uniqueId);
		boolean isNew = false;
		if (surveyResponse == null) {
			isNew = true;
			surveyResponse = new SurveyResponse();
		}
		surveyResponse.setActive(surveyResponseModel.getActive());
		surveyResponse.setUniqueId(surveyResponseModel.getUniqueId());
		surveyResponse.setFormName(surveyResponseModel.getFormName());
		surveyResponse
				.setCreatedOn(LocalDateTime.parse(surveyResponseModel.getCreatedOn(), DateConstant.DATE_TIME_FORMAT));
		ApplicationUser applicationUser = applicationUserRepository.getByUserName(surveyResponseModel.getUsername());
		surveyResponse.setApplicationUser(applicationUser);
		surveyResponse.setSurvey(surveyRepository.getById(Survey.class, surveyResponseModel.getSurveyId()));
		surveyResponse.setUpdatedByUser(applicationUser);
		surveyResponse.setUpdatedDate(LocalDateTime.now());
		surveyResponse = surveyResponseRepository.updateSingle(surveyResponse, isNew);
		return surveyResponse;
	}

}
