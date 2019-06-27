package software.simple.solutions.data.entry.es.control.service;

import java.util.List;

import software.simple.solutions.data.entry.es.control.entities.SurveyResponseSection;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.service.ISuperService;

public interface ISurveyResponseSectionService extends ISuperService {

	List<SurveyResponseSection> getSurveyResponseSections(Long surveyResponseId) throws FrameworkException;

	SurveyResponseSection updateNotApplicable(Long surveyId, Long surveySectionId, Boolean selected)
			throws FrameworkException;

	SurveyResponseSection getSurveyResponseSectionByResponseAndSection(Long surveyResponseId, Long surveySectionId)
			throws FrameworkException;

}
