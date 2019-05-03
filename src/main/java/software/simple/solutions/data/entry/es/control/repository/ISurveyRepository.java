package software.simple.solutions.data.entry.es.control.repository;

import java.util.List;

import software.simple.solutions.data.entry.es.control.entities.Survey;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.repository.IGenericRepository;

public interface ISurveyRepository extends IGenericRepository {

	List<Survey> findAllSurveys() throws FrameworkException;

	List<Survey> findAllSurveysByUser(String username) throws FrameworkException;

}
