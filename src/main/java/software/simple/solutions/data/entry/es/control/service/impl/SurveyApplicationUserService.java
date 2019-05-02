package software.simple.solutions.data.entry.es.control.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import software.simple.solutions.data.entry.es.control.entities.Survey;
import software.simple.solutions.data.entry.es.control.entities.SurveyApplicationUser;
import software.simple.solutions.data.entry.es.control.properties.SurveyApplicationUserProperty;
import software.simple.solutions.data.entry.es.control.repository.ISurveyApplicationUserRepository;
import software.simple.solutions.data.entry.es.control.service.ISurveyApplicationUserService;
import software.simple.solutions.data.entry.es.control.valueobjects.SurveyApplicationUserVO;
import software.simple.solutions.framework.core.annotations.ServiceRepository;
import software.simple.solutions.framework.core.entities.ApplicationUser;
import software.simple.solutions.framework.core.exceptions.Arg;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.SystemMessageProperty;
import software.simple.solutions.framework.core.service.impl.SuperService;
import software.simple.solutions.framework.core.valueobjects.SuperVO;

@Transactional
@Service
@ServiceRepository(claz = ISurveyApplicationUserRepository.class)
public class SurveyApplicationUserService extends SuperService implements ISurveyApplicationUserService {

	@Autowired
	private ISurveyApplicationUserRepository surveyApplicationUserRepository;

	@Override
	public <T, R extends SuperVO> T updateSingle(R valueObject) throws FrameworkException {
		SurveyApplicationUserVO vo = (SurveyApplicationUserVO) valueObject;

		if (vo.getApplicationUserId() == null) {
			throw new FrameworkException(SystemMessageProperty.FIELD_IS_REQUIRED,
					new Arg().key(SurveyApplicationUserProperty.APPLICATION_USER));
		}

		if (vo.getSurveyId() == null) {
			throw new FrameworkException(SystemMessageProperty.FIELD_IS_REQUIRED,
					new Arg().key(SurveyApplicationUserProperty.SURVEY));
		}

		SurveyApplicationUser surveyApplicationUser = new SurveyApplicationUser();
		if (!vo.isNew()) {
			surveyApplicationUser = get(SurveyApplicationUser.class, vo.getId());
		}
		Survey survey = get(Survey.class, vo.getSurveyId());
		surveyApplicationUser.setSurvey(survey);
		surveyApplicationUser.setApplicationUser(get(ApplicationUser.class, vo.getApplicationUserId()));

		return (T) saveOrUpdate(surveyApplicationUser, vo.isNew());
	}

}
