package software.simple.solutions.data.entry.es.control.rest.model;

import java.io.Serializable;

import software.simple.solutions.data.entry.es.control.entities.Survey;

public class SurveyModel implements Serializable {

	private Long id;
	private Boolean active;
	private String name;
	private String description;

	public SurveyModel() {
		super();
	}

	public SurveyModel(Survey survey) {
		this();
		this.active = survey.getActive();
		this.description = survey.getDescription();
		this.id = survey.getId();
		this.name = survey.getName();
	}

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

}
