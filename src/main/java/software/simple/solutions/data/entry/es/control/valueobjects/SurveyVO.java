package software.simple.solutions.data.entry.es.control.valueobjects;

import software.simple.solutions.data.entry.es.control.properties.SurveyProperty;
import software.simple.solutions.framework.core.annotations.FilterFieldProperty;
import software.simple.solutions.framework.core.pojo.StringInterval;
import software.simple.solutions.framework.core.valueobjects.SuperVO;

public class SurveyVO extends SuperVO {

	private static final long serialVersionUID = -2008276944089219114L;

	private Long id;
	private Boolean active;
	private String name;
	private String description;

	@FilterFieldProperty(fieldProperty = SurveyProperty.NAME)
	private StringInterval nameInterval;
	@FilterFieldProperty(fieldProperty = SurveyProperty.DESCRIPTION)
	private StringInterval descriptionInterval;
	
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

	public StringInterval getNameInterval() {
		return nameInterval;
	}

	public void setNameInterval(StringInterval nameInterval) {
		this.nameInterval = nameInterval;
	}

	public StringInterval getDescriptionInterval() {
		return descriptionInterval;
	}

	public void setDescriptionInterval(StringInterval descriptionInterval) {
		this.descriptionInterval = descriptionInterval;
	}

}
