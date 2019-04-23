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
@Table(name = EsControlTables.SURVEY_RESPONSE_ANSWER_TEXT_.NAME)
@DynamicUpdate(value = true)
@SelectBeforeUpdate(value = true)
public class SurveyResponseAnswerText extends MappedSuperClass {

	private static final long serialVersionUID = -8974022530355774576L;

	@Id
	@TableGenerator(name = "table", table = "sequences_", pkColumnName = "PK_NAME", valueColumnName = "PK_VALUE", initialValue = 1000000)
	@GeneratedValue(generator = "table", strategy = GenerationType.TABLE)
	@Column(name = ID_)
	private Long id;

	@Column(name = EsControlTables.SURVEY_RESPONSE_ANSWER_TEXT_.COLUMNS.ACTIVE_)
	private Boolean active;

	@ManyToOne
	@JoinColumn(name = EsControlTables.SURVEY_RESPONSE_ANSWER_TEXT_.COLUMNS.SURVEY_ID_)
	private Survey survey;

	@ManyToOne
	@JoinColumn(name = EsControlTables.SURVEY_RESPONSE_ANSWER_TEXT_.COLUMNS.QUESTION_ID_)
	private SurveyQuestion question;

	@ManyToOne
	@JoinColumn(name = EsControlTables.SURVEY_RESPONSE_ANSWER_TEXT_.COLUMNS.RESPONSE_ID_)
	private SurveyResponse surveyResponse;

	@Column(name = EsControlTables.SURVEY_RESPONSE_ANSWER_TEXT_.COLUMNS.RESPONSE_)
	private String response;

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

	public SurveyQuestion getQuestion() {
		return question;
	}

	public void setQuestion(SurveyQuestion question) {
		this.question = question;
	}

	public SurveyResponse getSurveyResponse() {
		return surveyResponse;
	}

	public void setSurveyResponse(SurveyResponse surveyResponse) {
		this.surveyResponse = surveyResponse;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

}
