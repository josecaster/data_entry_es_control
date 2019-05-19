package software.simple.solutions.data.entry.es.control.web.view.question.preview;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import software.simple.solutions.data.entry.es.control.entities.SurveyQuestion;
import software.simple.solutions.framework.core.components.CDecimalField;

public class QuestionTypeAreaFeetInchLayout extends VerticalLayout {

	private CDecimalField lenghtFeetFld;
	private CDecimalField lenghtInchFld;
	private CDecimalField widthFeetFld;
	private CDecimalField widthInchFld;
	private HorizontalLayout horizontalLayout;

	private SurveyQuestion surveyQuestion;

	private QuestionTypeAreaFeetInchLayout() {
		buildMainLayout();
	}

	public QuestionTypeAreaFeetInchLayout(SurveyQuestion surveyQuestion) {
		this();
		this.surveyQuestion = surveyQuestion;
	}

	private void buildMainLayout() {
		horizontalLayout = new HorizontalLayout();
		horizontalLayout.setMargin(false);
		horizontalLayout.setSpacing(true);
		addComponent(horizontalLayout);

		lenghtFeetFld = new CDecimalField();
		lenghtFeetFld.setWidth("100px");
		horizontalLayout.addComponent(lenghtFeetFld);

		horizontalLayout.addComponent(new Label("feet"));

		lenghtInchFld = new CDecimalField();
		lenghtInchFld.setWidth("100px");
		horizontalLayout.addComponent(lenghtInchFld);

		horizontalLayout.addComponent(new Label("inch by"));

		widthFeetFld = new CDecimalField();
		widthFeetFld.setWidth("100px");
		horizontalLayout.addComponent(widthFeetFld);

		horizontalLayout.addComponent(new Label("feet"));

		widthInchFld = new CDecimalField();
		widthInchFld.setWidth("100px");
		horizontalLayout.addComponent(widthInchFld);

		horizontalLayout.addComponent(new Label("inch"));
	}

	public void setPreviewMode() {
		horizontalLayout.setEnabled(false);
	}

}
