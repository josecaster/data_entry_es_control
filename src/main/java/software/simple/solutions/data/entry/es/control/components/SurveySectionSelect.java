package software.simple.solutions.data.entry.es.control.components;

import java.util.List;

import software.simple.solutions.data.entry.es.control.entities.SurveySection;
import software.simple.solutions.data.entry.es.control.properties.SurveySectionProperty;
import software.simple.solutions.data.entry.es.control.service.ISurveySectionService;
import software.simple.solutions.framework.core.components.CComboBox;
import software.simple.solutions.framework.core.components.MessageWindowHandler;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.pojo.ComboItem;
import software.simple.solutions.framework.core.util.ContextProvider;
import software.simple.solutions.framework.core.util.PropertyResolver;

public class SurveySectionSelect extends CComboBox {

	private static final long serialVersionUID = 8665850289294199446L;

	public SurveySectionSelect() {
		refresh();
		setEmptySelectionCaption(PropertyResolver.getPropertyValueByLocale(SurveySectionProperty.ALL));
	}

	public void refresh() {
		ISurveySectionService surveyQuestionSectionService = ContextProvider
				.getBean(ISurveySectionService.class);
		List<ComboItem> items;
		try {
			items = surveyQuestionSectionService.getForListing(SurveySection.class, true);
			items.add(createFilterByNone());
			setItems(items);
		} catch (FrameworkException e) {
			new MessageWindowHandler(e);
		}
	}

	private ComboItem createFilterByNone() {
		ComboItem comboItem = new ComboItem(-1,
				PropertyResolver.getPropertyValueByLocale(SurveySectionProperty.UN_SECTIONED));
		return comboItem;
	}

}
