package software.simple.solutions.data.entry.es.control.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import software.simple.solutions.data.entry.es.control.entities.SurveyResponseSection;
import software.simple.solutions.data.entry.es.control.repository.ISurveyResponseSectionRepository;
import software.simple.solutions.data.entry.es.control.service.ISurveyResponseSectionService;
import software.simple.solutions.framework.core.annotations.ServiceRepository;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.service.impl.SuperService;

@Transactional
@Service
@ServiceRepository(claz = ISurveyResponseSectionRepository.class)
public class SurveyResponseSectionService extends SuperService implements ISurveyResponseSectionService {

	@Autowired
	private ISurveyResponseSectionRepository surveyResponseSectionRepository;

	@Override
	public List<SurveyResponseSection> getSurveyResponseSections(Long surveyResponseId) throws FrameworkException {
		return surveyResponseSectionRepository.getSurveyResponseSections(surveyResponseId);
	}

}
