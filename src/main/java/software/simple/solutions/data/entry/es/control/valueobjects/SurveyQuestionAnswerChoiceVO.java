package software.simple.solutions.data.entry.es.control.valueobjects;

import software.simple.solutions.data.entry.es.control.entities.SurveyQuestionAnswerChoice;
import software.simple.solutions.framework.core.valueobjects.SuperVO;

public class SurveyQuestionAnswerChoiceVO extends SuperVO {

	private static final long serialVersionUID = -2008276944089219114L;

	private Long id;
	private Long surveyQuestionId;
	private String label;
	private String questionType;
	private String matrixColumnType;
	private Integer index;
	private Boolean multipleSelection;

	public SurveyQuestionAnswerChoiceVO() {
		super();
	}

	public SurveyQuestionAnswerChoiceVO(SurveyQuestionAnswerChoice surveyQuestionAnswerChoice) {
		this();
		this.id = surveyQuestionAnswerChoice.getId();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSurveyQuestionId() {
		return surveyQuestionId;
	}

	public void setSurveyQuestionId(Long surveyQuestionId) {
		this.surveyQuestionId = surveyQuestionId;
	}

	/**
	 * @see SurveyQuestionAnswerChoice#getLabel()
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @see SurveyQuestionAnswerChoice#getLabel()
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @see SurveyQuestionAnswerChoice#getQuestionType()
	 */
	public String getQuestionType() {
		return questionType;
	}

	/**
	 * @see SurveyQuestionAnswerChoice#getQuestionType()
	 */
	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	/**
	 * @see SurveyQuestionAnswerChoice#getMatrixColumnType()
	 */
	public String getMatrixColumnType() {
		return matrixColumnType;
	}

	/**
	 * @see SurveyQuestionAnswerChoice#getMatrixColumnType()
	 */
	public void setMatrixColumnType(String matrixColumnType) {
		this.matrixColumnType = matrixColumnType;
	}

	/**
	 * @see SurveyQuestionAnswerChoice#getIndex()
	 */
	public Integer getIndex() {
		return index;
	}

	/**
	 * @see SurveyQuestionAnswerChoice#getIndex()
	 */
	public void setIndex(Integer index) {
		this.index = index;
	}

	/**
	 * Indicates whether the choice supports multiple of a single answer.
	 * 
	 * @return
	 */
	public Boolean getMultipleSelection() {
		return multipleSelection;
	}

	public void setMultipleSelection(Boolean multipleSelection) {
		this.multipleSelection = multipleSelection;
	}

}
