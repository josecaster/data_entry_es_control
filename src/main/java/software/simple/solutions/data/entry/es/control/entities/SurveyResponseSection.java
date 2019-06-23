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
@Table(name = EsControlTables.SURVEY_RESPONSE_SECTION_.NAME)
@DynamicUpdate(value = true)
@SelectBeforeUpdate(value = true)
public class SurveyResponseSection extends MappedSuperClass {

	private static final long serialVersionUID = -8974022530355774576L;

	@Id
	@TableGenerator(name = "table", table = "sequences_", pkColumnName = "PK_NAME", valueColumnName = "PK_VALUE", initialValue = 1000000)
	@GeneratedValue(generator = "table", strategy = GenerationType.TABLE)
	@Column(name = ID_)
	private Long id;

	@Column(name = EsControlTables.SURVEY_RESPONSE_SECTION_.COLUMNS.ACTIVE_)
	private Boolean active;

	@Column(name = EsControlTables.SURVEY_RESPONSE_SECTION_.COLUMNS.UNIQUE_ID_)
	private String uniqueId;

	@ManyToOne
	@JoinColumn(name = EsControlTables.SURVEY_RESPONSE_SECTION_.COLUMNS.SURVEY_RESPONSE_ID_)
	private SurveyResponse surveyResponse;

	@ManyToOne
	@JoinColumn(name = EsControlTables.SURVEY_RESPONSE_SECTION_.COLUMNS.SURVEY_SECTION_ID_)
	private SurveySection surveySection;

	@Column(name = EsControlTables.SURVEY_RESPONSE_SECTION_.COLUMNS.NOT_APPLICABLE_)
	private Boolean notApplicable;

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

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public SurveyResponse getSurveyResponse() {
		return surveyResponse;
	}

	public void setSurveyResponse(SurveyResponse surveyResponse) {
		this.surveyResponse = surveyResponse;
	}

	public SurveySection getSurveySection() {
		return surveySection;
	}

	public void setSurveySection(SurveySection surveySection) {
		this.surveySection = surveySection;
	}

	public Boolean getNotApplicable() {
		return notApplicable;
	}

	public void setNotApplicable(Boolean notApplicable) {
		this.notApplicable = notApplicable;
	}

}
