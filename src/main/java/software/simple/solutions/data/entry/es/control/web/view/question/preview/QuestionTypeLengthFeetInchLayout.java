package software.simple.solutions.data.entry.es.control.web.view.question.preview;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import software.simple.solutions.data.entry.es.control.entities.SurveyQuestion;
import software.simple.solutions.data.entry.es.control.entities.SurveyResponse;
import software.simple.solutions.data.entry.es.control.entities.SurveyResponseAnswer;
import software.simple.solutions.data.entry.es.control.service.ISurveyResponseAnswerService;
import software.simple.solutions.framework.core.components.CDecimalField;
import software.simple.solutions.framework.core.components.SessionHolder;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.util.ContextProvider;
import software.simple.solutions.framework.core.util.NumberUtil;

public class QuestionTypeLengthFeetInchLayout extends VerticalLayout {

	private CDecimalField lenghtFeetFld;
	private CDecimalField lenghtInchFld;
	private HorizontalLayout horizontalLayout;

	private SurveyQuestion surveyQuestion;
	private SurveyResponse surveyResponse;
	private SessionHolder sessionHolder;

	// private QuestionTypeLengthFeetInchLayout(SessionHolder sessionHolder) {
	// this.sessionHolder = sessionHolder;
	// buildMainLayout();
	// }

	public QuestionTypeLengthFeetInchLayout(SessionHolder sessionHolder, SurveyQuestion surveyQuestion,
			SurveyResponse surveyResponse) {
		this.sessionHolder = sessionHolder;
		this.surveyQuestion = surveyQuestion;
		this.surveyResponse = surveyResponse;
		buildMainLayout();
	}

	private void buildMainLayout() {
		horizontalLayout = new HorizontalLayout();
		horizontalLayout.setMargin(false);
		horizontalLayout.setSpacing(true);
		addComponent(horizontalLayout);

		lenghtFeetFld = new CDecimalField(sessionHolder);
		lenghtFeetFld.setWidth("100px");
		horizontalLayout.addComponent(lenghtFeetFld);

		horizontalLayout.addComponent(new Label("feet"));

		lenghtInchFld = new CDecimalField(sessionHolder);
		lenghtInchFld.setWidth("100px");
		horizontalLayout.addComponent(lenghtInchFld);

		horizontalLayout.addComponent(new Label("inch"));

		if (surveyResponse != null) {
			ISurveyResponseAnswerService surveyResponseAnswerService = ContextProvider
					.getBean(ISurveyResponseAnswerService.class);
			try {
				SurveyResponseAnswer surveyResponseAnswer = surveyResponseAnswerService
						.getSurveyResponseAnswer(surveyResponse.getId(), surveyQuestion.getId());
				if (surveyResponseAnswer != null && StringUtils.isNotBlank(surveyResponseAnswer.getResponseText())) {
					String responseText = surveyResponseAnswer.getResponseText();
					String[] split = responseText.split(":");
					List<String> list = Arrays.asList(split);
					for (int i = 0; i < list.size(); i++) {
						switch (i) {
						case 0:
							lenghtFeetFld.setBigDecimalValue(NumberUtil.getBigDecimal(list.get(i)));
							break;
						case 1:
							lenghtInchFld.setBigDecimalValue(NumberUtil.getBigDecimal(list.get(i)));
							break;
						default:
							break;
						}
					}
				}
			} catch (FrameworkException e) {
				e.printStackTrace();
			}
		}

	}

	public void setPreviewMode() {
		horizontalLayout.setEnabled(false);
	}

}
