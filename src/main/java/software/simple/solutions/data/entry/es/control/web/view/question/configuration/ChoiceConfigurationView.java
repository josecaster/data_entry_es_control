package software.simple.solutions.data.entry.es.control.web.view.question.configuration;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;

import software.simple.solutions.data.entry.es.control.entities.SurveyQuestionAnswerChoice;
import software.simple.solutions.data.entry.es.control.properties.SurveyQuestionAnswerChoiceProperty;
import software.simple.solutions.data.entry.es.control.service.ISurveyQuestionAnswerChoiceService;
import software.simple.solutions.data.entry.es.control.service.ISurveyQuestionService;
import software.simple.solutions.framework.core.components.CCheckBox;
import software.simple.solutions.framework.core.components.CComboBox;
import software.simple.solutions.framework.core.components.MessageWindowHandler;
import software.simple.solutions.framework.core.components.SessionHolder;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.pojo.ComboItem;
import software.simple.solutions.framework.core.util.ContextProvider;

public class ChoiceConfigurationView extends HorizontalLayout {

	private static final Logger logger = LogManager.getLogger(ChoiceConfigurationView.class);

	private CCheckBox isOtherFld;
	private CComboBox makeQuestionRequiredFld;

	private SurveyQuestionAnswerChoice surveyQuestionAnswerChoice;
	private SessionHolder sessionHolder;

	private ISurveyQuestionAnswerChoiceService surveyQuestionAnswerChoiceService;

	{
		surveyQuestionAnswerChoiceService = ContextProvider.getBean(ISurveyQuestionAnswerChoiceService.class);
		sessionHolder = (SessionHolder) UI.getCurrent().getData();
	}

	public ChoiceConfigurationView(SurveyQuestionAnswerChoice surveyQuestionAnswerChoice) {
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
		setWidth("-1px");

		isOtherFld = new CCheckBox();
		isOtherFld.setValue(surveyQuestionAnswerChoice != null && surveyQuestionAnswerChoice.getIsOther() != null
				&& surveyQuestionAnswerChoice.getIsOther());
		isOtherFld.setCaptionByKey(SurveyQuestionAnswerChoiceProperty.IS_OTHER);
		addComponent(isOtherFld);
		setComponentAlignment(isOtherFld, Alignment.BOTTOM_LEFT);
		isOtherFld.addValueChangeListener(new IsOtherChangeListener());

		ISurveyQuestionService surveyQuestionService = ContextProvider.getBean(ISurveyQuestionService.class);
		List<ComboItem> questions = surveyQuestionService
				.getNextQuestions(surveyQuestionAnswerChoice.getSurveyQuestion().getOrder());
		makeQuestionRequiredFld = new CComboBox();
		makeQuestionRequiredFld.setItems(questions);
		makeQuestionRequiredFld.setWidth("300px");
		makeQuestionRequiredFld.setValue(surveyQuestionAnswerChoice.getMakeSelectedQuestionRequired());
		makeQuestionRequiredFld.setCaptionByKey(SurveyQuestionAnswerChoiceProperty.MAKE_SELECTED_QUESTION_REQUIRED);
		addComponent(makeQuestionRequiredFld);
		makeQuestionRequiredFld.addValueChangeListener(new ValueChangeListener<ComboItem>() {

			@Override
			public void valueChange(ValueChangeEvent<ComboItem> event) {
				try {
					surveyQuestionAnswerChoiceService.updateMakeSelectedQuestionRequired(
							surveyQuestionAnswerChoice.getId(), makeQuestionRequiredFld.getLongValue());
				} catch (FrameworkException e) {
					logger.error(e.getMessage(), e);
					new MessageWindowHandler(e);
				}
			}
		});
	}

	private final class IsOtherChangeListener implements ValueChangeListener<Boolean> {
		@Override
		public void valueChange(ValueChangeEvent<Boolean> event) {
			try {
				surveyQuestionAnswerChoiceService.updateIsOther(surveyQuestionAnswerChoice.getId(), event.getValue());
			} catch (FrameworkException e) {
				logger.error(e.getMessage(), e);
				new MessageWindowHandler(e);
			}
		}
	}
}