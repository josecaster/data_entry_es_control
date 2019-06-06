package software.simple.solutions.data.entry.es.control.web.view.question.preview;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.StringUtils;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.ui.VerticalLayout;

import software.simple.solutions.data.entry.es.control.entities.SurveyQuestion;
import software.simple.solutions.data.entry.es.control.entities.SurveyResponse;
import software.simple.solutions.data.entry.es.control.entities.SurveyResponseAnswer;
import software.simple.solutions.data.entry.es.control.service.ISurveyResponseAnswerService;
import software.simple.solutions.data.entry.es.control.valueobjects.SurveyResponseAnswerVO;
import software.simple.solutions.framework.core.components.CPopupDateField;
import software.simple.solutions.framework.core.components.SessionHolder;
import software.simple.solutions.framework.core.constants.Constants;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.util.ContextProvider;

public class QuestionTypeDateLayout extends VerticalLayout {

	private CPopupDateField answerFld;

	private SurveyQuestion surveyQuestion;
	private SurveyResponse surveyResponse;
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
			SurveyResponse surveyResponse) {
		this(sessionHolder, surveyQuestion);
		this.surveyResponse = surveyResponse;
		buildMainLayout();

		updateFields();
	}

	private void buildMainLayout() {
		answerFld = new CPopupDateField();
		answerFld.setDateFormat(Constants.SIMPLE_DATE_FORMAT.toPattern());
		addComponent(answerFld);

		if (surveyResponse != null) {
			ISurveyResponseAnswerService surveyResponseAnswerService = ContextProvider
					.getBean(ISurveyResponseAnswerService.class);
			try {
				SurveyResponseAnswer surveyResponseAnswer = surveyResponseAnswerService
						.getSurveyResponseAnswer(surveyResponse.getId(), surveyQuestion.getId());
				if (surveyResponseAnswer != null && StringUtils.isNotBlank(surveyResponseAnswer.getResponseText())) {
					String responseText = surveyResponseAnswer.getResponseText();
					LocalDate localDate = LocalDate.parse(responseText,
							DateTimeFormatter.ofPattern(Constants.SIMPLE_DATE_FORMAT.toPattern()));
					answerFld.setValue(localDate);
				}

				answerFld.addValueChangeListener(new ValueChangeListener<LocalDate>() {

					@Override
					public void valueChange(ValueChangeEvent<LocalDate> event) {
						LocalDate localDate = event.getValue();

						ISurveyResponseAnswerService surveyResponseAnswerService = ContextProvider
								.getBean(ISurveyResponseAnswerService.class);
						SurveyResponseAnswerVO surveyResponseAnswerVO = new SurveyResponseAnswerVO();
						surveyResponseAnswerVO
								.setId(surveyResponseAnswer == null ? null : surveyResponseAnswer.getId());
						surveyResponseAnswerVO.setActive(true);
						surveyResponseAnswerVO
								.setUniqueId(surveyResponseAnswer == null ? null : surveyResponseAnswer.getUniqueId());
						surveyResponseAnswerVO.setSurveyResponseId(surveyResponse.getId());
						surveyResponseAnswerVO.setSurveyQuestionId(surveyQuestion.getId());
						surveyResponseAnswerVO.setCurrentRoleId(sessionHolder.getSelectedRole().getId());
						surveyResponseAnswerVO.setCurrentUserId(sessionHolder.getApplicationUser().getId());
						surveyResponseAnswerVO.setResponseText(localDate == null ? null
								: localDate
										.format(DateTimeFormatter.ofPattern(Constants.SIMPLE_DATE_FORMAT.toPattern())));
						try {
							surveyResponseAnswerService.updateAnswerForSingle(surveyResponseAnswerVO);
						} catch (FrameworkException e) {
							e.printStackTrace();
						}
					}
				});

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
