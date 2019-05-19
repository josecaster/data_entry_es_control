package software.simple.solutions.data.entry.es.control.web.view.question.preview;

import com.vaadin.ui.VerticalLayout;

import software.simple.solutions.data.entry.es.control.entities.SurveyQuestion;
import software.simple.solutions.framework.core.components.CPopupDateField;

public class QuestionTypeDateLayout extends VerticalLayout {

	private CPopupDateField answerFld;

	private SurveyQuestion surveyQuestion;

	private QuestionTypeDateLayout() {
		buildMainLayout();
	}

	public QuestionTypeDateLayout(SurveyQuestion surveyQuestion) {
		this();
		this.surveyQuestion = surveyQuestion;
	}

	private void buildMainLayout() {
		answerFld = new CPopupDateField();
		addComponent(answerFld);
	}

	public void setPreviewMode() {
		answerFld.setEnabled(false);
	}

}
