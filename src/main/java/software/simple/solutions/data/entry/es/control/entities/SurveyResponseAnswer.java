package software.simple.solutions.data.entry.es.control.entities;

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

import software.simple.solutions.data.entry.es.control.constants.EsControlTables;
import software.simple.solutions.framework.core.entities.MappedSuperClass;

@Audited
@AuditOverride(forClass = MappedSuperClass.class)
@Entity
@Table(name = EsControlTables.SURVEY_RESPONSE_ANSWER_.NAME)
@DynamicUpdate(value = true)
@SelectBeforeUpdate(value = true)
public class SurveyResponseAnswer extends MappedSuperClass {

	private static final long serialVersionUID = -8974022530355774576L;

	@Id
	@TableGenerator(name = "table", table = "sequences_", pkColumnName = "PK_NAME", valueColumnName = "PK_VALUE", initialValue = 1000000)
	@GeneratedValue(generator = "table", strategy = GenerationType.TABLE)
	@Column(name = ID_)
	private Long id;

	@Column(name = EsControlTables.SURVEY_RESPONSE_ANSWER_.COLUMNS.ACTIVE_)
	private Boolean active;

	@Column(name = EsControlTables.SURVEY_RESPONSE_ANSWER_.COLUMNS.UNIQUE_ID_)
	private String uniqueId;

	@ManyToOne
	@JoinColumn(name = EsControlTables.SURVEY_RESPONSE_ANSWER_.COLUMNS.SURVEY_RESPONSE_ID_)
	private SurveyResponse surveyResponse;

	@ManyToOne
	@JoinColumn(name = EsControlTables.SURVEY_RESPONSE_ANSWER_.COLUMNS.QUESTION_ID_)
	private SurveyQuestion surveyQuestion;

	@ManyToOne
	@JoinColumn(name = EsControlTables.SURVEY_RESPONSE_ANSWER_.COLUMNS.QUESTION_CHOICE_ROW_ID_)
	private SurveyQuestionAnswerChoice surveyQuestionAnswerChoiceRow;

	@ManyToOne
	@JoinColumn(name = EsControlTables.SURVEY_RESPONSE_ANSWER_.COLUMNS.QUESTION_CHOICE_COLUMN_ID_)
	private SurveyQuestionAnswerChoice surveyQuestionAnswerChoiceColumn;

	@ManyToOne
	@JoinColumn(name = EsControlTables.SURVEY_RESPONSE_ANSWER_.COLUMNS.QUESTION_CHOICE_SELECTION_ID_)
	private SurveyQuestionAnswerChoiceSelection surveyQuestionAnswerChoiceSelection;

	@Column(name = EsControlTables.SURVEY_RESPONSE_ANSWER_.COLUMNS.QUESTION_TYPE_)
	private String questionType;

	@Column(name = EsControlTables.SURVEY_RESPONSE_ANSWER_.COLUMNS.MATRIX_COLUMN_TYPE_)
	private String matrixColumnType;

	@Column(name = EsControlTables.SURVEY_RESPONSE_ANSWER_.COLUMNS.RESPONSE_TEXT_)
	private String responseText;

	@Column(name = EsControlTables.SURVEY_RESPONSE_ANSWER_.COLUMNS.OTHER_VALUE_)
	private String otherValue;

	@Column(name = EsControlTables.SURVEY_RESPONSE_ANSWER_.COLUMNS.SELECTED_)
	private Boolean selected;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public SurveyQuestion getSurveyQuestion() {
		return surveyQuestion;
	}

	public void setSurveyQuestion(SurveyQuestion surveyQuestion) {
		this.surveyQuestion = surveyQuestion;
	}

	public SurveyResponse getSurveyResponse() {
		return surveyResponse;
	}

	public void setSurveyResponse(SurveyResponse surveyResponse) {
		this.surveyResponse = surveyResponse;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public SurveyQuestionAnswerChoice getSurveyQuestionAnswerChoiceRow() {
		return surveyQuestionAnswerChoiceRow;
	}

	public void setSurveyQuestionAnswerChoiceRow(SurveyQuestionAnswerChoice surveyQuestionAnswerChoiceRow) {
		this.surveyQuestionAnswerChoiceRow = surveyQuestionAnswerChoiceRow;
	}

	public SurveyQuestionAnswerChoice getSurveyQuestionAnswerChoiceColumn() {
		return surveyQuestionAnswerChoiceColumn;
	}

	public void setSurveyQuestionAnswerChoiceColumn(SurveyQuestionAnswerChoice surveyQuestionAnswerChoiceColumn) {
		this.surveyQuestionAnswerChoiceColumn = surveyQuestionAnswerChoiceColumn;
	}

	public SurveyQuestionAnswerChoiceSelection getSurveyQuestionAnswerChoiceSelection() {
		return surveyQuestionAnswerChoiceSelection;
	}

	public void setSurveyQuestionAnswerChoiceSelection(
			SurveyQuestionAnswerChoiceSelection surveyQuestionAnswerChoiceSelection) {
		this.surveyQuestionAnswerChoiceSelection = surveyQuestionAnswerChoiceSelection;
	}

	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	public String getMatrixColumnType() {
		return matrixColumnType;
	}

	public void setMatrixColumnType(String matrixColumnType) {
		this.matrixColumnType = matrixColumnType;
	}

	public String getResponseText() {
		return responseText;
	}

	public void setResponseText(String responseText) {
		this.responseText = responseText;
	}

	public String getOtherValue() {
		return otherValue;
	}

	public void setOtherValue(String otherValue) {
		this.otherValue = otherValue;
	}

	public Boolean getSelected() {
		return selected;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
	}

}
