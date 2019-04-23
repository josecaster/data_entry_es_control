package software.simple.solutions.data.entry.es.control.components;

import java.util.ArrayList;
import java.util.List;

import software.simple.solutions.data.entry.es.control.constants.Position;
import software.simple.solutions.data.entry.es.control.properties.SurveyQuestionProperty;
import software.simple.solutions.framework.core.components.CComboBox;
import software.simple.solutions.framework.core.pojo.ComboItem;
import software.simple.solutions.framework.core.util.PropertyResolver;

public class PositionSelect extends CComboBox {

	private static final long serialVersionUID = 8665850289294199446L;

	public PositionSelect() {
		List<ComboItem> comboItems = new ArrayList<ComboItem>();
		comboItems.add(new ComboItem(Position.BEFORE,
				PropertyResolver.getPropertyValueByLocale(SurveyQuestionProperty.QUESTION_ORDER_SITUATION_BEFORE)));
		comboItems.add(new ComboItem(Position.AFTER,
				PropertyResolver.getPropertyValueByLocale(SurveyQuestionProperty.QUESTION_ORDER_SITUATION_AFTER)));
		setItems(comboItems);
	}

}
