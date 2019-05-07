package software.simple.solutions.data.entry.es.control.rest.api.v1;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import software.simple.solutions.data.entry.es.control.entities.Survey;
import software.simple.solutions.data.entry.es.control.entities.SurveyGroup;
import software.simple.solutions.data.entry.es.control.entities.SurveyQuestion;
import software.simple.solutions.data.entry.es.control.entities.SurveyQuestionAnswerChoice;
import software.simple.solutions.data.entry.es.control.entities.SurveyQuestionAnswerChoiceSelection;
import software.simple.solutions.data.entry.es.control.entities.SurveySection;
import software.simple.solutions.data.entry.es.control.rest.model.SurveyGroupModel;
import software.simple.solutions.data.entry.es.control.rest.model.SurveyModel;
import software.simple.solutions.data.entry.es.control.rest.model.SurveyQuestionAnswerChoiceModel;
import software.simple.solutions.data.entry.es.control.rest.model.SurveyQuestionAnswerChoiceSelectionModel;
import software.simple.solutions.data.entry.es.control.rest.model.SurveyQuestionModel;
import software.simple.solutions.data.entry.es.control.rest.model.SurveyRestModel;
import software.simple.solutions.data.entry.es.control.rest.model.SurveySectionModel;
import software.simple.solutions.data.entry.es.control.service.ISurveyGroupService;
import software.simple.solutions.data.entry.es.control.service.ISurveyQuestionAnswerChoiceSelectionService;
import software.simple.solutions.data.entry.es.control.service.ISurveyQuestionAnswerChoiceService;
import software.simple.solutions.data.entry.es.control.service.ISurveyQuestionSectionService;
import software.simple.solutions.data.entry.es.control.service.ISurveyQuestionService;
import software.simple.solutions.data.entry.es.control.service.ISurveyService;
import software.simple.solutions.framework.core.exceptions.FrameworkException;

@RestController
@RequestMapping(path = "rest/api/v1/surveys")
public class SurveyRestController {

	@Autowired
	private ISurveyService surveyService;

	@Autowired
	private ISurveyQuestionSectionService surveyQuestionSectionService;

	@Autowired
	private ISurveyGroupService surveyGroupService;

	@Autowired
	private ISurveyQuestionService surveyQuestionService;

	@Autowired
	private ISurveyQuestionAnswerChoiceService surveyQuestionAnswerChoiceService;

	@Autowired
	private ISurveyQuestionAnswerChoiceSelectionService surveyQuestionAnswerChoiceSelectionService;

	@GetMapping(path = "/", produces = "application/json")
	public List<SurveyRestModel> getSurveys() throws FrameworkException {
		String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<Survey> surveys = surveyService.findAllSurveysByUser(username);
		List<SurveyRestModel> surveyRestModels = new ArrayList<SurveyRestModel>();
		if (surveys != null) {
			for (Survey survey : surveys) {
				SurveyRestModel surveyModel = new SurveyRestModel();
				surveyModel.setSurvey(new SurveyModel(survey));

				List<SurveySection> surveySections = surveyQuestionSectionService.findAllBySurveyId(survey.getId());
				surveyModel.setSurveySections(getSurveySections(surveySections));

				List<SurveyGroup> surveyGroups = surveyGroupService.findAllBySurveyId(survey.getId());
				surveyModel.setSurveyGroups(getSurveyGroups(surveyGroups));

				List<SurveyQuestion> surveyQuestions = surveyQuestionService.getQuestionList(survey.getId());
				surveyModel.setSurveyQuestions(getSurveyQuestions(surveyQuestions));

				List<SurveyQuestionAnswerChoice> surveyQuestionAnswerChoices = surveyQuestionAnswerChoiceService
						.findBySurvey(survey.getId());
				surveyModel.setSurveyQuestionAnswerChoices(getSurveyQuestionAnswerChoices(surveyQuestionAnswerChoices));

				List<SurveyQuestionAnswerChoiceSelection> surveyQuestionAnswerChoiceSelections = surveyQuestionAnswerChoiceSelectionService
						.findBySurvey(survey.getId());
				surveyModel.setSurveyQuestionAnswerChoiceSelections(
						getSurveyQuestionAnswerChoiceSelections(surveyQuestionAnswerChoiceSelections));

				surveyRestModels.add(surveyModel);
			}
		}

		return surveyRestModels;
	}

	private List<SurveyQuestionAnswerChoiceSelectionModel> getSurveyQuestionAnswerChoiceSelections(
			List<SurveyQuestionAnswerChoiceSelection> surveyQuestionAnswerChoiceSelections) {
		List<SurveyQuestionAnswerChoiceSelectionModel> surveyQuestionAnswerChoiceSelectionModels = new ArrayList<SurveyQuestionAnswerChoiceSelectionModel>();
		if (surveyQuestionAnswerChoiceSelections != null) {
			for (SurveyQuestionAnswerChoiceSelection surveyQuestionAnswerChoiceSelection : surveyQuestionAnswerChoiceSelections) {
				surveyQuestionAnswerChoiceSelectionModels
						.add(new SurveyQuestionAnswerChoiceSelectionModel(surveyQuestionAnswerChoiceSelection));
			}
		}
		return surveyQuestionAnswerChoiceSelectionModels;
	}

	private List<SurveyQuestionAnswerChoiceModel> getSurveyQuestionAnswerChoices(
			List<SurveyQuestionAnswerChoice> surveyQuestionAnswerChoices) {
		List<SurveyQuestionAnswerChoiceModel> surveyQuestionAnswerChoiceModels = new ArrayList<SurveyQuestionAnswerChoiceModel>();
		if (surveyQuestionAnswerChoices != null) {
			for (SurveyQuestionAnswerChoice surveyQuestionAnswerChoice : surveyQuestionAnswerChoices) {
				surveyQuestionAnswerChoiceModels.add(new SurveyQuestionAnswerChoiceModel(surveyQuestionAnswerChoice));
			}
		}
		return surveyQuestionAnswerChoiceModels;
	}

	private List<SurveyQuestionModel> getSurveyQuestions(List<SurveyQuestion> surveyQuestions) {
		List<SurveyQuestionModel> surveyQuestionModels = new ArrayList<SurveyQuestionModel>();
		if (surveyQuestions != null) {
			for (SurveyQuestion surveyQuestion : surveyQuestions) {
				surveyQuestionModels.add(new SurveyQuestionModel(surveyQuestion));
			}
		}
		return surveyQuestionModels;
	}

	private List<SurveyGroupModel> getSurveyGroups(List<SurveyGroup> surveyGroups) {
		List<SurveyGroupModel> surveyGroupModels = new ArrayList<SurveyGroupModel>();
		if (surveyGroups != null) {
			for (SurveyGroup surveyGroup : surveyGroups) {
				surveyGroupModels.add(new SurveyGroupModel(surveyGroup));
			}
		}
		return surveyGroupModels;
	}

	private List<SurveySectionModel> getSurveySections(List<SurveySection> surveySections) {
		List<SurveySectionModel> surveySectionModels = new ArrayList<SurveySectionModel>();
		if (surveySections != null) {
			for (SurveySection surveySection : surveySections) {
				surveySectionModels.add(new SurveySectionModel(surveySection));
			}
		}
		return surveySectionModels;
	}

}
