package software.simple.solutions.data.entry.es.control.service;

import java.math.BigDecimal;
import java.util.List;

import software.simple.solutions.data.entry.es.control.constants.MatrixColumnType;
import software.simple.solutions.data.entry.es.control.constants.QuestionType;
import software.simple.solutions.data.entry.es.control.entities.SurveyQuestionAnswerChoice;
import software.simple.solutions.data.entry.es.control.valueobjects.SurveyQuestionAnswerChoiceVO;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.service.ISuperService;

public interface ISurveyQuestionAnswerChoiceService extends ISuperService {

	List<SurveyQuestionAnswerChoice> findBySurveyQuestion(Long surveyQuestionId) throws FrameworkException;

	List<SurveyQuestionAnswerChoice> findBySurveyQuestion(Long surveyQuestionId, String axis) throws FrameworkException;

	/**
	 * Creates a new row. Typically created for:
	 * <ul>
	 * <li>{@link QuestionType#CHOICES}</li>
	 * <li>{@link QuestionType#MATRIX}</li>
	 * </ul>
	 * 
	 * @param surveyQuestionId
	 * @param questionType
	 * @param index
	 *            TODO
	 * @return
	 * @throws FrameworkException
	 */
	SurveyQuestionAnswerChoice createNewRow(Long surveyQuestionId, String questionType, Integer index)
			throws FrameworkException;

	/**
	 * Creates a new column. Typically created for:
	 * <ul>
	 * <li>{@link QuestionType#MATRIX}</li>
	 * </ul>
	 * 
	 * @param surveyQuestionId
	 * @param questionType
	 * @param index
	 * @return
	 * @throws FrameworkException
	 */
	SurveyQuestionAnswerChoice createNewColumn(Long surveyQuestionId, String questionType, Integer index)
			throws FrameworkException;

	/**
	 * Updates the label of an option. Can be described as the header of a
	 * column or a row.
	 * 
	 * @param surveyQuestionAnswerChoiceVO
	 * @throws FrameworkException
	 */
	void updateLabel(SurveyQuestionAnswerChoiceVO surveyQuestionAnswerChoiceVO) throws FrameworkException;

	/**
	 * Updates the value of the matrix column type. This value defines the type
	 * of field the column of a matrix will support. {@link MatrixColumnType}
	 * 
	 * @param surveyQuestionAnswerChoiceVO
	 * @throws FrameworkException
	 */
	void updateMatrixColumnType(SurveyQuestionAnswerChoiceVO surveyQuestionAnswerChoiceVO) throws FrameworkException;

	/**
	 * Updates the question type.
	 * {@link SurveyQuestionAnswerChoice#getQuestionType()}
	 * 
	 * @param id
	 * @param questionType
	 * @throws FrameworkException
	 */
	void updateQuestionType(Long id, String questionType) throws FrameworkException;

	/**
	 * Updates the minimum length.
	 * {@link SurveyQuestionAnswerChoice#getMinLength()}
	 * 
	 * @param id
	 * @param minLength
	 * @throws FrameworkException
	 */
	void updateMinLength(Long id, Long minLength) throws FrameworkException;

	/**
	 * Updates the maximum length.
	 * {@link SurveyQuestionAnswerChoice#getMaxLength()}
	 * 
	 * @param id
	 * @param maxLength
	 * @throws FrameworkException
	 */
	void updateMaxLength(Long id, Long maxLength) throws FrameworkException;

	/**
	 * Updates the minimum value.
	 * {@link SurveyQuestionAnswerChoice#getMinValue()}
	 * 
	 * @param id
	 * @param minValue
	 * @throws FrameworkException
	 */
	void updateMinValue(Long id, BigDecimal minValue) throws FrameworkException;

	/**
	 * Updates the minimum value.
	 * {@link SurveyQuestionAnswerChoice#getMaxValue()}
	 * 
	 * @param id
	 * @param maxValue
	 * @throws FrameworkException
	 */
	void updateMaxValue(Long id, BigDecimal maxValue) throws FrameworkException;

	/**
	 * Used to indicate if the choice is an "other".
	 * 
	 * @param id
	 * @param isOther
	 * @throws FrameworkException
	 */
	void updateIsOther(Long id, Boolean isOther) throws FrameworkException;

	void updateMakeSelectedQuestionRequired(Long id, Long selectedQuestionId) throws FrameworkException;

	List<SurveyQuestionAnswerChoice> findBySurvey(Long surveyId) throws FrameworkException;

}
