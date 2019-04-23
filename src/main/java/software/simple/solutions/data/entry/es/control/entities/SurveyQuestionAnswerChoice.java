package software.simple.solutions.data.entry.es.control.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;

import software.simple.solutions.data.entry.es.control.constants.Axis;
import software.simple.solutions.data.entry.es.control.constants.EsControlTables;
import software.simple.solutions.data.entry.es.control.constants.MatrixColumnType;
import software.simple.solutions.data.entry.es.control.constants.QuestionType;
import software.simple.solutions.framework.core.entities.MappedSuperClass;

@Audited
@AuditOverride(forClass = MappedSuperClass.class)
@Entity
@Table(name = EsControlTables.SURVEY_QUESTION_ANSWER_CHOICES_.NAME)
@DynamicUpdate(value = true)
@SelectBeforeUpdate(value = true)
public class SurveyQuestionAnswerChoice extends MappedSuperClass {

	private static final long serialVersionUID = -8974022530355774576L;

	@Id
	@TableGenerator(name = "table", table = "sequences_", pkColumnName = "PK_NAME", valueColumnName = "PK_VALUE", initialValue = 1000000)
	@GeneratedValue(generator = "table", strategy = GenerationType.TABLE)
	@Column(name = ID_)
	private Long id;

	@ManyToOne
	@JoinColumn(name = EsControlTables.SURVEY_QUESTION_ANSWER_CHOICES_.COLUMNS.SURVEY_QUESTION_ID_)
	private SurveyQuestion surveyQuestion;

	/**
	 * This is the question itself.
	 */
	@Column(name = EsControlTables.SURVEY_QUESTION_ANSWER_CHOICES_.COLUMNS.LABEL_)
	private String label;

	/**
	 * Indicate if the question is a:
	 * <ul>
	 * <li>single text question. {@link QuestionType#SINGLE}</li>
	 * <li>multiple choice with just one permissible answer.
	 * {@link QuestionType#CHOICES}</li>
	 * <li>multiple choice with just more than one permissible answer.
	 * {@link QuestionType#CHOICES}</li>
	 * <li>matrix with column and rows. {@link QuestionType#MATRIX}</li>
	 * </ul>
	 */
	@Column(name = EsControlTables.SURVEY_QUESTION_ANSWER_CHOICES_.COLUMNS.QUESTION_TYPE_)
	private String questionType;

	/**
	 * Indicates the dimension of the matrix.
	 * <ul>
	 * <li>A row. {@link Axis#ROW}</li>
	 * <li>A column. {@link Axis#COLUMN}</li>
	 * </ul>
	 */
	@Column(name = EsControlTables.SURVEY_QUESTION_ANSWER_CHOICES_.COLUMNS.AXIS_)
	private String axis;

	/**
	 * Indicates the type of field the column will carry.
	 * <ul>
	 * <li>A text field. {@link MatrixColumnType#TEXT}</li>
	 * <li>A whole number. {@link MatrixColumnType#WHOLE_NUMBER}</li>
	 * <li>A decimal number. {@link MatrixColumnType#DECIMAL_NUMBER}</li>
	 * <li>A single selection, which is to be compared to a radiogroup. Only one
	 * selection is possible. {@link MatrixColumnType#SINGLE_SELECTION}</li>
	 * <li>A multiple selection, which is to be compared to a group of
	 * checkboxes. Multiple selections are possible.
	 * {@link MatrixColumnType#MULTIPLE_SELECTION}</li>
	 * </ul>
	 * 
	 */
	@Column(name = EsControlTables.SURVEY_QUESTION_ANSWER_CHOICES_.COLUMNS.MATRIX_COLUMN_TYPE_)
	private String matrixColumnType;

	/**
	 * The order of the choices.
	 */
	@Column(name = EsControlTables.SURVEY_QUESTION_ANSWER_CHOICES_.COLUMNS.INDEX_)
	private Integer index;

	/**
	 * Used to validated the given answer on a minimum amount of characters.
	 */
	@Column(name = EsControlTables.SURVEY_QUESTION_ANSWER_CHOICES_.COLUMNS.MIN_LENGTH_)
	private Long minLength;

	/**
	 * Used to validated the given answer on a maximum amount of characters.
	 */
	@Column(name = EsControlTables.SURVEY_QUESTION_ANSWER_CHOICES_.COLUMNS.MAX_LENGTH_)
	private Long maxLength;

	/**
	 * Used to validated the given answer on a minimum numeric value.
	 */
	@Column(name = EsControlTables.SURVEY_QUESTION_ANSWER_CHOICES_.COLUMNS.MIN_VALUE_)
	private BigDecimal minValue;

	/**
	 * Used to validated the given answer on a maximum numeric value.
	 */
	@Column(name = EsControlTables.SURVEY_QUESTION_ANSWER_CHOICES_.COLUMNS.MAX_VALUE_)
	private BigDecimal maxValue;

	/**
	 * Used to validated the given answer on a minimum date value.
	 */
	@Column(name = EsControlTables.SURVEY_QUESTION_ANSWER_CHOICES_.COLUMNS.MIN_DATE_)
	private LocalDateTime minDate;

	/**
	 * Used to validated the given answer on a maximum date value.
	 */
	@Column(name = EsControlTables.SURVEY_QUESTION_ANSWER_CHOICES_.COLUMNS.MAX_DATE_)
	private LocalDateTime maxDate;

	/**
	 * Indicates whether this answer should be validated.
	 */
	@Column(name = EsControlTables.SURVEY_QUESTION_ANSWER_CHOICES_.COLUMNS.VALIDATE_)
	private Boolean validate;

	/**
	 * The message to be shown when this answer has failed validation.
	 */
	@Column(name = EsControlTables.SURVEY_QUESTION_ANSWER_CHOICES_.COLUMNS.VALIDATION_ERROR_)
	private String validationError;

	/**
	 * Indicates if the option has multiple answers that can be selected.
	 * 
	 */
	@Column(name = EsControlTables.SURVEY_QUESTION_ANSWER_CHOICES_.COLUMNS.MULTIPLE_SELECTION_)
	private Boolean multipleSelection;

	@Column(name = EsControlTables.SURVEY_QUESTION_ANSWER_CHOICES_.COLUMNS.IS_OTHER_)
	private Boolean isOther;

	@Column(name = EsControlTables.SURVEY_QUESTION_ANSWER_CHOICES_.COLUMNS.MAKE_SELECTED_QUESTION_REQUIRED_)
	private Long makeSelectedQuestionRequired;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SurveyQuestion getSurveyQuestion() {
		return surveyQuestion;
	}

	public void setSurveyQuestion(SurveyQuestion surveyQuestion) {
		this.surveyQuestion = surveyQuestion;
	}

	/**
	 * @see SurveyQuestionAnswerChoice#label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @see SurveyQuestionAnswerChoice#label
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @see SurveyQuestionAnswerChoice#axis
	 */
	public String getAxis() {
		return axis;
	}

	/**
	 * @see SurveyQuestionAnswerChoice#axis
	 */
	public void setAxis(String axis) {
		this.axis = axis;
	}

	/**
	 * @see SurveyQuestionAnswerChoice#questionType
	 */
	public String getQuestionType() {
		return questionType;
	}

	/**
	 * @see SurveyQuestionAnswerChoice#questionType
	 */
	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	/**
	 * @see SurveyQuestionAnswerChoice#matrixColumnType
	 */
	public String getMatrixColumnType() {
		return matrixColumnType;
	}

	/**
	 * @see SurveyQuestionAnswerChoice#matrixColumnType
	 */
	public void setMatrixColumnType(String matrixColumnType) {
		this.matrixColumnType = matrixColumnType;
	}

	/**
	 * @see SurveyQuestionAnswerChoice#index
	 */
	public Integer getIndex() {
		return index;
	}

	/**
	 * @see SurveyQuestionAnswerChoice#index
	 */
	public void setIndex(Integer index) {
		this.index = index;
	}

	/**
	 * @see SurveyQuestionAnswerChoice#minLength
	 */
	public Long getMinLength() {
		return minLength;
	}

	/**
	 * @see SurveyQuestionAnswerChoice#minLength
	 */
	public void setMinLength(Long minLength) {
		this.minLength = minLength;
	}

	/**
	 * @see SurveyQuestionAnswerChoice#maxLength
	 */
	public Long getMaxLength() {
		return maxLength;
	}

	/**
	 * @see SurveyQuestionAnswerChoice#maxLength
	 */
	public void setMaxLength(Long maxLength) {
		this.maxLength = maxLength;
	}

	/**
	 * @see SurveyQuestionAnswerChoice#minValue
	 */
	public BigDecimal getMinValue() {
		return minValue;
	}

	/**
	 * @see SurveyQuestionAnswerChoice#minValue
	 */
	public void setMinValue(BigDecimal minValue) {
		this.minValue = minValue;
	}

	/**
	 * @see SurveyQuestionAnswerChoice#maxValue
	 */
	public BigDecimal getMaxValue() {
		return maxValue;
	}

	/**
	 * @see SurveyQuestionAnswerChoice#maxValue
	 */
	public void setMaxValue(BigDecimal maxValue) {
		this.maxValue = maxValue;
	}

	/**
	 * @see SurveyQuestionAnswerChoice#minDate
	 */
	public LocalDateTime getMinDate() {
		return minDate;
	}

	/**
	 * @see SurveyQuestionAnswerChoice#minDate
	 */
	public void setMinDate(LocalDateTime minDate) {
		this.minDate = minDate;
	}

	/**
	 * @see SurveyQuestionAnswerChoice#maxDate
	 */
	public LocalDateTime getMaxDate() {
		return maxDate;
	}

	/**
	 * @see SurveyQuestionAnswerChoice#maxDate
	 */
	public void setMaxDate(LocalDateTime maxDate) {
		this.maxDate = maxDate;
	}

	/**
	 * @see SurveyQuestionAnswerChoice#validate
	 */
	public Boolean getValidate() {
		return validate;
	}

	/**
	 * @see SurveyQuestionAnswerChoice#validate
	 */
	public void setValidate(Boolean validate) {
		this.validate = validate;
	}

	/**
	 * @see SurveyQuestionAnswerChoice#validationError
	 */
	public String getValidationError() {
		return validationError;
	}

	/**
	 * @see SurveyQuestionAnswerChoice#validationError
	 */
	public void setValidationError(String validationError) {
		this.validationError = validationError;
	}

	@Override
	public Boolean getActive() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setActive(Boolean active) {
		// TODO Auto-generated method stub

	}

	public Boolean getMultipleSelection() {
		return multipleSelection;
	}

	public void setMultipleSelection(Boolean multipleSelection) {
		this.multipleSelection = multipleSelection;
	}

	public Boolean getIsOther() {
		return isOther;
	}

	public void setIsOther(Boolean isOther) {
		this.isOther = isOther;
	}

	public Long getMakeSelectedQuestionRequired() {
		return makeSelectedQuestionRequired;
	}

	public void setMakeSelectedQuestionRequired(Long makeSelectedQuestionRequired) {
		this.makeSelectedQuestionRequired = makeSelectedQuestionRequired;
	}

	@Override
	protected void customPreUpdate() {
		if (getIndex() == null) {
			setIndex(0);
		}
		if (getMultipleSelection() == null) {
			setMultipleSelection(false);
		}
	}
}
