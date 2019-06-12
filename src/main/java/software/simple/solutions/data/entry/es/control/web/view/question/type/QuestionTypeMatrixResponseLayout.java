package software.simple.solutions.data.entry.es.control.web.view.question.type;

import java.util.List;
import java.util.stream.Collectors;

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

import software.simple.solutions.data.entry.es.control.components.MatrixColumnTypeSelect;
import software.simple.solutions.data.entry.es.control.constants.Axis;
import software.simple.solutions.data.entry.es.control.constants.EsControlStyle;
import software.simple.solutions.data.entry.es.control.constants.MatrixColumnType;
import software.simple.solutions.data.entry.es.control.constants.QuestionType;
import software.simple.solutions.data.entry.es.control.entities.SurveyQuestion;
import software.simple.solutions.data.entry.es.control.entities.SurveyQuestionAnswerChoice;
import software.simple.solutions.data.entry.es.control.properties.SurveyQuestionProperty;
import software.simple.solutions.data.entry.es.control.service.facade.SurveyQuestionAnswerChoiceServiceFacade;
import software.simple.solutions.data.entry.es.control.valueobjects.SurveyQuestionAnswerChoiceVO;
import software.simple.solutions.data.entry.es.control.web.view.question.configuration.DateColumnConfigurationView;
import software.simple.solutions.data.entry.es.control.web.view.question.configuration.DecimalNumberColumnConfigurationView;
import software.simple.solutions.data.entry.es.control.web.view.question.configuration.SingleSelectionColumnConfigurationView;
import software.simple.solutions.data.entry.es.control.web.view.question.configuration.TextColumnConfigurationView;
import software.simple.solutions.data.entry.es.control.web.view.question.configuration.WholeNumberColumnConfigurationView;
import software.simple.solutions.framework.core.components.CButton;
import software.simple.solutions.framework.core.components.CTextField;
import software.simple.solutions.framework.core.components.MessageWindowHandler;
import software.simple.solutions.framework.core.components.SessionHolder;
import software.simple.solutions.framework.core.constants.Style;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.icons.CxodeIcons;
import software.simple.solutions.framework.core.pojo.ComboItem;
import software.simple.solutions.framework.core.util.PropertyResolver;

public class QuestionTypeMatrixResponseLayout extends VerticalLayout {

	private static final Logger logger = LogManager.getLogger(QuestionTypeMatrixResponseLayout.class);

	private VerticalLayout columContainerLayout;
	private VerticalLayout rowContainerLayout;

	private List<SurveyQuestionAnswerChoice> surveyQuestionAnswerChoices;
	private SurveyQuestion surveyQuestion;
	private SessionHolder sessionHolder;

	public QuestionTypeMatrixResponseLayout(SessionHolder sessionHolder, SurveyQuestion surveyQuestion) {
		this.sessionHolder = sessionHolder;
		this.surveyQuestion = surveyQuestion;
		setMargin(false);
		buildMainLayout();
	}

	public void buildMainLayout() {
		columContainerLayout = new VerticalLayout();
		columContainerLayout.setMargin(false);
		columContainerLayout.setWidth("100%");
		columContainerLayout.addStyleName(EsControlStyle.CAPTION_MATRIX_AXIS);
		columContainerLayout.setCaption(
				PropertyResolver.getPropertyValueByLocale(SurveyQuestionProperty.QUESTION_TYPE_MATRIX_COLUMNS));
		addComponent(columContainerLayout);

		rowContainerLayout = new VerticalLayout();
		rowContainerLayout.setMargin(false);
		rowContainerLayout.setWidth("100%");
		rowContainerLayout.addStyleName(EsControlStyle.CAPTION_MATRIX_AXIS);
		rowContainerLayout.setCaption(
				PropertyResolver.getPropertyValueByLocale(SurveyQuestionProperty.QUESTION_TYPE_MATRIX_ROWS));
		addComponent(rowContainerLayout);

		try {
			retrieveSurveyQuestionAnswerChoices();
			createColumnsFromSurveyQuestion();
			createRowsFromSurveyQuestion();
		} catch (FrameworkException e) {
			logger.error(e.getMessage(), e);
			new MessageWindowHandler(e);
		}
	}

	public void retrieveSurveyQuestionAnswerChoices() throws FrameworkException {
		surveyQuestionAnswerChoices = SurveyQuestionAnswerChoiceServiceFacade.get(UI.getCurrent())
				.findBySurveyQuestion(surveyQuestion.getId());
	}

	public void createColumnsFromSurveyQuestion() throws FrameworkException {
		if (surveyQuestionAnswerChoices != null && !surveyQuestionAnswerChoices.isEmpty()) {
			List<SurveyQuestionAnswerChoice> columns = surveyQuestionAnswerChoices.stream()
					.filter(p -> p.getAxis().equalsIgnoreCase(Axis.COLUMN)).collect(Collectors.toList());

			if (columns == null || columns.isEmpty()) {
				createColumnIfNonExists();
			} else {
				for (SurveyQuestionAnswerChoice surveyQuestionAnswerChoice : columns) {
					createColumn(null, surveyQuestionAnswerChoice);
				}
			}
		} else {
			createColumnIfNonExists();
		}
	}

	public void createRowsFromSurveyQuestion() throws FrameworkException {
		if (surveyQuestionAnswerChoices != null && !surveyQuestionAnswerChoices.isEmpty()) {
			List<SurveyQuestionAnswerChoice> rows = surveyQuestionAnswerChoices.stream()
					.filter(p -> p.getAxis().equalsIgnoreCase(Axis.ROW)).collect(Collectors.toList());

			if (rows == null || rows.isEmpty()) {
				createRowIfNonExists();
			} else {
				for (SurveyQuestionAnswerChoice surveyQuestionAnswerChoice : rows) {
					updateQuestionTypeIfNeeded(surveyQuestionAnswerChoice);
					createRow(null, surveyQuestionAnswerChoice);
				}
			}
		} else {
			createRowIfNonExists();
		}
	}

	private void updateQuestionTypeIfNeeded(SurveyQuestionAnswerChoice surveyQuestionAnswerChoice)
			throws FrameworkException {
		if (!surveyQuestionAnswerChoice.getQuestionType().equalsIgnoreCase(QuestionType.MATRIX)) {
			SurveyQuestionAnswerChoiceServiceFacade.get(UI.getCurrent())
					.updateQuestionType(surveyQuestionAnswerChoice.getId(), QuestionType.MATRIX);
		}
	}

	private void createColumn(Integer favoredComponentIndex, SurveyQuestionAnswerChoice surveyQuestionAnswerChoice)
			throws FrameworkException {
		VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.setWidth("100%");
		mainLayout.setMargin(false);

		HorizontalLayout rowLayout = new HorizontalLayout();
		rowLayout.setWidth("100%");
		mainLayout.addComponent(rowLayout);

		if (favoredComponentIndex == null) {
			columContainerLayout.addComponent(mainLayout);
		} else {
			columContainerLayout.addComponent(mainLayout, favoredComponentIndex);
		}
		int componentIndex = columContainerLayout.getComponentIndex(mainLayout);

		if (surveyQuestionAnswerChoice == null) {
			surveyQuestionAnswerChoice = SurveyQuestionAnswerChoiceServiceFacade.get(UI.getCurrent())
					.createNewColumn(surveyQuestion.getId(), QuestionType.MATRIX, componentIndex);
		}

		CTextField descriptionFld = new CTextField();
		descriptionFld.setWidth("100%");
		if (surveyQuestionAnswerChoice != null) {
			descriptionFld.setValue(surveyQuestionAnswerChoice.getLabel());
		}
		rowLayout.addComponent(descriptionFld);
		rowLayout.setExpandRatio(descriptionFld, 1);
		descriptionFld.setValueChangeMode(ValueChangeMode.BLUR);
		descriptionFld.addValueChangeListener(new LabelValueChangeListener(surveyQuestionAnswerChoice));

		MatrixColumnTypeSelect matrixColumnTypeSelectFld = new MatrixColumnTypeSelect();
		matrixColumnTypeSelectFld.setValue(MatrixColumnType.TEXT);
		matrixColumnTypeSelectFld.setEmptySelectionAllowed(false);
		matrixColumnTypeSelectFld.setWidth("150px");
		matrixColumnTypeSelectFld.setValue(surveyQuestionAnswerChoice.getMatrixColumnType());
		rowLayout.addComponent(matrixColumnTypeSelectFld);

		CButton configureBtn = new CButton();
		configureBtn.setData(Boolean.FALSE);
		configureBtn.setIcon(CxodeIcons.GEARS);
		configureBtn.addStyleName(Style.RESIZED_ICON);
		configureBtn.addClickListener(
				new ConfigureColumnListener(mainLayout, surveyQuestionAnswerChoice, matrixColumnTypeSelectFld));
		rowLayout.addComponent(configureBtn);

		matrixColumnTypeSelectFld.addValueChangeListener(new MatrixColumnTypeValueChangeListener(mainLayout,
				surveyQuestionAnswerChoice, matrixColumnTypeSelectFld, configureBtn));

		CButton addBtn = new CButton();
		addBtn.setIcon(CxodeIcons.ADD);
		addBtn.addStyleName(Style.RESIZED_ICON_80);
		addBtn.addClickListener(new AddNewColumnListener(mainLayout));
		rowLayout.addComponent(addBtn);

		CButton removeBtn = new CButton();
		removeBtn.setIcon(CxodeIcons.DELETE);
		removeBtn.addStyleName(Style.RESIZED_ICON_80);
		removeBtn.addClickListener(new RemoveColumnListener(mainLayout, surveyQuestionAnswerChoice));
		rowLayout.addComponent(removeBtn);
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
			surveyQuestionAnswerChoice = SurveyQuestionAnswerChoiceServiceFacade.get(UI.getCurrent())
					.createNewRow(surveyQuestion.getId(), QuestionType.MATRIX, componentIndex);
		}

		CTextField rowLabelFld = new CTextField();
		rowLabelFld.setWidth("100%");
		if (surveyQuestionAnswerChoice != null) {
			rowLabelFld.setValue(surveyQuestionAnswerChoice.getLabel());
		}
		rowLayout.addComponent(rowLabelFld);
		rowLayout.setExpandRatio(rowLabelFld, 1);
		rowLabelFld.setValueChangeMode(ValueChangeMode.BLUR);
		rowLabelFld.addValueChangeListener(new LabelValueChangeListener(surveyQuestionAnswerChoice));

		CButton configureBtn = new CButton();
		configureBtn.setVisible(false);
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

	public void createColumnIfNonExists() throws FrameworkException {
		int componentCount = columContainerLayout.getComponentCount();
		if (componentCount == 0) {
			createColumn(0, null);
		}
	}

	public void createRowIfNonExists() throws FrameworkException {
		int componentCount = rowContainerLayout.getComponentCount();
		if (componentCount == 0) {
			createRow(0, null);
		}
	}

	protected void showMatrixColumnConfiguration(VerticalLayout mainLayout,
			SurveyQuestionAnswerChoice surveyQuestionAnswerChoice, MatrixColumnTypeSelect matrixColumnTypeSelectFld) {
		mainLayout.setMargin(true);
		mainLayout.addStyleName(ValoTheme.LAYOUT_CARD);
		if (mainLayout.getComponentCount() > 1) {
			mainLayout.removeComponent(mainLayout.getComponent(1));
		}

		String columnType = matrixColumnTypeSelectFld.getStringValue();
		switch (columnType) {
		case MatrixColumnType.TEXT:
			TextColumnConfigurationView textColumnConfigurationView = new TextColumnConfigurationView(
					surveyQuestionAnswerChoice);
			mainLayout.addComponent(textColumnConfigurationView);
			break;
		case MatrixColumnType.DATE:
			DateColumnConfigurationView dateColumnConfigurationView = new DateColumnConfigurationView(
					surveyQuestionAnswerChoice);
			mainLayout.addComponent(dateColumnConfigurationView);
			break;
		case MatrixColumnType.WHOLE_NUMBER:
			WholeNumberColumnConfigurationView wholeNumberColumnConfigurationView = new WholeNumberColumnConfigurationView(
					surveyQuestionAnswerChoice);
			mainLayout.addComponent(wholeNumberColumnConfigurationView);
			break;
		case MatrixColumnType.DECIMAL_NUMBER:
			DecimalNumberColumnConfigurationView decimalNumberColumnConfigurationView = new DecimalNumberColumnConfigurationView(
					sessionHolder, surveyQuestionAnswerChoice);
			mainLayout.addComponent(decimalNumberColumnConfigurationView);
			break;
		case MatrixColumnType.SINGLE_COMPOSITE_SELECTION:
		case MatrixColumnType.MULTIPLE_COMPOSITE_SELECTION:
			SingleSelectionColumnConfigurationView singleSelectionColumnConfigurationView = new SingleSelectionColumnConfigurationView(
					surveyQuestionAnswerChoice);
			mainLayout.addComponent(singleSelectionColumnConfigurationView);
			break;
		default:
			break;
		}
	}

	private final class ConfigureColumnListener implements ClickListener {

		private VerticalLayout mainLayout;
		private SurveyQuestionAnswerChoice surveyQuestionAnswerChoice;
		private MatrixColumnTypeSelect matrixColumnTypeSelectFld;

		public ConfigureColumnListener(VerticalLayout mainLayout, SurveyQuestionAnswerChoice surveyQuestionAnswerChoice,
				MatrixColumnTypeSelect matrixColumnTypeSelectFld) {
			this.mainLayout = mainLayout;
			this.surveyQuestionAnswerChoice = surveyQuestionAnswerChoice;
			this.matrixColumnTypeSelectFld = matrixColumnTypeSelectFld;
		}

		@Override
		public void buttonClick(ClickEvent event) {
			if (!matrixColumnTypeSelectFld.getValue().getId().equals(MatrixColumnType.SINGLE_SELECTION)
					&& !matrixColumnTypeSelectFld.getValue().getId().equals(MatrixColumnType.MULTIPLE_SELECTION)) {
				Boolean showConfiguration = (Boolean) event.getButton().getData();
				if (!showConfiguration) {
					showMatrixColumnConfiguration(mainLayout, surveyQuestionAnswerChoice, matrixColumnTypeSelectFld);
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
		}
	}

	private final class LabelValueChangeListener implements ValueChangeListener<String> {

		private SurveyQuestionAnswerChoice surveyQuestionAnswerChoice;

		public LabelValueChangeListener(SurveyQuestionAnswerChoice surveyQuestionAnswerChoice) {
			this.surveyQuestionAnswerChoice = surveyQuestionAnswerChoice;
		}

		@Override
		public void valueChange(ValueChangeEvent<String> event) {
			try {
				SurveyQuestionAnswerChoiceVO surveyQuestionAnswerChoiceVO = new SurveyQuestionAnswerChoiceVO();
				surveyQuestionAnswerChoiceVO.setId(surveyQuestionAnswerChoice.getId());
				surveyQuestionAnswerChoiceVO.setLabel(event.getValue());
				SurveyQuestionAnswerChoiceServiceFacade.get(UI.getCurrent()).updateLabel(surveyQuestionAnswerChoiceVO);
			} catch (FrameworkException e) {
				logger.error(e.getMessage(), e);
				new MessageWindowHandler(e);
			}
		}
	}

	private final class MatrixColumnTypeValueChangeListener implements ValueChangeListener<ComboItem> {

		private VerticalLayout mainLayout;
		private SurveyQuestionAnswerChoice surveyQuestionAnswerChoice;
		private MatrixColumnTypeSelect matrixColumnTypeSelectFld;
		private CButton configureBtn;

		public MatrixColumnTypeValueChangeListener(VerticalLayout mainLayout,
				SurveyQuestionAnswerChoice surveyQuestionAnswerChoice, MatrixColumnTypeSelect matrixColumnTypeSelectFld,
				CButton configureBtn) {
			this.mainLayout = mainLayout;
			this.surveyQuestionAnswerChoice = surveyQuestionAnswerChoice;
			this.matrixColumnTypeSelectFld = matrixColumnTypeSelectFld;
			this.configureBtn = configureBtn;
		}

		@Override
		public void valueChange(ValueChangeEvent<ComboItem> event) {

			try {
				Boolean showConfiguration = (Boolean) configureBtn.getData();
				if (showConfiguration) {
					showMatrixColumnConfiguration(mainLayout, surveyQuestionAnswerChoice, matrixColumnTypeSelectFld);
				}
				SurveyQuestionAnswerChoiceVO surveyQuestionAnswerChoiceVO = new SurveyQuestionAnswerChoiceVO();
				surveyQuestionAnswerChoiceVO.setId(surveyQuestionAnswerChoice.getId());
				surveyQuestionAnswerChoiceVO.setMatrixColumnType(event.getValue().getId());
				if (event.getValue().getId().equals(MatrixColumnType.MULTIPLE_SELECTION)
						|| event.getValue().getId().equals(MatrixColumnType.MULTIPLE_COMPOSITE_SELECTION)) {
					surveyQuestionAnswerChoiceVO.setMultipleSelection(true);
				} else {
					surveyQuestionAnswerChoiceVO.setMultipleSelection(false);
				}
				SurveyQuestionAnswerChoiceServiceFacade.get(UI.getCurrent())
						.updateMatrixColumnType(surveyQuestionAnswerChoiceVO);
			} catch (FrameworkException e) {
				logger.error(e.getMessage(), e);
				new MessageWindowHandler(e);
			}
		}
	}

	private final class RemoveColumnListener implements ClickListener {

		private Layout rowLayout;
		private SurveyQuestionAnswerChoice surveyQuestionAnswerChoice;

		public RemoveColumnListener(Layout rowLayout, SurveyQuestionAnswerChoice surveyQuestionAnswerChoice) {
			this.rowLayout = rowLayout;
			this.surveyQuestionAnswerChoice = surveyQuestionAnswerChoice;
		}

		@Override
		public void buttonClick(ClickEvent event) {

			Integer componentIndex = rowContainerLayout.getComponentIndex(rowLayout);
			try {
				SurveyQuestionAnswerChoiceServiceFacade.get(UI.getCurrent()).deleteAndUpdateIndex(
						SurveyQuestionAnswerChoice.class, surveyQuestionAnswerChoice.getId(),
						surveyQuestionAnswerChoice.getSurveyQuestion().getId(), Axis.COLUMN, componentIndex + 1);

				columContainerLayout.removeComponent(rowLayout);
				createColumnIfNonExists();
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

			Integer componentIndex = rowContainerLayout.getComponentIndex(rowLayout);
			try {
				SurveyQuestionAnswerChoiceServiceFacade.get(UI.getCurrent()).deleteAndUpdateIndex(
						SurveyQuestionAnswerChoice.class, surveyQuestionAnswerChoice.getId(),
						surveyQuestionAnswerChoice.getSurveyQuestion().getId(), Axis.ROW, componentIndex + 1);
				rowContainerLayout.removeComponent(rowLayout);
				createRowIfNonExists();
			} catch (FrameworkException e) {
				logger.error(e.getMessage(), e);
				new MessageWindowHandler(e);
			}
		}
	}

	private class AddNewColumnListener implements ClickListener {

		private Layout rowLayout;

		public AddNewColumnListener(Layout rowLayout) {
			this.rowLayout = rowLayout;
		}

		@Override
		public void buttonClick(ClickEvent event) {
			try {
				Integer componentIndex = columContainerLayout.getComponentIndex(rowLayout);
				createColumn(componentIndex + 1, null);
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
