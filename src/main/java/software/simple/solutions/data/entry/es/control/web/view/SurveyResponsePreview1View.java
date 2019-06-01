package software.simple.solutions.data.entry.es.control.web.view;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.shared.MouseEventDetails.MouseButton;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import io.reactivex.functions.Consumer;
import software.simple.solutions.data.entry.es.control.constants.EsControlStyle;
import software.simple.solutions.data.entry.es.control.constants.EsReferenceKey;
import software.simple.solutions.data.entry.es.control.entities.SurveyQuestion;
import software.simple.solutions.data.entry.es.control.entities.SurveySection;
import software.simple.solutions.data.entry.es.control.service.ISurveyQuestionService;
import software.simple.solutions.data.entry.es.control.service.ISurveySectionService;
import software.simple.solutions.data.entry.es.control.web.view.question.QuestionCardLayout;
import software.simple.solutions.framework.core.components.AbstractBaseView;
import software.simple.solutions.framework.core.components.MessageWindowHandler;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.util.ContextProvider;

public class SurveyResponsePreview1View extends AbstractBaseView {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = LogManager.getLogger(SurveyResponsePreview1View.class);

	private HorizontalLayout h1;
	private VerticalLayout v1;
	private CssLayout sectionLayout;
	private Panel sectionsPanel;
	private VerticalLayout sectionPanelLayout;

	private int selectedTabIndex;
	private SurveySection surveySection;

	@Override
	public void executeBuild() throws FrameworkException {
		setHeight("100%");
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

		sectionsPanel = new Panel();
		sectionsPanel.setHeight("350px");
		v1.addComponent(sectionsPanel);
		v1.setExpandRatio(sectionsPanel, 1);

		sectionPanelLayout = new VerticalLayout();
		sectionPanelLayout.setMargin(false);
		sectionPanelLayout.setSpacing(false);
		sectionsPanel.setContent(sectionPanelLayout);

		sectionLayout = new CssLayout();
		sectionLayout.setSizeFull();
		h1.addComponent(sectionLayout);
		h1.setExpandRatio(sectionLayout, 1);

		try {
			createMenu();
		} catch (FrameworkException e) {
			logger.error(e.getMessage(), e);
			new MessageWindowHandler(e);
		}
	}

	private void createMenu() throws FrameworkException {
		sectionPanelLayout.removeAllComponents();
		sectionsPanel.setVisible(false);
		ISurveySectionService surveySectionService = ContextProvider.getBean(ISurveySectionService.class);
		List<SurveySection> surveySections = surveySectionService.findAllBySurveyId(surveySection.getId());
		if (surveySections != null && !surveySections.isEmpty()) {
			sectionsPanel.setVisible(true);
		}
	}

//	private void createQuestionCard(SurveyQuestion surveyQuestion, SurveySection surveySection) {
//		sectionLayout.removeAllComponents();
//
//		sectionPanelLayout.iterator().forEachRemaining(p -> p.removeStyleName(EsControlStyle.QUESTION_MENU_SELECTED));
//
//		QuestionCardLayout questionCardLayout = new QuestionCardLayout(getSessionHolder(),
//				getViewDetail().getPrivileges());
//		questionCardLayout.setSelectedTabIndex(selectedTabIndex);
//		sectionLayout.addComponent(questionCardLayout);
//		questionCardLayout.setSurvey(survey);
//		if (surveyQuestion == null && surveySection == null) {
//			questionCardLayout.doForNew();
//			questionCardLayout.getObserver().subscribe(new Consumer<SurveyQuestion>() {
//
//				@Override
//				public void accept(SurveyQuestion surveyQuestion) throws Exception {
//					createMenu(surveyQuestion);
//				}
//			});
//		} else {
//			questionCardLayout.setSurveyGroupObserver(getReferenceKey(EsReferenceKey.SURVEY_GROUP_OBSERVER));
//			questionCardLayout.setSurveyQuestion(surveyQuestion, surveySection);
//			questionCardLayout.getObserver().subscribe(new Consumer<SurveyQuestion>() {
//
//				@Override
//				public void accept(SurveyQuestion surveyQuestion) throws Exception {
//					createMenu(surveyQuestion);
//				}
//			});
//			questionCardLayout.getDeletedObserver().subscribe(new Consumer<Boolean>() {
//
//				@Override
//				public void accept(Boolean deleted) throws Exception {
//					createMenu();
//					sectionLayout.removeAllComponents();
//				}
//			});
//			questionCardLayout.getTabSelectedIndexObserver().subscribe(new Consumer<Integer>() {
//
//				@Override
//				public void accept(Integer index) throws Exception {
//					selectedTabIndex = index;
//				}
//			});
//
//		}
//	}

	private CssLayout createMenuItem(SurveyQuestion surveyQuestion) {
		CssLayout menuLayout = new CssLayout();
		menuLayout.setWidth("100%");
		menuLayout.addStyleName(EsControlStyle.QUESTION_MENU);
		menuLayout.setDescription(surveyQuestion.getQuestion());
		Label label = new Label("[" + surveyQuestion.getOrder() + "] " + surveyQuestion.getQuestion());
		label.addStyleName(ValoTheme.LABEL_LARGE);
		menuLayout.addComponent(label);
		sectionPanelLayout.addComponent(menuLayout);

		menuLayout.addLayoutClickListener(new LayoutClickListener() {

			@Override
			public void layoutClick(LayoutClickEvent event) {
				if (event.getButton().compareTo(MouseButton.LEFT) == 0) {
					try {
						ISurveyQuestionService surveyQuestionService = ContextProvider
								.getBean(ISurveyQuestionService.class);
						SurveyQuestion sq = surveyQuestionService.getById(SurveyQuestion.class, surveyQuestion.getId());
//						createQuestionCard(sq, sq.getSurveySection());
						sectionPanelLayout.iterator()
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
