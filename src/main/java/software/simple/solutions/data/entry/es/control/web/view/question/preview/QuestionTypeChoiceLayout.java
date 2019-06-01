package software.simple.solutions.data.entry.es.control.web.view.question.preview;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import software.simple.solutions.data.entry.es.control.entities.SurveyQuestion;
import software.simple.solutions.data.entry.es.control.entities.SurveyQuestionAnswerChoice;
import software.simple.solutions.data.entry.es.control.entities.SurveyResponse;
import software.simple.solutions.data.entry.es.control.entities.SurveyResponseAnswer;
import software.simple.solutions.data.entry.es.control.service.ISurveyQuestionAnswerChoiceService;
import software.simple.solutions.data.entry.es.control.service.ISurveyResponseAnswerService;
import software.simple.solutions.framework.core.components.CCheckBox;
import software.simple.solutions.framework.core.components.CTextField;
import software.simple.solutions.framework.core.components.MessageWindowHandler;
import software.simple.solutions.framework.core.components.SessionHolder;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.util.ContextProvider;

public class QuestionTypeChoiceLayout extends VerticalLayout {

	private static final Logger logger = LogManager.getLogger(QuestionTypeChoiceLayout.class);

	private VerticalLayout rowContainerLayout;
	private boolean previewMode = false;

	private SurveyQuestion surveyQuestion;
	private SurveyResponse surveyResponse;
	private SessionHolder sessionHolder;

	private ISurveyQuestionAnswerChoiceService surveyQuestionAnswerChoiceService;

	// private QuestionTypeChoiceLayout() {
	// surveyQuestionAnswerChoiceService =
	// ContextProvider.getBean(ISurveyQuestionAnswerChoiceService.class);
	// sessionHolder = (SessionHolder) UI.getCurrent().getData();
	// buildMainLayout();
	// }
	//
	// public QuestionTypeChoiceLayout(SurveyQuestion surveyQuestion) {
	// this();
	// this.surveyQuestion = surveyQuestion;
	// }

	// private QuestionTypeChoiceLayout(SessionHolder sessionHolder) {
	// this.sessionHolder = sessionHolder;
	// surveyQuestionAnswerChoiceService =
	// ContextProvider.getBean(ISurveyQuestionAnswerChoiceService.class);
	// buildMainLayout();
	// }

	public QuestionTypeChoiceLayout(SessionHolder sessionHolder, SurveyQuestion surveyQuestion,
			SurveyResponse surveyResponse) {
		this.sessionHolder = sessionHolder;
		this.surveyQuestion = surveyQuestion;
		this.surveyResponse = surveyResponse;
		surveyQuestionAnswerChoiceService = ContextProvider.getBean(ISurveyQuestionAnswerChoiceService.class);
		buildMainLayout();
	}

	private void buildMainLayout() {
		rowContainerLayout = new VerticalLayout();
		rowContainerLayout.setMargin(false);
		rowContainerLayout.setWidth("100%");
		addComponent(rowContainerLayout);
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
		List<Long> choiceIds = new ArrayList<Long>();
		if (surveyResponse != null) {
			ISurveyResponseAnswerService surveyResponseAnswerService = ContextProvider
					.getBean(ISurveyResponseAnswerService.class);
			List<SurveyResponseAnswer> surveyResponseAnswers = surveyResponseAnswerService
					.getSurveyResponseAnswers(surveyResponse.getId(), surveyQuestion.getId());
			if (surveyResponseAnswers != null) {
				choiceIds = surveyResponseAnswers.stream().map(p -> p.getSurveyQuestionAnswerChoiceRow().getId())
						.collect(Collectors.toList());
			}
		}
		List<SurveyQuestionAnswerChoice> surveyQuestionAnswerChoices = surveyQuestionAnswerChoiceService
				.findBySurveyQuestion(surveyQuestion.getId());
		if (surveyQuestionAnswerChoices != null && !surveyQuestionAnswerChoices.isEmpty()) {
			for (SurveyQuestionAnswerChoice surveyQuestionAnswerChoice : surveyQuestionAnswerChoices) {
				boolean selected = choiceIds.contains(surveyQuestionAnswerChoice.getId());
				createRow(surveyQuestionAnswerChoice, selected);
			}
		}
	}

	private void createRow(SurveyQuestionAnswerChoice surveyQuestionAnswerChoice, boolean selected)
			throws FrameworkException {
		HorizontalLayout rowLayout = new HorizontalLayout();
		rowLayout.setWidth("-1px");

		CCheckBox checkBox = new CCheckBox();
		checkBox.setValue(selected);
		checkBox.setCaption(surveyQuestionAnswerChoice.getLabel());
		rowLayout.addComponent(checkBox);
		rowLayout.setComponentAlignment(checkBox, Alignment.MIDDLE_LEFT);

		if (surveyQuestionAnswerChoice.getIsOther() != null && surveyQuestionAnswerChoice.getIsOther()) {
			CTextField otherFld = new CTextField();
			otherFld.setWidth("400px");
			rowLayout.addComponent(otherFld);
			otherFld.setEnabled(false);
		}

		if (previewMode) {
			checkBox.setEnabled(false);
		}

		rowContainerLayout.addComponent(rowLayout);
	}

	public void setPreviewMode() {
		previewMode = true;
		setUpFields();
	}

}
