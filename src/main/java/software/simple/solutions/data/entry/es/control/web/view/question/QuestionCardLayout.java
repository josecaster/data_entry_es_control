package software.simple.solutions.data.entry.es.control.web.view.question;

import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.SelectedTabChangeListener;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import io.reactivex.functions.Consumer;
import io.reactivex.subjects.BehaviorSubject;
import software.simple.solutions.data.entry.es.control.entities.Survey;
import software.simple.solutions.data.entry.es.control.entities.SurveyGroup;
import software.simple.solutions.data.entry.es.control.entities.SurveyQuestion;
import software.simple.solutions.data.entry.es.control.entities.SurveySection;
import software.simple.solutions.data.entry.es.control.properties.SurveyQuestionProperty;
import software.simple.solutions.framework.core.util.PropertyResolver;

public class QuestionCardLayout extends VerticalLayout {

	private static final long serialVersionUID = 2455709356330323559L;

	private TabSheet tabSheet;
	private QuestionDetailsLayout questionDetailsLayout;
	private QuestionOrderLayout questionOrderLayout;
	private QuestionOptionsLayout questionOptionsLayout;
	private QuestionPreviewLayout questionPreviewLayout;

	private final BehaviorSubject<SurveyQuestion> observer;
	private final BehaviorSubject<Boolean> deletedObserver;
	private final BehaviorSubject<Integer> tabSelectedIndexObserver;
	private final BehaviorSubject<Boolean> sectionUpdatedObserver;
	private BehaviorSubject<SurveyGroup> surveyGroupObserver;

	private Survey survey;
	private SurveyQuestion surveyQuestion;

	private int selectedTabIndex;


	public QuestionCardLayout() {
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
		createQuestionPreviewLayout();
		setValues();
	}

	private void setValues() {
		questionDetailsLayout.setSurveyQuestion(surveyQuestion);
		questionDetailsLayout.getDeletedObserver().subscribe(new Consumer<Boolean>() {

			@Override
			public void accept(Boolean deleted) throws Exception {
				deletedObserver.onNext(deleted);
			}
		});

		tabSheet.setSelectedTab(selectedTabIndex);
	}

	private void createQuestionDetailsLayout() {
		questionDetailsLayout = new QuestionDetailsLayout();
		questionDetailsLayout.setSurveyGroupObserver(surveyGroupObserver);
		questionDetailsLayout.build();
		tabSheet.addComponent(questionDetailsLayout);
		tabSheet.getTab(questionDetailsLayout)
				.setCaption(PropertyResolver.getPropertyValueByLocale(SurveyQuestionProperty.QUESTION));
		questionDetailsLayout.getObserver().subscribe(new Consumer<SurveyQuestion>() {

			@Override
			public void accept(SurveyQuestion surveyQuestion) throws Exception {
				QuestionCardLayout.this.surveyQuestion = surveyQuestion;
				questionPreviewLayout.setSurveyQuestion(surveyQuestion);
				observer.onNext(surveyQuestion);
			}
		});
	}

	private void createQuestionOrderLayout() {
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

	private void createQuestionOptionsLayout() {
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
				questionPreviewLayout.reset();
			}
		});
		questionOptionsLayout.getSectionUpdatedObserver().subscribe(new Consumer<SurveySection>() {

			@Override
			public void accept(SurveySection surveySection) throws Exception {
				questionDetailsLayout.setSurveySection(surveySection);
			}
		});
	}

	private void createQuestionPreviewLayout() {
		questionPreviewLayout = new QuestionPreviewLayout();
		questionPreviewLayout.setSurveyQuestion(surveyQuestion);
		tabSheet.addComponent(questionPreviewLayout);
		tabSheet.getTab(questionPreviewLayout)
				.setCaption(PropertyResolver.getPropertyValueByLocale(SurveyQuestionProperty.QUESTION_PREVIEW));
	}

	public void doForNew() {
		questionDetailsLayout = new QuestionDetailsLayout();
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
				observer.onNext(surveyQuestion);
				redrawLayout();
			}
		});
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

	public void setSurveyQuestion(SurveyQuestion surveyQuestion) {
		this.surveyQuestion = surveyQuestion;
		redrawLayout();
	}

	public void setSelectedTabIndex(int selectedTabIndex) {
		this.selectedTabIndex = selectedTabIndex;
	}

	public void setSurveyGroupObserver(BehaviorSubject<SurveyGroup> surveyGroupObserver) {
		this.surveyGroupObserver = surveyGroupObserver;
	}

}
