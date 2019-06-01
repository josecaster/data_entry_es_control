package software.simple.solutions.data.entry.es.control.web.view;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.data.ValueProvider;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.shared.MouseEventDetails.MouseButton;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import software.simple.solutions.data.entry.es.control.constants.EsControlStyle;
import software.simple.solutions.data.entry.es.control.entities.SurveyQuestion;
import software.simple.solutions.data.entry.es.control.entities.SurveyResponse;
import software.simple.solutions.data.entry.es.control.entities.SurveySection;
import software.simple.solutions.data.entry.es.control.properties.SurveyResponseProperty;
import software.simple.solutions.data.entry.es.control.service.ISurveyQuestionService;
import software.simple.solutions.data.entry.es.control.service.ISurveyResponseService;
import software.simple.solutions.data.entry.es.control.service.ISurveySectionService;
import software.simple.solutions.data.entry.es.control.valueobjects.SurveyResponseVO;
import software.simple.solutions.data.entry.es.control.web.view.question.QuestionSectionPreviewLayout;
import software.simple.solutions.framework.core.components.CCheckBox;
import software.simple.solutions.framework.core.components.CGridLayout;
import software.simple.solutions.framework.core.components.CLabel;
import software.simple.solutions.framework.core.components.FilterView;
import software.simple.solutions.framework.core.components.FormView;
import software.simple.solutions.framework.core.components.MessageWindowHandler;
import software.simple.solutions.framework.core.components.filter.CStringIntervalLayout;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.SystemProperty;
import software.simple.solutions.framework.core.util.ComponentUtil;
import software.simple.solutions.framework.core.util.ContextProvider;
import software.simple.solutions.framework.core.web.BasicTemplate;

public class SurveyResponsePreviewView extends BasicTemplate<SurveyResponse> {

	private static final Logger logger = LogManager.getLogger(SurveyResponsePreviewView.class);

	private static final long serialVersionUID = 6503015064562511801L;

	private UI ui;

	public SurveyResponsePreviewView() {
		setEntityClass(SurveyResponse.class);
		setServiceClass(ISurveyResponseService.class);
		setFilterClass(Filter.class);
		setFormClass(Form.class);

		ui = UI.getCurrent();
	}

	@Override
	public void setUpCustomColumns() {
		addContainerProperty(new ValueProvider<SurveyResponse, String>() {

			@Override
			public String apply(SurveyResponse source) {
				return source.getSurvey().getCaption();
			}
		}, SurveyResponseProperty.SURVEY);
		addContainerProperty(SurveyResponse::getFormName, SurveyResponseProperty.FORM_NAME);
		addContainerProperty(new ValueProvider<SurveyResponse, String>() {

			@Override
			public String apply(SurveyResponse source) {
				if (source.getApplicationUser() != null) {
					return source.getApplicationUser().getUsername();
				}
				return null;
			}
		}, SurveyResponseProperty.APPLICATION_USER);
	}

	/**
	 * Filter implementation
	 * 
	 * @author yusuf
	 *
	 */
	public class Filter extends FilterView {

		private static final long serialVersionUID = 117780868467918033L;

		private CStringIntervalLayout surveyNameFld;
		private CStringIntervalLayout surveyDescriptionFld;
		private CStringIntervalLayout formNameFld;
		private CStringIntervalLayout userNameFld;

		@Override
		public void executeBuild() {

			surveyNameFld = addField(CStringIntervalLayout.class, SurveyResponseProperty.SURVEY, 0, 0);
			surveyDescriptionFld = addField(CStringIntervalLayout.class, SurveyResponseProperty.SURVEY_DESCRIPTION, 0,
					1);

			formNameFld = addField(CStringIntervalLayout.class, SurveyResponseProperty.FORM_NAME, 1, 0);
			userNameFld = addField(CStringIntervalLayout.class, SurveyResponseProperty.APPLICATION_USER, 1, 1);
		}

		@Override
		public Object getCriteria() {
			SurveyResponseVO vo = new SurveyResponseVO();
			vo.setSurveyNameInterval(surveyNameFld.getValue());
			vo.setSurveyDescriptionInterval(surveyDescriptionFld.getValue());
			vo.setFormNameInterval(formNameFld.getValue());
			vo.setUserNameInInterval(userNameFld.getValue());
			return vo;
		}
	}

	public class Form extends FormView {

		private HorizontalLayout h1;
		private CGridLayout formGrid;
		private CLabel formNameFld;
		private CLabel surveyFld;
		private CLabel userNameFld;
		private CCheckBox activeFld;
		private Accordion accordion;

		private HorizontalLayout sectionMainLayout;
		private VerticalLayout v1;
		private CssLayout sectionLayout;
		private Panel sectionsPanel;
		private VerticalLayout sectionPanelLayout;

		private SurveyResponse surveyResponse;

		@Override
		public void executeBuild() {
			setWidth("100%");
			h1 = new HorizontalLayout();
			h1.setMargin(false);
			addComponent(h1);

			formGrid = ComponentUtil.createGrid();
			h1.addComponent(formGrid);

			formNameFld = formGrid.addField(CLabel.class, SurveyResponseProperty.FORM_NAME, 0, 0);
			formNameFld.addStyleName(ValoTheme.LABEL_COLORED);
			formNameFld.addStyleName(ValoTheme.LABEL_H3);

			surveyFld = formGrid.addField(CLabel.class, SurveyResponseProperty.SURVEY, 1, 0);
			surveyFld.addStyleName(ValoTheme.LABEL_COLORED);

			userNameFld = formGrid.addField(CLabel.class, SurveyResponseProperty.APPLICATION_USER, 2, 0);
			userNameFld.addStyleName(ValoTheme.LABEL_COLORED);

			activeFld = formGrid.addField(CCheckBox.class, SystemProperty.SYSTEM_ENTITY_ACTIVE, 3, 0);
			activeFld.setReadOnly(true);

			HorizontalLayout mainLayout = buildMainLayout();
			addComponent(mainLayout);
		}

		private HorizontalLayout buildMainLayout() {
			sectionMainLayout = new HorizontalLayout();
			sectionMainLayout.setHeight("100%");
			sectionMainLayout.setMargin(false);
			sectionMainLayout.setSizeFull();
			addComponent(sectionMainLayout);
			v1 = new VerticalLayout();
			v1.addStyleName(ValoTheme.LAYOUT_WELL);
			v1.setHeight("100%");
			v1.setWidth("225px");
			sectionMainLayout.addComponentAsFirst(v1);

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
			sectionMainLayout.addComponent(sectionLayout);
			sectionMainLayout.setExpandRatio(sectionLayout, 1);

			return sectionMainLayout;
		}

		private void createMenu() throws FrameworkException {
			sectionPanelLayout.removeAllComponents();
			sectionsPanel.setVisible(false);
			ISurveySectionService surveySectionService = ContextProvider.getBean(ISurveySectionService.class);
			List<SurveySection> surveySections = surveySectionService
					.findAllBySurveyId(surveyResponse.getSurvey().getId());
			if (surveySections != null && !surveySections.isEmpty()) {
				sectionsPanel.setVisible(true);
				surveySections.forEach(p -> {
					CssLayout lastMenuLayout = createMenuItem(p);
				});
			}
		}

		private CssLayout createMenuItem(SurveySection surveySection) {
			CssLayout menuLayout = new CssLayout();
			menuLayout.setWidth("100%");
			menuLayout.addStyleName(EsControlStyle.QUESTION_MENU);
			menuLayout.setDescription(surveySection.getName());
			Label label = new Label(surveySection.getName());
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
							SurveyQuestion sq = surveyQuestionService.getById(SurveyQuestion.class,
									surveySection.getId());
							createQuestionCard(surveySection);
							// createQuestionCard(sq, sq.getSurveySection());
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

		private void createQuestionCard(SurveySection surveySection) {
			sectionLayout.removeAllComponents();

			sectionPanelLayout.iterator()
					.forEachRemaining(p -> p.removeStyleName(EsControlStyle.QUESTION_MENU_SELECTED));

			QuestionSectionPreviewLayout questionSectionPreviewLayout = new QuestionSectionPreviewLayout(sessionHolder);
			questionSectionPreviewLayout.setShowInfo(false);
			questionSectionPreviewLayout.setUseGrid(false);
			questionSectionPreviewLayout.setSurveySection(surveySection, surveyResponse);
			questionSectionPreviewLayout.setWidth("100%");
			sectionLayout.addComponent(questionSectionPreviewLayout);
		}

		@Override
		public void handleNewForm() throws FrameworkException {
			activeFld.setValue(true);
		}

		@SuppressWarnings("unchecked")
		@Override
		public SurveyResponse setFormValues(Object entity) throws FrameworkException {
			surveyResponse = (SurveyResponse) entity;

			createMenu();

			formNameFld.setValue(surveyResponse.getFormName());
			userNameFld.setValue(surveyResponse.getApplicationUser() == null ? null
					: surveyResponse.getApplicationUser().getUsername());
			surveyFld.setValue(surveyResponse.getSurvey() == null ? null : surveyResponse.getSurvey().getCaption());
			activeFld.setValue(surveyResponse.getActive());

			// new Thread(new Runnable() {
			//
			// @Override
			// public void run() {
			// try {
			// ISurveySectionService surveySectionService = ContextProvider
			// .getBean(ISurveySectionService.class);
			// List<SurveySection> sections = surveySectionService
			// .findAllBySurveyId(surveyResponse.getSurvey().getId());
			// if (sections != null) {
			// accordion = new Accordion();
			// accordion.setWidth("100%");
			// ui.access(new Runnable() {
			//
			// @Override
			// public void run() {
			// addComponent(accordion);
			// }
			// });
			// for (SurveySection surveySection : sections) {
			// Thread thread = createSurveyResponsePreviewLayout(surveySection);
			// try {
			// thread.join();
			// } catch (InterruptedException e) {
			// e.printStackTrace();
			// }
			// }
			// }
			// } catch (FrameworkException e) {
			// e.printStackTrace();
			// }
			// }
			// }).start();

			return surveyResponse;
		}

		private synchronized Thread createSurveyResponsePreviewLayout(SurveySection surveySection) {
			Thread thread = new Thread(new Runnable() {

				@Override
				public void run() {
					QuestionSectionPreviewLayout questionSectionPreviewLayout = new QuestionSectionPreviewLayout(
							sessionHolder);
					questionSectionPreviewLayout.setShowInfo(false);
					questionSectionPreviewLayout.setUseGrid(false);
					questionSectionPreviewLayout.setSurveySection(surveySection, surveyResponse);
					questionSectionPreviewLayout.setWidth("100%");
					ui.access(new Runnable() {

						@Override
						public void run() {
							accordion.addComponent(questionSectionPreviewLayout);
							accordion.getTab(questionSectionPreviewLayout).setCaption(surveySection.getName());
						}
					});
				}
			});
			thread.start();
			return thread;
		}

		@Override
		public Object getFormValues() throws FrameworkException {
			SurveyResponseVO vo = new SurveyResponseVO();
			return vo;
		}

	}

}
