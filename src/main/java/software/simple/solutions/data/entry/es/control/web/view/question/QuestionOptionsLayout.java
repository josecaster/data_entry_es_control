package software.simple.solutions.data.entry.es.control.web.view.question;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import io.reactivex.subjects.BehaviorSubject;
import software.simple.solutions.data.entry.es.control.components.lookup.SurveyGroupLookUpField;
import software.simple.solutions.data.entry.es.control.entities.Survey;
import software.simple.solutions.data.entry.es.control.entities.SurveyGroup;
import software.simple.solutions.data.entry.es.control.entities.SurveyQuestion;
import software.simple.solutions.data.entry.es.control.properties.SurveyGroupProperty;
import software.simple.solutions.data.entry.es.control.properties.SurveyQuestionProperty;
import software.simple.solutions.data.entry.es.control.service.ISurveyQuestionService;
import software.simple.solutions.framework.core.components.CCheckBox;
import software.simple.solutions.framework.core.components.CTextArea;
import software.simple.solutions.framework.core.components.MessageWindowHandler;
import software.simple.solutions.framework.core.components.SessionHolder;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.util.ContextProvider;

public class QuestionOptionsLayout extends VerticalLayout {

	private static final Logger logger = LogManager.getLogger(QuestionOptionsLayout.class);

	private CTextArea questionDescriptionFld;
	private CCheckBox requiredFld;
	private CTextArea requiredErrorFld;
	private SurveyGroupLookUpField groupFld;
	// private CButton saveBtn;
	private SurveyQuestion surveyQuestion;
	private final BehaviorSubject<SurveyQuestion> observer;
	private final BehaviorSubject<Boolean> optionsUpdatedObserver;
	private final BehaviorSubject<SurveyGroup> groupUpdatedObserver;

	private Survey survey;
	private SessionHolder sessionHolder;

	@Override
	public void detach() {
		observer.onComplete();
		super.detach();
	}

	public QuestionOptionsLayout() {
		sessionHolder = (SessionHolder) UI.getCurrent().getData();
		observer = BehaviorSubject.create();
		optionsUpdatedObserver = BehaviorSubject.create();
		groupUpdatedObserver = BehaviorSubject.create();

		createMainLayout();
	}

	public void createMainLayout() {

		questionDescriptionFld = new CTextArea();
		questionDescriptionFld.setValueChangeMode(ValueChangeMode.BLUR);
		questionDescriptionFld.setWidth("100%");
		questionDescriptionFld.setRows(1);
		questionDescriptionFld.setCaptionByKey(SurveyQuestionProperty.QUESTION_DESCRIPTION);
		addComponent(questionDescriptionFld);

		requiredFld = new CCheckBox();
		requiredFld.setCaptionByKey(SurveyQuestionProperty.QUESTION_IS_REQUIRED);
		addComponent(requiredFld);

		requiredErrorFld = new CTextArea();
		requiredErrorFld.setValueChangeMode(ValueChangeMode.BLUR);
		requiredErrorFld.setRows(1);
		requiredErrorFld.setVisible(false);
		requiredErrorFld.setWidth("500px");
		requiredErrorFld.setCaptionByKey(SurveyQuestionProperty.QUESTION_IS_REQUIRED_MESSAGE);
		addComponent(requiredErrorFld);

		groupFld = new SurveyGroupLookUpField();
		groupFld.setWidth("300px");
		groupFld.setCaptionByKey(SurveyGroupProperty.SURVEY_GROUP);
		addComponent(groupFld);

		// saveBtn = new CButton();
		// saveBtn.setCaptionByKey(SystemProperty.SYSTEM_BUTTON_SUBMIT);
		// saveBtn.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		// addComponent(saveBtn);
		// saveBtn.addClickListener(new ClickListener() {
		//
		// @Override
		// public void buttonClick(ClickEvent event) {
		// try {
		// handleSaveOrUpdate();
		// observer.onNext(surveyQuestion);
		// } catch (FrameworkException e) {
		// logger.error(e.getMessage(), e);
		// new MessageWindowHandler(e);
		// }
		// }
		// });
	}

	// protected void handleSaveOrUpdate() throws FrameworkException {
	// SurveyQuestionVO vo = new SurveyQuestionVO();
	// vo.setRequired(requiredFld.getValue());
	// if (requiredFld.getValue()) {
	// vo.setRequiredMessage(requiredErrorFld.getValue());
	// }
	// vo.setSurveyId(survey == null ? null : survey.getId());
	// vo.setId(surveyQuestion == null ? null : surveyQuestion.getId());
	// vo.setNew(surveyQuestion == null);
	// vo.setUpdatedBy(sessionHolder.getApplicationUser().getId());
	//
	// ISurveyQuestionService surveyQuestionService =
	// ContextProvider.getBean(ISurveyQuestionService.class);
	// surveyQuestion = surveyQuestionService.updateOptions(vo);
	// }

	public BehaviorSubject<SurveyQuestion> getObserver() {
		return observer;
	}

	public void setSurvey(Survey survey) {
		this.survey = survey;
	}

	public void setSurveyQuestion(SurveyQuestion surveyQuestion) {
		this.surveyQuestion = surveyQuestion;
		setSurvey(surveyQuestion.getSurvey());
		setValue();
	}

	private void setValue() {
		questionDescriptionFld.setValue(surveyQuestion.getQuestionDescription());
		questionDescriptionFld.addValueChangeListener(new ValueChangeListener<String>() {

			@Override
			public void valueChange(ValueChangeEvent<String> event) {
				try {
					ISurveyQuestionService surveyQuestionService = ContextProvider
							.getBean(ISurveyQuestionService.class);
					surveyQuestion = surveyQuestionService.updateDescription(surveyQuestion.getId(),
							questionDescriptionFld.getValue());
					optionsUpdatedObserver.onNext(true);
				} catch (FrameworkException e) {
					new MessageWindowHandler(e);
				}
			}
		});

		requiredFld.setValue(surveyQuestion.getRequired());
		requiredFld.addValueChangeListener(new ValueChangeListener<Boolean>() {

			@Override
			public void valueChange(ValueChangeEvent<Boolean> event) {
				try {
					Boolean value = event.getValue();
					requiredErrorFld.setVisible(value);
					ISurveyQuestionService surveyQuestionService = ContextProvider
							.getBean(ISurveyQuestionService.class);
					surveyQuestion = surveyQuestionService.updateRequired(surveyQuestion.getId(), event.getValue());
				} catch (FrameworkException e) {
					new MessageWindowHandler(e);
				}
			}
		});
		requiredErrorFld.setValue(surveyQuestion.getRequiredError());
		requiredErrorFld.addValueChangeListener(new ValueChangeListener<String>() {

			@Override
			public void valueChange(ValueChangeEvent<String> event) {
				try {
					ISurveyQuestionService surveyQuestionService = ContextProvider
							.getBean(ISurveyQuestionService.class);
					surveyQuestion = surveyQuestionService.updateRequiredError(surveyQuestion.getId(),
							requiredErrorFld.getValue());
				} catch (FrameworkException e) {
					new MessageWindowHandler(e);
				}
			}
		});

		groupFld.setValue(surveyQuestion.getSurveyGroup());
		groupFld.addValueChangeListener(new ValueChangeListener<Object>() {

			@Override
			public void valueChange(ValueChangeEvent<Object> event) {
				SurveyGroup surveyGroup = (SurveyGroup) event.getValue();
				try {
					ISurveyQuestionService surveyQuestionService = ContextProvider
							.getBean(ISurveyQuestionService.class);
					surveyQuestion = surveyQuestionService.updateSurveyQuestionGroup(surveyQuestion.getId(),
							surveyGroup == null ? null : surveyGroup.getId());
					groupUpdatedObserver.onNext(surveyGroup);
					optionsUpdatedObserver.onNext(true);
				} catch (FrameworkException e) {
					new MessageWindowHandler(e);
				}
			}
		});
	}

	public BehaviorSubject<Boolean> getOptionsUpdatedObserver() {
		return optionsUpdatedObserver;
	}

	public BehaviorSubject<SurveyGroup> getGroupUpdatedObserver() {
		return groupUpdatedObserver;
	}

}
