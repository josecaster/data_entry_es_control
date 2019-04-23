package software.simple.solutions.data.entry.es.control.rest.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import software.simple.solutions.data.entry.es.control.entities.Survey;
import software.simple.solutions.data.entry.es.control.entities.SurveyGroup;
import software.simple.solutions.data.entry.es.control.entities.SurveyQuestion;
import software.simple.solutions.data.entry.es.control.entities.SurveyQuestionAnswerChoice;
import software.simple.solutions.data.entry.es.control.entities.SurveyQuestionAnswerChoiceSelection;
import software.simple.solutions.data.entry.es.control.pojo.SurveyPojo;
import software.simple.solutions.data.entry.es.control.service.ISurveyQuestionAnswerChoiceSelectionService;
import software.simple.solutions.data.entry.es.control.service.ISurveyQuestionAnswerChoiceService;
import software.simple.solutions.data.entry.es.control.service.ISurveyQuestionGroupService;
import software.simple.solutions.data.entry.es.control.service.ISurveyQuestionService;
import software.simple.solutions.data.entry.es.control.service.ISurveyService;
import software.simple.solutions.framework.core.exceptions.FrameworkException;

@RestController
@RequestMapping(path = "rest/api/v1/surveys")
public class SurveyRestController {

	@Autowired
	private ISurveyService surveyService;

	@Autowired
	private ISurveyQuestionGroupService surveyQuestionGroupService;

	@Autowired
	private ISurveyQuestionService surveyQuestionService;

	@Autowired
	private ISurveyQuestionAnswerChoiceService surveyQuestionAnswerChoiceService;

	@Autowired
	private ISurveyQuestionAnswerChoiceSelectionService surveyQuestionAnswerChoiceSelectionService;

	@GetMapping(path = "/", produces = "application/json")
	public List<SurveyPojo> getSurveys() throws FrameworkException {
		List<Survey> surveys = surveyService.findAllSurveys();
		List<SurveyPojo> surveyModels = new ArrayList<SurveyPojo>();
		if (surveys != null) {
			for (Survey survey : surveys) {
				SurveyPojo surveyModel = new SurveyPojo();
				surveyModel.setSurvey(survey);

				List<SurveyGroup> surveyGroups = surveyQuestionGroupService.findAllBySurveyId(survey.getId());
				surveyModel.setSurveyGroups(surveyGroups);

				List<SurveyQuestion> surveyQuestions = surveyQuestionService.getQuestionList(survey.getId());
				surveyModel.setSurveyQuestions(surveyQuestions);

				List<SurveyQuestionAnswerChoice> surveyQuestionAnswerChoices = surveyQuestionAnswerChoiceService
						.findBySurvey(survey.getId());
				surveyModel.setSurveyQuestionAnswerChoices(surveyQuestionAnswerChoices);

				List<SurveyQuestionAnswerChoiceSelection> surveyQuestionAnswerChoiceSelections = surveyQuestionAnswerChoiceSelectionService
						.findBySurvey(survey.getId());
				surveyModel.setSurveyQuestionAnswerChoiceSelections(surveyQuestionAnswerChoiceSelections);

				surveyModels.add(surveyModel);
			}
		}

		return surveyModels;
	}

}