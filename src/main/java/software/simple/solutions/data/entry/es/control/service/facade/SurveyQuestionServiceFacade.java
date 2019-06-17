package software.simple.solutions.data.entry.es.control.service.facade;

import java.util.List;

import com.vaadin.ui.UI;

import software.simple.solutions.data.entry.es.control.entities.SurveyQuestion;
import software.simple.solutions.data.entry.es.control.service.ISurveyQuestionService;
import software.simple.solutions.data.entry.es.control.valueobjects.SurveyQuestionVO;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.pojo.ComboItem;
import software.simple.solutions.framework.core.service.facade.SuperServiceFacade;

public class SurveyQuestionServiceFacade extends SuperServiceFacade<ISurveyQuestionService>
		implements ISurveyQuestionService {

	public SurveyQuestionServiceFacade(UI ui, Class<ISurveyQuestionService> s) {
		super(ui, s);
	}

	public static SurveyQuestionServiceFacade get(UI ui) {
		return new SurveyQuestionServiceFacade(ui, ISurveyQuestionService.class);
	}

	@Override
	public List<ComboItem> getQuestionListForOrder(Long surveyId, Long surveyQuestionId) throws FrameworkException {
		return service.getQuestionListForOrder(surveyId, surveyQuestionId);
	}

	@Override
	public SurveyQuestion updateOrder(SurveyQuestionVO vo) throws FrameworkException {
		return service.updateOrder(vo);
	}

	@Override
	public List<SurveyQuestion> getQuestionList(Long surveyId) throws FrameworkException {
		return service.getQuestionList(surveyId);
	}

	@Override
	public List<SurveyQuestion> getQuestionList(Long surveyId, String queryText) throws FrameworkException {
		return service.getQuestionList(surveyId, queryText);
	}

	@Override
	public List<SurveyQuestion> getQuestionList(Long surveyId, String queryText, Long surveySectionId)
			throws FrameworkException {
		return service.getQuestionList(surveyId, queryText, surveySectionId);
	}

	@Override
	public SurveyQuestion updateOptions(SurveyQuestionVO vo) throws FrameworkException {
		return service.updateOptions(vo);
	}

	@Override
	public void updateMultipleSelection(SurveyQuestionVO vo) throws FrameworkException {
		service.updateMultipleSelection(vo);
	}

	@Override
	public List<ComboItem> getNextQuestions(Long order) throws FrameworkException {
		return service.getNextQuestions(order);
	}

	@Override
	public SurveyQuestion updateSurveyQuestionSection(Long surveyQuestionId, Long sectionId) throws FrameworkException {
		return service.updateSurveyQuestionSection(surveyQuestionId, sectionId);
	}

	@Override
	public SurveyQuestion updateSurveyQuestionGroup(Long surveyQuestionId, Long groupId) throws FrameworkException {
		return service.updateSurveyQuestionGroup(surveyQuestionId, groupId);
	}

	@Override
	public SurveyQuestion updateDescription(Long surveyQuestionId, String description) throws FrameworkException {
		return service.updateDescription(surveyQuestionId, description);
	}

	@Override
	public SurveyQuestion updateRequired(Long surveyQuestionId, Boolean required) throws FrameworkException {
		return service.updateRequired(surveyQuestionId, required);
	}

	@Override
	public SurveyQuestion updateRequiredError(Long surveyQuestionId, String requiredError) throws FrameworkException {
		return service.updateRequiredError(surveyQuestionId, requiredError);
	}

	@Override
	public void removeUsersFromQuestions(Long surveyId, Long userId) throws FrameworkException {
		service.removeUsersFromQuestions(surveyId, userId);
	}
}
