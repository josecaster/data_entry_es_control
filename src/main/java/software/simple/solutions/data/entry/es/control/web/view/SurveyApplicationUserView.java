package software.simple.solutions.data.entry.es.control.web.view;

import software.simple.solutions.data.entry.es.control.components.lookup.SurveyLookUpField;
import software.simple.solutions.data.entry.es.control.constants.EsControlTables;
import software.simple.solutions.data.entry.es.control.entities.SurveyApplicationUser;
import software.simple.solutions.data.entry.es.control.properties.SurveyApplicationUserProperty;
import software.simple.solutions.data.entry.es.control.properties.SurveySectionProperty;
import software.simple.solutions.data.entry.es.control.service.ISurveyApplicationUserService;
import software.simple.solutions.data.entry.es.control.valueobjects.SurveyApplicationUserVO;
import software.simple.solutions.framework.core.components.CGridLayout;
import software.simple.solutions.framework.core.components.FilterView;
import software.simple.solutions.framework.core.components.FormView;
import software.simple.solutions.framework.core.components.select.ActiveSelect;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.util.ComponentUtil;
import software.simple.solutions.framework.core.web.BasicTemplate;
import software.simple.solutions.framework.core.web.lookup.ApplicationUserLookUpField;

public class SurveyApplicationUserView extends BasicTemplate<SurveyApplicationUser> {

	private static final long serialVersionUID = 6503015064562511801L;

	public SurveyApplicationUserView() {
		setEntityClass(SurveyApplicationUser.class);
		setServiceClass(ISurveyApplicationUserService.class);
		setFilterClass(Filter.class);
		setFormClass(Form.class);
		setEntityReferenceKey(EsControlTables.SURVEY_APPLICATION_USERS_.NAME);
	}

	@Override
	public void setUpCustomColumns() {
		addContainerProperty(source -> (source.getSurvey() == null ? null : source.getSurvey().getName()),
				SurveyApplicationUserProperty.SURVEY);
		addContainerProperty(
				source -> (source.getApplicationUser() == null ? null : source.getApplicationUser().getUsername()),
				SurveyApplicationUserProperty.APPLICATION_USER);
	}

	/**
	 * Filter implementation
	 * 
	 * @author yusuf
	 *
	 */
	public class Filter extends FilterView {

		private static final long serialVersionUID = 117780868467918033L;

		private SurveyLookUpField surveyFld;
		private ApplicationUserLookUpField applicationUserLookUpField;

		@Override
		public void executeBuild() {

			surveyFld = addField(SurveyLookUpField.class, SurveyApplicationUserProperty.SURVEY, 0, 0);

			applicationUserLookUpField = addField(ApplicationUserLookUpField.class, SurveyApplicationUserProperty.APPLICATION_USER, 0, 1);

		}

		@Override
		public Object getCriteria() {
			SurveyApplicationUserVO vo = new SurveyApplicationUserVO();
			vo.setSurveyId(surveyFld.getItemId());
			vo.setApplicationUserId(applicationUserLookUpField.getItemId());
			return vo;
		}
	}

	public class Form extends FormView {

		private static final long serialVersionUID = 6109727427163734676L;

		private CGridLayout formGrid;
		private SurveyLookUpField surveyFld;
		private ApplicationUserLookUpField applicationUserLookUpField;

		private SurveyApplicationUser surveyApplicationUser;

		@Override
		public void executeBuild() {
			formGrid = ComponentUtil.createGrid();
			addComponent(formGrid);

			surveyFld = formGrid.addField(SurveyLookUpField.class, SurveyApplicationUserProperty.SURVEY, 0, 0);
			surveyFld.handleForParentEntity(getParentEntity());

			applicationUserLookUpField = formGrid.addField(ApplicationUserLookUpField.class,
					SurveyApplicationUserProperty.APPLICATION_USER, 0, 1);
			applicationUserLookUpField.handleForParentEntity(getParentEntity());

		}

		@Override
		public void handleNewForm() throws FrameworkException {
		}

		@SuppressWarnings("unchecked")
		@Override
		public SurveyApplicationUser setFormValues(Object entity) throws FrameworkException {
			surveyApplicationUser = (SurveyApplicationUser) entity;
			surveyFld.setValue(surveyApplicationUser.getSurvey());
			applicationUserLookUpField.setValue(surveyApplicationUser.getApplicationUser());
			return surveyApplicationUser;
		}

		@Override
		public Object getFormValues() throws FrameworkException {
			SurveyApplicationUserVO vo = new SurveyApplicationUserVO();

			vo.setId(surveyApplicationUser == null ? null : surveyApplicationUser.getId());
			vo.setSurveyId(surveyFld.getItemId());
			vo.setApplicationUserId(applicationUserLookUpField.getItemId());
			return vo;
		}
	}

}
