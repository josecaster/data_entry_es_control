package software.simple.solutions.data.entry.es.control.web.view.question;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import io.reactivex.subjects.BehaviorSubject;
import software.simple.solutions.data.entry.es.control.components.QuestionTypeSelect;
import software.simple.solutions.data.entry.es.control.constants.QuestionType;
import software.simple.solutions.data.entry.es.control.entities.Survey;
import software.simple.solutions.data.entry.es.control.entities.SurveyGroup;
import software.simple.solutions.data.entry.es.control.entities.SurveyQuestion;
import software.simple.solutions.data.entry.es.control.entities.SurveySection;
import software.simple.solutions.data.entry.es.control.properties.SurveyQuestionProperty;
import software.simple.solutions.data.entry.es.control.service.ISurveyQuestionService;
import software.simple.solutions.data.entry.es.control.service.ISurveySectionService;
import software.simple.solutions.data.entry.es.control.valueobjects.SurveyQuestionVO;
import software.simple.solutions.data.entry.es.control.web.view.question.type.QuestionTypeChoicesResponseLayout;
import software.simple.solutions.data.entry.es.control.web.view.question.type.QuestionTypeMatrixResponseLayout;
import software.simple.solutions.data.entry.es.control.web.view.question.type.QuestionTypeSingleResponseLayout;
import software.simple.solutions.framework.core.components.CButton;
import software.simple.solutions.framework.core.components.CTextArea;
import software.simple.solutions.framework.core.components.ConfirmWindow;
import software.simple.solutions.framework.core.components.ConfirmWindow.ConfirmationHandler;
import software.simple.solutions.framework.core.components.MessageWindowHandler;
import software.simple.solutions.framework.core.components.NotificationWindow;
import software.simple.solutions.framework.core.components.SessionHolder;
import software.simple.solutions.framework.core.constants.Style;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.pojo.ComboItem;
import software.simple.solutions.framework.core.properties.SystemProperty;
import software.simple.solutions.framework.core.util.ContextProvider;

public class QuestionDetailsLayout extends VerticalLayout {

	private static final Logger logger = LogManager.getLogger(QuestionDetailsLayout.class);

	private HorizontalLayout questionFieldLayout;
	private CTextArea questionFld;

	private QuestionTypeSelect questionTypeFld;

	private VerticalLayout containerLayout;
	private HorizontalLayout actionLayout;
	private VerticalLayout v1;
	private CButton deleteBtn;
	private CButton submitBtn;
	private Label sectionLbl;

	private final BehaviorSubject<SurveyQuestion> observer;
	private final BehaviorSubject<Boolean> deletedObserver;

	private Survey survey;
	private SurveyQuestion surveyQuestion;
	private SessionHolder sessionHolder;

	private BehaviorSubject<SurveyGroup> surveyGroupObserver;

	@Override
	public void detach() {
		observer.onComplete();
		super.detach();
	}

	public QuestionDetailsLayout(SessionHolder sessionHolder) {
		this.sessionHolder = sessionHolder;
		observer = BehaviorSubject.create();
		deletedObserver = BehaviorSubject.create();
	}

	public void build() {
		sectionLbl = new Label();
		sectionLbl.setVisible(false);
		sectionLbl.addStyleName(ValoTheme.LABEL_COLORED);
		sectionLbl.addStyleName(ValoTheme.LABEL_H4);
		addComponent(sectionLbl);

		questionFieldLayout = new HorizontalLayout();
		questionFieldLayout.setWidth("100%");
		questionFieldLayout.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		addComponent(questionFieldLayout);

		questionFld = new CTextArea();
		questionFld.setWidth("100%");
		questionFld.setRows(1);
		questionFld.setHeight("55px");
		questionFld.setCaptionByKey(SurveyQuestionProperty.QUESTION);
		questionFieldLayout.addComponent(questionFld);
		questionFieldLayout.setExpandRatio(questionFld, 1);

		v1 = new VerticalLayout();
		v1.setMargin(false);
		v1.setWidth("200px");
		v1.setVisible(false);
		questionFieldLayout.addComponent(v1);

		containerLayout = new VerticalLayout();
		containerLayout.setVisible(false);
		containerLayout.addStyleName(ValoTheme.LAYOUT_WELL);
		addComponent(containerLayout);

		questionTypeFld = new QuestionTypeSelect();
		questionTypeFld.setWidth("200px");
		questionTypeFld.setVisible(false);
		questionTypeFld.setEmptySelectionAllowed(false);
		questionTypeFld.setCaptionByKey(SurveyQuestionProperty.QUESTION_TYPE);
		v1.addComponent(questionTypeFld);
		questionTypeFld.setEmptySelectionAllowed(false);

		questionTypeFld.addValueChangeListener(new QuestionTypeSelectValueChangeListener());

		actionLayout = new HorizontalLayout();
		actionLayout.setWidth("-1px");
		addComponent(actionLayout);

		submitBtn = new CButton();
		submitBtn.setCaptionByKey(SystemProperty.SYSTEM_BUTTON_SUBMIT);
		submitBtn.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		actionLayout.addComponent(submitBtn);

		submitBtn.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					handleSaveOrUpdate();
					NotificationWindow.notificationNormalWindow(SystemProperty.UPDATE_SUCCESSFULL);
					observer.onNext(surveyQuestion);
				} catch (FrameworkException e) {
					logger.error(e.getMessage(), e);
					new MessageWindowHandler(e);
				}
			}
		});

		deleteBtn = new CButton();
		deleteBtn.setVisible(false);
		deleteBtn.setCaptionByKey(SystemProperty.SYSTEM_BUTTON_DELETE);
		deleteBtn.addStyleName(ValoTheme.BUTTON_QUIET);
		deleteBtn.addStyleName(ValoTheme.BUTTON_DANGER);
		deleteBtn.addStyleName(Style.BUTTON_DANGEROUS);
		actionLayout.addComponent(deleteBtn);
		deleteBtn.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				ConfirmWindow confirmWindow = new ConfirmWindow(SystemProperty.DELETE_HEADER,
						SystemProperty.DELETE_CONFIRMATION_REQUEST, SystemProperty.CONFIRM, SystemProperty.CANCEL);
				confirmWindow.execute(new ConfirmationHandler() {

					@Override
					public void handlePositive() {
						try {
							ISurveyQuestionService surveyQuestionService = ContextProvider
									.getBean(ISurveyQuestionService.class);
							surveyQuestionService.delete(SurveyQuestion.class, surveyQuestion.getId());
							NotificationWindow.notificationNormalWindow(SystemProperty.UPDATE_SUCCESSFULL);
							deletedObserver.onNext(true);
						} catch (FrameworkException e) {
							logger.error(e.getMessage(), e);
							new MessageWindowHandler(e);
						}
					}

					@Override
					public void handleNegative() {
						return;
					}
				});

			}
		});

	}

	protected void handleSaveOrUpdate() throws FrameworkException {
		SurveyQuestionVO vo = new SurveyQuestionVO();
		vo.setQuestion(questionFld.getValue());
		vo.setQuestionType(questionTypeFld.getStringValue());
		vo.setSurveyId(survey == null ? null : survey.getId());
		vo.setNew(surveyQuestion == null);
		vo.setId(surveyQuestion == null ? null : surveyQuestion.getId());

		ISurveyQuestionService surveyQuestionService = ContextProvider.getBean(ISurveyQuestionService.class);
		surveyQuestion = surveyQuestionService.updateSingle(vo);
	}

	public BehaviorSubject<SurveyQuestion> getObserver() {
		return observer;
	}

	public BehaviorSubject<Boolean> getDeletedObserver() {
		return deletedObserver;
	}

	public void setSurveyQuestion(SurveyQuestion surveyQuestion) {
		this.surveyQuestion = surveyQuestion;
		setSurvey(surveyQuestion.getSurvey());
		setValue();
	}

	public void setSurvey(Survey survey) {
		this.survey = survey;
		try {
			ISurveySectionService surveyQuestionSectionService = ContextProvider.getBean(ISurveySectionService.class);
			SurveySection surveySection = surveyQuestionSectionService.getPinnedSectionBySurvey(survey.getId());
			setSurveySection(surveySection);
		} catch (FrameworkException e) {
			new MessageWindowHandler(e);
		}
	}

	private void setValue() {
		v1.setVisible(true);
		deleteBtn.setVisible(true);
		questionTypeFld.setVisible(true);
		questionFld.setValue(surveyQuestion.getQuestion());
		questionTypeFld.setValue(surveyQuestion.getQuestionType());

		SurveySection surveySection = surveyQuestion.getSurveySection();
		setSurveySection(surveySection);
	}

	public void setSurveySection(SurveySection surveySection) {
		sectionLbl.setVisible(false);
		if (surveySection != null) {
			sectionLbl.setValue(surveySection.getName());
			sectionLbl.setVisible(true);
		}
	}

	private class QuestionTypeSelectValueChangeListener implements ValueChangeListener<ComboItem> {

		@Override
		public void valueChange(ValueChangeEvent<ComboItem> event) {
			containerLayout.removeAllComponents();
			containerLayout.setVisible(false);

			ComboItem comboItem = event.getValue();
			String id = comboItem.getId();
			switch (id) {
			case QuestionType.SINGLE:
			case QuestionType.DATE:
			case QuestionType.LENGTH_FT_INCH:
			case QuestionType.AREA_FT_INCH:
				createSingleLayout();
				break;
			case QuestionType.CHOICES:
				createChoicesLayout();
				break;
			case QuestionType.MATRIX:
				createMatrixLayout();
				break;
			}

		}

		private void createSingleLayout() {
			if (surveyQuestion != null) {
				QuestionTypeSingleResponseLayout questionTypeSingleResponseLayout = new QuestionTypeSingleResponseLayout(
						surveyQuestion);
				containerLayout.setVisible(true);
				containerLayout.addComponent(questionTypeSingleResponseLayout);
				questionTypeSingleResponseLayout.setSurveyGroupObserver(surveyGroupObserver);
			}
		}

		private void createChoicesLayout() {
			QuestionTypeChoicesResponseLayout questionTypeChoicesResponseLayout = new QuestionTypeChoicesResponseLayout(
					surveyQuestion);
			containerLayout.setVisible(true);
			containerLayout.addComponent(questionTypeChoicesResponseLayout);
		}

		private void createMatrixLayout() {
			QuestionTypeMatrixResponseLayout questionTypeMatrixResponseLayout = new QuestionTypeMatrixResponseLayout(
					sessionHolder, surveyQuestion);
			containerLayout.setVisible(true);
			containerLayout.addComponent(questionTypeMatrixResponseLayout);
		}

	}

	public void setSurveyGroupObserver(BehaviorSubject<SurveyGroup> surveyGroupObserver) {
		this.surveyGroupObserver = surveyGroupObserver;
	}

	public void doForNew() {
		questionTypeFld.setValue(QuestionType.SINGLE);
	}

}
