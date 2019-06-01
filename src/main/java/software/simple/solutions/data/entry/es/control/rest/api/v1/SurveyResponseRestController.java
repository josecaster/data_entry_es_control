package software.simple.solutions.data.entry.es.control.rest.api.v1;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import software.simple.solutions.data.entry.es.control.entities.SurveyResponse;
import software.simple.solutions.data.entry.es.control.entities.SurveyResponseAnswer;
import software.simple.solutions.data.entry.es.control.entities.SurveyResponseSection;
import software.simple.solutions.data.entry.es.control.rest.model.SurveyResponseAnswerModel;
import software.simple.solutions.data.entry.es.control.rest.model.SurveyResponseModel;
import software.simple.solutions.data.entry.es.control.rest.model.SurveyResponseRestModel;
import software.simple.solutions.data.entry.es.control.rest.model.SurveyResponseSectionModel;
import software.simple.solutions.data.entry.es.control.service.ISurveyResponseAnswerService;
import software.simple.solutions.data.entry.es.control.service.ISurveyResponseSectionService;
import software.simple.solutions.data.entry.es.control.service.ISurveyResponseService;
import software.simple.solutions.framework.core.exceptions.FrameworkException;

@RestController
@RequestMapping(path = "rest/api/v1/surveyResponses")
public class SurveyResponseRestController {

	@Autowired
	private ISurveyResponseService surveyResponseService;

	@Autowired
	private ISurveyResponseAnswerService surveyResponseAnswerService;

	@Autowired
	private ISurveyResponseSectionService surveyResponseSectionService;

	@PostMapping(path = "/", consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE,
			MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.TEXT_PLAIN_VALUE })
	public int createSurveyResponse(@RequestBody SurveyResponseRestModel surveyResponseRestModel)
			throws FrameworkException {
		surveyResponseService.updateFromRest(surveyResponseRestModel);
		return HttpServletResponse.SC_OK;
	}

	@GetMapping(path = "/", produces = "application/json")
	public List<SurveyResponseRestModel> getSurveyResponse() throws FrameworkException {
		// String username = (String)
		// SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = user.getUsername();
		List<SurveyResponse> surveyResponses = surveyResponseService.findAllSurveyResponsesByUser(username);
		List<SurveyResponseRestModel> surveyResponseRestModels = new ArrayList<SurveyResponseRestModel>();
		if (surveyResponses != null) {
			for (SurveyResponse surveyResponse : surveyResponses) {
				SurveyResponseRestModel surveyResponseRestModel = new SurveyResponseRestModel();
				surveyResponseRestModel.setSurveyResponse(new SurveyResponseModel(surveyResponse));

				surveyResponseRestModel.setSurveyResponseAnswers(getSurveyResponseAnswers(surveyResponse));

				surveyResponseRestModel.setSurveyResponseSections(getSurveyResponseSections(surveyResponse));

				surveyResponseRestModels.add(surveyResponseRestModel);
			}
		}

		return surveyResponseRestModels;
	}

	private List<SurveyResponseAnswerModel> getSurveyResponseAnswers(SurveyResponse surveyResponse)
			throws FrameworkException {
		List<SurveyResponseAnswer> surveyResponseAnswers = surveyResponseAnswerService
				.getSurveyResponseAnswers(surveyResponse.getId());
		List<SurveyResponseAnswerModel> surveyResponseAnswerModels = new ArrayList<SurveyResponseAnswerModel>();
		if (surveyResponseAnswers != null) {
			for (SurveyResponseAnswer surveyResponseAnswer : surveyResponseAnswers) {
				surveyResponseAnswerModels.add(new SurveyResponseAnswerModel(surveyResponseAnswer));
			}
		}
		return surveyResponseAnswerModels;
	}

	private List<SurveyResponseSectionModel> getSurveyResponseSections(SurveyResponse surveyResponse)
			throws FrameworkException {
		List<SurveyResponseSection> surveyResponseSections = surveyResponseSectionService
				.getSurveyResponseSections(surveyResponse.getId());
		List<SurveyResponseSectionModel> surveyResponseSectionModels = new ArrayList<SurveyResponseSectionModel>();
		if (surveyResponseSections != null) {
			for (SurveyResponseSection surveyResponseSection : surveyResponseSections) {
				surveyResponseSectionModels.add(new SurveyResponseSectionModel(surveyResponseSection));
			}
		}
		return surveyResponseSectionModels;
	}

}
