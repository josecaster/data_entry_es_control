package software.simple.solutions.data.entry.es.control.web.view.question.type;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import software.simple.solutions.data.entry.es.control.constants.Axis;
import software.simple.solutions.data.entry.es.control.constants.QuestionType;
import software.simple.solutions.data.entry.es.control.entities.SurveyQuestion;
import software.simple.solutions.data.entry.es.control.entities.SurveyQuestionAnswerChoice;
import software.simple.solutions.data.entry.es.control.properties.SurveyQuestionAnswerChoiceProperty;
import software.simple.solutions.data.entry.es.control.service.ISurveyQuestionAnswerChoiceService;
import software.simple.solutions.data.entry.es.control.service.ISurveyQuestionService;
import software.simple.solutions.data.entry.es.control.valueobjects.SurveyQuestionAnswerChoiceVO;
import software.simple.solutions.data.entry.es.control.valueobjects.SurveyQuestionVO;
import software.simple.solutions.data.entry.es.control.web.view.question.configuration.ChoiceConfigurationView;
import software.simple.solutions.framework.core.components.CButton;
import software.simple.solutions.framework.core.components.CCheckBox;
import software.simple.solutions.framework.core.components.CTextField;
import software.simple.solutions.framework.core.components.MessageWindowHandler;
import software.simple.solutions.framework.core.components.SessionHolder;
import software.simple.solutions.framework.core.constants.Style;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.icons.CxodeIcons;
import software.simple.solutions.framework.core.util.ContextProvider;

public class QuestionTypeChoicesResponseLayout extends VerticalLayout {

	private static final Logger logger = LogManager.getLogger(QuestionTypeChoicesResponseLayout.class);

	private CCheckBox allowMultipleAnswersFld;
	private VerticalLayout rowContainerLayout;

	private SurveyQuestion surveyQuestion;
	private SessionHolder sessionHolder;

	private ISurveyQuestionAnswerChoiceService surveyQuestionAnswerChoiceService;
	private ISurveyQuestionService surveyQuestionService;

	{
		surveyQuestionAnswerChoiceService = ContextProvider.getBean(ISurveyQuestionAnswerChoiceService.class);
		surveyQuestionService = ContextProvider.getBean(ISurveyQuestionService.class);
		sessionHolder = (SessionHolder) UI.getCurrent().getData();
	}

	public QuestionTypeChoicesResponseLayout(SurveyQuestion surveyQuestion) {
		this.surveyQuestion = surveyQuestion;
		setMargin(false);
		try {
			buildMainLayout();
		} catch (FrameworkException e) {
			logger.error(e.getMessage(), e);
			new MessageWindowHandler(e);
		}
	}

	public void buildMainLayout() throws FrameworkException {
		allowMultipleAnswersFld = new CCheckBox();
		allowMultipleAnswersFld.setCaptionByKey(SurveyQuestionAnswerChoiceProperty.CHOICE_TYPE_MULTIPLE_ANSWER);
		addComponent(allowMultipleAnswersFld);

		rowContainerLayout = new VerticalLayout();
		rowContainerLayout.setMargin(false);
		rowContainerLayout.setWidth("100%");
		addComponent(rowContainerLayout);

		try {
			allowMultipleAnswersFld.setValue(surveyQuestion.getMultipleSelection());
			createRowsFromSurveyQuestion();
		} catch (FrameworkException e) {
			logger.error(e.getMessage(), e);
			new MessageWindowHandler(e);
		}

		allowMultipleAnswersFld.addValueChangeListener(new AllowMultipleValueChangeListener());
	}

	public void createRowsFromSurveyQuestion() throws FrameworkException {
		List<SurveyQuestionAnswerChoice> surveyQuestionAnswerChoices = surveyQuestionAnswerChoiceService
				.findBySurveyQuestion(surveyQuestion.getId(), Axis.ROW);
		if (surveyQuestionAnswerChoices == null || surveyQuestionAnswerChoices.isEmpty()) {
			createRowIfNonExists();
		} else {
			for (SurveyQuestionAnswerChoice surveyQuestionAnswerChoice : surveyQuestionAnswerChoices) {
				updateQuestionTypeIfNeeded(surveyQuestionAnswerChoice);
				createRow(null, surveyQuestionAnswerChoice);
			}
		}
	}

	private void updateQuestionTypeIfNeeded(SurveyQuestionAnswerChoice surveyQuestionAnswerChoice)
			throws FrameworkException {
		if (!surveyQuestionAnswerChoice.getQuestionType().equalsIgnoreCase(QuestionType.CHOICES)) {
			surveyQuestionAnswerChoiceService.updateQuestionType(surveyQuestionAnswerChoice.getId(),
					QuestionType.CHOICES);
		}
	}

	private void createRow(Integer favoredComponentIndex, SurveyQuestionAnswerChoice surveyQuestionAnswerChoice)
			throws FrameworkException {
		VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.setWidth("100%");
		mainLayout.setMargin(false);

		HorizontalLayout rowLayout = new HorizontalLayout();
		rowLayout.setWidth("100%");
		mainLayout.addComponent(rowLayout);

		if (favoredComponentIndex == null) {
			rowContainerLayout.addComponent(mainLayout);
		} else {
			rowContainerLayout.addComponent(mainLayout, favoredComponentIndex);
		}
		int componentIndex = rowContainerLayout.getComponentIndex(mainLayout);

		if (surveyQuestionAnswerChoice == null) {
			surveyQuestionAnswerChoice = surveyQuestionAnswerChoiceService.createNewRow(surveyQuestion.getId(),
					QuestionType.CHOICES, componentIndex);
		}

		CTextField descriptionFld = new CTextField();
		descriptionFld.setWidth("100%");
		if (surveyQuestionAnswerChoice != null) {
			descriptionFld.setValue(surveyQuestionAnswerChoice.getLabel());
		}
		rowLayout.addComponent(descriptionFld);
		rowLayout.setExpandRatio(descriptionFld, 1);
		descriptionFld.setValueChangeMode(ValueChangeMode.BLUR);
		descriptionFld.addValueChangeListener(new DescriptionValueChangeListener(surveyQuestionAnswerChoice));

		CButton configureBtn = new CButton();
		configureBtn.setData(Boolean.FALSE);
		configureBtn.setIcon(CxodeIcons.GEARS);
		configureBtn.addStyleName(Style.RESIZED_ICON);
		configureBtn.addClickListener(new ConfigureRowListener(mainLayout, surveyQuestionAnswerChoice));
		rowLayout.addComponent(configureBtn);

		CButton addBtn = new CButton();
		addBtn.setIcon(CxodeIcons.ADD);
		addBtn.addStyleName(Style.RESIZED_ICON_80);
		addBtn.addClickListener(new AddNewRowListener(mainLayout));
		rowLayout.addComponent(addBtn);

		CButton removeBtn = new CButton();
		removeBtn.setIcon(CxodeIcons.DELETE);
		removeBtn.addStyleName(Style.RESIZED_ICON_80);
		removeBtn.addClickListener(new RemoveRowListener(mainLayout, surveyQuestionAnswerChoice));
		rowLayout.addComponent(removeBtn);
	}

	public void createRowIfNonExists() throws FrameworkException {
		int componentCount = rowContainerLayout.getComponentCount();
		if (componentCount == 0) {
			createRow(0, null);
		}
	}

	protected void showChoiceConfiguration(VerticalLayout mainLayout,
			SurveyQuestionAnswerChoice surveyQuestionAnswerChoice) {
		mainLayout.setMargin(true);
		mainLayout.addStyleName(ValoTheme.LAYOUT_CARD);
		if (mainLayout.getComponentCount() > 1) {
			mainLayout.removeComponent(mainLayout.getComponent(1));
		}

		ChoiceConfigurationView choiceConfigurationView = new ChoiceConfigurationView(surveyQuestionAnswerChoice);
		mainLayout.addComponent(choiceConfigurationView);
	}

	private final class ConfigureRowListener implements ClickListener {

		private VerticalLayout mainLayout;
		private SurveyQuestionAnswerChoice surveyQuestionAnswerChoice;

		public ConfigureRowListener(VerticalLayout mainLayout, SurveyQuestionAnswerChoice surveyQuestionAnswerChoice) {
			this.mainLayout = mainLayout;
			this.surveyQuestionAnswerChoice = surveyQuestionAnswerChoice;
		}

		@Override
		public void buttonClick(ClickEvent event) {
			Boolean showConfiguration = (Boolean) event.getButton().getData();
			if (!showConfiguration) {
				showChoiceConfiguration(mainLayout, surveyQuestionAnswerChoice);
			} else {
				if (mainLayout.getComponentCount() > 1) {
					mainLayout.removeComponent(mainLayout.getComponent(1));
				}
				mainLayout.setMargin(false);
				mainLayout.removeStyleName(ValoTheme.LAYOUT_CARD);
			}
			showConfiguration = !showConfiguration;
			event.getButton().setData(showConfiguration);
		}

	}

	private final class AllowMultipleValueChangeListener implements ValueChangeListener<Boolean> {
		@Override
		public void valueChange(ValueChangeEvent<Boolean> event) {
			try {
				SurveyQuestionVO surveyQuestionVO = new SurveyQuestionVO();
				surveyQuestionVO.setId(surveyQuestion.getId());
				surveyQuestionVO.setMultipleSelection(event.getValue());
				surveyQuestionService.updateMultipleSelection(surveyQuestionVO);
			} catch (FrameworkException e) {
				logger.error(e.getMessage(), e);
				new MessageWindowHandler(e);
			}
		}
	}

	private final class DescriptionValueChangeListener implements ValueChangeListener<String> {

		private SurveyQuestionAnswerChoice surveyQuestionAnswerChoice;

		public DescriptionValueChangeListener(SurveyQuestionAnswerChoice surveyQuestionAnswerChoice) {
			this.surveyQuestionAnswerChoice = surveyQuestionAnswerChoice;
		}

		@Override
		public void valueChange(ValueChangeEvent<String> event) {
			try {
				SurveyQuestionAnswerChoiceVO surveyQuestionAnswerChoiceVO = new SurveyQuestionAnswerChoiceVO();
				surveyQuestionAnswerChoiceVO.setId(surveyQuestionAnswerChoice.getId());
				surveyQuestionAnswerChoiceVO.setLabel(event.getValue());
				surveyQuestionAnswerChoiceService.updateLabel(surveyQuestionAnswerChoiceVO);
			} catch (FrameworkException e) {
				logger.error(e.getMessage(), e);
				new MessageWindowHandler(e);
			}
		}
	}

	private final class RemoveRowListener implements ClickListener {

		private Layout rowLayout;
		private SurveyQuestionAnswerChoice surveyQuestionAnswerChoice;

		public RemoveRowListener(Layout rowLayout, SurveyQuestionAnswerChoice surveyQuestionAnswerChoice) {
			this.rowLayout = rowLayout;
			this.surveyQuestionAnswerChoice = surveyQuestionAnswerChoice;
		}

		@Override
		public void buttonClick(ClickEvent event) {
			rowContainerLayout.removeComponent(rowLayout);

			try {
				surveyQuestionAnswerChoiceService.delete(SurveyQuestionAnswerChoice.class,
						surveyQuestionAnswerChoice.getId(), sessionHolder.getApplicationUser().getId());

				createRowIfNonExists();
			} catch (FrameworkException e) {
				logger.error(e.getMessage(), e);
				new MessageWindowHandler(e);
			}
		}
	}

	private class AddNewRowListener implements ClickListener {

		private Layout rowLayout;

		public AddNewRowListener(Layout rowLayout) {
			this.rowLayout = rowLayout;
		}

		@Override
		public void buttonClick(ClickEvent event) {
			try {
				Integer componentIndex = rowContainerLayout.getComponentIndex(rowLayout);
				createRow(componentIndex + 1, null);
			} catch (FrameworkException e) {
				logger.error(e.getMessage(), e);
				new MessageWindowHandler(e);
			}
		}

	}
}
