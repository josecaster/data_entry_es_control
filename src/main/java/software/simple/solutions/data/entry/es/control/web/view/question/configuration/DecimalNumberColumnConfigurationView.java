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
import software.simple.solutions.framework.core.components.CDecimalField;
import software.simple.solutions.framework.core.components.MessageWindowHandler;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.util.ContextProvider;

public class DecimalNumberColumnConfigurationView extends VerticalLayout {

	private final class MinValueChangeListener implements ValueChangeListener<String> {
		@Override
		public void valueChange(ValueChangeEvent<String> event) {
			try {
				Long id = surveyQuestionAnswerChoice.getId();
				surveyQuestionAnswerChoiceService.updateMinValue(id, minValueFld.getBigDecimalValue());
			} catch (FrameworkException e) {
				logger.error(e.getMessage(), e);
				new MessageWindowHandler(e);
			}
		}
	}

	private final class MaxValueChangeListener implements ValueChangeListener<String> {
		@Override
		public void valueChange(ValueChangeEvent<String> event) {
			try {
				Long id = surveyQuestionAnswerChoice.getId();
				surveyQuestionAnswerChoiceService.updateMaxValue(id, maxValueFld.getBigDecimalValue());
			} catch (FrameworkException e) {
				logger.error(e.getMessage(), e);
				new MessageWindowHandler(e);
			}
		}
	}

	private static final Logger logger = LogManager.getLogger(DecimalNumberColumnConfigurationView.class);

	private SurveyQuestionAnswerChoice surveyQuestionAnswerChoice;
	private CDecimalField minValueFld;
	private CDecimalField maxValueFld;

	private ISurveyQuestionAnswerChoiceService surveyQuestionAnswerChoiceService;

	{
		surveyQuestionAnswerChoiceService = ContextProvider.getBean(ISurveyQuestionAnswerChoiceService.class);
	}

	public DecimalNumberColumnConfigurationView(SurveyQuestionAnswerChoice surveyQuestionAnswerChoice) {
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

		minValueFld = new CDecimalField();
		minValueFld.setWidth("100px");
		minValueFld.setCaptionByKey(SurveyQuestionAnswerChoiceProperty.VALIDATE_MIN_VALUE);
		horizontalLayout.addComponent(minValueFld);
		minValueFld.setBigDecimalValue(surveyQuestionAnswerChoice.getMinValue());
		minValueFld.setValueChangeMode(ValueChangeMode.BLUR);
		minValueFld.addValueChangeListener(new MinValueChangeListener());

		maxValueFld = new CDecimalField();
		maxValueFld.setWidth("100px");
		maxValueFld.setCaptionByKey(SurveyQuestionAnswerChoiceProperty.VALIDATE_MAX_VALUE);
		horizontalLayout.addComponent(maxValueFld);
		maxValueFld.setBigDecimalValue(surveyQuestionAnswerChoice.getMaxValue());
		maxValueFld.setValueChangeMode(ValueChangeMode.BLUR);
		maxValueFld.addValueChangeListener(new MaxValueChangeListener());
	}

}