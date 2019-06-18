package software.simple.solutions.data.entry.es.control.web.view.question;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import software.simple.solutions.data.entry.es.control.constants.EsControlStyle;
import software.simple.solutions.data.entry.es.control.constants.QuestionType;
import software.simple.solutions.data.entry.es.control.entities.SurveyQuestion;
import software.simple.solutions.data.entry.es.control.entities.SurveyResponse;
import software.simple.solutions.data.entry.es.control.entities.SurveyResponseAnswerHistory;
import software.simple.solutions.data.entry.es.control.properties.SurveyQuestionProperty;
import software.simple.solutions.data.entry.es.control.service.facade.SurveyQuestionServiceFacade;
import software.simple.solutions.data.entry.es.control.service.facade.SurveyResponseAnswerHistoryServiceFacade;
import software.simple.solutions.data.entry.es.control.web.view.SurveyResponseHistoryPreviewGenerator;
import software.simple.solutions.data.entry.es.control.web.view.question.preview.QuestionTypeAreaFeetInchLayout;
import software.simple.solutions.data.entry.es.control.web.view.question.preview.QuestionTypeChoiceLayout;
import software.simple.solutions.data.entry.es.control.web.view.question.preview.QuestionTypeDateLayout;
import software.simple.solutions.data.entry.es.control.web.view.question.preview.QuestionTypeLengthFeetInchLayout;
import software.simple.solutions.data.entry.es.control.web.view.question.preview.QuestionTypeMatrixLayout;
import software.simple.solutions.data.entry.es.control.web.view.question.preview.QuestionTypeSingleLayout;
import software.simple.solutions.framework.core.components.CButton;
import software.simple.solutions.framework.core.components.CaptionLabel;
import software.simple.solutions.framework.core.components.SessionHolder;
import software.simple.solutions.framework.core.constants.Style;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.pojo.RevisionPojo;
import software.simple.solutions.framework.core.util.PropertyResolver;
import software.simple.solutions.framework.core.web.view.audit.HistoryView;
import software.simple.solutions.framework.core.web.view.audit.RevisionViewGenerator;

public class QuestionPreviewLayout extends VerticalLayout {

	private final class HistoryBtnClick implements ClickListener {

		private Class<?> cl;
		private Long id;

		public HistoryBtnClick(Class<?> cl, Long id) {
			this.cl = cl;
			this.id = id;
		}

		@Override
		public void buttonClick(ClickEvent event) {
			try {
				SurveyResponseAnswerHistory surveyResponseAnswerHistory = SurveyResponseAnswerHistoryServiceFacade
						.get(UI.getCurrent()).getSurveyResponseAnswerHistoryByResponseAndQuestion(
								surveyResponse == null ? null : surveyResponse.getId(),
								surveyQuestion == null ? null : surveyQuestion.getId());
				HistoryView historyView = new HistoryView(cl,
						surveyResponseAnswerHistory == null ? null : surveyResponseAnswerHistory.getId());
				historyView.setRevisionViewGenerator(new RevisionViewGenerator() {

					@Override
					public Component getRevisionView(RevisionPojo revisionPojo) {
						SurveyResponseAnswerHistory surveyResponseAnswerHistory = (SurveyResponseAnswerHistory) revisionPojo
								.getEntity();
						SurveyResponseHistoryPreviewGenerator surveyResponseHistoryPreviewGenerator = new SurveyResponseHistoryPreviewGenerator(
								sessionHolder, surveyQuestion, surveyResponse, surveyResponseAnswerHistory);
						return surveyResponseHistoryPreviewGenerator.getQuestionPreviewLayout();
					}
				});
			} catch (FrameworkException e) {
				e.printStackTrace();
			}
		}
	}

	private static final Logger logger = LogManager.getLogger(QuestionPreviewLayout.class);

	private CaptionLabel sectionFld;
	private Label infoFld;
	private CaptionLabel questionFld;
	private CaptionLabel questionDescriptionFld;
	private boolean showSection = true;
	private boolean showInfo = true;
	private boolean editable = false;
	private boolean auditView = false;
	private SessionHolder sessionHolder;

	private SurveyQuestion surveyQuestion;
	private SurveyResponse surveyResponse;
	private SurveyResponseAnswerHistory surveyResponseAnswerHistory;

	@Override
	public void detach() {
		super.detach();
	}

	public QuestionPreviewLayout(SessionHolder sessionHolder) {
		this.sessionHolder = sessionHolder;
	}

	public void setPreviewData(SurveyQuestion surveyQuestion) {
		setPreviewData(surveyQuestion, null);
	}

	public void setPreviewData(SurveyQuestion surveyQuestion, SurveyResponse surveyResponse) {
		setPreviewData(surveyQuestion, surveyResponse, null);
	}

	public void setPreviewData(SurveyQuestion surveyQuestion, SurveyResponse surveyResponse,
			SurveyResponseAnswerHistory surveyResponseAnswerHistory) {

		this.surveyQuestion = surveyQuestion;
		this.surveyResponse = surveyResponse;
		this.surveyResponseAnswerHistory = surveyResponseAnswerHistory;
		setUpLayout();
	}

	public void setUpLayout() {
		setSpacing(false);
		removeAllComponents();

		infoFld = new Label();
		infoFld.setWidth("100%");
		infoFld.setValue(PropertyResolver.getPropertyValueByLocale(SurveyQuestionProperty.QUESTION_SAVE_BEFORE_PREVIEW,
				sessionHolder.getLocale()));
		infoFld.addStyleName(ValoTheme.LABEL_COLORED);
		infoFld.addStyleName(ValoTheme.LABEL_H4);
		addComponent(infoFld);
		if (!showInfo) {
			infoFld.setVisible(false);
		}

		Label hrFld = new Label("<hr>");
		hrFld.setWidth("100%");
		hrFld.setContentMode(ContentMode.HTML);
		addComponent(hrFld);

		sectionFld = new CaptionLabel();
		sectionFld.setVisible(false);
		sectionFld.setWidth("100%");
		sectionFld.addStyleName(Style.WORD_WRAP);
		sectionFld.addStyleName(ValoTheme.LABEL_LIGHT);
		sectionFld.addStyleName(ValoTheme.LABEL_H3);
		sectionFld.addStyleName(EsControlStyle.SECTION_LABEL);
		addComponent(sectionFld);
		if (!showSection) {
			sectionFld.setVisible(false);
		}

		HorizontalLayout questionHeaderLayout = new HorizontalLayout();
		questionHeaderLayout.setMargin(false);
		addComponent(questionHeaderLayout);
		questionFld = new CaptionLabel();
		questionFld.addStyleName(Style.WORD_WRAP);
		questionHeaderLayout.addComponent(questionFld);
		if (surveyResponse != null && !auditView) {
			CButton historyBtn = new CButton();
			historyBtn.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
			historyBtn.setIcon(VaadinIcons.TIME_BACKWARD);
			questionHeaderLayout.addComponent(historyBtn);
			historyBtn.addClickListener(new HistoryBtnClick(SurveyResponseAnswerHistory.class, surveyQuestion.getId()));
		}

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
				QuestionTypeSingleLayout questionTypeSingleLayout = new QuestionTypeSingleLayout(sessionHolder,
						surveyQuestion, surveyResponse, surveyResponseAnswerHistory);
				questionTypeSingleLayout.setEditable(editable);
				addComponent(questionTypeSingleLayout);
				break;
			case QuestionType.DATE:
				QuestionTypeDateLayout questionTypeDateLayout = new QuestionTypeDateLayout(sessionHolder,
						surveyQuestion, surveyResponse, surveyResponseAnswerHistory);
				questionTypeDateLayout.setEditable(editable);
				addComponent(questionTypeDateLayout);
				break;
			case QuestionType.AREA_FT_INCH:
				QuestionTypeAreaFeetInchLayout questionTypeAreaFtInchLayout = new QuestionTypeAreaFeetInchLayout(
						sessionHolder, surveyQuestion, surveyResponse, surveyResponseAnswerHistory);
				questionTypeAreaFtInchLayout.setEditable(editable);
				addComponent(questionTypeAreaFtInchLayout);
				break;
			case QuestionType.LENGTH_FT_INCH:
				QuestionTypeLengthFeetInchLayout questionTypeLengthFeetInchLayout = new QuestionTypeLengthFeetInchLayout(
						sessionHolder, surveyQuestion, surveyResponse, surveyResponseAnswerHistory);
				questionTypeLengthFeetInchLayout.setEditable(editable);
				addComponent(questionTypeLengthFeetInchLayout);
				break;
			case QuestionType.CHOICES:
				QuestionTypeChoiceLayout questionTypeChoicesLayout = new QuestionTypeChoiceLayout(sessionHolder,
						surveyQuestion, surveyResponse, surveyResponseAnswerHistory);
				questionTypeChoicesLayout.setEditable(editable);
				questionTypeChoicesLayout.build();
				addComponent(questionTypeChoicesLayout);
				break;
			case QuestionType.MATRIX:
				QuestionTypeMatrixLayout questionTypeMatrixLayout = new QuestionTypeMatrixLayout(sessionHolder,
						surveyQuestion, surveyResponse, surveyResponseAnswerHistory);
				questionTypeMatrixLayout.setEditable(editable);
				questionTypeMatrixLayout.build();
				addComponent(questionTypeMatrixLayout);
				break;
			}
		}
	}

	public void setValue() {
		if (surveyQuestion != null) {
			questionFld.setValue(surveyQuestion.getOrder() + ". " + surveyQuestion.getQuestion());

			if (surveyQuestion.getSurveySection() != null) {
				// sectionFld.setVisible(true);
				sectionFld.setValue(surveyQuestion.getSurveySection().getName());
			}

			if (StringUtils.isNotBlank(surveyQuestion.getQuestionDescription())) {
				questionDescriptionFld.setVisible(true);
				questionDescriptionFld.setValue(surveyQuestion.getQuestionDescription());
			}
		}
	}

	public void reset() throws FrameworkException {
		surveyQuestion = SurveyQuestionServiceFacade.get(UI.getCurrent()).getById(SurveyQuestion.class,
				surveyQuestion.getId());
		removeAllComponents();
		setUpLayout();
		setValue();
	}

	public boolean isShowSection() {
		return showSection;
	}

	public void setShowSection(boolean showSection) {
		this.showSection = showSection;
	}

	public boolean isShowInfo() {
		return showInfo;
	}

	public void setShowInfo(boolean showInfo) {
		this.showInfo = showInfo;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public boolean isAuditView() {
		return auditView;
	}

	public void setAuditView(boolean auditView) {
		this.auditView = auditView;
	}

}
