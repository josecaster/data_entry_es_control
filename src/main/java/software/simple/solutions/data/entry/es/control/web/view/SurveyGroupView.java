package software.simple.solutions.data.entry.es.control.web.view;

import software.simple.solutions.data.entry.es.control.components.lookup.SurveyLookUpField;
import software.simple.solutions.data.entry.es.control.constants.EsControlTables;
import software.simple.solutions.data.entry.es.control.constants.EsReferenceKey;
import software.simple.solutions.data.entry.es.control.entities.SurveyGroup;
import software.simple.solutions.data.entry.es.control.properties.SurveyGroupProperty;
import software.simple.solutions.data.entry.es.control.service.ISurveyGroupService;
import software.simple.solutions.data.entry.es.control.valueobjects.SurveyGroupVO;
import software.simple.solutions.framework.core.components.CCheckBox;
import software.simple.solutions.framework.core.components.CGridLayout;
import software.simple.solutions.framework.core.components.CTextArea;
import software.simple.solutions.framework.core.components.CTextField;
import software.simple.solutions.framework.core.components.FilterView;
import software.simple.solutions.framework.core.components.FormView;
import software.simple.solutions.framework.core.components.filter.CStringIntervalLayout;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.SystemProperty;
import software.simple.solutions.framework.core.util.ColumnSort;
import software.simple.solutions.framework.core.util.ComponentUtil;
import software.simple.solutions.framework.core.util.SortingHelper;
import software.simple.solutions.framework.core.web.BasicTemplate;

public class SurveyGroupView extends BasicTemplate<SurveyGroup> {

	private static final long serialVersionUID = 6503015064562511801L;

	public SurveyGroupView() {
		setEntityClass(SurveyGroup.class);
		setServiceClass(ISurveyGroupService.class);
		setFilterClass(Filter.class);
		setFormClass(Form.class);
		setEntityReferenceKey(EsControlTables.SURVEY_GROUPS_.NAME);
		setUpdateObserverReferenceKey(EsReferenceKey.SURVEY_GROUP_OBSERVER);
	}

	@Override
	public void setUpCustomColumns() {
		addContainerProperty(SurveyGroup::getName, SurveyGroupProperty.NAME);
		addContainerProperty(SurveyGroup::getDescription, SurveyGroupProperty.DESCRIPTION);
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

		@Override
		public void executeBuild() {

			surveyFld = addField(SurveyLookUpField.class, SurveyGroupProperty.SURVEY, 0, 0);

			nameFld = addField(CStringIntervalLayout.class, SurveyGroupProperty.NAME, 0, 1);

			descriptionFld = addField(CStringIntervalLayout.class, SurveyGroupProperty.DESCRIPTION, 1, 0);
		}

		@Override
		public Object getCriteria() {
			SurveyGroupVO vo = new SurveyGroupVO();
			vo.setSurveyId(surveyFld.getItemId());
			vo.setNameInterval(nameFld.getValue());
			vo.setDescriptionInterval(descriptionFld.getValue());
			SortingHelper sortingHelper = new SortingHelper();
			sortingHelper.addColumnSort(new ColumnSort(SurveyGroupProperty.ID, SortingHelper.DESCENDING));
			vo.setSortingHelper(sortingHelper);
			return vo;
		}
	}

	public class Form extends FormView {

		private static final long serialVersionUID = 6109727427163734676L;

		private CGridLayout formGrid;
		private CTextField nameFld;
		private CTextArea descriptionFld;
		private CCheckBox activeFld;
		private SurveyLookUpField surveyFld;

		private SurveyGroup surveyGroup;

		@Override
		public void executeBuild() {
			formGrid = ComponentUtil.createGrid();
			addComponent(formGrid);

			surveyFld = formGrid.addField(SurveyLookUpField.class, SurveyGroupProperty.SURVEY, 0, 0);
			surveyFld.handleForParentEntity(getParentEntity());
			surveyFld.setRequiredIndicatorVisible(true);

			nameFld = formGrid.addField(CTextField.class, SurveyGroupProperty.NAME, 0, 1);
			nameFld.setRequiredIndicatorVisible(true);

			descriptionFld = formGrid.addField(CTextArea.class, SurveyGroupProperty.DESCRIPTION, 0, 2);

			activeFld = formGrid.addField(CCheckBox.class, SystemProperty.SYSTEM_ENTITY_ACTIVE, 1, 0);
		}

		@Override
		public void handleNewForm() throws FrameworkException {
			activeFld.setValue(true);
		}

		@SuppressWarnings("unchecked")
		@Override
		public SurveyGroup setFormValues(Object entity) throws FrameworkException {
			surveyGroup = (SurveyGroup) entity;
			nameFld.setValue(surveyGroup.getName());
			descriptionFld.setValue(surveyGroup.getDescription());
			activeFld.setValue(surveyGroup.getActive());
			surveyFld.setValue(surveyGroup.getSurvey());
			return surveyGroup;
		}

		@Override
		public Object getFormValues() throws FrameworkException {
			SurveyGroupVO vo = new SurveyGroupVO();

			vo.setId(surveyGroup == null ? null : surveyGroup.getId());
			vo.setSurveyId(surveyFld.getItemId());
			vo.setName(nameFld.getValue());
			vo.setDescription(descriptionFld.getValue());
			vo.setActive(activeFld.getValue());
			return vo;
		}
	}

}
