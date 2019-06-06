package software.simple.solutions.data.entry.es.control.web.view.question.preview;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import software.simple.solutions.data.entry.es.control.components.SingleSelectField;
import software.simple.solutions.data.entry.es.control.entities.SurveyQuestion;
import software.simple.solutions.data.entry.es.control.entities.SurveyQuestionAnswerChoice;
import software.simple.solutions.data.entry.es.control.entities.SurveyResponse;
import software.simple.solutions.data.entry.es.control.entities.SurveyResponseAnswer;
import software.simple.solutions.data.entry.es.control.service.ISurveyQuestionAnswerChoiceService;
import software.simple.solutions.data.entry.es.control.service.ISurveyResponseAnswerService;
import software.simple.solutions.data.entry.es.control.valueobjects.SurveyResponseAnswerVO;
import software.simple.solutions.framework.core.components.CCheckBox;
import software.simple.solutions.framework.core.components.CTextField;
import software.simple.solutions.framework.core.components.MessageWindowHandler;
import software.simple.solutions.framework.core.components.SessionHolder;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.util.ContextProvider;

public class QuestionTypeChoiceLayout extends VerticalLayout {

	private static final Logger logger = LogManager.getLogger(QuestionTypeChoiceLayout.class);

	private VerticalLayout rowContainerLayout;

	private SurveyQuestion surveyQuestion;
	private SurveyResponse surveyResponse;
	private SessionHolder sessionHolder;
	private boolean editable = false;

	private ISurveyQuestionAnswerChoiceService surveyQuestionAnswerChoiceService;

	public QuestionTypeChoiceLayout(SessionHolder sessionHolder, SurveyQuestion surveyQuestion,
			SurveyResponse surveyResponse) {
		this.sessionHolder = sessionHolder;
		this.surveyQuestion = surveyQuestion;
		this.surveyResponse = surveyResponse;
		surveyQuestionAnswerChoiceService = ContextProvider.getBean(ISurveyQuestionAnswerChoiceService.class);
	}

	public void build() {
		buildMainLayout();
	}

	private void buildMainLayout() {
		rowContainerLayout = new VerticalLayout();
		rowContainerLayout.setMargin(false);
		rowContainerLayout.setWidth("100%");
		addComponent(rowContainerLayout);

		setUpFields();
	}

	public void setUpFields() {
		if (surveyQuestion != null) {
			try {
				createRowsFromSurveyQuestion();
			} catch (FrameworkException e) {
				logger.error(e.getMessage(), e);
				new MessageWindowHandler(e);
			}
		}
	}

	public void createRowsFromSurveyQuestion() throws FrameworkException {
		Boolean multipleSelection = surveyQuestion.getMultipleSelection();
		if (multipleSelection != null && multipleSelection) {
			List<SurveyResponseAnswer> surveyResponseAnswers = null;
			if (surveyResponse != null) {
				ISurveyResponseAnswerService surveyResponseAnswerService = ContextProvider
						.getBean(ISurveyResponseAnswerService.class);
				surveyResponseAnswers = surveyResponseAnswerService.getSurveyResponseAnswers(surveyResponse.getId(),
						surveyQuestion.getId());
			}
			if (surveyResponseAnswers == null) {
				surveyResponseAnswers = new ArrayList<SurveyResponseAnswer>();
			}
			List<SurveyQuestionAnswerChoice> surveyQuestionAnswerChoices = surveyQuestionAnswerChoiceService
					.findBySurveyQuestion(surveyQuestion.getId());
			if (surveyQuestionAnswerChoices != null && !surveyQuestionAnswerChoices.isEmpty()) {
				for (SurveyQuestionAnswerChoice surveyQuestionAnswerChoice : surveyQuestionAnswerChoices) {
					Optional<SurveyResponseAnswer> optional = surveyResponseAnswers.stream()
							.filter(p -> (p.getSurveyQuestionAnswerChoiceRow().getId()
									.compareTo(surveyQuestionAnswerChoice.getId()) == 0))
							.findFirst();
					SurveyResponseAnswer surveyResponseAnswer = null;
					if (optional.isPresent()) {
						surveyResponseAnswer = optional.get();
					}
					createMultipleSelectionRow(surveyResponseAnswer, surveyQuestionAnswerChoice);
				}
			}
		} else {
			SurveyResponseAnswer surveyResponseAnswer = null;
			if (surveyResponse != null) {
				ISurveyResponseAnswerService surveyResponseAnswerService = ContextProvider
						.getBean(ISurveyResponseAnswerService.class);
				List<SurveyResponseAnswer> surveyResponseAnswers = surveyResponseAnswerService
						.getSurveyResponseAnswers(surveyResponse.getId(), surveyQuestion.getId());
				if (surveyResponseAnswers != null && !surveyResponseAnswers.isEmpty()) {
					surveyResponseAnswer = surveyResponseAnswers.get(0);
				}
			}
			List<SurveyQuestionAnswerChoice> surveyQuestionAnswerChoices = surveyQuestionAnswerChoiceService
					.findBySurveyQuestion(surveyQuestion.getId());
			if (surveyQuestionAnswerChoices != null && !surveyQuestionAnswerChoices.isEmpty()) {
				createSingleSelectionRow(surveyQuestionAnswerChoices, surveyResponseAnswer);
			}
		}
	}

	private void createMultipleSelectionRow(SurveyResponseAnswer surveyResponseAnswer,
			SurveyQuestionAnswerChoice surveyQuestionAnswerChoice) throws FrameworkException {
		HorizontalLayout rowLayout = new HorizontalLayout();
		rowLayout.setWidth("-1px");

		CCheckBox checkBox = new CCheckBox();
		checkBox.setCaption(surveyQuestionAnswerChoice.getLabel());
		checkBox.setEnabled(editable);
		rowLayout.addComponent(checkBox);
		rowLayout.setComponentAlignment(checkBox, Alignment.MIDDLE_LEFT);

		CTextField otherFld = null;
		if (surveyQuestionAnswerChoice.getIsOther() != null && surveyQuestionAnswerChoice.getIsOther()) {
			otherFld = new CTextField();
			otherFld.setWidth("400px");
			otherFld.setEnabled(false);
			rowLayout.addComponent(otherFld);
		}
		if (surveyResponseAnswer != null && surveyResponseAnswer.getSelected() != null) {
			checkBox.setValue(surveyResponseAnswer.getSelected());
		}
		if (otherFld != null) {
			otherFld.setValue(surveyResponseAnswer.getOtherValue());
		}

		checkBox.addValueChangeListener(
				new CheckValueChangeListener(surveyResponseAnswer, surveyQuestionAnswerChoice, otherFld));

		rowContainerLayout.addComponent(rowLayout);
	}

	private class CheckValueChangeListener implements ValueChangeListener<Boolean> {

		private SurveyResponseAnswer surveyResponseAnswer;
		private SurveyQuestionAnswerChoice surveyQuestionAnswerChoice;
		private CTextField otherFld;

		public CheckValueChangeListener(SurveyResponseAnswer surveyResponseAnswer,
				SurveyQuestionAnswerChoice surveyQuestionAnswerChoice, CTextField otherFld) {
			this.surveyResponseAnswer = surveyResponseAnswer;
			this.surveyQuestionAnswerChoice = surveyQuestionAnswerChoice;
			this.otherFld = otherFld;
		}

		@Override
		public void valueChange(ValueChangeEvent<Boolean> event) {
			if (otherFld != null) {
				otherFld.setEnabled(false);
				if (event.getValue() && editable) {
					otherFld.setEnabled(true);
				}
			}

			Boolean selected = event.getValue();
			ISurveyResponseAnswerService surveyResponseAnswerService = ContextProvider
					.getBean(ISurveyResponseAnswerService.class);
			SurveyResponseAnswerVO surveyResponseAnswerVO = new SurveyResponseAnswerVO();
			surveyResponseAnswerVO.setId(surveyResponseAnswer == null ? null : surveyResponseAnswer.getId());
			surveyResponseAnswerVO.setActive(true);
			surveyResponseAnswerVO
					.setUniqueId(surveyResponseAnswer == null ? null : surveyResponseAnswer.getUniqueId());
			surveyResponseAnswerVO.setSurveyResponseId(surveyResponse.getId());
			surveyResponseAnswerVO.setSurveyQuestionId(surveyQuestion.getId());
			surveyResponseAnswerVO.setCurrentRoleId(sessionHolder.getSelectedRole().getId());
			surveyResponseAnswerVO.setCurrentUserId(sessionHolder.getApplicationUser().getId());
			surveyResponseAnswerVO.setQuestionAnswerChoiceRowId(surveyQuestionAnswerChoice.getId());
			surveyResponseAnswerVO.setSelected(selected);
			try {
				surveyResponseAnswer = surveyResponseAnswerService
						.updateAnswerMultipleSelection(surveyResponseAnswerVO);
			} catch (FrameworkException e) {
				e.printStackTrace();
			}
		}
	}

	private void createSingleSelectionRow(List<SurveyQuestionAnswerChoice> surveyQuestionAnswerChoices,
			SurveyResponseAnswer surveyResponseAnswer) throws FrameworkException {
		SingleSelectField singleSelectField = new SingleSelectField(sessionHolder, surveyQuestionAnswerChoices,
				surveyResponse, surveyQuestion, surveyResponseAnswer);
		singleSelectField.setEnabled(editable);
		singleSelectField.build();

		rowContainerLayout.addComponent(singleSelectField);
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

}
