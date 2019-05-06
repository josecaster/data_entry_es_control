package software.simple.solutions.data.entry.es.control.repository;

import java.util.List;

import software.simple.solutions.data.entry.es.control.entities.SurveyGroup;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.pojo.ComboItem;
import software.simple.solutions.framework.core.repository.IGenericRepository;

public interface ISurveyGroupRepository extends IGenericRepository {

	List<ComboItem> findBySurvey(Long surveyId) throws FrameworkException;

	List<SurveyGroup> findAllBySurveyId(Long id) throws FrameworkException;

}
