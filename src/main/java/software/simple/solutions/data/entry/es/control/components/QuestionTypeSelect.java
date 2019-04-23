package software.simple.solutions.data.entry.es.control.components;

import java.util.ArrayList;
import java.util.List;

import software.simple.solutions.data.entry.es.control.constants.QuestionType;
import software.simple.solutions.data.entry.es.control.properties.QuestionTypeProperty;
import software.simple.solutions.framework.core.components.CComboBox;
import software.simple.solutions.framework.core.pojo.ComboItem;
import software.simple.solutions.framework.core.util.PropertyResolver;

public class QuestionTypeSelect extends CComboBox {

	private static final long serialVersionUID = 8665850289294199446L;

	public QuestionTypeSelect() {
		List<ComboItem> comboItems = new ArrayList<ComboItem>();
		comboItems.add(new ComboItem(QuestionType.SINGLE,
				PropertyResolver.getPropertyValueByLocale(QuestionTypeProperty.SINGLE)));
		comboItems.add(new ComboItem(QuestionType.CHOICES,
				PropertyResolver.getPropertyValueByLocale(QuestionTypeProperty.CHOICES)));
		comboItems.add(new ComboItem(QuestionType.MATRIX,
				PropertyResolver.getPropertyValueByLocale(QuestionTypeProperty.MATRIX)));
		setItems(comboItems);
	}

}
