package software.simple.solutions.data.entry.es.control.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import software.simple.solutions.data.entry.es.control.entities.Survey;
import software.simple.solutions.data.entry.es.control.entities.SurveySection;
import software.simple.solutions.data.entry.es.control.properties.SurveySectionProperty;
import software.simple.solutions.data.entry.es.control.repository.ISurveySectionRepository;
import software.simple.solutions.data.entry.es.control.service.ISurveySectionService;
import software.simple.solutions.data.entry.es.control.valueobjects.SurveySectionVO;
import software.simple.solutions.framework.core.annotations.ServiceRepository;
import software.simple.solutions.framework.core.exceptions.Arg;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.pojo.ComboItem;
import software.simple.solutions.framework.core.properties.SystemMessageProperty;
import software.simple.solutions.framework.core.service.impl.SuperService;
import software.simple.solutions.framework.core.valueobjects.SuperVO;

@Transactional
@Service
@ServiceRepository(claz = ISurveySectionRepository.class)
public class SurveySectionService extends SuperService implements ISurveySectionService {

	@Autowired
	private ISurveySectionRepository surveyQuestionSectionRepository;

	@Override
	public <T, R extends SuperVO> T updateSingle(R valueObject) throws FrameworkException {
		SurveySectionVO vo = (SurveySectionVO) valueObject;

		if (StringUtils.isBlank(vo.getName())) {
			throw new FrameworkException(SystemMessageProperty.FIELD_IS_REQUIRED,
					new Arg().key(SurveySectionProperty.NAME));
		}

		boolean isCodeUnique = isSectionCodeUniqueForSurvey(vo.getSurveyId(), null, vo.getCode());
		if (!isCodeUnique) {
			throw new FrameworkException(SystemMessageProperty.FIELD_MUST_BE_UNIQUE,
					new Arg().key(SurveySectionProperty.CODE));
		}

		SurveySection surveyQuestionSection = new SurveySection();
		if (!vo.isNew()) {
			surveyQuestionSection = get(SurveySection.class, vo.getId());
		}
		surveyQuestionSection.setCode(vo.getCode());
		surveyQuestionSection.setName(vo.getName());
		surveyQuestionSection.setDescription(vo.getDescription());
		surveyQuestionSection.setActive(vo.getActive());
		surveyQuestionSection.setEnableApplicability(vo.getEnableApplicability());
		Survey survey = get(Survey.class, vo.getSurveyId());
		surveyQuestionSection.setSurvey(survey);
		if (vo.getPinned()) {
			updateSectionsToUnPinned(survey.getId(), surveyQuestionSection.getId());
		}
		surveyQuestionSection.setPinned(vo.getPinned());

		return (T) saveOrUpdate(surveyQuestionSection, vo.isNew());
	}

	private Boolean isSectionCodeUniqueForSurvey(Long surveyId, Long surveySectionId, String sectionCode)
			throws FrameworkException {
		return surveyQuestionSectionRepository.isSectionCodeUniqueForSurvey(surveyId, surveySectionId, sectionCode);
	}

	private void updateSectionsToUnPinned(Long surveyId, Long surveySectionId) throws FrameworkException {
		surveyQuestionSectionRepository.updateSectionsToUnPinned(surveyId, surveySectionId);
	}

	@Override
	public List<ComboItem> findBySurvey(Long surveyId) throws FrameworkException {
		return surveyQuestionSectionRepository.findBySurvey(surveyId);
	}

	@Override
	public List<SurveySection> findAllBySurveyId(Long id) throws FrameworkException {
		return surveyQuestionSectionRepository.findAllBySurveyId(id);
	}

	@Override
	public SurveySection getPinnedSectionBySurvey(Long surveyId) throws FrameworkException {
		return surveyQuestionSectionRepository.getPinnedSectionBySurvey(surveyId);
	}
}
