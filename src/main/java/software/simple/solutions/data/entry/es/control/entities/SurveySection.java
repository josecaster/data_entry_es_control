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
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;

import software.simple.solutions.data.entry.es.control.constants.EsControlTables;
import software.simple.solutions.data.entry.es.control.properties.SurveyProperty;
import software.simple.solutions.data.entry.es.control.properties.SurveySectionProperty;
import software.simple.solutions.framework.core.annotations.FilterFieldProperty;
import software.simple.solutions.framework.core.entities.MappedSuperClass;

@Audited
@AuditOverride(forClass = MappedSuperClass.class)
@Entity
@Table(name = EsControlTables.SURVEY_SECTIONS_.NAME)
@DynamicUpdate(value = true)
@SelectBeforeUpdate(value = true)
public class SurveySection extends MappedSuperClass {

	private static final long serialVersionUID = -8974022530355774576L;

	@Id
	@TableGenerator(name = "table", table = "sequences_", pkColumnName = "PK_NAME", valueColumnName = "PK_VALUE", initialValue = 1000000)
	@GeneratedValue(generator = "table", strategy = GenerationType.TABLE)
	@Column(name = ID_)
	@FilterFieldProperty(fieldProperty = SurveySectionProperty.ID)
	private Long id;

	@Column(name = EsControlTables.SURVEY_SECTIONS_.COLUMNS.ACTIVE_)
	private Boolean active;

	/**
	 * The {@link Survey} this question is linked to.
	 */
	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne
	@JoinColumn(name = EsControlTables.SURVEY_SECTIONS_.COLUMNS.SURVEY_ID_)
	@FilterFieldProperty(fieldProperty = SurveyProperty.ID)
	private Survey survey;

	/**
	 * The name of the group.
	 */
	@FilterFieldProperty(fieldProperty = SurveySectionProperty.NAME)
	@Column(name = EsControlTables.SURVEY_SECTIONS_.COLUMNS.CODE_)
	private String code;

	/**
	 * The name of the group.
	 */
	@FilterFieldProperty(fieldProperty = SurveySectionProperty.NAME)
	@Column(name = EsControlTables.SURVEY_SECTIONS_.COLUMNS.NAME_)
	private String name;

	/**
	 * A description of the group.
	 */
	@FilterFieldProperty(fieldProperty = SurveySectionProperty.DESCRIPTION)
	@Column(name = EsControlTables.SURVEY_SECTIONS_.COLUMNS.DESCRIPTION_)
	private String description;

	@FilterFieldProperty(fieldProperty = SurveySectionProperty.PINNED)
	@Column(name = EsControlTables.SURVEY_SECTIONS_.COLUMNS.PINNED_)
	private Boolean pinned;

	@FilterFieldProperty(fieldProperty = SurveySectionProperty.ENABLE_APPLICABILITY)
	@Column(name = EsControlTables.SURVEY_SECTIONS_.COLUMNS.ENABLE_APPLICABILITY_)
	private Boolean enableApplicability;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getPinned() {
		return pinned;
	}

	public void setPinned(Boolean pinned) {
		this.pinned = pinned;
	}

	@Override
	public String getCaption() {
		return name;
	}

	public Boolean getEnableApplicability() {
		return enableApplicability;
	}

	public void setEnableApplicability(Boolean enableApplicability) {
		this.enableApplicability = enableApplicability;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
