package software.simple.solutions.data.entry.es.control.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import software.simple.solutions.data.entry.es.control.entities.Survey;
import software.simple.solutions.data.entry.es.control.entities.SurveyGroup;
import software.simple.solutions.data.entry.es.control.properties.SurveyGroupProperty;
import software.simple.solutions.data.entry.es.control.repository.ISurveyQuestionGroupRepository;
import software.simple.solutions.data.entry.es.control.service.ISurveyQuestionGroupService;
import software.simple.solutions.data.entry.es.control.valueobjects.SurveyGroupVO;
import software.simple.solutions.framework.core.annotations.ServiceRepository;
import software.simple.solutions.framework.core.exceptions.Arg;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.pojo.ComboItem;
import software.simple.solutions.framework.core.properties.SystemMessageProperty;
import software.simple.solutions.framework.core.service.impl.SuperService;
import software.simple.solutions.framework.core.valueobjects.SuperVO;

@Transactional
@Service
@ServiceRepository(claz = ISurveyQuestionGroupRepository.class)
public class SurveyQuestionGroupService extends SuperService implements ISurveyQuestionGroupService {

	@Autowired
	private ISurveyQuestionGroupRepository surveyQuestionGroupRepository;

	@Override
	public <T, R extends SuperVO> T updateSingle(R valueObject) throws FrameworkException {
		SurveyGroupVO vo = (SurveyGroupVO) valueObject;

		if (StringUtils.isBlank(vo.getName())) {
			throw new FrameworkException(SystemMessageProperty.FIELD_IS_REQUIRED,
					new Arg().key(SurveyGroupProperty.NAME));
		}

		SurveyGroup surveyQuestionGroup = new SurveyGroup();
		if (!vo.isNew()) {
			surveyQuestionGroup = get(SurveyGroup.class, vo.getId());
		}
		surveyQuestionGroup.setName(vo.getName());
		surveyQuestionGroup.setDescription(vo.getDescription());
		surveyQuestionGroup.setActive(vo.getActive());
		surveyQuestionGroup.setEnableApplicability(vo.getEnableApplicability());
		Survey survey = get(Survey.class, vo.getSurveyId());
		surveyQuestionGroup.setSurvey(survey);
		if (vo.getPinned()) {
			updateGroupsToUnPinned(survey.getId(), surveyQuestionGroup.getId());
		}
		surveyQuestionGroup.setPinned(vo.getPinned());

		return (T) saveOrUpdate(surveyQuestionGroup, vo.isNew());
	}

	private void updateGroupsToUnPinned(Long surveyId, Long surveyGroupId) throws FrameworkException {
		surveyQuestionGroupRepository.updateGroupsToUnPinned(surveyId, surveyGroupId);
	}

	@Override
	public List<ComboItem> findBySurvey(Long surveyId) throws FrameworkException {
		return surveyQuestionGroupRepository.findBySurvey(surveyId);
	}

	@Override
	public List<SurveyGroup> findAllBySurveyId(Long id) throws FrameworkException {
		return surveyQuestionGroupRepository.findAllBySurveyId(id);
	}

	@Override
	public SurveyGroup getPinnedGroupBySurvey(Long surveyId) throws FrameworkException {
		return surveyQuestionGroupRepository.getPinnedGroupBySurvey(surveyId);
	}
}
