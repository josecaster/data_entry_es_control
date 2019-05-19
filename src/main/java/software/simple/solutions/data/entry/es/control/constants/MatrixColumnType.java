package software.simple.solutions.data.entry.es.control.constants;

/**
 * Manages the type of fields a matrix can support within its column.
 * <ul>
 * <li>{@link MatrixColumnType#TEXT}</li>
 * <li>{@link MatrixColumnType#DATE}</li>
 * <li>{@link MatrixColumnType#WHOLE_NUMBER}</li>
 * <li>{@link MatrixColumnType#DECIMAL_NUMBER}</li>
 * <li>{@link MatrixColumnType#SINGLE_SELECTION}</li>
 * <li>{@link MatrixColumnType#MULTIPLE_SELECTION}</li>
 * </ul>
 * 
 * @author yusuf
 *
 */
public class MatrixColumnType {

	public static final String TEXT = "TEXT";
	public static final String DATE = "DATE";
	public static final String WHOLE_NUMBER = "WHOLE_NUMBER";
	public static final String DECIMAL_NUMBER = "DECIMAL_NUMBER";
	public static final String SINGLE_SELECTION = "SINGLE_SELECTION";
	public static final String MULTIPLE_SELECTION = "MULTIPLE_SELECTION";
	public static final String SINGLE_COMPOSITE_SELECTION = "SINGLE_COMPOSITE_SELECTION";
	public static final String MULTIPLE_COMPOSITE_SELECTION = "MULTIPLE_COMPOSITE_SELECTION";

}
