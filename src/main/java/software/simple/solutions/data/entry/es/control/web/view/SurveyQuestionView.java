package software.simple.solutions.data.entry.es.control.web.view;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.shared.MouseEventDetails.MouseButton;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import io.reactivex.functions.Consumer;
import io.reactivex.subjects.BehaviorSubject;
import software.simple.solutions.data.entry.es.control.components.SurveySectionSelect;
import software.simple.solutions.data.entry.es.control.constants.EsControlStyle;
import software.simple.solutions.data.entry.es.control.constants.EsReferenceKey;
import software.simple.solutions.data.entry.es.control.constants.EscontrolPrivileges;
import software.simple.solutions.data.entry.es.control.entities.Survey;
import software.simple.solutions.data.entry.es.control.entities.SurveyQuestion;
import software.simple.solutions.data.entry.es.control.entities.SurveySection;
import software.simple.solutions.data.entry.es.control.properties.SurveyProperty;
import software.simple.solutions.data.entry.es.control.properties.SurveyQuestionProperty;
import software.simple.solutions.data.entry.es.control.properties.SurveySectionProperty;
import software.simple.solutions.data.entry.es.control.service.ISurveyQuestionService;
import software.simple.solutions.data.entry.es.control.service.ISurveySectionService;
import software.simple.solutions.data.entry.es.control.web.view.question.QuestionCardLayout;
import software.simple.solutions.framework.core.annotations.SupportedPrivileges;
import software.simple.solutions.framework.core.components.AbstractBaseView;
import software.simple.solutions.framework.core.components.CButton;
import software.simple.solutions.framework.core.components.CTextField;
import software.simple.solutions.framework.core.components.MessageWindowHandler;
import software.simple.solutions.framework.core.constants.Privileges;
import software.simple.solutions.framework.core.constants.Style;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.icons.CxodeIcons;
import software.simple.solutions.framework.core.pojo.ComboItem;
import software.simple.solutions.framework.core.util.ContextProvider;
import software.simple.solutions.framework.core.util.PropertyResolver;

@SupportedPrivileges(extraPrivileges = { EscontrolPrivileges.SURVEY_SHOW_MOVE_TAB,
		EscontrolPrivileges.SURVEY_SHOW_OPTIONS_TAB, EscontrolPrivileges.SURVEY_SHOW_PREVIEW_TAB,
		EscontrolPrivileges.SURVEY_SHOW_QUESTION_TAB })
public class SurveyQuestionView extends AbstractBaseView {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = LogManager.getLogger(SurveyQuestionView.class);

	private HorizontalLayout h1;
	private VerticalLayout v1;
	private CssLayout questionLayout;
	private CButton newQuestionBtn;
	private Panel questionsPanel;
	private VerticalLayout questionPanelLayout;
	private CssLayout lastMenuLayout;
	private Survey survey;
	private SurveySectionSelect surveySectionFld;
	private CTextField questionFilterFld;

	private int selectedTabIndex;

	@Override
	public void executeBuild() throws FrameworkException {
		setHeight("100%");
		survey = getParentEntity();
		buildMainLayout();
	}

	private void buildMainLayout() {
		h1 = new HorizontalLayout();
		h1.setHeight("100%");
		h1.setMargin(false);
		h1.setSizeFull();
		addComponent(h1);
		v1 = new VerticalLayout();
		v1.addStyleName(ValoTheme.LAYOUT_WELL);
		v1.setHeight("100%");
		v1.setWidth("225px");
		h1.addComponentAsFirst(v1);

		newQuestionBtn = new CButton();
		newQuestionBtn.setCaptionByKey(SurveyProperty.CREATE_NEW_QUESTION);
		newQuestionBtn.addStyleName(ValoTheme.BUTTON_PRIMARY);
		newQuestionBtn.setWidth("100%");
		newQuestionBtn.setIcon(CxodeIcons.ADD);
		newQuestionBtn.addStyleName(Style.RESIZED_ICON_80);
		newQuestionBtn.addStyleName(ValoTheme.BUTTON_SMALL);
		v1.addComponent(newQuestionBtn);
		newQuestionBtn.setVisible(false);
		if (getViewDetail().getPrivileges().contains(Privileges.INSERT)) {
			newQuestionBtn.setVisible(true);
		}

		surveySectionFld = new SurveySectionSelect();
		surveySectionFld.setPlaceholder(
				PropertyResolver.getPropertyValueByLocale(SurveySectionProperty.FILTER_BY_SURVEY_SECTION));
		surveySectionFld.setWidth("100%");
		v1.addComponent(surveySectionFld);
		BehaviorSubject<SurveySection> surveySectionObserver = getReferenceKey(EsReferenceKey.SURVEY_SECTION_OBSERVER);
		surveySectionObserver.subscribe(new Consumer<SurveySection>() {

			@Override
			public void accept(SurveySection t) throws Exception {
				surveySectionFld.refresh();
			}
		});
		surveySectionFld.addValueChangeListener(new ValueChangeListener<ComboItem>() {

			@Override
			public void valueChange(ValueChangeEvent<ComboItem> event) {
				try {
					createMenu();
				} catch (FrameworkException e) {
					logger.error(e.getMessage(), e);
					new MessageWindowHandler(e);
				}
			}
		});

		questionFilterFld = new CTextField();
		questionFilterFld.setPlaceholder(
				PropertyResolver.getPropertyValueByLocale(SurveyQuestionProperty.FILTER_BY_SURVEY_QUESTION));
		questionFilterFld.setWidth("100%");
		v1.addComponent(questionFilterFld);
		questionFilterFld.setValueChangeMode(ValueChangeMode.TIMEOUT);
		questionFilterFld.setValueChangeTimeout(1000);
		questionFilterFld.addValueChangeListener(new ValueChangeListener<String>() {

			@Override
			public void valueChange(ValueChangeEvent<String> event) {
				try {
					createMenu();
				} catch (FrameworkException e) {
					logger.error(e.getMessage(), e);
					new MessageWindowHandler(e);
				}
			}
		});

		questionsPanel = new Panel();
		questionsPanel.setHeight("350px");
		v1.addComponent(questionsPanel);
		v1.setExpandRatio(questionsPanel, 1);

		questionPanelLayout = new VerticalLayout();
		questionPanelLayout.setMargin(false);
		questionPanelLayout.setSpacing(false);
		questionsPanel.setContent(questionPanelLayout);

		questionLayout = new CssLayout();
		questionLayout.setSizeFull();
		h1.addComponent(questionLayout);
		h1.setExpandRatio(questionLayout, 1);

		newQuestionBtn.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 3370694121720270976L;

			@Override
			public void buttonClick(ClickEvent event) {
				selectedTabIndex = 0;
				createQuestionCard(null, null);
			}
		});

		try {
			createMenu();
		} catch (FrameworkException e) {
			logger.error(e.getMessage(), e);
			new MessageWindowHandler(e);
		}
	}

	private void createMenu() throws FrameworkException {
		createMenu(null);
	}

	private void createMenu(SurveyQuestion surveyQuestion) throws FrameworkException {
		questionPanelLayout.removeAllComponents();
		questionsPanel.setVisible(false);
		lastMenuLayout = null;
		ISurveyQuestionService surveyQuestionService = ContextProvider.getBean(ISurveyQuestionService.class);
		List<SurveyQuestion> questions = surveyQuestionService.getQuestionList(survey.getId(),
				questionFilterFld.getValue(), surveySectionFld.getLongValue());
		if (questions != null && !questions.isEmpty()) {
			questionsPanel.setVisible(true);
			questions.forEach(p -> {
				CssLayout lastMenuLayout = createMenuItem(p);
				if (surveyQuestion != null && p.getId().compareTo(surveyQuestion.getId()) == 0) {
					this.lastMenuLayout = lastMenuLayout;
				}
			});
		}
		Long sectionId = surveySectionFld.getLongValue();
		ISurveySectionService surveySectionService = ContextProvider.getBean(ISurveySectionService.class);
		SurveySection surveySection = surveySectionService.get(SurveySection.class, sectionId);
		// if (surveyQuestion != null) {
		createQuestionCard(surveyQuestion, surveySection);
		// }
		if (lastMenuLayout != null) {
			UI.getCurrent().scrollIntoView(lastMenuLayout);
		}
	}

	private void createQuestionCard(SurveyQuestion surveyQuestion, SurveySection surveySection) {
		questionLayout.removeAllComponents();

		questionPanelLayout.iterator().forEachRemaining(p -> p.removeStyleName(EsControlStyle.QUESTION_MENU_SELECTED));

		QuestionCardLayout questionCardLayout = new QuestionCardLayout(getSessionHolder(),
				getViewDetail().getPrivileges());
		questionCardLayout.setSelectedTabIndex(selectedTabIndex);
		questionLayout.addComponent(questionCardLayout);
		questionCardLayout.setSurvey(survey);
		if (surveyQuestion == null && surveySection == null) {
			questionCardLayout.doForNew();
			questionCardLayout.getObserver().subscribe(new Consumer<SurveyQuestion>() {

				@Override
				public void accept(SurveyQuestion surveyQuestion) throws Exception {
					createMenu(surveyQuestion);
				}
			});
		} else {
			questionCardLayout.setSurveyGroupObserver(getReferenceKey(EsReferenceKey.SURVEY_GROUP_OBSERVER));
			questionCardLayout.setSurveyQuestion(surveyQuestion, surveySection);
			questionCardLayout.getObserver().subscribe(new Consumer<SurveyQuestion>() {

				@Override
				public void accept(SurveyQuestion surveyQuestion) throws Exception {
					createMenu(surveyQuestion);
				}
			});
			questionCardLayout.getDeletedObserver().subscribe(new Consumer<Boolean>() {

				@Override
				public void accept(Boolean deleted) throws Exception {
					createMenu();
					questionLayout.removeAllComponents();
				}
			});
			questionCardLayout.getTabSelectedIndexObserver().subscribe(new Consumer<Integer>() {

				@Override
				public void accept(Integer index) throws Exception {
					selectedTabIndex = index;
				}
			});

		}
	}

	private CssLayout createMenuItem(SurveyQuestion surveyQuestion) {
		CssLayout menuLayout = new CssLayout();
		menuLayout.setWidth("100%");
		menuLayout.addStyleName(EsControlStyle.QUESTION_MENU);
		menuLayout.setDescription(surveyQuestion.getQuestion());
		Label label = new Label("[" + surveyQuestion.getOrder() + "] " + surveyQuestion.getQuestion());
		label.addStyleName(ValoTheme.LABEL_LARGE);
		menuLayout.addComponent(label);
		questionPanelLayout.addComponent(menuLayout);

		menuLayout.addLayoutClickListener(new LayoutClickListener() {

			@Override
			public void layoutClick(LayoutClickEvent event) {
				if (event.getButton().compareTo(MouseButton.LEFT) == 0) {
					try {
						ISurveyQuestionService surveyQuestionService = ContextProvider
								.getBean(ISurveyQuestionService.class);
						SurveyQuestion sq = surveyQuestionService.getById(SurveyQuestion.class, surveyQuestion.getId());
						createQuestionCard(sq, sq.getSurveySection());
						questionPanelLayout.iterator()
								.forEachRemaining(p -> p.removeStyleName(EsControlStyle.QUESTION_MENU_SELECTED));
						menuLayout.addStyleName(EsControlStyle.QUESTION_MENU_SELECTED);
					} catch (FrameworkException e) {
						new MessageWindowHandler(e);
					}
				}
			}
		});
		return menuLayout;
	}

}
