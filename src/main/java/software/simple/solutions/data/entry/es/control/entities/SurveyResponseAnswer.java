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

	@ManyToOne
	@JoinColumn(name = EsControlTables.SURVEY_RESPONSE_ANSWER_.COLUMNS.SURVEY_RESPONSE_ID_)
	private SurveyResponse surveyResponse;

	@ManyToOne
	@JoinColumn(name = EsControlTables.SURVEY_RESPONSE_ANSWER_.COLUMNS.QUESTION_ID_)
	private SurveyQuestion surveyQuestion;

	@ManyToOne
	@JoinColumn(name = EsControlTables.SURVEY_RESPONSE_ANSWER_.COLUMNS.RESPONSE_TEXT_ID_)
	private SurveyResponseAnswerText surveyResponseText;

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

	public SurveyResponseAnswerText getSurveyResponseText() {
		return surveyResponseText;
	}

	public void setSurveyResponseText(SurveyResponseAnswerText surveyResponseText) {
		this.surveyResponseText = surveyResponseText;
	}

	public SurveyResponse getSurveyResponse() {
		return surveyResponse;
	}

	public void setSurveyResponse(SurveyResponse surveyResponse) {
		this.surveyResponse = surveyResponse;
	}

}
