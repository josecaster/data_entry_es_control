package software.simple.solutions.data.entry.es.control.web.view;

import com.vaadin.data.ValueProvider;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;

import io.reactivex.functions.Consumer;
import io.reactivex.subjects.BehaviorSubject;
import software.simple.solutions.data.entry.es.control.components.lookup.SurveyLookUpField;
import software.simple.solutions.data.entry.es.control.constants.EsControlTables;
import software.simple.solutions.data.entry.es.control.constants.EsReferenceKey;
import software.simple.solutions.data.entry.es.control.entities.SurveySection;
import software.simple.solutions.data.entry.es.control.properties.SurveySectionProperty;
import software.simple.solutions.data.entry.es.control.service.ISurveyQuestionSectionService;
import software.simple.solutions.data.entry.es.control.valueobjects.SurveySectionVO;
import software.simple.solutions.framework.core.components.CButton;
import software.simple.solutions.framework.core.components.CCheckBox;
import software.simple.solutions.framework.core.components.CGridLayout;
import software.simple.solutions.framework.core.components.CTextArea;
import software.simple.solutions.framework.core.components.CTextField;
import software.simple.solutions.framework.core.components.FilterView;
import software.simple.solutions.framework.core.components.FormView;
import software.simple.solutions.framework.core.components.filter.CStringIntervalLayout;
import software.simple.solutions.framework.core.components.select.ActiveSelect;
import software.simple.solutions.framework.core.constants.Style;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.icons.CxodeIcons;
import software.simple.solutions.framework.core.properties.SystemProperty;
import software.simple.solutions.framework.core.util.ComponentUtil;
import software.simple.solutions.framework.core.web.BasicTemplate;

public class SurveySectionView extends BasicTemplate<SurveySection> {

	private static final long serialVersionUID = 6503015064562511801L;

	public SurveySectionView() {
		setEntityClass(SurveySection.class);
		setServiceClass(ISurveyQuestionSectionService.class);
		setFilterClass(Filter.class);
		setFormClass(Form.class);
		setEntityReferenceKey(EsControlTables.SURVEY_SECTIONS_.NAME);

		getUpdateObserver().subscribe(new Consumer<Object>() {

			@Override
			public void accept(Object t) throws Exception {
				BehaviorSubject<SurveySection> surveySectionObserver = getReferenceKey(
						EsReferenceKey.SURVEY_SECTION_OBSERVER);
				surveySectionObserver.onNext((SurveySection) t);
			}
		});
	}

	@Override
	public void setUpCustomColumns() {
		addContainerProperty(SurveySection::getName, SurveySectionProperty.NAME);
		addContainerProperty(SurveySection::getDescription, SurveySectionProperty.DESCRIPTION);
		addComponentContainerProperty(new ValueProvider<SurveySection, CCheckBox>() {

			@Override
			public CCheckBox apply(SurveySection source) {
				CCheckBox pinnedFld = new CCheckBox();
				pinnedFld.setEnabled(false);
				pinnedFld.setValue(source.getPinned());
				return pinnedFld;
			}
		}, SurveySectionProperty.PINNED);
	}

	/**
	 * Filter implementation
	 * 
	 * @author yusuf
	 *
	 */
	public class Filter extends FilterView {

		private static final long serialVersionUID = 117780868467918033L;

		private CStringIntervalLayout nameFld;
		private CStringIntervalLayout descriptionFld;
		private SurveyLookUpField surveyFld;
		private ActiveSelect pinnedFld;

		@Override
		public void executeBuild() {

			surveyFld = addField(SurveyLookUpField.class, SurveySectionProperty.SURVEY, 0, 0);

			nameFld = addField(CStringIntervalLayout.class, SurveySectionProperty.NAME, 0, 1);

			descriptionFld = addField(CStringIntervalLayout.class, SurveySectionProperty.DESCRIPTION, 1, 0);

			pinnedFld = addField(ActiveSelect.class, SurveySectionProperty.PINNED, 1, 1);

		}

		@Override
		public Object getCriteria() {
			SurveySectionVO vo = new SurveySectionVO();
			vo.setSurveyId(surveyFld.getItemId());
			vo.setNameInterval(nameFld.getValue());
			vo.setDescriptionInterval(descriptionFld.getValue());
			vo.setPinned(pinnedFld.getItemId());
			return vo;
		}
	}

	public class Form extends FormView {

		private static final long serialVersionUID = 6109727427163734676L;

		private CGridLayout formGrid;
		private CTextField nameFld;
		private CTextArea descriptionFld;
		private CCheckBox activeFld;
		private CCheckBox enableApplicabilityFld;
		private CButton pinnedFld;
		private SurveyLookUpField surveyFld;

		private SurveySection surveySection;
		private Boolean pinned = false;

		@Override
		public void executeBuild() {
			formGrid = ComponentUtil.createGrid();
			addComponent(formGrid);

			surveyFld = formGrid.addField(SurveyLookUpField.class, SurveySectionProperty.SURVEY, 0, 0);
			surveyFld.handleForParentEntity(getParentEntity());

			nameFld = formGrid.addField(CTextField.class, SurveySectionProperty.NAME, 0, 1);

			descriptionFld = formGrid.addField(CTextArea.class, SurveySectionProperty.DESCRIPTION, 0, 2);

			activeFld = formGrid.addField(CCheckBox.class, SystemProperty.SYSTEM_ENTITY_ACTIVE, 1, 0);

			enableApplicabilityFld = formGrid.addField(CCheckBox.class, SurveySectionProperty.ENABLE_APPLICABILITY, 1, 1);

			pinnedFld = formGrid.addField(CButton.class, SurveySectionProperty.PINNED, 2, 0);
			pinnedFld.addStyleName(ValoTheme.BUTTON_BORDERLESS);
			pinnedFld.addStyleName(Style.NO_PADDING);
			pinnedFld.addStyleName(Style.RESIZED_ICON);
			pinnedFld.setIcon(CxodeIcons.UNPIN);
			pinnedFld.addClickListener(new ClickListener() {

				@Override
				public void buttonClick(ClickEvent event) {
					setPinned(!pinned);
				}
			});
		}

		private void setPinned(Boolean pinned) {
			this.pinned = pinned;
			if (this.pinned == null) {
				this.pinned = false;
			}
			if (this.pinned) {
				pinnedFld.setIcon(CxodeIcons.PIN);
			} else {
				pinnedFld.setIcon(CxodeIcons.UNPIN);
			}
		}

		@Override
		public void handleNewForm() throws FrameworkException {
			activeFld.setValue(true);
		}

		@SuppressWarnings("unchecked")
		@Override
		public SurveySection setFormValues(Object entity) throws FrameworkException {
			surveySection = (SurveySection) entity;
			nameFld.setValue(surveySection.getName());
			descriptionFld.setValue(surveySection.getDescription());
			activeFld.setValue(surveySection.getActive());
			surveyFld.setValue(surveySection.getSurvey());
			enableApplicabilityFld.setValue(surveySection.getEnableApplicability());

			pinned = surveySection.getPinned();
			setPinned(pinned);
			return surveySection;
		}

		@Override
		public Object getFormValues() throws FrameworkException {
			SurveySectionVO vo = new SurveySectionVO();

			vo.setId(surveySection == null ? null : surveySection.getId());
			vo.setSurveyId(surveyFld.getItemId());
			vo.setName(nameFld.getValue());
			vo.setDescription(descriptionFld.getValue());
			vo.setActive(activeFld.getValue());
			vo.setPinned(pinned);
			vo.setEnableApplicability(enableApplicabilityFld.getValue());
			return vo;
		}
	}

}
