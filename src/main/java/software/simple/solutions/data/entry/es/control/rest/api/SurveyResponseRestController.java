package software.simple.solutions.data.entry.es.control.rest.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import software.simple.solutions.data.entry.es.control.rest.model.SurveyResponseModel;
import software.simple.solutions.data.entry.es.control.service.ISurveyQuestionAnswerChoiceSelectionService;
import software.simple.solutions.data.entry.es.control.service.ISurveyQuestionAnswerChoiceService;
import software.simple.solutions.data.entry.es.control.service.ISurveyQuestionSectionService;
import software.simple.solutions.data.entry.es.control.service.ISurveyQuestionService;
import software.simple.solutions.data.entry.es.control.service.ISurveyResponseService;
import software.simple.solutions.framework.core.exceptions.FrameworkException;

@RestController
@RequestMapping(path = "rest/api/v1/surveyResponse")
public class SurveyResponseRestController {

	@Autowired
	private ISurveyResponseService surveyResponseService;

	@Autowired
	private ISurveyQuestionSectionService surveyQuestionSectionService;

	@Autowired
	private ISurveyQuestionService surveyQuestionService;

	@Autowired
	private ISurveyQuestionAnswerChoiceService surveyQuestionAnswerChoiceService;

	@Autowired
	private ISurveyQuestionAnswerChoiceSelectionService surveyQuestionAnswerChoiceSelectionService;

	@RequestMapping(path = "/create", method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE,
			MediaType.TEXT_PLAIN_VALUE })
	public String createSurveyResponse(@RequestBody SurveyResponseModel surveyResponseModel) throws FrameworkException {
		surveyResponseService.updateFromRest(surveyResponseModel);
		System.out.println(surveyResponseModel);
		return "done";
	}

}
