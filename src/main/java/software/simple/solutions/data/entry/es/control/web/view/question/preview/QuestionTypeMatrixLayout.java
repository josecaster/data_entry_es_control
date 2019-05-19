package software.simple.solutions.data.entry.es.control.web.view.question.preview;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.data.ValueProvider;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import software.simple.solutions.data.entry.es.control.constants.Axis;
import software.simple.solutions.data.entry.es.control.constants.MatrixColumnType;
import software.simple.solutions.data.entry.es.control.entities.SurveyQuestion;
import software.simple.solutions.data.entry.es.control.entities.SurveyQuestionAnswerChoice;
import software.simple.solutions.data.entry.es.control.entities.SurveyQuestionAnswerChoiceSelection;
import software.simple.solutions.data.entry.es.control.service.ISurveyQuestionAnswerChoiceSelectionService;
import software.simple.solutions.data.entry.es.control.service.ISurveyQuestionAnswerChoiceService;
import software.simple.solutions.framework.core.components.CCheckBox;
import software.simple.solutions.framework.core.components.CDecimalField;
import software.simple.solutions.framework.core.components.CDiscreetNumberField;
import software.simple.solutions.framework.core.components.CPopupDateField;
import software.simple.solutions.framework.core.components.MessageWindowHandler;
import software.simple.solutions.framework.core.components.SessionHolder;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.util.ContextProvider;

public class QuestionTypeMatrixLayout extends VerticalLayout {

	private static final Logger logger = LogManager.getLogger(QuestionTypeMatrixLayout.class);

	private VerticalLayout matrixContainerLayout;
	private boolean previewMode = false;

	private SurveyQuestion surveyQuestion;
	private SessionHolder sessionHolder;

	private ISurveyQuestionAnswerChoiceService surveyQuestionAnswerChoiceService;
	private ISurveyQuestionAnswerChoiceSelectionService surveyQuestionAnswerChoiceSelectionService;

	{
		surveyQuestionAnswerChoiceService = ContextProvider.getBean(ISurveyQuestionAnswerChoiceService.class);
		surveyQuestionAnswerChoiceSelectionService = ContextProvider
				.getBean(ISurveyQuestionAnswerChoiceSelectionService.class);
		sessionHolder = (SessionHolder) UI.getCurrent().getData();
	}

	private QuestionTypeMatrixLayout() {
		buildMainLayout();
	}

	public QuestionTypeMatrixLayout(SurveyQuestion surveyQuestion) {
		this();
		this.surveyQuestion = surveyQuestion;
	}

	private void buildMainLayout() {
		matrixContainerLayout = new VerticalLayout();
		matrixContainerLayout.setMargin(false);
		matrixContainerLayout.setWidth("100%");
		addComponent(matrixContainerLayout);
	}

	public void setUpFields() {
		if (surveyQuestion != null) {
			try {
				createMatrixFromSurveyQuestion();
			} catch (FrameworkException e) {
				logger.error(e.getMessage(), e);
				new MessageWindowHandler(e);
			}
		}
	}

	public void createMatrixFromSurveyQuestion() throws FrameworkException {
		matrixContainerLayout.removeAllComponents();
		List<SurveyQuestionAnswerChoice> surveyQuestionAnswerChoices = surveyQuestionAnswerChoiceService
				.findBySurveyQuestion(surveyQuestion.getId());
		if (surveyQuestionAnswerChoices != null && !surveyQuestionAnswerChoices.isEmpty()) {
			Grid<SurveyQuestionAnswerChoice> grid = new Grid<SurveyQuestionAnswerChoice>();
			grid.setHeightMode(HeightMode.UNDEFINED);
			grid.setSelectionMode(SelectionMode.NONE);
			grid.setWidth("100%");
//			grid.setEnabled(false);
			matrixContainerLayout.addComponent(grid);

			List<SurveyQuestionAnswerChoice> columns = surveyQuestionAnswerChoices.stream()
					.filter(p -> p.getAxis().equalsIgnoreCase(Axis.COLUMN)).collect(Collectors.toList());
			if (columns != null && !columns.isEmpty()) {
				boolean usedRowHeader = false;
				for (SurveyQuestionAnswerChoice surveyQuestionAnswerChoice : columns) {
					if (StringUtils.isNotBlank(surveyQuestionAnswerChoice.getLabel())) {
						if (!usedRowHeader) {
							grid.addColumn(SurveyQuestionAnswerChoice::getLabel)
									.setCaption(surveyQuestionAnswerChoice.getLabel())
									.setId(surveyQuestionAnswerChoice.getLabel());
							usedRowHeader = true;
						} else {
							grid.addComponentColumn(new ComponentColumn(surveyQuestionAnswerChoice))
									.setCaption(surveyQuestionAnswerChoice.getLabel());
						}
					}
				}

				List<SurveyQuestionAnswerChoice> rows = surveyQuestionAnswerChoices.stream()
						.filter(p -> p.getAxis().equalsIgnoreCase(Axis.ROW)).collect(Collectors.toList());
				grid.setItems(rows);

				for (SurveyQuestionAnswerChoice surveyQuestionAnswerChoice : rows) {
					if (StringUtils.isNotBlank(surveyQuestionAnswerChoice.getLabel())) {
					}
				}
			}
		}
	}

	public void setPreviewMode() {
		previewMode = true;
		setUpFields();
	}

	private final class ComponentColumn implements ValueProvider<SurveyQuestionAnswerChoice, Component> {

		private SurveyQuestionAnswerChoice column;

		public ComponentColumn(SurveyQuestionAnswerChoice column) {
			this.column = column;
		}

		@Override
		public Component apply(SurveyQuestionAnswerChoice source) {
			String matrixColumnType = column.getMatrixColumnType();
			switch (matrixColumnType) {
			case MatrixColumnType.TEXT:
				TextField textField = new TextField();
				textField.setWidth("100%");
				textField.addStyleName(ValoTheme.TEXTFIELD_SMALL);
				return textField;
			case MatrixColumnType.DATE:
				CPopupDateField dateField = new CPopupDateField();
				dateField.addStyleName(ValoTheme.DATEFIELD_SMALL);
				return dateField;
			case MatrixColumnType.DECIMAL_NUMBER:
				CDecimalField decimalField = new CDecimalField();
				decimalField.setWidth("100%");
				decimalField.addStyleName(ValoTheme.TEXTFIELD_SMALL);
				return decimalField;
			case MatrixColumnType.WHOLE_NUMBER:
				CDiscreetNumberField discreetNumberField = new CDiscreetNumberField();
				discreetNumberField.setWidth("100%");
				discreetNumberField.addStyleName(ValoTheme.TEXTFIELD_SMALL);
				return discreetNumberField;
			case MatrixColumnType.SINGLE_SELECTION:
			case MatrixColumnType.MULTIPLE_SELECTION:
				CCheckBox singleSelectionFld = new CCheckBox();
				return singleSelectionFld;
			case MatrixColumnType.SINGLE_COMPOSITE_SELECTION:
			case MatrixColumnType.MULTIPLE_COMPOSITE_SELECTION:
				HorizontalLayout horizontalLayout = new HorizontalLayout();
				horizontalLayout.setMargin(false);
				horizontalLayout.setWidth("-1px");
				try {
					List<SurveyQuestionAnswerChoiceSelection> selections = surveyQuestionAnswerChoiceSelectionService
							.getBySurveyQuestionAnswerChoice(column.getId());
					if (selections != null) {
						for (SurveyQuestionAnswerChoiceSelection selection : selections) {
							CCheckBox checkBox = new CCheckBox();
							checkBox.setCaption(selection.getLabel());
							horizontalLayout.addComponent(checkBox);
						}
					}
				} catch (FrameworkException e) {
					logger.error(e.getMessage(), e);
					new MessageWindowHandler(e);
				}
				return horizontalLayout;
			default:
				break;
			}
			return null;
		}
	}
}
