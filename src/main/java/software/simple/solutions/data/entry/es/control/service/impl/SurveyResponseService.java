package software.simple.solutions.data.entry.es.control.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import software.simple.solutions.data.entry.es.control.entities.Survey;
import software.simple.solutions.data.entry.es.control.entities.SurveyQuestion;
import software.simple.solutions.data.entry.es.control.entities.SurveyQuestionAnswerChoice;
import software.simple.solutions.data.entry.es.control.entities.SurveyQuestionAnswerChoiceSelection;
import software.simple.solutions.data.entry.es.control.entities.SurveyResponse;
import software.simple.solutions.data.entry.es.control.entities.SurveyResponseAnswer;
import software.simple.solutions.data.entry.es.control.entities.SurveyResponseSection;
import software.simple.solutions.data.entry.es.control.entities.SurveySection;
import software.simple.solutions.data.entry.es.control.repository.ISurveyQuestionAnswerChoiceRepository;
import software.simple.solutions.data.entry.es.control.repository.ISurveyQuestionAnswerChoiceSelectionRepository;
import software.simple.solutions.data.entry.es.control.repository.ISurveyQuestionRepository;
import software.simple.solutions.data.entry.es.control.repository.ISurveyRepository;
import software.simple.solutions.data.entry.es.control.repository.ISurveyResponseAnswerRepository;
import software.simple.solutions.data.entry.es.control.repository.ISurveyResponseRepository;
import software.simple.solutions.data.entry.es.control.repository.ISurveyResponseSectionRepository;
import software.simple.solutions.data.entry.es.control.repository.ISurveySectionRepository;
import software.simple.solutions.data.entry.es.control.rest.model.SurveyResponseAnswerModel;
import software.simple.solutions.data.entry.es.control.rest.model.SurveyResponseModel;
import software.simple.solutions.data.entry.es.control.rest.model.SurveyResponseRestModel;
import software.simple.solutions.data.entry.es.control.rest.model.SurveyResponseSectionModel;
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
	private ISurveyResponseAnswerRepository surveyResponseAnswerRepository;

	@Autowired
	private ISurveyQuestionRepository surveyQuestionRepository;

	@Autowired
	private ISurveyQuestionAnswerChoiceRepository surveyQuestionAnswerChoiceRepository;

	@Autowired
	private ISurveyQuestionAnswerChoiceSelectionRepository surveyQuestionAnswerChoiceSelectionRepository;

	@Autowired
	private ISurveyResponseSectionRepository surveyResponseSectionRepository;

	@Autowired
	private IApplicationUserRepository applicationUserRepository;

	@Autowired
	private ISurveyRepository surveyRepository;

	@Autowired
	private ISurveySectionRepository surveySectionRepository;

	@Override
	public SurveyResponse updateFromRest(SurveyResponseRestModel surveyResponseRestModel) throws FrameworkException {

		SurveyResponse surveyResponse = updateSurveyResponse(surveyResponseRestModel);

		updateSurveyResponseAnswer(surveyResponseRestModel, surveyResponse);

		updateSurveyResponseSection(surveyResponseRestModel, surveyResponse);

		return surveyResponse;
	}

	private void updateSurveyResponseSection(SurveyResponseRestModel surveyResponseRestModel,
			SurveyResponse surveyResponse) throws FrameworkException {
		List<SurveyResponseSectionModel> surveyResponseSections = surveyResponseRestModel.getSurveyResponseSections();
		if (surveyResponseSections != null) {
			for (SurveyResponseSectionModel surveyResponseSectionModel : surveyResponseSections) {
				String uniqueId = surveyResponseSectionModel.getUniqueId();
				SurveyResponseSection surveyResponseSection = surveyResponseSectionRepository.getByUniqueId(uniqueId);
				boolean isNew = false;
				if (surveyResponseSection == null) {
					isNew = true;
					surveyResponseSection = new SurveyResponseSection();
				}
				surveyResponseSection.setActive(surveyResponseSectionModel.getActive());
				surveyResponseSection.setNotApplicable(surveyResponseSectionModel.getNotApplicable());
				surveyResponseSection.setSurveyResponse(surveyResponse);
				surveyResponseSection.setSurveySection(surveySectionRepository.get(SurveySection.class,
						surveyResponseSectionModel.getSurveySectionId()));
				surveyResponseSection.setUniqueId(surveyResponseSectionModel.getUniqueId());
				surveySectionRepository.updateSingle(surveyResponseSection, isNew);
			}
		}
	}

	private void updateSurveyResponseAnswer(SurveyResponseRestModel surveyResponseRestModel,
			SurveyResponse surveyResponse) throws FrameworkException {
		List<SurveyResponseAnswerModel> surveyResponseAnswers = surveyResponseRestModel.getSurveyResponseAnswers();
		if (surveyResponseAnswers != null) {
			for (SurveyResponseAnswerModel surveyResponseAnswerModel : surveyResponseAnswers) {
				String uniqueId = surveyResponseAnswerModel.getUniqueId();
				SurveyResponseAnswer surveyResponseAnswer = surveyResponseAnswerRepository.getByUniqueId(uniqueId);
				boolean isNew = false;
				if (surveyResponseAnswer == null) {
					isNew = true;
					surveyResponseAnswer = new SurveyResponseAnswer();
				}
				surveyResponseAnswer.setActive(surveyResponseAnswerModel.getActive());
				surveyResponseAnswer.setMatrixColumnType(surveyResponseAnswerModel.getMatrixColumnType());
				surveyResponseAnswer.setOtherValue(surveyResponseAnswerModel.getOtherValue());
				surveyResponseAnswer.setQuestionType(surveyResponseAnswerModel.getQuestionType());
				surveyResponseAnswer.setResponseText(surveyResponseAnswerModel.getResponseText());
				surveyResponseAnswer.setSelected(surveyResponseAnswerModel.getSelected());
				surveyResponseAnswer.setSurveyQuestion(surveyQuestionRepository.get(SurveyQuestion.class,
						surveyResponseAnswerModel.getSurveyQuestionId()));
				surveyResponseAnswer.setSurveyQuestionAnswerChoiceColumn(
						surveyQuestionAnswerChoiceRepository.get(SurveyQuestionAnswerChoice.class,
								surveyResponseAnswerModel.getSurveyQuestionAnswerChoiceColumnId()));
				surveyResponseAnswer.setSurveyQuestionAnswerChoiceRow(
						surveyQuestionAnswerChoiceRepository.get(SurveyQuestionAnswerChoice.class,
								surveyResponseAnswerModel.getSurveyQuestionAnswerChoiceRowId()));
				surveyResponseAnswer.setSurveyQuestionAnswerChoiceSelection(
						surveyQuestionAnswerChoiceSelectionRepository.get(SurveyQuestionAnswerChoiceSelection.class,
								surveyResponseAnswerModel.getSurveyQuestionAnswerChoiceSelectionId()));
				surveyResponseAnswer.setSurveyResponse(surveyResponse);
				surveyResponseAnswer.setUniqueId(surveyResponseAnswer.getUniqueId());
				surveyResponseAnswerRepository.updateSingle(surveyResponseAnswer, isNew);
			}
		}
	}

	private SurveyResponse updateSurveyResponse(SurveyResponseRestModel surveyResponseRestModel)
			throws FrameworkException {
		SurveyResponseModel surveyResponseModel = surveyResponseRestModel.getSurveyResponse();
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

	@Override
	public List<SurveyResponse> findAllSurveyResponsesByUser(String username) throws FrameworkException {
		return surveyResponseRepository.findAllSurveyResponsesByUser(username);
	}

}
