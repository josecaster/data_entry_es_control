package software.simple.solutions.data.entry.es.control.service;

import java.util.List;

import software.simple.solutions.data.entry.es.control.entities.Survey;
import software.simple.solutions.framework.core.entities.Configuration;
import software.simple.solutions.framework.core.entities.EntityFile;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.service.ISuperService;
import software.simple.solutions.framework.core.valueobjects.EntityFileVO;

public interface ISurveyService extends ISuperService {

	EntityFile upLoadFile(EntityFileVO vo) throws FrameworkException;

	List<Survey> findAllSurveys() throws FrameworkException;

	List<Survey> findAllSurveysByUser(String username) throws FrameworkException;

	List<Configuration> getEsControlConfigurations() throws FrameworkException;

}
