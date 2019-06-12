package software.simple.solutions.data.entry.es.control.web.view.question.preview;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import software.simple.solutions.data.entry.es.control.entities.SurveyQuestion;
import software.simple.solutions.data.entry.es.control.entities.SurveyResponse;
import software.simple.solutions.data.entry.es.control.entities.SurveyResponseAnswer;
import software.simple.solutions.data.entry.es.control.entities.SurveyResponseAnswerHistory;
import software.simple.solutions.data.entry.es.control.service.facade.SurveyResponseAnswerServiceFacade;
import software.simple.solutions.data.entry.es.control.valueobjects.SurveyResponseAnswerVO;
import software.simple.solutions.framework.core.components.CPopupDateField;
import software.simple.solutions.framework.core.components.SessionHolder;
import software.simple.solutions.framework.core.constants.Constants;
import software.simple.solutions.framework.core.exceptions.FrameworkException;

public class QuestionTypeDateLayout extends VerticalLayout {

	private final class AnswerFldChange implements ValueChangeListener<LocalDate> {

		private SurveyResponseAnswer surveyResponseAnswer;

		public AnswerFldChange(SurveyResponseAnswer surveyResponseAnswer) {
			this.surveyResponseAnswer = surveyResponseAnswer;
		}

		@Override
		public void valueChange(ValueChangeEvent<LocalDate> event) {
			LocalDate localDate = event.getValue();

			SurveyResponseAnswerVO surveyResponseAnswerVO = new SurveyResponseAnswerVO();
			surveyResponseAnswerVO.setId(surveyResponseAnswer == null ? null : surveyResponseAnswer.getId());
			surveyResponseAnswerVO.setActive(true);
			surveyResponseAnswerVO
					.setUniqueId(surveyResponseAnswer == null ? null : surveyResponseAnswer.getUniqueId());
			surveyResponseAnswerVO.setSurveyResponseId(surveyResponse.getId());
			surveyResponseAnswerVO.setSurveyQuestionId(surveyQuestion.getId());
			surveyResponseAnswerVO.setCurrentRoleId(sessionHolder.getSelectedRole().getId());
			surveyResponseAnswerVO.setCurrentUserId(sessionHolder.getApplicationUser().getId());
			surveyResponseAnswerVO.setResponseText(localDate == null ? null
					: localDate.format(DateTimeFormatter.ofPattern(Constants.SIMPLE_DATE_FORMAT.toPattern())));
			try {
				SurveyResponseAnswerServiceFacade.get(UI.getCurrent()).updateAnswerForSingle(surveyResponseAnswerVO);
			} catch (FrameworkException e) {
				e.printStackTrace();
			}
		}
	}

	private CPopupDateField answerFld;

	private SurveyQuestion surveyQuestion;
	private SurveyResponse surveyResponse;
	private SurveyResponseAnswerHistory surveyResponseAnswerHistory;
	private SessionHolder sessionHolder;

	private boolean editable = false;

	private QuestionTypeDateLayout() {
		super();
	}

	public QuestionTypeDateLayout(SessionHolder sessionHolder, SurveyQuestion surveyQuestion) {
		this();
		this.sessionHolder = sessionHolder;
		this.surveyQuestion = surveyQuestion;
	}

	public QuestionTypeDateLayout(SessionHolder sessionHolder, SurveyQuestion surveyQuestion,
			SurveyResponse surveyResponse, SurveyResponseAnswerHistory surveyResponseAnswerHistory) {
		this(sessionHolder, surveyQuestion);
		this.surveyResponse = surveyResponse;
		this.surveyResponseAnswerHistory = surveyResponseAnswerHistory;
		buildMainLayout();

		updateFields();
	}

	private void buildMainLayout() {
		answerFld = new CPopupDateField();
		answerFld.setDateFormat(Constants.SIMPLE_DATE_FORMAT.toPattern());
		addComponent(answerFld);

		if (surveyResponse != null) {
			try {
				if (surveyResponseAnswerHistory != null) {
					String responseJson = surveyResponseAnswerHistory.getResponseJson();
					String fromJson = new Gson().fromJson(responseJson, String.class);
					LocalDate localDate = LocalDate.parse(fromJson,
							DateTimeFormatter.ofPattern(Constants.SIMPLE_DATE_FORMAT.toPattern()));
					answerFld.setValue(localDate);
				} else {
					SurveyResponseAnswer surveyResponseAnswer = SurveyResponseAnswerServiceFacade.get(UI.getCurrent())
							.getSurveyResponseAnswer(surveyResponse.getId(), surveyQuestion.getId());
					if (surveyResponseAnswer != null
							&& StringUtils.isNotBlank(surveyResponseAnswer.getResponseText())) {
						String responseText = surveyResponseAnswer.getResponseText();
						LocalDate localDate = LocalDate.parse(responseText,
								DateTimeFormatter.ofPattern(Constants.SIMPLE_DATE_FORMAT.toPattern()));
						answerFld.setValue(localDate);
					}

					answerFld.addValueChangeListener(new AnswerFldChange(surveyResponseAnswer));
				}

			} catch (FrameworkException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
		updateFields();
	}

	private void updateFields() {
		if (answerFld != null) {
			answerFld.setEnabled(editable);
		}
	}

}
