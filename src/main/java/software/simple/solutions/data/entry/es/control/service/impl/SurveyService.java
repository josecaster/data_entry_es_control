package software.simple.solutions.data.entry.es.control.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import software.simple.solutions.data.entry.es.control.entities.Survey;
import software.simple.solutions.data.entry.es.control.entities.SurveyApplicationUser;
import software.simple.solutions.data.entry.es.control.entities.SurveySection;
import software.simple.solutions.data.entry.es.control.properties.EsControlConfigurationProperty;
import software.simple.solutions.data.entry.es.control.properties.SurveyProperty;
import software.simple.solutions.data.entry.es.control.repository.ISurveyRepository;
import software.simple.solutions.data.entry.es.control.service.ISurveyApplicationUserService;
import software.simple.solutions.data.entry.es.control.service.ISurveyService;
import software.simple.solutions.data.entry.es.control.valueobjects.SurveyVO;
import software.simple.solutions.framework.core.annotations.ServiceRepository;
import software.simple.solutions.framework.core.constants.Constants;
import software.simple.solutions.framework.core.entities.ApplicationUser;
import software.simple.solutions.framework.core.entities.Configuration;
import software.simple.solutions.framework.core.entities.EntityFile;
import software.simple.solutions.framework.core.exceptions.Arg;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.SystemMessageProperty;
import software.simple.solutions.framework.core.service.IConfigurationService;
import software.simple.solutions.framework.core.service.IFileService;
import software.simple.solutions.framework.core.service.impl.SuperService;
import software.simple.solutions.framework.core.util.ThreadAttributes;
import software.simple.solutions.framework.core.util.ThreadContext;
import software.simple.solutions.framework.core.valueobjects.EntityFileVO;
import software.simple.solutions.framework.core.valueobjects.SuperVO;

@Transactional
@Service
@ServiceRepository(claz = ISurveyRepository.class)
public class SurveyService extends SuperService implements ISurveyService {

	@Autowired
	private ISurveyRepository surveyRepository;

	@Autowired
	private IFileService fileService;

	@Autowired
	private IConfigurationService configurationService;

	@Override
	public <T, R extends SuperVO> T updateSingle(R valueObject) throws FrameworkException {
		SurveyVO vo = (SurveyVO) valueObject;

		if (StringUtils.isBlank(vo.getName())) {
			throw new FrameworkException(SystemMessageProperty.FIELD_IS_REQUIRED, new Arg().key(SurveyProperty.NAME));
		}

		Survey survey = new Survey();
		if (!vo.isNew()) {
			survey = get(Survey.class, vo.getId());
		}
		survey.setName(vo.getName());
		survey.setDescription(vo.getDescription());
		survey.setActive(vo.getActive());
		survey = saveOrUpdate(survey, vo.isNew());

		ThreadAttributes threadAttributes = ThreadContext.get();
		Long userId = threadAttributes.getUserId();

		if (vo.isNew()) {
			SurveyApplicationUser surveyApplicationUser = new SurveyApplicationUser();
			surveyApplicationUser.setActive(true);
			surveyApplicationUser.setApplicationUser(get(ApplicationUser.class, userId));
			surveyApplicationUser.setSurvey(survey);
			saveOrUpdate(surveyApplicationUser, true);
		}
		
		if(vo.isNew()){
			SurveySection surveySection = new SurveySection();
			surveySection.setActive(true);
			surveySection.setDescription("Page 1");
			surveySection.setEnableApplicability(false);
			surveySection.setCode("PAGE_1");
			surveySection.setName("Page 1");
			surveySection.setPinned(true);
			surveySection.setSurvey(survey);
			saveOrUpdate(surveySection, true);
		}

		return (T) survey;
	}

	@Override
	public EntityFile upLoadFile(EntityFileVO vo) throws FrameworkException {
		String location = placeFileInSurveyFileFolder(vo.getEntityId(), vo.getFilename(), vo.getFileObject());
		vo.setFilePath(location);
		return fileService.upLoadFile(vo);
	}

	private String placeFileInSurveyFileFolder(String surveyId, String fileName, byte[] fileObject)
			throws FrameworkException {
		Configuration configuration = configurationService
				.getByCode(EsControlConfigurationProperty.SURVEY_FILE_STORAGE_LOCATION);
		if (configuration == null) {
			return null;
		}
		String storageLocation = configuration.getValue();
		storageLocation += Constants.FILE_SEPARATOR + surveyId + Constants.FILE_SEPARATOR + fileName;
		try {
			FileUtils.writeByteArrayToFile(new File(storageLocation), fileObject);
		} catch (IOException e) {
			throw new FrameworkException(SystemMessageProperty.UNABLE_TO_STORE_FILE_ON_SYSTEM, e);
		}
		return storageLocation;
	}

	@Override
	public List<Survey> findAllSurveys() throws FrameworkException {
		return surveyRepository.findAllSurveys();
	}

	@Override
	public List<Survey> findAllSurveysByUser(String username) throws FrameworkException {
		return surveyRepository.findAllSurveysByUser(username);
	}

}
