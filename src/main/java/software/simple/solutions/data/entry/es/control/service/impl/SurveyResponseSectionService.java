package software.simple.solutions.data.entry.es.control.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

import software.simple.solutions.data.entry.es.control.entities.SurveyResponse;
import software.simple.solutions.data.entry.es.control.entities.SurveyResponseSection;
import software.simple.solutions.data.entry.es.control.entities.SurveySection;
import software.simple.solutions.data.entry.es.control.repository.ISurveyResponseSectionRepository;
import software.simple.solutions.data.entry.es.control.service.ISurveyResponseSectionService;
import software.simple.solutions.framework.core.annotations.ServiceRepository;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.service.impl.SuperService;

@Transactional(propagation=Propagation.REQUIRED, rollbackFor = Exception.class)
@Service
@ServiceRepository(claz = ISurveyResponseSectionRepository.class)
public class SurveyResponseSectionService extends SuperService implements ISurveyResponseSectionService {

	@Autowired
	private ISurveyResponseSectionRepository surveyResponseSectionRepository;

	@Override
	public List<SurveyResponseSection> getSurveyResponseSections(Long surveyResponseId) throws FrameworkException {
		return surveyResponseSectionRepository.getSurveyResponseSections(surveyResponseId);
	}

	@Override
	public SurveyResponseSection updateNotApplicable(Long surveyResponseId, Long surveySectionId, Boolean selected)
			throws FrameworkException {
		SurveyResponseSection surveyResponseSection = surveyResponseSectionRepository
				.getBySurveyResponseAndSection(surveyResponseId, surveySectionId);
		boolean isNew = false;
		if (surveyResponseSection == null) {
			surveyResponseSection = new SurveyResponseSection();
			isNew = true;
		}
		surveyResponseSection.setActive(true);
		surveyResponseSection.setNotApplicable(selected);
		surveyResponseSection.setSurveyResponse(get(SurveyResponse.class, surveyResponseId));
		surveyResponseSection.setSurveySection(get(SurveySection.class, surveySectionId));
		surveyResponseSection.setUniqueId(UUID.randomUUID().toString());
		return saveOrUpdate(surveyResponseSection, isNew);
	}

	@Override
	public SurveyResponseSection getSurveyResponseSectionByResponseAndSection(Long surveyResponseId,
			Long surveySectionId) throws FrameworkException {
		return surveyResponseSectionRepository.getBySurveyResponseAndSection(surveyResponseId, surveySectionId);
	}

}
