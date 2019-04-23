package software.simple.solutions.data.entry.es.control.valueobjects;

import software.simple.solutions.framework.core.valueobjects.SuperVO;

public class SurveyQuestionAnswerChoiceSelectionVO extends SuperVO {

	private static final long serialVersionUID = -2008276944089219114L;

	private Long id;
	private Long surveyQuestionAnswerChoiceId;
	private String label;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSurveyQuestionAnswerChoiceId() {
		return surveyQuestionAnswerChoiceId;
	}

	public void setSurveyQuestionAnswerChoiceId(Long surveyQuestionAnswerChoiceId) {
		this.surveyQuestionAnswerChoiceId = surveyQuestionAnswerChoiceId;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
