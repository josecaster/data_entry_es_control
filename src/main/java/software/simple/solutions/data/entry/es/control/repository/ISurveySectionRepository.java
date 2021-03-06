package software.simple.solutions.data.entry.es.control.repository;

import java.util.List;

import software.simple.solutions.data.entry.es.control.entities.SurveySection;
import software.simple.solutions.data.entry.es.control.valueobjects.SurveySectionVO;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.pojo.ComboItem;
import software.simple.solutions.framework.core.repository.IGenericRepository;

public interface ISurveySectionRepository extends IGenericRepository {

	List<ComboItem> findBySurvey(Long surveyId) throws FrameworkException;

	List<SurveySection> findAllBySurveyId(Long id) throws FrameworkException;

	void updateSectionsToUnPinned(Long surveyId, Long surveySectionId) throws FrameworkException;

	SurveySection getPinnedSectionBySurvey(Long surveyId) throws FrameworkException;

	Boolean isSectionCodeUniqueForSurvey(Long surveyId, Long surveySectionId, String sectionCode)
			throws FrameworkException;

	List<ComboItem> getForListing(SurveySectionVO vo) throws FrameworkException;

}
