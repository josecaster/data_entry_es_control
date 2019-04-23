package software.simple.solutions.data.entry.es.control.web.view.question.preview;

import com.vaadin.ui.VerticalLayout;

import software.simple.solutions.data.entry.es.control.entities.SurveyQuestion;
import software.simple.solutions.framework.core.components.CTextArea;

public class QuestionTypeSingleLayout extends VerticalLayout {

	private CTextArea answerFld;

	private SurveyQuestion surveyQuestion;

	private QuestionTypeSingleLayout() {
		buildMainLayout();
	}

	public QuestionTypeSingleLayout(SurveyQuestion surveyQuestion) {
		this();
		this.surveyQuestion = surveyQuestion;
	}

	private void buildMainLayout() {
		answerFld = new CTextArea();
		answerFld.setWidth("100%");
		answerFld.setRows(1);
		addComponent(answerFld);
	}

	public void setPreviewMode() {
		answerFld.setEnabled(false);
	}

}
