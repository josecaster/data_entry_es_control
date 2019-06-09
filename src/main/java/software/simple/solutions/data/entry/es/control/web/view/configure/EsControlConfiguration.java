package software.simple.solutions.data.entry.es.control.web.view.configure;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

import software.simple.solutions.data.entry.es.control.constants.EsControlConfigurationCodes;
import software.simple.solutions.data.entry.es.control.properties.EsControlConfigurationProperty;
import software.simple.solutions.data.entry.es.control.service.ISurveyService;
import software.simple.solutions.framework.core.annotations.CxodeConfigurationComponent;
import software.simple.solutions.framework.core.components.CButton;
import software.simple.solutions.framework.core.components.CGridLayout;
import software.simple.solutions.framework.core.components.CTextField;
import software.simple.solutions.framework.core.components.MessageWindowHandler;
import software.simple.solutions.framework.core.components.NotificationWindow;
import software.simple.solutions.framework.core.components.SessionHolder;
import software.simple.solutions.framework.core.entities.Configuration;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.SystemProperty;
import software.simple.solutions.framework.core.service.IConfigurationService;
import software.simple.solutions.framework.core.util.ContextProvider;
import software.simple.solutions.framework.core.valueobjects.ConfigurationVO;

@CxodeConfigurationComponent(order = 100, captionKey = EsControlConfigurationProperty.ES_CONTROL_SURVEY_CONFIGURATION)
public class EsControlConfiguration extends CGridLayout {

	private static final long serialVersionUID = -4290908153354032925L;

	private static final Logger logger = LogManager.getLogger(EsControlConfiguration.class);

	private CTextField surveyFileStorageLocationFld;

	private CButton persistBtn;
	private SessionHolder sessionHolder;
	private UI ui;

	public EsControlConfiguration() throws FrameworkException {
		ui = UI.getCurrent();
		sessionHolder = (SessionHolder) UI.getCurrent().getData();
		setSpacing(true);
		int i = 0;
		surveyFileStorageLocationFld = addField(CTextField.class,
				EsControlConfigurationCodes.SURVEY_FILE_STORAGE_LOCATION, 0, ++i);
		surveyFileStorageLocationFld.setWidth("600px");

		persistBtn = addField(CButton.class, 0, ++i);
		persistBtn.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		persistBtn.setCaptionByKey(SystemProperty.SYSTEM_BUTTON_SUBMIT);

		persistBtn.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 3418895031034445319L;

			@Override
			public void buttonClick(ClickEvent event) {
				List<ConfigurationVO> configurations = new ArrayList<ConfigurationVO>();
				configurations.add(getValue(EsControlConfigurationCodes.SURVEY_FILE_STORAGE_LOCATION,
						surveyFileStorageLocationFld.getValue()));
				IConfigurationService configurationService = ContextProvider.getBean(IConfigurationService.class);
				try {
					configurationService.update(configurations);
					NotificationWindow.notificationNormalWindow(SystemProperty.UPDATE_SUCCESSFULL);
				} catch (FrameworkException e) {
					logger.error(e.getMessage(), e);
					new MessageWindowHandler(e);
				}
			}
		});

		setValues();

	}

	private void setValues() throws FrameworkException {
		ISurveyService surveyService = ContextProvider.getBean(ISurveyService.class);
		List<Configuration> configurations = surveyService.getEsControlConfigurations();
		configurations.forEach(configuration -> {
			switch (configuration.getCode()) {
			case EsControlConfigurationCodes.SURVEY_FILE_STORAGE_LOCATION:
				surveyFileStorageLocationFld.setValue(configuration.getValue());
				break;
			default:
				break;
			}
		});
	}

	private ConfigurationVO getValue(String code, Object value) {
		ConfigurationVO vo = new ConfigurationVO();
		vo.setCode(code);
		vo.setValue(value == null ? null : value.toString());

		vo.setUpdatedBy(sessionHolder.getApplicationUser().getId());
		vo.setUpdatedDate(LocalDateTime.now());
		return vo;
	}

}
