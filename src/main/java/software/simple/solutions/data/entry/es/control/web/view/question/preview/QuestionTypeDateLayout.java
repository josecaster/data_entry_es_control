package software.simple.solutions.data.entry.es.control.web.view.question.preview;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.StringUtils;

import com.vaadin.ui.VerticalLayout;

import software.simple.solutions.data.entry.es.control.entities.SurveyQuestion;
import software.simple.solutions.data.entry.es.control.entities.SurveyResponse;
import software.simple.solutions.data.entry.es.control.entities.SurveyResponseAnswer;
import software.simple.solutions.data.entry.es.control.service.ISurveyResponseAnswerService;
import software.simple.solutions.framework.core.components.CPopupDateField;
import software.simple.solutions.framework.core.constants.Constants;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.util.ContextProvider;

public class QuestionTypeDateLayout extends VerticalLayout {

	private CPopupDateField answerFld;

	private SurveyQuestion surveyQuestion;
	private SurveyResponse surveyResponse;

	private QuestionTypeDateLayout() {
		super();
	}

	public QuestionTypeDateLayout(SurveyQuestion surveyQuestion) {
		this();
		this.surveyQuestion = surveyQuestion;
	}

	public QuestionTypeDateLayout(SurveyQuestion surveyQuestion, SurveyResponse surveyResponse) {
		this(surveyQuestion);
		this.surveyResponse = surveyResponse;
		buildMainLayout();
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
			} catch (FrameworkException e) {
				e.printStackTrace();
			}
		}
	}

	public void setPreviewMode() {
		answerFld.setEnabled(false);
	}

}
