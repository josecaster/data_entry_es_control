package software.simple.solutions.data.entry.es.control.service;

import java.util.List;

import software.simple.solutions.data.entry.es.control.entities.SurveyResponseAnswer;
import software.simple.solutions.data.entry.es.control.valueobjects.SurveyResponseAnswerVO;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.service.ISuperService;

public interface ISurveyResponseAnswerService extends ISuperService {

	List<SurveyResponseAnswer> getSurveyResponseAnswers(Long surveyResponseId) throws FrameworkException;

	SurveyResponseAnswer getSurveyResponseAnswer(Long surveyResponseId, Long surveyQuestionId)
			throws FrameworkException;

	List<SurveyResponseAnswer> getSurveyResponseAnswers(Long surveyResponseId, Long surveyQuestionId)
			throws FrameworkException;

	SurveyResponseAnswer updateAnswerForSingle(SurveyResponseAnswerVO surveyResponseAnswerVO) throws FrameworkException;

	SurveyResponseAnswer updateAnswerMultipleSelection(SurveyResponseAnswerVO surveyResponseAnswerVO)
			throws FrameworkException;

	SurveyResponseAnswer updateAnswerSingleSelection(SurveyResponseAnswerVO surveyResponseAnswerVO)
			throws FrameworkException;

	SurveyResponseAnswer getSurveyResponseAnswer(Long surveyResponseId, Long surveyQuestionId,
			Long surveyQuestionAnswerChoiceRowId) throws FrameworkException;

	SurveyResponseAnswer getSurveyResponseAnswer(Long surveyResponseId, Long surveyQuestionId,
			Long surveyQuestionAnswerChoiceRowId, Long surveyQuestionAnswerChoiceColumnId) throws FrameworkException;

	SurveyResponseAnswer getSurveyResponseAnswer(Long surveyResponseId, Long surveyQuestionId,
			Long surveyQuestionAnswerChoiceRowId, Long surveyQuestionAnswerChoiceColumnId,
			Long surveyQuestionAnswerChoiceSelectionId) throws FrameworkException;

	SurveyResponseAnswer updateAnswerOtherSelection(SurveyResponseAnswerVO vo) throws FrameworkException;

	SurveyResponseAnswer updateAnswerMatrixCellForText(SurveyResponseAnswerVO surveyResponseAnswerVO)
			throws FrameworkException;

	SurveyResponseAnswer updateAnswerMatrixCellForSelection(SurveyResponseAnswerVO surveyResponseAnswerVO)
			throws FrameworkException;

	void createUpdateAnswerHistory(SurveyResponseAnswer surveyResponseAnswer) throws FrameworkException;

}
