package software.simple.solutions.data.entry.es.control.web.view.question;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import software.simple.solutions.data.entry.es.control.constants.EsControlStyle;
import software.simple.solutions.data.entry.es.control.entities.SurveyQuestion;
import software.simple.solutions.data.entry.es.control.entities.SurveySection;
import software.simple.solutions.data.entry.es.control.properties.SurveyQuestionProperty;
import software.simple.solutions.data.entry.es.control.service.ISurveyQuestionService;
import software.simple.solutions.framework.core.components.CaptionLabel;
import software.simple.solutions.framework.core.components.SessionHolder;
import software.simple.solutions.framework.core.constants.Style;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.util.ContextProvider;
import software.simple.solutions.framework.core.util.PropertyResolver;

public class QuestionSectionPreviewLayout extends VerticalLayout {

	private static final Logger logger = LogManager.getLogger(QuestionSectionPreviewLayout.class);

	private CaptionLabel sectionFld;
	private Label infoFld;
	// private SurveyQuestion surveyQuestion;
	private SurveySection surveySection;
	private SessionHolder sessionHolder;

	@Override
	public void detach() {
		super.detach();
	}

	public QuestionSectionPreviewLayout() {
		sessionHolder = (SessionHolder) UI.getCurrent().getData();
	}

//	public void setSurveyQuestion(SurveySection surveySection) {
//		this.surveySection = surveySection;
//		try {
//			setUpLayout();
//		} catch (FrameworkException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	public void setUpLayout() throws FrameworkException {
		setSpacing(false);
		removeAllComponents();

		infoFld = new Label();
		infoFld.setWidth("100%");
		infoFld.setValue(
				PropertyResolver.getPropertyValueByLocale(SurveyQuestionProperty.QUESTION_SAVE_BEFORE_PREVIEW));
		infoFld.addStyleName(ValoTheme.LABEL_COLORED);
		infoFld.addStyleName(ValoTheme.LABEL_H4);
		addComponent(infoFld);

		sectionFld = new CaptionLabel();
		sectionFld.setWidth("100%");
		sectionFld.addStyleName(Style.WORD_WRAP);
		sectionFld.addStyleName(ValoTheme.LABEL_LIGHT);
		sectionFld.addStyleName(ValoTheme.LABEL_H3);
		sectionFld.addStyleName(EsControlStyle.SECTION_LABEL);
		addComponent(sectionFld);

		// SurveySection surveySection = surveyQuestion.getSurveySection();
		sectionFld.setValue(surveySection.getName());

		ISurveyQuestionService surveyQuestionService = ContextProvider.getBean(ISurveyQuestionService.class);
		List<SurveyQuestion> questionList = surveyQuestionService.getQuestionList(surveySection.getSurvey().getId(),
				null, surveySection.getId());
		for (SurveyQuestion question : questionList) {
			QuestionPreviewLayout questionPreviewLayout = new QuestionPreviewLayout();
			questionPreviewLayout.setShowInfo(false);
			questionPreviewLayout.setShowSection(false);
			questionPreviewLayout.setSurveyQuestion(question);
			addComponent(questionPreviewLayout);
		}
	}

	public void reset() throws FrameworkException {
		// ISurveyQuestionService surveyQuestionService =
		// ContextProvider.getBean(ISurveyQuestionService.class);
		// surveyQuestion = surveyQuestionService.getById(SurveyQuestion.class,
		// surveyQuestion.getId());
		removeAllComponents();
		setUpLayout();
	}

	public SurveySection getSurveySection() {
		return surveySection;
	}

	public void setSurveySection(SurveySection surveySection) {
		this.surveySection = surveySection;
		try {
			setUpLayout();
		} catch (FrameworkException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
