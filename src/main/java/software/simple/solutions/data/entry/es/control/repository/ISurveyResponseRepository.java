package software.simple.solutions.data.entry.es.control.repository;

import software.simple.solutions.data.entry.es.control.entities.SurveyResponse;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.repository.IGenericRepository;

public interface ISurveyResponseRepository extends IGenericRepository {

	SurveyResponse getByUniqueId(String uniqueId) throws FrameworkException;

}
