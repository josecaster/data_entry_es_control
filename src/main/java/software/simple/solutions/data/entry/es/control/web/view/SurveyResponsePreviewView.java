package software.simple.solutions.data.entry.es.control.web.view;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.vaadin.teemu.switchui.Switch;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.data.ValueProvider;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.MouseEventDetails.MouseButton;
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
import software.simple.solutions.data.entry.es.control.components.lookup.SurveyLookUpField;
import software.simple.solutions.data.entry.es.control.constants.EsControlConfigurationCodes;
import software.simple.solutions.data.entry.es.control.constants.EsControlStyle;
import software.simple.solutions.data.entry.es.control.constants.EsReferenceKey;
import software.simple.solutions.data.entry.es.control.entities.Survey;
import software.simple.solutions.data.entry.es.control.entities.SurveyResponse;
import software.simple.solutions.data.entry.es.control.entities.SurveySection;
import software.simple.solutions.data.entry.es.control.properties.SurveyResponseProperty;
import software.simple.solutions.data.entry.es.control.properties.SurveySectionProperty;
import software.simple.solutions.data.entry.es.control.service.facade.SurveyApplicationUserServiceFacade;
import software.simple.solutions.data.entry.es.control.service.facade.SurveyResponseServiceFacade;
import software.simple.solutions.data.entry.es.control.service.facade.SurveySectionServiceFacade;
import software.simple.solutions.data.entry.es.control.valueobjects.SurveyResponseVO;
import software.simple.solutions.data.entry.es.control.web.view.question.QuestionSectionPreviewLayout;
import software.simple.solutions.framework.core.components.CButton;
import software.simple.solutions.framework.core.components.CCheckBox;
import software.simple.solutions.framework.core.components.CGridLayout;
import software.simple.solutions.framework.core.components.CTextField;
import software.simple.solutions.framework.core.components.CaptionLabel;
import software.simple.solutions.framework.core.components.ConfirmWindow;
import software.simple.solutions.framework.core.components.ConfirmWindow.ConfirmationHandler;
import software.simple.solutions.framework.core.components.FilterView;
import software.simple.solutions.framework.core.components.FormView;
import software.simple.solutions.framework.core.components.NotificationWindow;
import software.simple.solutions.framework.core.components.filter.CStringIntervalLayout;
import software.simple.solutions.framework.core.components.select.ApplicationUserSelect;
import software.simple.solutions.framework.core.entities.ApplicationUser;
import software.simple.solutions.framework.core.entities.Configuration;
import software.simple.solutions.framework.core.entities.MappedSuperClass;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.SystemProperty;
import software.simple.solutions.framework.core.service.facade.ConfigurationServiceFacade;
import software.simple.solutions.framework.core.util.ComponentUtil;
import software.simple.solutions.framework.core.util.OnDemandFileDownloader;
import software.simple.solutions.framework.core.util.OnDemandStreamResource;
import software.simple.solutions.framework.core.util.PropertyResolver;
import software.simple.solutions.framework.core.web.BasicTemplate;

public class SurveyResponsePreviewView extends BasicTemplate<SurveyResponse> {

	private static final Logger logger = LogManager.getLogger(SurveyResponsePreviewView.class);

	private static final long serialVersionUID = 6503015064562511801L;

	public SurveyResponsePreviewView() {
		setEntityClass(SurveyResponse.class);
		setServiceClass(SurveyResponseServiceFacade.class);
		setFilterClass(Filter.class);
		setFormClass(Form.class);
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
			MappedSuperClass parentEntity = getParentEntity();
			if (parentEntity instanceof Survey) {
				Survey survey = (Survey) getParentEntity();
				surveyNameFld.setValue(survey.getName());
				surveyNameFld.setReadOnly(true);

				surveyDescriptionFld.setValue(survey.getDescription());
				surveyDescriptionFld.setReadOnly(true);
			}

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
			MappedSuperClass parentEntity = getParentEntity();
			if (parentEntity instanceof Survey) {
				Survey survey = (Survey) getParentEntity();
				vo.setSurveyId(survey.getId());
			}
			return vo;
		}
	}

	public class Form extends FormView {

		private HorizontalLayout h1;

		private CGridLayout newFormGrid;
		private CTextField formNameFld;
		private SurveyLookUpField surveyFld;
		private ApplicationUserSelect applicationUserFld;
		private CCheckBox activeFld;
		private CaptionLabel editAnswersLbl;
		private Switch editAnswersSwitch;
		private CButton generateFormFld;

		private HorizontalLayout sectionMainLayout;
		private VerticalLayout v1;
		private VerticalLayout sectionLayout;
		private Panel sectionsPanel;
		private VerticalLayout sectionPanelLayout;
		private boolean editAnswers = false;
		private CssLayout selectedMenuLayout;
		private QuestionSectionPreviewLayout questionSectionPreviewLayout;
		private CButton clearAllDataFld;

		private SurveyResponse surveyResponse;

		@Override
		public void executeBuild() {
			setWidth("100%");
			newFormGrid = ComponentUtil.createGrid();
			addComponent(newFormGrid);

			formNameFld = newFormGrid.addField(CTextField.class, SurveyResponseProperty.FORM_NAME, 0, 0);
			formNameFld.setRequiredIndicatorVisible(true);

			surveyFld = newFormGrid.addField(SurveyLookUpField.class, SurveyResponseProperty.SURVEY, 0, 1);
			surveyFld.handleForParentEntity(getParentEntity());
			surveyFld.setRequiredIndicatorVisible(true);

			activeFld = newFormGrid.addField(CCheckBox.class, SystemProperty.SYSTEM_ENTITY_ACTIVE, 1, 0);
			activeFld.setReadOnly(true);

			applicationUserFld = newFormGrid.addField(ApplicationUserSelect.class,
					SurveyResponseProperty.APPLICATION_USER, 1, 1);
			applicationUserFld.setEmptySelectionAllowed(false);
			applicationUserFld.setRequiredIndicatorVisible(true);
			applicationUserFld.setWidth("200px");

			HorizontalLayout actionLayout = buildActionLayout();
			addComponent(actionLayout);

			sectionMainLayout = buildMainLayout();
			addComponent(sectionMainLayout);
		}

		private HorizontalLayout buildActionLayout() {
			HorizontalLayout actionLayout = new HorizontalLayout();
			actionLayout.setMargin(true);
			actionLayout.addStyleName(ValoTheme.LAYOUT_CARD);

			editAnswersLbl = new CaptionLabel();
			editAnswersLbl.setValueByKey(SurveyResponseProperty.EDIT_ANSWERS);
			actionLayout.addComponent(editAnswersLbl);

			editAnswersSwitch = new Switch();
			editAnswersSwitch.setVisible(false);
			editAnswersSwitch.setAnimationEnabled(true);
			editAnswersSwitch.addStyleName("compact");
			actionLayout.addComponent(editAnswersSwitch);

			generateFormFld = new CButton();
			generateFormFld.setCaptionByKey(SurveyResponseProperty.GENERATE_FORM);
			generateFormFld.addStyleName(ValoTheme.BUTTON_SMALL);
			generateFormFld.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
			generateFormFld.setIcon(VaadinIcons.DOWNLOAD);
			actionLayout.addComponent(generateFormFld);

			clearAllDataFld = new CButton();
			clearAllDataFld.setCaptionByKey(SurveyResponseProperty.CLEAR_ALL_FORM_DATA);
			clearAllDataFld.addStyleName(ValoTheme.BUTTON_SMALL);
			clearAllDataFld.addStyleName(ValoTheme.BUTTON_DANGER);
			clearAllDataFld.setIcon(FontAwesome.WARNING);
			actionLayout.addComponent(clearAllDataFld);

			clearAllDataFld.addClickListener(new ClickListener() {

				@Override
				public void buttonClick(ClickEvent event) {
					ConfirmWindow confirmWindow = new ConfirmWindow(SystemProperty.DELETE_HEADER,
							SurveyResponseProperty.CLEAR_ALL_FORM_DATA_CONFIRMATION, SystemProperty.CONFIRM,
							SystemProperty.CANCEL);
					confirmWindow.execute(new ConfirmationHandler() {

						@Override
						public void handlePositive() {
							try {
								SurveyResponseServiceFacade surveyResponseServiceFacade = SurveyResponseServiceFacade
										.get(UI.getCurrent());
								surveyResponseServiceFacade.removeAllFormData(surveyResponse.getId());
								sectionLayout.removeAllComponents();
								createMenu();
								NotificationWindow.notificationNormalWindow(SystemProperty.UPDATE_SUCCESSFULL);
							} catch (FrameworkException e) {
								updateErrorContent(e);
							}
						}

						@Override
						public void handleNegative() {
							// TODO Auto-generated method stub
						}
					});
				}
			});

			return actionLayout;
		}

		private HorizontalLayout buildMainLayout() {
			sectionMainLayout = new HorizontalLayout();
			sectionMainLayout.setWidth("100%");
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
			sectionsPanel.setCaption(PropertyResolver.getPropertyValueByLocale(SurveySectionProperty.SURVEY_SECTIONS,
					UI.getCurrent().getLocale()));

			sectionPanelLayout = new VerticalLayout();
			sectionPanelLayout.setMargin(false);
			sectionPanelLayout.setSpacing(false);
			sectionsPanel.setContent(sectionPanelLayout);

			sectionLayout = new VerticalLayout();
			sectionLayout.setWidth("100%");
			sectionMainLayout.addComponent(sectionLayout);
			sectionMainLayout.setExpandRatio(sectionLayout, 1);

			return sectionMainLayout;
		}

		private void createMenu() throws FrameworkException {
			sectionPanelLayout.removeAllComponents();
			sectionsPanel.setVisible(false);
			List<SurveySection> surveySections = SurveySectionServiceFacade.get(UI.getCurrent())
					.findAllBySurveyId(surveyResponse.getSurvey().getId());
			if (surveySections != null && !surveySections.isEmpty()) {
				sectionsPanel.setVisible(true);
				surveySections.forEach(p -> {
					createMenuItem(p);
				});
			}
		}

		private CssLayout createMenuItem(SurveySection surveySection) {
			CssLayout menuLayout = new CssLayout();
			menuLayout.setWidth("100%");
			menuLayout.addStyleName(EsControlStyle.QUESTION_MENU);
			menuLayout.setDescription(surveySection.getName());
			menuLayout.setData(surveySection);

			Label label = new Label(surveySection.getName());
			label.addStyleName(ValoTheme.LABEL_LARGE);
			menuLayout.addComponent(label);
			sectionPanelLayout.addComponent(menuLayout);

			menuLayout.addLayoutClickListener(new LayoutClickListener() {

				@Override
				public void layoutClick(LayoutClickEvent event) {
					if (event.getButton().compareTo(MouseButton.LEFT) == 0) {
						selectedMenuLayout = menuLayout;
						createQuestionCard(surveySection);
						sectionPanelLayout.iterator()
								.forEachRemaining(p -> p.removeStyleName(EsControlStyle.QUESTION_MENU_SELECTED));
						menuLayout.addStyleName(EsControlStyle.QUESTION_MENU_SELECTED);
					}
				}
			});
			return menuLayout;
		}

		private void createQuestionCard(SurveySection surveySection) {
			sectionLayout.removeAllComponents();

			sectionPanelLayout.iterator()
					.forEachRemaining(p -> p.removeStyleName(EsControlStyle.QUESTION_MENU_SELECTED));

			questionSectionPreviewLayout = new QuestionSectionPreviewLayout(sessionHolder);
			questionSectionPreviewLayout.setShowInfo(false);
			questionSectionPreviewLayout.setEditable(editAnswers);
			questionSectionPreviewLayout.setSurveySection(surveySection, surveyResponse);
			questionSectionPreviewLayout.setWidth("100%");
			sectionLayout.addComponent(questionSectionPreviewLayout);
		}

		@Override
		public void handleNewForm() throws FrameworkException {
			setWidth("-1px");
			activeFld.setValue(true);
			newFormGrid.setVisible(true);
			sectionMainLayout.setVisible(false);
			generateFormFld.setVisible(false);
			populateApplicationUserFld();

			surveyFld.addValueChangeListener(new ValueChangeListener<Object>() {

				@Override
				public void valueChange(ValueChangeEvent<Object> event) {
					try {
						populateApplicationUserFld();
					} catch (FrameworkException e) {
						logger.error(e.getMessage(), e);
						updateErrorContent(e);
					}
				}
			});

			BehaviorSubject<Object> surveyApplicationUserObserver = getReferenceKey(
					EsReferenceKey.SURVEY_APPLICATION_USER_OBSERVER);
			surveyApplicationUserObserver.subscribe(new Consumer<Object>() {

				@Override
				public void accept(Object t) throws Exception {
					populateApplicationUserFld();
				}
			});
		}

		protected void populateApplicationUserFld() throws FrameworkException {
			applicationUserFld.removeAllItems();
			Long surveyId = surveyFld.getItemId();
			if (surveyId != null) {
				List<ApplicationUser> applicationUsers = SurveyApplicationUserServiceFacade.get(UI.getCurrent())
						.findApplicationUserBySurvey(surveyId);
				applicationUserFld.setValues(applicationUsers);
			}

		}

		@SuppressWarnings("unchecked")
		@Override
		public SurveyResponse setFormValues(Object entity) throws FrameworkException {
			surveyResponse = (SurveyResponse) entity;

			createMenu();

			surveyFld.addValueChangeListener(new ValueChangeListener<Object>() {

				@Override
				public void valueChange(ValueChangeEvent<Object> event) {
					try {
						populateApplicationUserFld();
					} catch (FrameworkException e) {
						logger.error(e.getMessage(), e);
						updateErrorContent(e);
					}
				}
			});

			formNameFld.setValue(surveyResponse.getFormName());
			surveyFld.setValue(surveyResponse.getSurvey());
			applicationUserFld.setValue(surveyResponse.getApplicationUser());
			activeFld.setValue(surveyResponse.getActive());
			editAnswersSwitch.setVisible(true);
			editAnswersSwitch.setValue(editAnswers);

			editAnswersSwitch.addValueChangeListener(new ValueChangeListener<Boolean>() {

				@Override
				public void valueChange(ValueChangeEvent<Boolean> event) {
					editAnswers = !editAnswers;
					try {
						createMenu();
					} catch (FrameworkException e) {
						logger.error(e.getMessage(), e);
						updateErrorContent(e);
					}
					if (selectedMenuLayout != null) {
						SurveySection surveySection = (SurveySection) selectedMenuLayout.getData();
						createQuestionCard(surveySection);
						sectionPanelLayout.iterator()
								.forEachRemaining(p -> p.removeStyleName(EsControlStyle.QUESTION_MENU_SELECTED));
						selectedMenuLayout.addStyleName(EsControlStyle.QUESTION_MENU_SELECTED);
					}
				}
			});

			BehaviorSubject<Object> surveyApplicationUserObserver = getReferenceKey(
					EsReferenceKey.SURVEY_APPLICATION_USER_OBSERVER);
			surveyApplicationUserObserver.subscribe(new Consumer<Object>() {

				@Override
				public void accept(Object t) throws Exception {
					populateApplicationUserFld();
				}
			});

			OnDemandFileDownloader fileDownloader = new OnDemandFileDownloader(new OnDemandStreamResource() {

				private static final long serialVersionUID = 2210160695902329446L;

				@Override
				public InputStream getStream() {
					try {
						return new ByteArrayInputStream(fetchFile());
					} catch (IOException | FrameworkException e) {
						e.printStackTrace();
						return null;
					}

				}

				@Override
				public String getFilename() {
					return surveyResponse.getFormName() + ".pdf";
				}

				public byte[] fetchFile() throws IOException, FrameworkException {

					RestTemplate restTemplate = new RestTemplate();
					restTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter());

					HttpHeaders headers = new HttpHeaders();
					// headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));

					HttpEntity<String> entity = new HttpEntity<String>(headers);

					Long id = surveyResponse.getId();
					ConfigurationServiceFacade configurationServiceFacade = ConfigurationServiceFacade
							.get(UI.getCurrent());
					Configuration configuration = configurationServiceFacade
							.getByCode(EsControlConfigurationCodes.SURVEY_REPORT_SERVER);
					if (configuration != null && StringUtils.isNotBlank(configuration.getValue())) {
						String reportServerURL = configuration.getValue();
						ResponseEntity<byte[]> response = restTemplate.exchange(reportServerURL + "/report/elv1/" + id,
								HttpMethod.GET, entity, byte[].class);

						if (response.getStatusCode() == HttpStatus.OK) {
							return response.getBody();
						}
					}
					return new byte[0];
				}
			});
			fileDownloader.extend(generateFormFld);

			return surveyResponse;
		}

		@Override
		public Object getFormValues() throws FrameworkException {
			SurveyResponseVO vo = new SurveyResponseVO();
			vo.setId(surveyResponse == null ? null : surveyResponse.getId());
			vo.setFormName(formNameFld.getValue());
			vo.setSurveyId(surveyFld.getItemId());
			vo.setApplicationUserId(applicationUserFld.getItemId());
			vo.setActive(activeFld.getValue());
			return vo;
		}

	}

}
