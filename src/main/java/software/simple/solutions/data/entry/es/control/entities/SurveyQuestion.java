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
import software.simple.solutions.data.entry.es.control.constants.QuestionType;
import software.simple.solutions.framework.core.entities.MappedSuperClass;

@Audited
@AuditOverride(forClass = MappedSuperClass.class)
@Entity
@Table(name = EsControlTables.SURVEY_QUESTIONS_.NAME)
@DynamicUpdate(value = true)
@SelectBeforeUpdate(value = true)
public class SurveyQuestion extends MappedSuperClass {

	private static final long serialVersionUID = -8974022530355774576L;

	@Id
	@TableGenerator(name = "table", table = "sequences_", pkColumnName = "PK_NAME", valueColumnName = "PK_VALUE", initialValue = 1000000)
	@GeneratedValue(generator = "table", strategy = GenerationType.TABLE)
	@Column(name = ID_)
	private Long id;

	@Column(name = EsControlTables.SURVEY_QUESTIONS_.COLUMNS.ACTIVE_)
	private Boolean active;

	/**
	 * The {@link Survey} this question is linked to.
	 */
	@ManyToOne
	@JoinColumn(name = EsControlTables.SURVEY_QUESTIONS_.COLUMNS.SURVEY_ID_)
	private Survey survey;

	/**
	 * The actual question being asked should worded within here.
	 */
	@Column(name = EsControlTables.SURVEY_QUESTIONS_.COLUMNS.QUESTION_)
	private String question;

	/**
	 * To describe and give additional information concerning the question being
	 * asked.
	 */
	@Column(name = EsControlTables.SURVEY_QUESTIONS_.COLUMNS.QUESTION_DESCRIPTION_)
	private String questionDescription;

	/**
	 * The order of the question. Makes sure the question follow each other in
	 * the right order.
	 */
	@Column(name = EsControlTables.SURVEY_QUESTIONS_.COLUMNS.ORDER_)
	private Long order;

	/**
	 * Indicates the type of question.
	 * <ul>
	 * <li>{@link QuestionType#SINGLE}</li>
	 * <li>{@link QuestionType#DATE}</li>
	 * <li>{@link QuestionType#LENGTH_FT_INCH}</li>
	 * <li>{@link QuestionType#AREA_FT_INCH}</li>
	 * <li>{@link QuestionType#CHOICES}</li>
	 * <li>{@link QuestionType#MATRIX}</li>
	 * </ul>
	 */
	@Column(name = EsControlTables.SURVEY_QUESTIONS_.COLUMNS.QUESTION_TYPE_)
	private String questionType;

	/**
	 * Indicates whether this question is mandatory.
	 */
	@Column(name = EsControlTables.SURVEY_QUESTIONS_.COLUMNS.REQUIRED_)
	private Boolean required;

	/**
	 * Message to be shown if a mandatory question is not answered.
	 */
	@Column(name = EsControlTables.SURVEY_QUESTIONS_.COLUMNS.REQUIRED_ERROR_)
	private String requiredError;

	/**
	 * Indicates if the question has multiple answers that can be selected.
	 * Basically turning the question from a multiple choice to being able to
	 * choose more than on.
	 */
	@Column(name = EsControlTables.SURVEY_QUESTIONS_.COLUMNS.MULTIPLE_SELECTION_)
	private Boolean multipleSelection;

	@ManyToOne
	@JoinColumn(name = EsControlTables.SURVEY_QUESTIONS_.COLUMNS.SURVEY_SECTION_ID_)
	private SurveySection surveySection;

	@ManyToOne
	@JoinColumn(name = EsControlTables.SURVEY_QUESTIONS_.COLUMNS.SURVEY_GROUP_ID_)
	private SurveyGroup surveyGroup;

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

	public Survey getSurvey() {
		return survey;
	}

	public void setSurvey(Survey survey) {
		this.survey = survey;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public Long getOrder() {
		return order;
	}

	public void setOrder(Long order) {
		this.order = order;
	}

	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	public Boolean getRequired() {
		return required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

	public String getRequiredError() {
		return requiredError;
	}

	public void setRequiredError(String requiredError) {
		this.requiredError = requiredError;
	}

	public String getQuestionDescription() {
		return questionDescription;
	}

	public void setQuestionDescription(String questionDescription) {
		this.questionDescription = questionDescription;
	}

	/**
	 * @see SurveyQuestion#multipleSelection
	 */
	public Boolean getMultipleSelection() {
		return multipleSelection;
	}

	/**
	 * @see SurveyQuestion#multipleSelection
	 */
	public void setMultipleSelection(Boolean choiceType) {
		this.multipleSelection = choiceType;
	}

	public SurveySection getSurveySection() {
		return surveySection;
	}

	public void setSurveySection(SurveySection surveySection) {
		this.surveySection = surveySection;
	}

	public SurveyGroup getSurveyGroup() {
		return surveyGroup;
	}

	public void setSurveyGroup(SurveyGroup surveyGroup) {
		this.surveyGroup = surveyGroup;
	}

	@Override
	protected void customPreUpdate() {
		if (getMultipleSelection() == null) {
			setMultipleSelection(false);
		}
	}

}
