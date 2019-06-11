package software.simple.solutions.data.entry.es.control.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import software.simple.solutions.data.entry.es.control.constants.QuestionType;
import software.simple.solutions.data.entry.es.control.constants.State;
import software.simple.solutions.data.entry.es.control.entities.Survey;
import software.simple.solutions.data.entry.es.control.entities.SurveyQuestion;
import software.simple.solutions.data.entry.es.control.entities.SurveyQuestionAnswerChoice;
import software.simple.solutions.data.entry.es.control.entities.SurveyQuestionAnswerChoiceSelection;
import software.simple.solutions.data.entry.es.control.entities.SurveyResponse;
import software.simple.solutions.data.entry.es.control.entities.SurveyResponseAnswer;
import software.simple.solutions.data.entry.es.control.entities.SurveyResponseSection;
import software.simple.solutions.data.entry.es.control.entities.SurveySection;
import software.simple.solutions.data.entry.es.control.properties.SurveyResponseProperty;
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
import software.simple.solutions.data.entry.es.control.service.ISurveyResponseAnswerService;
import software.simple.solutions.data.entry.es.control.service.ISurveyResponseService;
import software.simple.solutions.data.entry.es.control.valueobjects.SurveyResponseVO;
import software.simple.solutions.framework.core.annotations.ServiceRepository;
import software.simple.solutions.framework.core.constants.DateConstant;
import software.simple.solutions.framework.core.entities.ApplicationUser;
import software.simple.solutions.framework.core.exceptions.Arg;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.SystemMessageProperty;
import software.simple.solutions.framework.core.repository.IApplicationUserRepository;
import software.simple.solutions.framework.core.service.impl.SuperService;
import software.simple.solutions.framework.core.valueobjects.SuperVO;

@Transactional
@Service
@ServiceRepository(claz = ISurveyResponseRepository.class)
public class SurveyResponseService extends SuperService implements ISurveyResponseService {

	protected static final Logger logger = LogManager.getLogger(SurveyResponseService.class);

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

	@Autowired
	private ISurveyResponseAnswerService surveyResponseAnswerService;

	@Override
	public <T, R extends SuperVO> T updateSingle(R valueObject) throws FrameworkException {
		SurveyResponseVO vo = (SurveyResponseVO) valueObject;

		if (StringUtils.isBlank(vo.getFormName())) {
			throw new FrameworkException(SystemMessageProperty.FIELD_IS_REQUIRED,
					new Arg().key(SurveyResponseProperty.FORM_NAME));
		}

		if (vo.getSurveyId() == null) {
			throw new FrameworkException(SystemMessageProperty.FIELD_IS_REQUIRED,
					new Arg().key(SurveyResponseProperty.SURVEY));
		}

		if (vo.getApplicationUserId() == null) {
			throw new FrameworkException(SystemMessageProperty.FIELD_IS_REQUIRED,
					new Arg().key(SurveyResponseProperty.APPLICATION_USER));
		}

		SurveyResponse surveyResponse = get(SurveyResponse.class, vo.getId());
		if (surveyResponse == null) {
			surveyResponse = new SurveyResponse();
			surveyResponse.setCreatedOn(LocalDateTime.now());
			surveyResponse.setUniqueId(UUID.randomUUID().toString());
		}
		surveyResponse.setActive(vo.getActive());
		surveyResponse.setFormName(vo.getFormName());
		ApplicationUser applicationUser = getById(ApplicationUser.class, vo.getApplicationUserId());
		surveyResponse.setApplicationUser(applicationUser);
		surveyResponse.setSurvey(surveyRepository.getById(Survey.class, vo.getSurveyId()));
		surveyResponse.setUpdatedByUser(applicationUser);
		surveyResponse.setUpdatedDate(LocalDateTime.now());

		return (T) saveOrUpdate(surveyResponse, vo.isNew());
	}

	@Override
	public SurveyResponse updateFromRest(SurveyResponseRestModel surveyResponseRestModel) throws FrameworkException {

		SurveyResponse surveyResponse = updateSurveyResponse(surveyResponseRestModel);

		// cleanPreviousResponses(surveyResponse.getId());

		updateSurveyResponseAnswer(surveyResponseRestModel, surveyResponse);

		updateSurveyResponseSection(surveyResponseRestModel, surveyResponse);

		return surveyResponse;
	}

	// private void cleanPreviousResponses(Long surveyResponseId) throws
	// FrameworkException {
	// surveyResponseSectionRepository.removeAllBySurveyResponse(surveyResponseId);
	// surveyResponseAnswerRepository.removeAllBySurveyResponse(surveyResponseId);
	// }

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
			Map<Pair<String, Long>, List<SurveyResponseAnswerModel>> map = null;
			try{
			map = surveyResponseAnswers.stream()
					.filter(p -> (p.getQuestionType().equalsIgnoreCase(QuestionType.CHOICES)
							|| p.getQuestionType().equalsIgnoreCase(QuestionType.MATRIX)))
					.collect(Collectors
							.groupingBy(p -> Pair.of(p.getSurveyResponseUniqueId(), p.getSurveyQuestionId())));
			}catch (Exception e) {
				e.printStackTrace();
			}
			for(Entry<Pair<String, Long>, List<SurveyResponseAnswerModel>> entry:map.entrySet()){
				try {
					Pair<String,Long> key = entry.getKey();
					surveyResponseAnswerRepository.removeByResponseUniqueIdAndQuestion(key.getFirst(), key.getSecond());
				} catch (FrameworkException e) {
					logger.error(e.getMessage(), e);
				}
			}

			for (SurveyResponseAnswerModel surveyResponseAnswerModel : surveyResponseAnswers) {

				SurveyResponseAnswer surveyResponseAnswer = null;
				SurveyQuestion surveyQuestion = surveyQuestionRepository.get(SurveyQuestion.class,
						surveyResponseAnswerModel.getSurveyQuestionId());
				String questionType = surveyQuestion.getQuestionType();
				switch (questionType) {
				case QuestionType.SINGLE:
				case QuestionType.DATE:
				case QuestionType.LENGTH_FT_INCH:
				case QuestionType.AREA_FT_INCH:
					surveyResponseAnswer = surveyResponseAnswerRepository.getSurveyResponseAnswer(
							surveyResponseAnswerModel.getSurveyResponseUniqueId(),
							surveyResponseAnswerModel.getSurveyQuestionId());
					break;
				default:
					break;
				}

				boolean isNew = false;
				if (surveyResponseAnswer == null) {
					isNew = true;
					surveyResponseAnswer = new SurveyResponseAnswer();
					surveyResponseAnswer.setUniqueId(UUID.randomUUID().toString());
				}
				surveyResponseAnswer.setActive(surveyResponseAnswerModel.getActive());
				surveyResponseAnswer.setMatrixColumnType(surveyResponseAnswerModel.getMatrixColumnType());
				surveyResponseAnswer.setOtherValue(surveyResponseAnswerModel.getOtherValue());
				surveyResponseAnswer.setResponseText(surveyResponseAnswerModel.getResponseText());
				surveyResponseAnswer.setSelected(surveyResponseAnswerModel.getSelected());
				surveyResponseAnswer.setSurveyQuestion(surveyQuestion);
				surveyResponseAnswer.setQuestionType(surveyResponseAnswerModel.getQuestionType());
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
				surveyResponseAnswer.setState(State.SYNCED);
				surveyResponseAnswer = surveyResponseAnswerRepository.updateSingle(surveyResponseAnswer, isNew);

				surveyResponseAnswerService.createUpdateAnswerHistory(surveyResponseAnswer);
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
			surveyResponse.setCreatedOn(
					LocalDateTime.parse(surveyResponseModel.getCreatedOn(), DateConstant.DATE_TIME_FORMAT));
		}
		surveyResponse.setActive(surveyResponseModel.getActive());
		surveyResponse.setUniqueId(surveyResponseModel.getUniqueId());
		surveyResponse.setFormName(surveyResponseModel.getFormName());
		surveyResponse.setSurvey(surveyRepository.getById(Survey.class, surveyResponseModel.getSurveyId()));
		// ApplicationUser applicationUser =
		// applicationUserRepository.getByUserName(surveyResponseModel.getUsername());
		// surveyResponse.setApplicationUser(applicationUser);
		// surveyResponse.setUpdatedByUser(applicationUser);
		surveyResponse.setUpdatedDate(LocalDateTime.now());
		surveyResponse = surveyResponseRepository.updateSingle(surveyResponse, isNew);
		return surveyResponse;
	}

	@Override
	public List<SurveyResponse> findAllSurveyResponsesByUser(String username) throws FrameworkException {
		return surveyResponseRepository.findAllSurveyResponsesByUser(username);
	}

	@Override
	public List<String> findAllActiveSurveyResponseUuIdsByUser(String username) throws FrameworkException {
		return surveyResponseRepository.findAllActiveSurveyResponseUuIdsByUser(username);
	}

}
