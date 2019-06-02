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
import software.simple.solutions.data.entry.es.control.properties.SurveyApplicationUserProperty;
import software.simple.solutions.data.entry.es.control.properties.SurveyProperty;
import software.simple.solutions.framework.core.annotations.FilterFieldProperty;
import software.simple.solutions.framework.core.entities.ApplicationUser;
import software.simple.solutions.framework.core.entities.MappedSuperClass;
import software.simple.solutions.framework.core.properties.ApplicationUserProperty;

@Audited
@AuditOverride(forClass = MappedSuperClass.class)
@Entity
@Table(name = EsControlTables.SURVEY_APPLICATION_USERS_.NAME)
@DynamicUpdate(value = true)
@SelectBeforeUpdate(value = true)
public class SurveyApplicationUser extends MappedSuperClass {

	private static final long serialVersionUID = -8974022530355774576L;

	@Id
	@TableGenerator(name = "table", table = "sequences_", pkColumnName = "PK_NAME", valueColumnName = "PK_VALUE", initialValue = 1000000)
	@GeneratedValue(generator = "table", strategy = GenerationType.TABLE)
	@Column(name = ID_)
	@FilterFieldProperty(fieldProperty = SurveyApplicationUserProperty.ID)
	private Long id;

	/**
	 * The {@link Survey} this question is linked to.
	 */
	@NotFound(action=NotFoundAction.IGNORE)
	@ManyToOne
	@JoinColumn(name = EsControlTables.SURVEY_APPLICATION_USERS_.COLUMNS.SURVEY_ID_)
	@FilterFieldProperty(fieldProperty = SurveyProperty.ID)
	private Survey survey;

	@ManyToOne
	@JoinColumn(name = EsControlTables.SURVEY_APPLICATION_USERS_.COLUMNS.APPLICATION_USER_ID_)
	@FilterFieldProperty(fieldProperty = ApplicationUserProperty.ID)
	private ApplicationUser applicationUser;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
