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
import software.simple.solutions.data.entry.es.control.entities.SurveyGroup;
import software.simple.solutions.data.entry.es.control.properties.SurveyGroupProperty;
import software.simple.solutions.data.entry.es.control.service.ISurveyQuestionGroupService;
import software.simple.solutions.data.entry.es.control.valueobjects.SurveyGroupVO;
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

public class SurveyGroupView extends BasicTemplate<SurveyGroup> {

	private static final long serialVersionUID = 6503015064562511801L;

	public SurveyGroupView() {
		setEntityClass(SurveyGroup.class);
		setServiceClass(ISurveyQuestionGroupService.class);
		setFilterClass(Filter.class);
		setFormClass(Form.class);
		setEntityReferenceKey(EsControlTables.SURVEY_GROUPS_.NAME);

		getUpdateObserver().subscribe(new Consumer<Object>() {

			@Override
			public void accept(Object t) throws Exception {
				BehaviorSubject<SurveyGroup> surveyGroupObserver = getReferenceKey(
						EsReferenceKey.SURVEY_GROUP_OBSERVER);
				surveyGroupObserver.onNext((SurveyGroup) t);
			}
		});
	}

	@Override
	public void setUpCustomColumns() {
		addContainerProperty(SurveyGroup::getName, SurveyGroupProperty.NAME);
		addContainerProperty(SurveyGroup::getDescription, SurveyGroupProperty.DESCRIPTION);
		addComponentContainerProperty(new ValueProvider<SurveyGroup, CCheckBox>() {

			@Override
			public CCheckBox apply(SurveyGroup source) {
				CCheckBox pinnedFld = new CCheckBox();
				pinnedFld.setEnabled(false);
				pinnedFld.setValue(source.getPinned());
				return pinnedFld;
			}
		}, SurveyGroupProperty.PINNED);
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

			surveyFld = addField(SurveyLookUpField.class, SurveyGroupProperty.SURVEY, 0, 0);

			nameFld = addField(CStringIntervalLayout.class, SurveyGroupProperty.NAME, 0, 1);

			descriptionFld = addField(CStringIntervalLayout.class, SurveyGroupProperty.DESCRIPTION, 1, 0);

			pinnedFld = addField(ActiveSelect.class, SurveyGroupProperty.PINNED, 1, 1);

		}

		@Override
		public Object getCriteria() {
			SurveyGroupVO vo = new SurveyGroupVO();
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

		private SurveyGroup surveyQuestionGroup;
		private Boolean pinned = false;

		@Override
		public void executeBuild() {
			formGrid = ComponentUtil.createGrid();
			addComponent(formGrid);

			surveyFld = formGrid.addField(SurveyLookUpField.class, SurveyGroupProperty.SURVEY, 0, 0);
			surveyFld.handleForParentEntity(getParentEntity());

			nameFld = formGrid.addField(CTextField.class, SurveyGroupProperty.NAME, 0, 1);

			descriptionFld = formGrid.addField(CTextArea.class, SurveyGroupProperty.DESCRIPTION, 0, 2);

			activeFld = formGrid.addField(CCheckBox.class, SystemProperty.SYSTEM_ENTITY_ACTIVE, 1, 0);

			enableApplicabilityFld = formGrid.addField(CCheckBox.class, SurveyGroupProperty.ENABLE_APPLICABILITY, 1, 1);

			pinnedFld = formGrid.addField(CButton.class, SurveyGroupProperty.PINNED, 2, 0);
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
		public SurveyGroup setFormValues(Object entity) throws FrameworkException {
			surveyQuestionGroup = (SurveyGroup) entity;
			nameFld.setValue(surveyQuestionGroup.getName());
			descriptionFld.setValue(surveyQuestionGroup.getDescription());
			activeFld.setValue(surveyQuestionGroup.getActive());
			surveyFld.setValue(surveyQuestionGroup.getSurvey());
			enableApplicabilityFld.setValue(surveyQuestionGroup.getEnableApplicability());

			pinned = surveyQuestionGroup.getPinned();
			setPinned(pinned);
			return surveyQuestionGroup;
		}

		@Override
		public Object getFormValues() throws FrameworkException {
			SurveyGroupVO vo = new SurveyGroupVO();

			vo.setId(surveyQuestionGroup == null ? null : surveyQuestionGroup.getId());
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
