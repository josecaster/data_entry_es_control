package software.simple.solutions.data.entry.es.control.web.view.question;

import java.util.List;

import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.SelectedTabChangeListener;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import io.reactivex.functions.Consumer;
import io.reactivex.subjects.BehaviorSubject;
import software.simple.solutions.data.entry.es.control.constants.EscontrolPrivileges;
import software.simple.solutions.data.entry.es.control.entities.Survey;
import software.simple.solutions.data.entry.es.control.entities.SurveyGroup;
import software.simple.solutions.data.entry.es.control.entities.SurveyQuestion;
import software.simple.solutions.data.entry.es.control.entities.SurveySection;
import software.simple.solutions.data.entry.es.control.properties.SurveyQuestionProperty;
import software.simple.solutions.framework.core.components.SessionHolder;
import software.simple.solutions.framework.core.util.PropertyResolver;

public class QuestionCardLayout extends VerticalLayout {

	private static final long serialVersionUID = 2455709356330323559L;

	private TabSheet tabSheet;
	private QuestionDetailsLayout questionDetailsLayout;
	private QuestionOrderLayout questionOrderLayout;
	private QuestionOptionsLayout questionOptionsLayout;
	// private QuestionPreviewLayout questionPreviewLayout;
	private QuestionSectionPreviewLayout questionSectionPreviewLayout;

	private final BehaviorSubject<SurveyQuestion> observer;
	private final BehaviorSubject<Boolean> deletedObserver;
	private final BehaviorSubject<Integer> tabSelectedIndexObserver;
	private final BehaviorSubject<Boolean> sectionUpdatedObserver;
	private BehaviorSubject<SurveyGroup> surveyGroupObserver;

	private Survey survey;
	private SurveyQuestion surveyQuestion;
	private SurveySection surveySection;
	private List<String> privileges;

	private int selectedTabIndex;
	private SessionHolder sessionHolder;

	public QuestionCardLayout(SessionHolder sessionHolder, List<String> privileges) {
		this.sessionHolder = sessionHolder;
		this.privileges = privileges;
		addStyleName(ValoTheme.LAYOUT_CARD);
		observer = BehaviorSubject.create();
		deletedObserver = BehaviorSubject.create();
		tabSelectedIndexObserver = BehaviorSubject.create();
		sectionUpdatedObserver = BehaviorSubject.create();
		tabSheet = new TabSheet();
		tabSheet.setSizeFull();
		addComponent(tabSheet);

		tabSheet.addSelectedTabChangeListener(new SelectedTabChangeListener() {

			@Override
			public void selectedTabChange(SelectedTabChangeEvent event) {
				if (event.isUserOriginated()) {
					int tabPosition = event.getTabSheet().getTabPosition(tabSheet.getTab(tabSheet.getSelectedTab()));
					tabSelectedIndexObserver.onNext(tabPosition);
				}
			}
		});
	}

	@Override
	public void detach() {
		super.detach();
	}

	protected void redrawLayout() {
		tabSheet.removeAllComponents();
		createQuestionDetailsLayout();
		createQuestionOrderLayout();
		createQuestionOptionsLayout();
		// createQuestionPreviewLayout();
		createQuestionSectionPreviewLayout();
		setValues();
	}

	private void setValues() {
		if (surveyQuestion != null && privileges.contains(EscontrolPrivileges.SURVEY_SHOW_OPTIONS_TAB)) {
			questionDetailsLayout.setSurveyQuestion(surveyQuestion);
			questionDetailsLayout.getDeletedObserver().subscribe(new Consumer<Boolean>() {

				@Override
				public void accept(Boolean deleted) throws Exception {
					deletedObserver.onNext(deleted);
				}
			});

			tabSheet.setSelectedTab(selectedTabIndex);
		}
	}

	private void createQuestionDetailsLayout() {
		if (surveyQuestion != null && privileges.contains(EscontrolPrivileges.SURVEY_SHOW_QUESTION_TAB)) {
			questionDetailsLayout = new QuestionDetailsLayout(sessionHolder);
			questionDetailsLayout.setSurveyGroupObserver(surveyGroupObserver);
			questionDetailsLayout.build();
			tabSheet.addComponent(questionDetailsLayout);
			tabSheet.getTab(questionDetailsLayout)
					.setCaption(PropertyResolver.getPropertyValueByLocale(SurveyQuestionProperty.QUESTION));
			questionDetailsLayout.getObserver().subscribe(new Consumer<SurveyQuestion>() {

				@Override
				public void accept(SurveyQuestion surveyQuestion) throws Exception {
					QuestionCardLayout.this.surveyQuestion = surveyQuestion;
					// questionPreviewLayout.setSurveyQuestion(surveyQuestion);

					questionSectionPreviewLayout.setSurveySection(surveyQuestion.getSurveySection());
					observer.onNext(surveyQuestion);
				}
			});
		}
	}

	private void createQuestionOrderLayout() {
		if (surveyQuestion != null && privileges.contains(EscontrolPrivileges.SURVEY_SHOW_MOVE_TAB)) {
			questionOrderLayout = new QuestionOrderLayout();
			questionOrderLayout.setSurveyQuestion(surveyQuestion);
			tabSheet.addComponent(questionOrderLayout);
			tabSheet.getTab(questionOrderLayout)
					.setCaption(PropertyResolver.getPropertyValueByLocale(SurveyQuestionProperty.QUESTION_MOVE));
			questionOrderLayout.getObserver().subscribe(new Consumer<SurveyQuestion>() {

				@Override
				public void accept(SurveyQuestion surveyQuestion) throws Exception {
					QuestionCardLayout.this.surveyQuestion = surveyQuestion;
					observer.onNext(surveyQuestion);
				}
			});
		}
	}

	private void createQuestionOptionsLayout() {
		if (surveyQuestion != null && privileges.contains(EscontrolPrivileges.SURVEY_SHOW_OPTIONS_TAB)) {
			questionOptionsLayout = new QuestionOptionsLayout();
			questionOptionsLayout.setSurveyQuestion(surveyQuestion);
			tabSheet.addComponent(questionOptionsLayout);
			tabSheet.getTab(questionOptionsLayout)
					.setCaption(PropertyResolver.getPropertyValueByLocale(SurveyQuestionProperty.QUESTION_OPTIONS));
			questionOptionsLayout.getObserver().subscribe(new Consumer<SurveyQuestion>() {

				@Override
				public void accept(SurveyQuestion surveyQuestion) throws Exception {
					QuestionCardLayout.this.surveyQuestion = surveyQuestion;
					observer.onNext(surveyQuestion);
				}
			});
			questionOptionsLayout.getOptionsUpdatedObserver().subscribe(new Consumer<Boolean>() {

				@Override
				public void accept(Boolean t) throws Exception {
					// questionPreviewLayout.reset();
					questionSectionPreviewLayout.reset();
				}
			});
			questionOptionsLayout.getSectionUpdatedObserver().subscribe(new Consumer<SurveySection>() {

				@Override
				public void accept(SurveySection surveySection) throws Exception {
					questionDetailsLayout.setSurveySection(surveySection);
				}
			});
		}
	}

	// private void createQuestionPreviewLayout() {
	// questionPreviewLayout = new QuestionPreviewLayout();
	// questionPreviewLayout.setSurveyQuestion(surveyQuestion);
	// tabSheet.addComponent(questionPreviewLayout);
	// tabSheet.getTab(questionPreviewLayout)
	// .setCaption(PropertyResolver.getPropertyValueByLocale(SurveyQuestionProperty.QUESTION_PREVIEW));
	// }

	private void createQuestionSectionPreviewLayout() {
		if (privileges.contains(EscontrolPrivileges.SURVEY_SHOW_PREVIEW_TAB)) {
			questionSectionPreviewLayout = new QuestionSectionPreviewLayout();
			// questionSectionPreviewLayout.setSurveyQuestion(surveyQuestion);
			questionSectionPreviewLayout
					.setSurveySection(surveyQuestion == null ? surveySection : surveyQuestion.getSurveySection());
			tabSheet.addComponent(questionSectionPreviewLayout);
			tabSheet.getTab(questionSectionPreviewLayout)
					.setCaption(PropertyResolver.getPropertyValueByLocale(SurveyQuestionProperty.QUESTION_PREVIEW));
		}
	}

	public void doForNew() {
		if (privileges.contains(EscontrolPrivileges.SURVEY_SHOW_QUESTION_TAB)) {
			questionDetailsLayout = new QuestionDetailsLayout(sessionHolder);
			questionDetailsLayout.setSurveyGroupObserver(surveyGroupObserver);
			questionDetailsLayout.build();
			questionDetailsLayout.doForNew();

			questionDetailsLayout.setSurvey(survey);
			tabSheet.addComponent(questionDetailsLayout);
			tabSheet.getTab(questionDetailsLayout)
					.setCaption(PropertyResolver.getPropertyValueByLocale(SurveyQuestionProperty.QUESTION));
			questionDetailsLayout.getObserver().subscribe(new Consumer<SurveyQuestion>() {

				@Override
				public void accept(SurveyQuestion surveyQuestion) throws Exception {
					QuestionCardLayout.this.surveyQuestion = surveyQuestion;
					QuestionCardLayout.this.surveySection = surveyQuestion.getSurveySection();
					observer.onNext(surveyQuestion);
					redrawLayout();
				}
			});
		} else {
			this.setVisible(false);
		}
	}

	public BehaviorSubject<SurveyQuestion> getObserver() {
		return observer;
	}

	public BehaviorSubject<Boolean> getDeletedObserver() {
		return deletedObserver;
	}

	public BehaviorSubject<Integer> getTabSelectedIndexObserver() {
		return tabSelectedIndexObserver;
	}

	public void setSurvey(Survey survey) {
		this.survey = survey;

	}

	public void setSurveyQuestion(SurveyQuestion surveyQuestion, SurveySection surveySection) {
		this.surveyQuestion = surveyQuestion;
		this.surveySection = surveySection;
		redrawLayout();
	}

	public void setSelectedTabIndex(int selectedTabIndex) {
		this.selectedTabIndex = selectedTabIndex;
	}

	public void setSurveyGroupObserver(BehaviorSubject<SurveyGroup> surveyGroupObserver) {
		this.surveyGroupObserver = surveyGroupObserver;
	}

}
