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
import com.vaadin.ui.VerticalLayout;

import software.simple.solutions.data.entry.es.control.entities.SurveyQuestionAnswerChoice;
import software.simple.solutions.data.entry.es.control.properties.SurveyQuestionAnswerChoiceProperty;
import software.simple.solutions.data.entry.es.control.properties.SurveyQuestionProperty;
import software.simple.solutions.data.entry.es.control.service.facade.SurveyGroupServiceFacade;
import software.simple.solutions.data.entry.es.control.service.facade.SurveyQuestionAnswerChoiceServiceFacade;
import software.simple.solutions.data.entry.es.control.service.facade.SurveyQuestionServiceFacade;
import software.simple.solutions.framework.core.components.CCheckBox;
import software.simple.solutions.framework.core.components.CComboBox;
import software.simple.solutions.framework.core.components.MessageWindowHandler;
import software.simple.solutions.framework.core.components.SessionHolder;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.pojo.ComboItem;

public class ChoiceConfigurationView extends HorizontalLayout {

	private static final Logger logger = LogManager.getLogger(ChoiceConfigurationView.class);

	private CCheckBox isOtherFld;
	private CComboBox makeQuestionRequiredFld;
	private CComboBox makeGroupQuestionRequiredFld;

	private SurveyQuestionAnswerChoice surveyQuestionAnswerChoice;
	private SessionHolder sessionHolder;

	public ChoiceConfigurationView(SurveyQuestionAnswerChoice surveyQuestionAnswerChoice) {
		this.surveyQuestionAnswerChoice = surveyQuestionAnswerChoice;
		sessionHolder = (SessionHolder) UI.getCurrent().getData();
		try {
			buildMainLayout();
		} catch (FrameworkException e) {
			logger.error(e.getMessage(), e);
			new MessageWindowHandler(e);
		}
	}

	public void buildMainLayout() throws FrameworkException {
		surveyQuestionAnswerChoice = SurveyQuestionAnswerChoiceServiceFacade.get(UI.getCurrent())
				.get(SurveyQuestionAnswerChoice.class, surveyQuestionAnswerChoice.getId());
		setMargin(new MarginInfo(false, false, false, true));
		setWidth("-1px");

		isOtherFld = new CCheckBox();
		isOtherFld.setValue(surveyQuestionAnswerChoice != null && surveyQuestionAnswerChoice.getIsOther() != null
				&& surveyQuestionAnswerChoice.getIsOther());
		isOtherFld.setCaptionByKey(SurveyQuestionAnswerChoiceProperty.IS_OTHER);
		addComponent(isOtherFld);
		setComponentAlignment(isOtherFld, Alignment.TOP_LEFT);
		isOtherFld.addValueChangeListener(new IsOtherChangeListener());

		VerticalLayout verticalLayout = new VerticalLayout();
		verticalLayout.setMargin(false);
		verticalLayout.setSpacing(true);
		addComponent(verticalLayout);

		List<ComboItem> questions = SurveyQuestionServiceFacade.get(UI.getCurrent())
				.getNextQuestions(surveyQuestionAnswerChoice.getSurveyQuestion().getOrder());
		makeQuestionRequiredFld = new CComboBox();
		makeQuestionRequiredFld.setItems(questions);
		makeQuestionRequiredFld.setWidth("300px");
		makeQuestionRequiredFld.setValue(surveyQuestionAnswerChoice.getMakeSelectedQuestionRequired());
		makeQuestionRequiredFld.setCaptionByKey(SurveyQuestionAnswerChoiceProperty.MAKE_SELECTED_QUESTION_REQUIRED);
		verticalLayout.addComponent(makeQuestionRequiredFld);
		makeQuestionRequiredFld.addValueChangeListener(new ValueChangeListener<ComboItem>() {

			@Override
			public void valueChange(ValueChangeEvent<ComboItem> event) {
				try {
					SurveyQuestionAnswerChoiceServiceFacade.get(UI.getCurrent()).updateMakeSelectedQuestionRequired(
							surveyQuestionAnswerChoice.getId(), makeQuestionRequiredFld.getLongValue());
				} catch (FrameworkException e) {
					logger.error(e.getMessage(), e);
					new MessageWindowHandler(e);
				}
			}
		});

		List<ComboItem> surveyGroups = SurveyGroupServiceFacade.get(UI.getCurrent())
				.findBySurvey(surveyQuestionAnswerChoice.getSurveyQuestion().getSurvey().getId());
		makeGroupQuestionRequiredFld = new CComboBox();
		makeGroupQuestionRequiredFld.setItems(surveyGroups);
		makeGroupQuestionRequiredFld.setWidth("300px");
		makeGroupQuestionRequiredFld.setValue(surveyQuestionAnswerChoice.getMakeSelectedGroupRequired());
		makeGroupQuestionRequiredFld.setCaptionByKey(SurveyQuestionProperty.MAKE_SELECTED_GROUP_REQUIRED);
		verticalLayout.addComponent(makeGroupQuestionRequiredFld);

		makeGroupQuestionRequiredFld.addValueChangeListener(new ValueChangeListener<ComboItem>() {

			@Override
			public void valueChange(ValueChangeEvent<ComboItem> event) {
				try {
					SurveyQuestionAnswerChoiceServiceFacade.get(UI.getCurrent()).updateMakeSelectedGroupRequired(
							surveyQuestionAnswerChoice.getId(), makeGroupQuestionRequiredFld.getLongValue());
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
				SurveyQuestionAnswerChoiceServiceFacade.get(UI.getCurrent())
						.updateIsOther(surveyQuestionAnswerChoice.getId(), event.getValue());
			} catch (FrameworkException e) {
				logger.error(e.getMessage(), e);
				new MessageWindowHandler(e);
			}
		}
	}
}
