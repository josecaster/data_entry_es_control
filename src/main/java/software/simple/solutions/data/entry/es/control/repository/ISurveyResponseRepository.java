package software.simple.solutions.data.entry.es.control.repository;

import java.util.List;

import software.simple.solutions.data.entry.es.control.entities.SurveyResponse;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.repository.IGenericRepository;

public interface ISurveyResponseRepository extends IGenericRepository {

	SurveyResponse getByUniqueId(String uniqueId) throws FrameworkException;

	List<SurveyResponse> findAllSurveyResponsesByUser(String username) throws FrameworkException;

	List<String> findAllActiveSurveyResponseUuIdsByUser(String username) throws FrameworkException;

	Boolean removeAllFormData(Long surveyResponseId) throws FrameworkException;

}
