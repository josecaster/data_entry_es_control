package software.simple.solutions.data.entry.es.control.components;

import software.simple.solutions.data.entry.es.control.properties.SurveySectionProperty;
import software.simple.solutions.framework.core.components.CComboBox;
import software.simple.solutions.framework.core.util.PropertyResolver;

public class SurveySectionSelect extends CComboBox {

	private static final long serialVersionUID = 8665850289294199446L;

	public SurveySectionSelect() {
		// refresh();
		setEmptySelectionCaption(PropertyResolver.getPropertyValueByLocale(SurveySectionProperty.ALL));
	}

}
