package software.simple.solutions.data.entry.es.control.web.view.question.preview;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.data.ValueProvider;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import software.simple.solutions.data.entry.es.control.constants.Axis;
import software.simple.solutions.data.entry.es.control.constants.MatrixColumnType;
import software.simple.solutions.data.entry.es.control.entities.SurveyQuestion;
import software.simple.solutions.data.entry.es.control.entities.SurveyQuestionAnswerChoice;
import software.simple.solutions.data.entry.es.control.entities.SurveyQuestionAnswerChoiceSelection;
import software.simple.solutions.data.entry.es.control.entities.SurveyResponse;
import software.simple.solutions.data.entry.es.control.entities.SurveyResponseAnswer;
import software.simple.solutions.data.entry.es.control.service.ISurveyQuestionAnswerChoiceSelectionService;
import software.simple.solutions.data.entry.es.control.service.ISurveyQuestionAnswerChoiceService;
import software.simple.solutions.data.entry.es.control.service.ISurveyResponseAnswerService;
import software.simple.solutions.framework.core.components.CCheckBox;
import software.simple.solutions.framework.core.components.CDecimalField;
import software.simple.solutions.framework.core.components.CDiscreetNumberField;
import software.simple.solutions.framework.core.components.CPopupDateField;
import software.simple.solutions.framework.core.components.MessageWindowHandler;
import software.simple.solutions.framework.core.components.SessionHolder;
import software.simple.solutions.framework.core.constants.Constants;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.util.ContextProvider;

public class QuestionTypeMatrixLayout extends VerticalLayout {

	private static final Logger logger = LogManager.getLogger(QuestionTypeMatrixLayout.class);

	private VerticalLayout matrixContainerLayout;
	private boolean previewMode = false;

	private SurveyQuestion surveyQuestion;
	private SurveyResponse surveyResponse;
	private SessionHolder sessionHolder;

	private ISurveyQuestionAnswerChoiceService surveyQuestionAnswerChoiceService;
	private ISurveyQuestionAnswerChoiceSelectionService surveyQuestionAnswerChoiceSelectionService;

	// private QuestionTypeMatrixLayout(SessionHolder sessionHolder) {
	// surveyQuestionAnswerChoiceService =
	// ContextProvider.getBean(ISurveyQuestionAnswerChoiceService.class);
	// surveyQuestionAnswerChoiceSelectionService = ContextProvider
	// .getBean(ISurveyQuestionAnswerChoiceSelectionService.class);
	// this.sessionHolder = sessionHolder;
	// buildMainLayout();
	// }

	public QuestionTypeMatrixLayout(SessionHolder sessionHolder, SurveyQuestion surveyQuestion,
			SurveyResponse surveyResponse) {
		this.sessionHolder = sessionHolder;
		this.surveyQuestion = surveyQuestion;
		this.surveyResponse = surveyResponse;
		surveyQuestionAnswerChoiceService = ContextProvider.getBean(ISurveyQuestionAnswerChoiceService.class);
		surveyQuestionAnswerChoiceSelectionService = ContextProvider
				.getBean(ISurveyQuestionAnswerChoiceSelectionService.class);
		buildMainLayout();
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

		List<SurveyResponseAnswer> surveyResponseAnswers = null;
		if(surveyResponse!=null){
			ISurveyResponseAnswerService surveyResponseAnswerService = ContextProvider
					.getBean(ISurveyResponseAnswerService.class);
			surveyResponseAnswers = surveyResponseAnswerService
					.getSurveyResponseAnswers(surveyResponse.getId(), surveyQuestion.getId());
		}

		List<SurveyQuestionAnswerChoice> surveyQuestionAnswerChoices = surveyQuestionAnswerChoiceService
				.findBySurveyQuestion(surveyQuestion.getId());
		if (surveyQuestionAnswerChoices != null && !surveyQuestionAnswerChoices.isEmpty()) {
			Grid<MatrixRow> grid = new Grid<MatrixRow>();
			grid.setHeightMode(HeightMode.UNDEFINED);
			grid.setSelectionMode(SelectionMode.NONE);
			grid.setWidth("100%");
			// grid.setEnabled(false);
			matrixContainerLayout.addComponent(grid);

			List<SurveyQuestionAnswerChoice> columns = surveyQuestionAnswerChoices.stream()
					.filter(p -> p.getAxis().equalsIgnoreCase(Axis.COLUMN)).collect(Collectors.toList());
			if (columns != null && !columns.isEmpty()) {
				boolean usedRowHeader = false;
				for (SurveyQuestionAnswerChoice surveyQuestionAnswerChoice : columns) {
					if (StringUtils.isNotBlank(surveyQuestionAnswerChoice.getLabel())) {
						if (!usedRowHeader) {
							Column<MatrixRow, String> column = grid.addColumn(new ValueProvider<MatrixRow, String>() {

								@Override
								public String apply(MatrixRow source) {
									return source.surveyQuestionAnswerChoice.getLabel();
								}
							});
							column.setCaption(surveyQuestionAnswerChoice.getLabel())
									.setId(surveyQuestionAnswerChoice.getLabel());
							usedRowHeader = true;
						} else {
							grid.addComponentColumn(new ComponentColumn(surveyQuestionAnswerChoice))
									.setId(surveyQuestionAnswerChoice.getId().toString())
									.setCaption(surveyQuestionAnswerChoice.getLabel());
						}
					}
				}

				if (surveyResponseAnswers == null) {
					surveyResponseAnswers = new ArrayList<SurveyResponseAnswer>();
				}
				List<SurveyQuestionAnswerChoice> rows = surveyQuestionAnswerChoices.stream()
						.filter(p -> p.getAxis().equalsIgnoreCase(Axis.ROW)).collect(Collectors.toList());
				List<MatrixRow> matrixRows = new ArrayList<QuestionTypeMatrixLayout.MatrixRow>();
				for (SurveyQuestionAnswerChoice surveyQuestionAnswerChoice : rows) {

					Map<Long, List<SurveyResponseAnswer>> map = surveyResponseAnswers.stream()
							.filter(p -> (p.getSurveyQuestionAnswerChoiceRow().getId()
									.compareTo(surveyQuestionAnswerChoice.getId()) == 0))
							.collect(Collectors.groupingBy(p -> p.getSurveyQuestionAnswerChoiceColumn().getId()));
					MatrixRow matrixRow = new MatrixRow();
					matrixRow.surveyQuestionAnswerChoice = surveyQuestionAnswerChoice;
					matrixRow.responseMap = map;
					matrixRows.add(matrixRow);
				}
				grid.setItems(matrixRows);
			}
		}
	}

	private class MatrixRow {
		private String responseText;
		private SurveyQuestionAnswerChoice surveyQuestionAnswerChoice;
		private Map<Long, List<SurveyResponseAnswer>> responseMap;
	}

	public void setPreviewMode() {
		previewMode = true;
		setUpFields();
	}

	private final class ComponentColumn implements ValueProvider<MatrixRow, Component> {

		private SurveyQuestionAnswerChoice column;

		public ComponentColumn(SurveyQuestionAnswerChoice column) {
			this.column = column;
		}

		@Override
		public Component apply(MatrixRow source) {
			List<SurveyResponseAnswer> answers = null;
			if (source.responseMap != null) {
				answers = source.responseMap.get(column.getId());
			}
			String matrixColumnType = column.getMatrixColumnType();
			switch (matrixColumnType) {
			case MatrixColumnType.TEXT:
				TextField textField = new TextField();
				textField.setWidth("100%");
				textField.addStyleName(ValoTheme.TEXTFIELD_SMALL);

				if (answers != null && answers.size() == 1) {
					SurveyResponseAnswer surveyResponseAnswer = answers.get(0);
					textField.setValue(surveyResponseAnswer.getResponseText());
				}

				return textField;
			case MatrixColumnType.DATE:
				CPopupDateField dateField = new CPopupDateField();
				dateField.addStyleName(ValoTheme.DATEFIELD_SMALL);
				dateField.setDateFormat(Constants.SIMPLE_DATE_FORMAT.toPattern());

				if (answers != null && answers.size() == 1) {
					SurveyResponseAnswer surveyResponseAnswer = answers.get(0);
					if (StringUtils.isNotBlank(surveyResponseAnswer.getResponseText())) {
						LocalDate localDate = LocalDate.parse(surveyResponseAnswer.getResponseText(),
								DateTimeFormatter.ofPattern(Constants.SIMPLE_DATE_FORMAT.toPattern()));
						dateField.setValue(localDate);
					}
				}

				return dateField;
			case MatrixColumnType.DECIMAL_NUMBER:
				CDecimalField decimalField = new CDecimalField(sessionHolder);
				decimalField.setWidth("100%");
				decimalField.addStyleName(ValoTheme.TEXTFIELD_SMALL);

				if (answers != null && answers.size() == 1) {
					SurveyResponseAnswer surveyResponseAnswer = answers.get(0);
					decimalField.setValue(surveyResponseAnswer.getResponseText());
				}

				return decimalField;
			case MatrixColumnType.WHOLE_NUMBER:
				CDiscreetNumberField discreetNumberField = new CDiscreetNumberField();
				discreetNumberField.setWidth("100%");
				discreetNumberField.addStyleName(ValoTheme.TEXTFIELD_SMALL);

				if (answers != null && answers.size() == 1) {
					SurveyResponseAnswer surveyResponseAnswer = answers.get(0);
					discreetNumberField.setValue(surveyResponseAnswer.getResponseText());
				}

				return discreetNumberField;
			case MatrixColumnType.SINGLE_SELECTION:
			case MatrixColumnType.MULTIPLE_SELECTION:
				CCheckBox singleSelectionFld = new CCheckBox();

				if (answers != null && answers.size() == 1) {
					SurveyResponseAnswer surveyResponseAnswer = answers.get(0);
					singleSelectionFld.setValue(surveyResponseAnswer.getSelected());
				}

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

						Map<Long, List<SurveyResponseAnswer>> selectionMap = null;
						if (answers != null) {
							selectionMap = answers.stream().collect(
									Collectors.groupingBy(p -> p.getSurveyQuestionAnswerChoiceSelection().getId()));
						}
						for (SurveyQuestionAnswerChoiceSelection selection : selections) {
							CCheckBox checkBox = new CCheckBox();
							checkBox.setCaption(selection.getLabel());
							horizontalLayout.addComponent(checkBox);

							if (selectionMap != null) {
								List<SurveyResponseAnswer> responses = selectionMap.get(selection.getId());
								if (responses != null && responses.size() == 1) {
									SurveyResponseAnswer surveyResponseAnswer = responses.get(0);
									checkBox.setValue(surveyResponseAnswer.getSelected());
								}
							}

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
