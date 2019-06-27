package software.simple.solutions.data.entry.es.control.repository;

import java.util.List;

import software.simple.solutions.data.entry.es.control.entities.SurveyResponseSection;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.repository.IGenericRepository;

public interface ISurveyResponseSectionRepository extends IGenericRepository {

	SurveyResponseSection getByUniqueId(String uniqueId) throws FrameworkException;

	List<SurveyResponseSection> getSurveyResponseSections(Long surveyResponseId) throws FrameworkException;

	void removeAllBySurveyResponse(Long surveyResponseId) throws FrameworkException;

	SurveyResponseSection getBySurveyResponseAndSection(Long surveyResponseId, Long surveySectionId)
			throws FrameworkException;

}
