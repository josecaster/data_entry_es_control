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
@Table(name = EsControlTables.SURVEY_QUESTION_ANSWER_CHOICE_SELECTIONS_.NAME)
@DynamicUpdate(value = true)
@SelectBeforeUpdate(value = true)
public class SurveyQuestionAnswerChoiceSelection extends MappedSuperClass {

	private static final long serialVersionUID = -8974022530355774576L;

	@Id
	@TableGenerator(name = "table", table = "sequences_", pkColumnName = "PK_NAME", valueColumnName = "PK_VALUE", initialValue = 1000000)
	@GeneratedValue(generator = "table", strategy = GenerationType.TABLE)
	@Column(name = ID_)
	private Long id;

	@ManyToOne
	@JoinColumn(name = EsControlTables.SURVEY_QUESTION_ANSWER_CHOICE_SELECTIONS_.COLUMNS.SURVEY_QUESTION_ANSWER_CHOICE_ID_)
	private SurveyQuestionAnswerChoice surveyQuestionAnswerChoice;

	/**
	 * This is the question itself.
	 */
	@Column(name = EsControlTables.SURVEY_QUESTION_ANSWER_CHOICE_SELECTIONS_.COLUMNS.LABEL_)
	private String label;

	@Column(name = EsControlTables.SURVEY_QUESTION_ANSWER_CHOICE_SELECTIONS_.COLUMNS.INDEX_)
	private Integer index;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SurveyQuestionAnswerChoice getSurveyQuestionAnswerChoice() {
		return surveyQuestionAnswerChoice;
	}

	public void setSurveyQuestionAnswerChoice(SurveyQuestionAnswerChoice surveyQuestionAnswerChoice) {
		this.surveyQuestionAnswerChoice = surveyQuestionAnswerChoice;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
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

}
