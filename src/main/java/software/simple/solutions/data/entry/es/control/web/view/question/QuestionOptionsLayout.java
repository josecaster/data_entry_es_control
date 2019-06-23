package software.simple.solutions.data.entry.es.control.web.view.question;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fo0.advancedtokenfield.listener.TokenAddListener;
import com.fo0.advancedtokenfield.listener.TokenRemoveListener;
import com.fo0.advancedtokenfield.main.AdvancedTokenField;
import com.fo0.advancedtokenfield.model.Token;
import com.fo0.advancedtokenfield.model.TokenLayout;
import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import io.reactivex.subjects.BehaviorSubject;
import software.simple.solutions.data.entry.es.control.components.lookup.SurveyGroupLookUpField;
import software.simple.solutions.data.entry.es.control.components.lookup.SurveySectionLookUpField;
import software.simple.solutions.data.entry.es.control.entities.Survey;
import software.simple.solutions.data.entry.es.control.entities.SurveyGroup;
import software.simple.solutions.data.entry.es.control.entities.SurveyQuestion;
import software.simple.solutions.data.entry.es.control.entities.SurveySection;
import software.simple.solutions.data.entry.es.control.properties.SurveyGroupProperty;
import software.simple.solutions.data.entry.es.control.properties.SurveyQuestionProperty;
import software.simple.solutions.data.entry.es.control.properties.SurveyQuestionUserProperty;
import software.simple.solutions.data.entry.es.control.properties.SurveySectionProperty;
import software.simple.solutions.data.entry.es.control.service.facade.SurveyApplicationUserServiceFacade;
import software.simple.solutions.data.entry.es.control.service.facade.SurveyQuestionServiceFacade;
import software.simple.solutions.data.entry.es.control.service.facade.SurveyQuestionUserServiceFacade;
import software.simple.solutions.framework.core.components.CCheckBox;
import software.simple.solutions.framework.core.components.CTextArea;
import software.simple.solutions.framework.core.components.MessageWindowHandler;
import software.simple.solutions.framework.core.components.SessionHolder;
import software.simple.solutions.framework.core.entities.ApplicationUser;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.util.NumberUtil;
import software.simple.solutions.framework.core.util.PropertyResolver;

public class QuestionOptionsLayout extends VerticalLayout {

	private final class UserTokenRemoved implements TokenRemoveListener {
		@Override
		public void action(TokenLayout event) {
			Token token = event.getToken();
			usersFld.getTokensOfInputField().add(token);
			SurveyQuestionUserServiceFacade surveyQuestionUserServiceFacade = SurveyQuestionUserServiceFacade
					.get(UI.getCurrent());
			try {
				surveyQuestionUserServiceFacade.removeUserFromSurveyQuestion(survey.getId(), surveyQuestion.getId(),
						NumberUtil.getLong(token.getId()));
			} catch (FrameworkException e) {
				new MessageWindowHandler(e);
			}
		}
	}

	private final class UserTokenSelected implements TokenAddListener {
		@Override
		public void action(Token token) {
			usersFld.getTokensOfInputField().remove(token);
			SurveyQuestionUserServiceFacade surveyQuestionUserServiceFacade = SurveyQuestionUserServiceFacade
					.get(UI.getCurrent());
			try {
				surveyQuestionUserServiceFacade.addUserToSurveyQuestion(survey.getId(), surveyQuestion.getId(),
						NumberUtil.getLong(token.getId()));
			} catch (FrameworkException e) {
				new MessageWindowHandler(e);
			}
		}
	}

	private static final Logger logger = LogManager.getLogger(QuestionOptionsLayout.class);

	private CTextArea questionDescriptionFld;
	private CCheckBox requiredFld;
	private CTextArea requiredErrorFld;
	private SurveySectionLookUpField sectionFld;
	private SurveyGroupLookUpField groupFld;
	private AdvancedTokenField usersFld;
	// private CButton saveBtn;
	private SurveyQuestion surveyQuestion;
	private final BehaviorSubject<SurveyQuestion> observer;
	private final BehaviorSubject<Boolean> optionsUpdatedObserver;
	private final BehaviorSubject<SurveySection> sectionUpdatedObserver;

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
		sectionUpdatedObserver = BehaviorSubject.create();

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

		sectionFld = new SurveySectionLookUpField();
		sectionFld.setWidth("300px");
		sectionFld.setCaptionByKey(SurveySectionProperty.SURVEY_SECTION);
		addComponent(sectionFld);

		groupFld = new SurveyGroupLookUpField();
		groupFld.setWidth("300px");
		groupFld.setParentEntity(survey);
		groupFld.setCaptionByKey(SurveyGroupProperty.SURVEY_GROUP);
		addComponent(groupFld);

		usersFld = new AdvancedTokenField();
		usersFld.setQuerySuggestionInputMinLength(0);
		usersFld.addStyleName(ValoTheme.COMBOBOX_SMALL);
		usersFld.setCaption(PropertyResolver.getPropertyValueByLocale(SurveyQuestionUserProperty.ACCESSIBLE_TO_USER,
				UI.getCurrent().getLocale()));
		addComponent(usersFld);
	}

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
					surveyQuestion = SurveyQuestionServiceFacade.get(UI.getCurrent())
							.updateDescription(surveyQuestion.getId(), questionDescriptionFld.getValue());
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
					surveyQuestion = SurveyQuestionServiceFacade.get(UI.getCurrent())
							.updateRequired(surveyQuestion.getId(), event.getValue());
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
					surveyQuestion = SurveyQuestionServiceFacade.get(UI.getCurrent())
							.updateRequiredError(surveyQuestion.getId(), requiredErrorFld.getValue());
				} catch (FrameworkException e) {
					new MessageWindowHandler(e);
				}
			}
		});

		sectionFld.setValue(surveyQuestion.getSurveySection());
		sectionFld.setParentEntity(surveyQuestion.getSurvey());
		sectionFld.addValueChangeListener(new ValueChangeListener<Object>() {

			@Override
			public void valueChange(ValueChangeEvent<Object> event) {
				SurveySection surveySection = (SurveySection) event.getValue();
				try {
					surveyQuestion = SurveyQuestionServiceFacade.get(UI.getCurrent()).updateSurveyQuestionSection(
							surveyQuestion.getId(), surveySection == null ? null : surveySection.getId());
					sectionUpdatedObserver.onNext(surveySection);
					optionsUpdatedObserver.onNext(true);
				} catch (FrameworkException e) {
					new MessageWindowHandler(e);
				}
			}
		});

		groupFld.setValue(surveyQuestion.getSurveyGroup());
		groupFld.setParentEntity(surveyQuestion.getSurvey());
		groupFld.addValueChangeListener(new ValueChangeListener<Object>() {

			@Override
			public void valueChange(ValueChangeEvent<Object> event) {
				SurveyGroup surveyGroup = (SurveyGroup) event.getValue();
				try {
					surveyQuestion = SurveyQuestionServiceFacade.get(UI.getCurrent()).updateSurveyQuestionGroup(
							surveyQuestion.getId(), surveyGroup == null ? null : surveyGroup.getId());
					optionsUpdatedObserver.onNext(true);
				} catch (FrameworkException e) {
					new MessageWindowHandler(e);
				}
			}
		});
		SurveyApplicationUserServiceFacade surveyApplicationUserServiceFacade = SurveyApplicationUserServiceFacade
				.get(UI.getCurrent());
		try {
			List<ApplicationUser> users = surveyApplicationUserServiceFacade
					.findApplicationUserBySurvey(survey.getId());
			if (users != null) {
				List<Token> list = users.stream()
						.map(p -> (Token.builder().id(p.getId().toString()).value(p.getUsername()).build()))
						.collect(Collectors.toList());
				usersFld.addTokensToInputField(list);

				SurveyQuestionUserServiceFacade surveyQuestionUserServiceFacade = SurveyQuestionUserServiceFacade
						.get(UI.getCurrent());
				List<ApplicationUser> applicationUsers = surveyQuestionUserServiceFacade
						.findBySurveyAndQuestion(survey.getId(), surveyQuestion.getId());
				List<Token> applicationUserTokens = applicationUsers.stream()
						.map(p -> (Token.builder().id(p.getId().toString()).value(p.getUsername()).build()))
						.collect(Collectors.toList());
				usersFld.addTokens(applicationUserTokens);
				usersFld.getTokensOfInputField().removeAll(applicationUserTokens);

				usersFld.addTokenAddListener(new UserTokenSelected());
				usersFld.addTokenRemoveListener(new UserTokenRemoved());
			}

		} catch (FrameworkException e) {
			new MessageWindowHandler(e);
		}
	}

	public BehaviorSubject<Boolean> getOptionsUpdatedObserver() {
		return optionsUpdatedObserver;
	}

	public BehaviorSubject<SurveySection> getSectionUpdatedObserver() {
		return sectionUpdatedObserver;
	}

}
