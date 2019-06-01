package software.simple.solutions.data.entry.es.control.web.view.question.preview;

import com.vaadin.ui.VerticalLayout;

import software.simple.solutions.data.entry.es.control.entities.SurveyQuestion;
import software.simple.solutions.data.entry.es.control.entities.SurveyResponse;
import software.simple.solutions.data.entry.es.control.entities.SurveyResponseAnswer;
import software.simple.solutions.data.entry.es.control.service.ISurveyResponseAnswerService;
import software.simple.solutions.framework.core.components.CTextArea;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.util.ContextProvider;

public class QuestionTypeSingleLayout extends VerticalLayout {

	private CTextArea answerFld;

	private SurveyQuestion surveyQuestion;
	private SurveyResponse surveyResponse;

	private QuestionTypeSingleLayout() {
	}

	public QuestionTypeSingleLayout(SurveyQuestion surveyQuestion) {
		this();
		this.surveyQuestion = surveyQuestion;
	}

	public QuestionTypeSingleLayout(SurveyQuestion surveyQuestion, SurveyResponse surveyResponse) {
		this(surveyQuestion);
		this.surveyResponse = surveyResponse;
		buildMainLayout();
	}

	private void buildMainLayout() {
		answerFld = new CTextArea();
		answerFld.setWidth("100%");
		answerFld.setRows(1);
		addComponent(answerFld);

		if (surveyResponse != null) {
			ISurveyResponseAnswerService surveyResponseAnswerService = ContextProvider
					.getBean(ISurveyResponseAnswerService.class);
			try {
				SurveyResponseAnswer surveyResponseAnswer = surveyResponseAnswerService
						.getSurveyResponseAnswer(surveyResponse.getId(), surveyQuestion.getId());
				if (surveyResponseAnswer != null) {
					answerFld.setValue(surveyResponseAnswer.getResponseText());
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
