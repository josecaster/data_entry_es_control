package software.simple.solutions.data.entry.es.control.repository.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import software.simple.solutions.data.entry.es.control.entities.Survey;
import software.simple.solutions.data.entry.es.control.repository.ISurveyRepository;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.repository.impl.GenericRepository;

@Repository
public class SurveyRepository extends GenericRepository implements ISurveyRepository {

	@Override
	public List<Survey> findAllSurveys() throws FrameworkException {
		String query = "from Survey";
		return createListQuery(query);
	}

}
