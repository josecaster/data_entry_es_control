package software.simple.solutions.data.entry.es.control.components;

import java.util.ArrayList;
import java.util.List;

import software.simple.solutions.data.entry.es.control.constants.MatrixColumnType;
import software.simple.solutions.data.entry.es.control.properties.MatrixColumnTypeProperty;
import software.simple.solutions.framework.core.components.CComboBox;
import software.simple.solutions.framework.core.pojo.ComboItem;
import software.simple.solutions.framework.core.util.PropertyResolver;

public class MatrixColumnTypeSelect extends CComboBox {

	private static final long serialVersionUID = 8665850289294199446L;

	public MatrixColumnTypeSelect() {
		List<ComboItem> comboItems = new ArrayList<ComboItem>();
		comboItems.add(new ComboItem(MatrixColumnType.TEXT,
				PropertyResolver.getPropertyValueByLocale(MatrixColumnTypeProperty.TEXT)));
//		comboItems.add(new ComboItem(MatrixColumnType.WHOLE_NUMBER,
//				PropertyResolver.getPropertyValueByLocale(MatrixColumnTypeProperty.WHOLE_NUMBER)));
//		comboItems.add(new ComboItem(MatrixColumnType.DECIMAL_NUMBER,
//				PropertyResolver.getPropertyValueByLocale(MatrixColumnTypeProperty.DECIMAL_NUMBER)));
//		comboItems.add(new ComboItem(MatrixColumnType.SINGLE_SELECTION,
//				PropertyResolver.getPropertyValueByLocale(MatrixColumnTypeProperty.SINGLE_SELECTION)));
//		comboItems.add(new ComboItem(MatrixColumnType.MULTIPLE_SELECTION,
//				PropertyResolver.getPropertyValueByLocale(MatrixColumnTypeProperty.MULTIPLE_SELECTION)));
		comboItems.add(new ComboItem(MatrixColumnType.SINGLE_COMPOSITE_SELECTION,
				PropertyResolver.getPropertyValueByLocale(MatrixColumnTypeProperty.SINGLE_COMPOSITE_SELECTION)));
		comboItems.add(new ComboItem(MatrixColumnType.MULTIPLE_COMPOSITE_SELECTION,
				PropertyResolver.getPropertyValueByLocale(MatrixColumnTypeProperty.MULTIPLE_COMPOSITE_SELECTION)));
		setItems(comboItems);
	}

}
