package software.simple.solutions.data.entry.es.control.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;

import software.simple.solutions.data.entry.es.control.constants.EsControlTables;
import software.simple.solutions.data.entry.es.control.properties.SurveyProperty;
import software.simple.solutions.framework.core.annotations.FilterFieldProperty;
import software.simple.solutions.framework.core.entities.MappedSuperClass;

@Audited
@AuditOverride(forClass = MappedSuperClass.class)
@Entity
@Table(name = EsControlTables.SURVEYS_.NAME)
@DynamicUpdate(value = true)
@SelectBeforeUpdate(value = true)
public class Survey extends MappedSuperClass {

	private static final long serialVersionUID = -8974022530355774576L;

	@Id
	@TableGenerator(name = "table", table = "sequences_", pkColumnName = "PK_NAME", valueColumnName = "PK_VALUE", initialValue = 1000000)
	@GeneratedValue(generator = "table", strategy = GenerationType.TABLE)
	@Column(name = ID_)
	@FilterFieldProperty(fieldProperty = SurveyProperty.ID)
	private Long id;

	@Column(name = EsControlTables.SURVEYS_.COLUMNS.ACTIVE_)
	private Boolean active;

	@FilterFieldProperty(fieldProperty = SurveyProperty.NAME)
	@Column(name = EsControlTables.SURVEYS_.COLUMNS.NAME_)
	private String name;

	@FilterFieldProperty(fieldProperty = SurveyProperty.DESCRIPTION)
	@Column(name = EsControlTables.SURVEYS_.COLUMNS.DESCRIPTION_)
	private String description;

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

	@Override
	public String getCaption() {
		return name;
	}

}
