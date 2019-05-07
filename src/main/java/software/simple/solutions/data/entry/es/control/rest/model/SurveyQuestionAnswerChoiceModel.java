package software.simple.solutions.data.entry.es.control.rest.model;

import java.io.Serializable;
import java.math.BigDecimal;

import software.simple.solutions.data.entry.es.control.entities.SurveyQuestionAnswerChoice;
import software.simple.solutions.framework.core.constants.DateConstant;

public class SurveyQuestionAnswerChoiceModel implements Serializable{
	private Long id;
	private Long surveyQuestionId;
	private String label;
	private String questionType;
	private String axis;
	private String matrixColumnType;
	private Integer index;
	private Long minLength;
	private Long maxLength;
	private BigDecimal minValue;
	private BigDecimal maxValue;
	private String minDate;
	private String maxDate;
	private Boolean validate;
	private String validationError;
	private Boolean multipleSelection;
	private Boolean isOther;
	private Long makeSelectedQuestionRequired;
	private Long makeSelectedGroupRequired;

	public SurveyQuestionAnswerChoiceModel() {
		super();
	}

	public SurveyQuestionAnswerChoiceModel(SurveyQuestionAnswerChoice surveyQuestionAnswerChoice) {
		this();
		this.axis = surveyQuestionAnswerChoice.getAxis();
		this.id = surveyQuestionAnswerChoice.getId();
		this.index = surveyQuestionAnswerChoice.getIndex();
		this.isOther = surveyQuestionAnswerChoice.getIsOther();
		this.label = surveyQuestionAnswerChoice.getLabel();
		this.makeSelectedGroupRequired = surveyQuestionAnswerChoice.getMakeSelectedGroupRequired();
		this.makeSelectedQuestionRequired = surveyQuestionAnswerChoice.getMakeSelectedQuestionRequired();
		this.matrixColumnType = surveyQuestionAnswerChoice.getMatrixColumnType();
		this.maxDate = surveyQuestionAnswerChoice.getMaxDate() == null ? null
				: DateConstant.SIMPLE_DATE_FORMAT.format(surveyQuestionAnswerChoice.getMaxDate());
		this.maxLength = surveyQuestionAnswerChoice.getMaxLength();
		this.maxValue = surveyQuestionAnswerChoice.getMaxValue();
		this.minDate = surveyQuestionAnswerChoice.getMinDate() == null ? null
				: DateConstant.SIMPLE_DATE_FORMAT.format(surveyQuestionAnswerChoice.getMinDate());
		this.minLength = surveyQuestionAnswerChoice.getMinLength();
		this.minValue = surveyQuestionAnswerChoice.getMinValue();
		this.multipleSelection = surveyQuestionAnswerChoice.getMultipleSelection();
		this.questionType = surveyQuestionAnswerChoice.getQuestionType();
		this.surveyQuestionId = surveyQuestionAnswerChoice.getSurveyQuestion() == null ? null
				: surveyQuestionAnswerChoice.getSurveyQuestion().getId();
		this.validate = surveyQuestionAnswerChoice.getValidate();
		this.validationError = surveyQuestionAnswerChoice.getValidationError();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSurveyQuestionId() {
		return surveyQuestionId;
	}

	public void setSurveyQuestionId(Long surveyQuestionId) {
		this.surveyQuestionId = surveyQuestionId;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	public String getAxis() {
		return axis;
	}

	public void setAxis(String axis) {
		this.axis = axis;
	}

	public String getMatrixColumnType() {
		return matrixColumnType;
	}

	public void setMatrixColumnType(String matrixColumnType) {
		this.matrixColumnType = matrixColumnType;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public Long getMinLength() {
		return minLength;
	}

	public void setMinLength(Long minLength) {
		this.minLength = minLength;
	}

	public Long getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(Long maxLength) {
		this.maxLength = maxLength;
	}

	public BigDecimal getMinValue() {
		return minValue;
	}

	public void setMinValue(BigDecimal minValue) {
		this.minValue = minValue;
	}

	public BigDecimal getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(BigDecimal maxValue) {
		this.maxValue = maxValue;
	}

	public String getMinDate() {
		return minDate;
	}

	public void setMinDate(String minDate) {
		this.minDate = minDate;
	}

	public String getMaxDate() {
		return maxDate;
	}

	public void setMaxDate(String maxDate) {
		this.maxDate = maxDate;
	}

	public Boolean getValidate() {
		return validate;
	}

	public void setValidate(Boolean validate) {
		this.validate = validate;
	}

	public String getValidationError() {
		return validationError;
	}

	public void setValidationError(String validationError) {
		this.validationError = validationError;
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

	public Long getMakeSelectedGroupRequired() {
		return makeSelectedGroupRequired;
	}

	public void setMakeSelectedGroupRequired(Long makeSelectedGroupRequired) {
		this.makeSelectedGroupRequired = makeSelectedGroupRequired;
	}

}
