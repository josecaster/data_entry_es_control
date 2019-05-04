package software.simple.solutions.data.entry.es.control.service;

import java.util.List;

import software.simple.solutions.data.entry.es.control.entities.SurveySection;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.pojo.ComboItem;
import software.simple.solutions.framework.core.service.ISuperService;

public interface ISurveyQuestionSectionService extends ISuperService {

	List<ComboItem> findBySurvey(Long surveyId) throws FrameworkException;

	List<SurveySection> findAllBySurveyId(Long id) throws FrameworkException;

	SurveySection getPinnedSectionBySurvey(Long id) throws FrameworkException;

}
