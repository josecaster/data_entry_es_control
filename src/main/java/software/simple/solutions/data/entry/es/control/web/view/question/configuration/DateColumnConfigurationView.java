package software.simple.solutions.data.entry.es.control.web.view.question.configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import software.simple.solutions.data.entry.es.control.entities.SurveyQuestionAnswerChoice;
import software.simple.solutions.data.entry.es.control.properties.SurveyQuestionAnswerChoiceProperty;
import software.simple.solutions.data.entry.es.control.service.ISurveyQuestionAnswerChoiceService;
import software.simple.solutions.framework.core.components.CDiscreetNumberField;
import software.simple.solutions.framework.core.components.MessageWindowHandler;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.util.ContextProvider;

public class DateColumnConfigurationView extends VerticalLayout {

	private final class MinLengthValueChangeListener implements ValueChangeListener<String> {
		@Override
		public void valueChange(ValueChangeEvent<String> event) {
			try {
				Long id = surveyQuestionAnswerChoice.getId();
				surveyQuestionAnswerChoiceService.updateMinLength(id, minLengthFld.getLongValue());
			} catch (FrameworkException e) {
				logger.error(e.getMessage(), e);
				new MessageWindowHandler(e);
			}
		}
	}

	private final class MaxLengthValueChangeListener implements ValueChangeListener<String> {
		@Override
		public void valueChange(ValueChangeEvent<String> event) {
			try {
				Long id = surveyQuestionAnswerChoice.getId();
				surveyQuestionAnswerChoiceService.updateMaxLength(id, maxLengthFld.getLongValue());
			} catch (FrameworkException e) {
				logger.error(e.getMessage(), e);
				new MessageWindowHandler(e);
			}
		}
	}

	private static final Logger logger = LogManager.getLogger(DateColumnConfigurationView.class);

	private SurveyQuestionAnswerChoice surveyQuestionAnswerChoice;
	private CDiscreetNumberField minLengthFld;
	private CDiscreetNumberField maxLengthFld;

	private ISurveyQuestionAnswerChoiceService surveyQuestionAnswerChoiceService;

	{
		surveyQuestionAnswerChoiceService = ContextProvider.getBean(ISurveyQuestionAnswerChoiceService.class);
	}

	public DateColumnConfigurationView(SurveyQuestionAnswerChoice surveyQuestionAnswerChoice) {
		this.surveyQuestionAnswerChoice = surveyQuestionAnswerChoice;
		try {
			buildMainLayout();
		} catch (FrameworkException e) {
			logger.error(e.getMessage(), e);
			new MessageWindowHandler(e);
		}
	}

	public void buildMainLayout() throws FrameworkException {

		surveyQuestionAnswerChoice = surveyQuestionAnswerChoiceService.get(SurveyQuestionAnswerChoice.class,
				surveyQuestionAnswerChoice.getId());

		setMargin(new MarginInfo(false, false, false, true));
		setWidth("100%");
		HorizontalLayout horizontalLayout = new HorizontalLayout();
		horizontalLayout.setMargin(false);
		addComponent(horizontalLayout);

		minLengthFld = new CDiscreetNumberField();
		minLengthFld.setWidth("100px");
		minLengthFld.setCaptionByKey(SurveyQuestionAnswerChoiceProperty.VALIDATE_MIN_LENGTH);
		horizontalLayout.addComponent(minLengthFld);
		minLengthFld.setLongValue(surveyQuestionAnswerChoice.getMinLength());
		minLengthFld.setValueChangeMode(ValueChangeMode.BLUR);
		minLengthFld.addValueChangeListener(new MinLengthValueChangeListener());

		maxLengthFld = new CDiscreetNumberField();
		maxLengthFld.setWidth("100px");
		maxLengthFld.setCaptionByKey(SurveyQuestionAnswerChoiceProperty.VALIDATE_MAX_LENGTH);
		horizontalLayout.addComponent(maxLengthFld);
		maxLengthFld.setLongValue(surveyQuestionAnswerChoice.getMaxLength());
		maxLengthFld.setValueChangeMode(ValueChangeMode.BLUR);
		maxLengthFld.addValueChangeListener(new MaxLengthValueChangeListener());
	}

}
