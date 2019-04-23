package software.simple.solutions.data.entry.es.control.web.view.question;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import software.simple.solutions.data.entry.es.control.constants.EsControlStyle;
import software.simple.solutions.data.entry.es.control.constants.QuestionType;
import software.simple.solutions.data.entry.es.control.entities.SurveyQuestion;
import software.simple.solutions.data.entry.es.control.properties.SurveyQuestionProperty;
import software.simple.solutions.data.entry.es.control.service.ISurveyQuestionService;
import software.simple.solutions.data.entry.es.control.web.view.question.preview.QuestionTypeChoiceLayout;
import software.simple.solutions.data.entry.es.control.web.view.question.preview.QuestionTypeMatrixLayout;
import software.simple.solutions.data.entry.es.control.web.view.question.preview.QuestionTypeSingleLayout;
import software.simple.solutions.framework.core.components.CaptionLabel;
import software.simple.solutions.framework.core.components.SessionHolder;
import software.simple.solutions.framework.core.constants.Style;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.util.ContextProvider;
import software.simple.solutions.framework.core.util.PropertyResolver;

public class QuestionPreviewLayout extends VerticalLayout {

	private static final Logger logger = LogManager.getLogger(QuestionPreviewLayout.class);

	private CaptionLabel groupFld;
	private CaptionLabel questionFld;
	private CaptionLabel questionDescriptionFld;
	private Label infoFld;

	private SurveyQuestion surveyQuestion;
	private SessionHolder sessionHolder;

	@Override
	public void detach() {
		super.detach();
	}

	public QuestionPreviewLayout() {
		sessionHolder = (SessionHolder) UI.getCurrent().getData();
	}

	public void setSurveyQuestion(SurveyQuestion surveyQuestion) {
		this.surveyQuestion = surveyQuestion;
		setUpLayout();
	}

	public void setUpLayout() {
		setSpacing(false);
		removeAllComponents();

		infoFld = new Label();
		infoFld.setWidth("100%");
		infoFld.setValue(
				PropertyResolver.getPropertyValueByLocale(SurveyQuestionProperty.QUESTION_SAVE_BEFORE_PREVIEW));
		infoFld.addStyleName(ValoTheme.LABEL_COLORED);
		infoFld.addStyleName(ValoTheme.LABEL_H4);
		addComponent(infoFld);

		Label hrFld = new Label("<hr>");
		hrFld.setWidth("100%");
		hrFld.setContentMode(ContentMode.HTML);
		addComponent(hrFld);

		groupFld = new CaptionLabel();
		groupFld.setVisible(false);
		groupFld.setWidth("100%");
		groupFld.addStyleName(Style.WORD_WRAP);
		groupFld.addStyleName(ValoTheme.LABEL_LIGHT);
		groupFld.addStyleName(ValoTheme.LABEL_H3);
		groupFld.addStyleName(EsControlStyle.GROUP_LABEL);
		addComponent(groupFld);

		questionFld = new CaptionLabel();
		questionFld.addStyleName(Style.WORD_WRAP);
		addComponent(questionFld);

		questionDescriptionFld = new CaptionLabel();
		questionDescriptionFld.setVisible(false);
		questionDescriptionFld.addStyleName(Style.WORD_WRAP);
		questionDescriptionFld.addStyleName(ValoTheme.LABEL_COLORED);
		questionDescriptionFld.addStyleName(ValoTheme.LABEL_SMALL);
		questionDescriptionFld.addStyleName(ValoTheme.LABEL_LIGHT);
		addComponent(questionDescriptionFld);

		setValue();

		if (surveyQuestion != null) {
			String questionType = surveyQuestion.getQuestionType();
			switch (questionType) {
			case QuestionType.SINGLE:
				QuestionTypeSingleLayout questionTypeSingleLayout = new QuestionTypeSingleLayout(surveyQuestion);
				questionTypeSingleLayout.setPreviewMode();
				addComponent(questionTypeSingleLayout);
				break;
			case QuestionType.CHOICES:
				QuestionTypeChoiceLayout questionTypeChoicesLayout = new QuestionTypeChoiceLayout(surveyQuestion);
				questionTypeChoicesLayout.setPreviewMode();
				addComponent(questionTypeChoicesLayout);
				break;
			case QuestionType.MATRIX:
				QuestionTypeMatrixLayout questionTypeMatrixLayout = new QuestionTypeMatrixLayout(surveyQuestion);
				questionTypeMatrixLayout.setPreviewMode();
				addComponent(questionTypeMatrixLayout);
				break;
			}
		}
	}

	public void setValue() {
		if (surveyQuestion != null) {
			questionFld.setValue(surveyQuestion.getOrder() + ". " + surveyQuestion.getQuestion());

			if (surveyQuestion.getSurveyGroup() != null) {
				groupFld.setVisible(true);
				groupFld.setValue(surveyQuestion.getSurveyGroup().getName());
			}

			if (StringUtils.isNotBlank(surveyQuestion.getQuestionDescription())) {
				questionDescriptionFld.setVisible(true);
				questionDescriptionFld.setValue(surveyQuestion.getQuestionDescription());
			}
		}
	}

	public void reset() throws FrameworkException {
		ISurveyQuestionService surveyQuestionService = ContextProvider.getBean(ISurveyQuestionService.class);
		surveyQuestion = surveyQuestionService.getById(SurveyQuestion.class, surveyQuestion.getId());
		removeAllComponents();
		setUpLayout();
		setValue();
	}

}
