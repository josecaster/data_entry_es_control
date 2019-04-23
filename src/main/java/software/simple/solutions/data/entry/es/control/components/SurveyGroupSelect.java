package software.simple.solutions.data.entry.es.control.components;

import java.util.List;

import software.simple.solutions.data.entry.es.control.entities.SurveyGroup;
import software.simple.solutions.data.entry.es.control.properties.SurveyGroupProperty;
import software.simple.solutions.data.entry.es.control.service.ISurveyQuestionGroupService;
import software.simple.solutions.framework.core.components.CComboBox;
import software.simple.solutions.framework.core.components.MessageWindowHandler;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.pojo.ComboItem;
import software.simple.solutions.framework.core.util.ContextProvider;
import software.simple.solutions.framework.core.util.PropertyResolver;

public class SurveyGroupSelect extends CComboBox {

	private static final long serialVersionUID = 8665850289294199446L;

	public SurveyGroupSelect() {
		refresh();
		setEmptySelectionCaption(PropertyResolver.getPropertyValueByLocale(SurveyGroupProperty.ALL));
	}

	public void refresh() {
		ISurveyQuestionGroupService surveyQuestionGroupService = ContextProvider
				.getBean(ISurveyQuestionGroupService.class);
		List<ComboItem> items;
		try {
			items = surveyQuestionGroupService.getForListing(SurveyGroup.class, true);
			items.add(createFilterByNone());
			setItems(items);
		} catch (FrameworkException e) {
			new MessageWindowHandler(e);
		}
	}

	private ComboItem createFilterByNone() {
		ComboItem comboItem = new ComboItem(-1,
				PropertyResolver.getPropertyValueByLocale(SurveyGroupProperty.UN_GROUPED));
		return comboItem;
	}

}
