package software.simple.solutions.data.entry.es.control.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

import software.simple.solutions.data.entry.es.control.entities.Survey;
import software.simple.solutions.data.entry.es.control.entities.SurveyGroup;
import software.simple.solutions.data.entry.es.control.properties.SurveyGroupProperty;
import software.simple.solutions.data.entry.es.control.repository.ISurveyGroupRepository;
import software.simple.solutions.data.entry.es.control.service.ISurveyGroupService;
import software.simple.solutions.data.entry.es.control.valueobjects.SurveyGroupVO;
import software.simple.solutions.framework.core.annotations.ServiceRepository;
import software.simple.solutions.framework.core.exceptions.Arg;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.pojo.ComboItem;
import software.simple.solutions.framework.core.properties.SystemMessageProperty;
import software.simple.solutions.framework.core.service.impl.SuperService;
import software.simple.solutions.framework.core.valueobjects.SuperVO;

@Transactional(propagation=Propagation.REQUIRED, rollbackFor = Exception.class)
@Service
@ServiceRepository(claz = ISurveyGroupRepository.class)
public class SurveyGroupService extends SuperService implements ISurveyGroupService {

	@Autowired
	private ISurveyGroupRepository surveyGroupRepository;

	@Override
	public <T, R extends SuperVO> T updateSingle(R valueObject) throws FrameworkException {
		SurveyGroupVO vo = (SurveyGroupVO) valueObject;

		if (StringUtils.isBlank(vo.getName())) {
			throw new FrameworkException(SystemMessageProperty.FIELD_IS_REQUIRED,
					new Arg().key(SurveyGroupProperty.NAME));
		}

		SurveyGroup surveyGroup = new SurveyGroup();
		if (!vo.isNew()) {
			surveyGroup = get(SurveyGroup.class, vo.getId());
		}
		surveyGroup.setName(vo.getName());
		surveyGroup.setDescription(vo.getDescription());
		surveyGroup.setActive(vo.getActive());
		Survey survey = get(Survey.class, vo.getSurveyId());
		surveyGroup.setSurvey(survey);

		return (T) saveOrUpdate(surveyGroup, vo.isNew());
	}

	@Override
	public List<ComboItem> findBySurvey(Long surveyId) throws FrameworkException {
		return surveyGroupRepository.findBySurvey(surveyId);
	}

	@Override
	public List<SurveyGroup> findAllBySurveyId(Long id) throws FrameworkException {
		return surveyGroupRepository.findAllBySurveyId(id);
	}
}
