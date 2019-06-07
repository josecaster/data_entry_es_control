package software.simple.solutions.data.entry.es.control.web.view;

import org.vaadin.teemu.switchui.Switch;

import com.vaadin.data.ValueProvider;

import software.simple.solutions.data.entry.es.control.components.lookup.SurveyLookUpField;
import software.simple.solutions.data.entry.es.control.constants.EsControlTables;
import software.simple.solutions.data.entry.es.control.constants.EsReferenceKey;
import software.simple.solutions.data.entry.es.control.entities.SurveySection;
import software.simple.solutions.data.entry.es.control.properties.SurveySectionProperty;
import software.simple.solutions.data.entry.es.control.service.ISurveySectionService;
import software.simple.solutions.data.entry.es.control.valueobjects.SurveySectionVO;
import software.simple.solutions.framework.core.components.CCheckBox;
import software.simple.solutions.framework.core.components.CGridLayout;
import software.simple.solutions.framework.core.components.CTextArea;
import software.simple.solutions.framework.core.components.CTextField;
import software.simple.solutions.framework.core.components.FilterView;
import software.simple.solutions.framework.core.components.FormView;
import software.simple.solutions.framework.core.components.filter.CStringIntervalLayout;
import software.simple.solutions.framework.core.components.select.ActiveSelect;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.SystemProperty;
import software.simple.solutions.framework.core.util.ColumnSort;
import software.simple.solutions.framework.core.util.ComponentUtil;
import software.simple.solutions.framework.core.util.SortingHelper;
import software.simple.solutions.framework.core.web.BasicTemplate;

public class SurveySectionView extends BasicTemplate<SurveySection> {

	private static final long serialVersionUID = 6503015064562511801L;

	public SurveySectionView() {
		setEntityClass(SurveySection.class);
		setServiceClass(ISurveySectionService.class);
		setFilterClass(Filter.class);
		setFormClass(Form.class);
		setEntityReferenceKey(EsControlTables.SURVEY_SECTIONS_.NAME);
		setUpdateObserverReferenceKey(EsReferenceKey.SURVEY_SECTION_OBSERVER);
	}

	@Override
	public void setUpCustomColumns() {
		addContainerProperty(SurveySection::getCode, SurveySectionProperty.CODE);
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

		private CStringIntervalLayout codeFld;
		private CStringIntervalLayout nameFld;
		private CStringIntervalLayout descriptionFld;
		private SurveyLookUpField surveyFld;
		private ActiveSelect pinnedFld;

		@Override
		public void executeBuild() {

			surveyFld = addField(SurveyLookUpField.class, SurveySectionProperty.SURVEY, 0, 0);

			codeFld = addField(CStringIntervalLayout.class, SurveySectionProperty.CODE, 0, 1);

			nameFld = addField(CStringIntervalLayout.class, SurveySectionProperty.NAME, 0, 2);

			descriptionFld = addField(CStringIntervalLayout.class, SurveySectionProperty.DESCRIPTION, 1, 0);

			pinnedFld = addField(ActiveSelect.class, SurveySectionProperty.PINNED, 1, 1);

		}

		@Override
		public Object getCriteria() {
			SurveySectionVO vo = new SurveySectionVO();
			vo.setSurveyId(surveyFld.getItemId());
			vo.setCodeInterval(codeFld.getValue());
			vo.setNameInterval(nameFld.getValue());
			vo.setDescriptionInterval(descriptionFld.getValue());
			vo.setPinned(pinnedFld.getItemId());
			SortingHelper sortingHelper = new SortingHelper();
			sortingHelper.addColumnSort(new ColumnSort(SurveySectionProperty.ID, SortingHelper.DESCENDING));
			vo.setSortingHelper(sortingHelper);
			return vo;
		}
	}

	public class Form extends FormView {

		private static final long serialVersionUID = 6109727427163734676L;

		private CGridLayout formGrid;
		private CTextField codeFld;
		private CTextField nameFld;
		private CTextArea descriptionFld;
		private CCheckBox activeFld;
		private CCheckBox enableApplicabilityFld;
		private Switch pinnedFld;
		private SurveyLookUpField surveyFld;

		private SurveySection surveySection;
		// private Boolean pinned = false;

		@Override
		public void executeBuild() {
			formGrid = ComponentUtil.createGrid();
			addComponent(formGrid);

			surveyFld = formGrid.addField(SurveyLookUpField.class, SurveySectionProperty.SURVEY, 0, 0);
			surveyFld.handleForParentEntity(getParentEntity());
			surveyFld.setRequiredIndicatorVisible(true);

			codeFld = formGrid.addField(CTextField.class, SurveySectionProperty.CODE, 0, 1);
			codeFld.setRequiredIndicatorVisible(true);

			nameFld = formGrid.addField(CTextField.class, SurveySectionProperty.NAME, 0, 2);
			nameFld.setRequiredIndicatorVisible(true);

			descriptionFld = formGrid.addField(CTextArea.class, SurveySectionProperty.DESCRIPTION, 0, 3);

			activeFld = formGrid.addField(CCheckBox.class, SystemProperty.SYSTEM_ENTITY_ACTIVE, 1, 0);

			enableApplicabilityFld = formGrid.addField(CCheckBox.class, SurveySectionProperty.ENABLE_APPLICABILITY, 1,
					1);

			pinnedFld = formGrid.addField(Switch.class, SurveySectionProperty.PINNED, 2, 0);
			pinnedFld.setAnimationEnabled(true);
			pinnedFld.addStyleName("compact");
		}

		@Override
		public void handleNewForm() throws FrameworkException {
			activeFld.setValue(true);
		}

		@SuppressWarnings("unchecked")
		@Override
		public SurveySection setFormValues(Object entity) throws FrameworkException {
			surveySection = (SurveySection) entity;
			codeFld.setValue(surveySection.getCode());
			nameFld.setValue(surveySection.getName());
			descriptionFld.setValue(surveySection.getDescription());
			activeFld.setValue(surveySection.getActive());
			surveyFld.setValue(surveySection.getSurvey());
			enableApplicabilityFld.setValue(surveySection.getEnableApplicability());

			pinnedFld.setValue(surveySection.getPinned());
			return surveySection;
		}

		@Override
		public Object getFormValues() throws FrameworkException {
			SurveySectionVO vo = new SurveySectionVO();

			vo.setId(surveySection == null ? null : surveySection.getId());
			vo.setSurveyId(surveyFld.getItemId());
			vo.setCode(codeFld.getValue());
			vo.setName(nameFld.getValue());
			vo.setDescription(descriptionFld.getValue());
			vo.setActive(activeFld.getValue());
			vo.setPinned(pinnedFld.getValue());
			vo.setEnableApplicability(enableApplicabilityFld.getValue());
			return vo;
		}
	}

}
