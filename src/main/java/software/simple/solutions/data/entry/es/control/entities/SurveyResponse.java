package software.simple.solutions.data.entry.es.control.entities;

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
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;

import software.simple.solutions.data.entry.es.control.constants.EsControlTables;
import software.simple.solutions.data.entry.es.control.properties.SurveyProperty;
import software.simple.solutions.data.entry.es.control.properties.SurveyQuestionAnswerChoiceProperty;
import software.simple.solutions.data.entry.es.control.properties.SurveyResponseProperty;
import software.simple.solutions.framework.core.annotations.FilterFieldProperties;
import software.simple.solutions.framework.core.annotations.FilterFieldProperty;
import software.simple.solutions.framework.core.entities.ApplicationUser;
import software.simple.solutions.framework.core.entities.MappedSuperClass;
import software.simple.solutions.framework.core.properties.ApplicationUserProperty;

@Audited
@AuditOverride(forClass = MappedSuperClass.class)
@Entity
@Table(name = EsControlTables.SURVEY_RESPONSE_.NAME)
@DynamicUpdate(value = true)
@SelectBeforeUpdate(value = true)
public class SurveyResponse extends MappedSuperClass {

	private static final long serialVersionUID = -8974022530355774576L;
	
	public static final String VAR_APPLICATION_USER = "applicationUser";

	@Id
	@TableGenerator(name = "table", table = "sequences_", pkColumnName = "PK_NAME", valueColumnName = "PK_VALUE", initialValue = 1000000)
	@GeneratedValue(generator = "table", strategy = GenerationType.TABLE)
	@Column(name = ID_)
	@FilterFieldProperty(fieldProperty = SurveyResponseProperty.ID)
	private Long id;

	@Column(name = EsControlTables.SURVEY_RESPONSE_.COLUMNS.ACTIVE_)
	private Boolean active;

	@Column(name = EsControlTables.SURVEY_RESPONSE_.COLUMNS.UNIQUE_ID_)
	private String uniqueId;

	@FilterFieldProperty(fieldProperty = SurveyResponseProperty.FORM_NAME)
	@Column(name = EsControlTables.SURVEY_RESPONSE_.COLUMNS.FORM_NAME_)
	private String formName;

	@Column(name = EsControlTables.SURVEY_RESPONSE_.COLUMNS.CREATED_ON_)
	private LocalDateTime createdOn;

	@FilterFieldProperties(fieldProperties = { @FilterFieldProperty(fieldProperty = SurveyProperty.ID),
			@FilterFieldProperty(fieldProperty = SurveyProperty.NAME),
			@FilterFieldProperty(fieldProperty = SurveyProperty.DESCRIPTION) })
	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne
	@JoinColumn(name = EsControlTables.SURVEY_RESPONSE_.COLUMNS.SURVEY_ID_)
	private Survey survey;

	@FilterFieldProperties(fieldProperties = { @FilterFieldProperty(fieldProperty = ApplicationUserProperty.USERNAME) })
	@ManyToOne
	@JoinColumn(name = EsControlTables.SURVEY_RESPONSE_.COLUMNS.APPLICATION_USER_ID_)
	private ApplicationUser applicationUser;

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

	public ApplicationUser getApplicationUser() {
		return applicationUser;
	}

	public void setApplicationUser(ApplicationUser applicationUser) {
		this.applicationUser = applicationUser;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

}
