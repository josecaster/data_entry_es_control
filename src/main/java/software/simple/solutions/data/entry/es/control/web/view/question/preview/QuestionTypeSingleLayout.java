package software.simple.solutions.data.entry.es.control.web.view.question.preview;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.VerticalLayout;

import software.simple.solutions.data.entry.es.control.entities.SurveyQuestion;
import software.simple.solutions.data.entry.es.control.entities.SurveyResponse;
import software.simple.solutions.data.entry.es.control.entities.SurveyResponseAnswer;
import software.simple.solutions.data.entry.es.control.service.ISurveyResponseAnswerService;
import software.simple.solutions.data.entry.es.control.valueobjects.SurveyResponseAnswerVO;
import software.simple.solutions.framework.core.components.CTextArea;
import software.simple.solutions.framework.core.components.SessionHolder;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.util.ContextProvider;

public class QuestionTypeSingleLayout extends VerticalLayout {

	private boolean editable;

	private CTextArea answerFld;

	private SurveyQuestion surveyQuestion;
	private SurveyResponse surveyResponse;
	private SessionHolder sessionHolder;

	private QuestionTypeSingleLayout() {
		super();
	}

	public QuestionTypeSingleLayout(SessionHolder sessionHolder, SurveyQuestion surveyQuestion) {
		this();
		this.sessionHolder = sessionHolder;
		this.surveyQuestion = surveyQuestion;
	}

	public QuestionTypeSingleLayout(SessionHolder sessionHolder, SurveyQuestion surveyQuestion, SurveyResponse surveyResponse) {
		this(sessionHolder, surveyQuestion);
		this.surveyResponse = surveyResponse;
		buildMainLayout();

		updateFields();
	}

	private void buildMainLayout() {
		answerFld = new CTextArea();
		answerFld.setValueChangeMode(ValueChangeMode.BLUR);
		answerFld.setWidth("100%");
		answerFld.setRows(1);
		addComponent(answerFld);

		if (surveyResponse != null) {
			ISurveyResponseAnswerService surveyResponseAnswerService = ContextProvider
					.getBean(ISurveyResponseAnswerService.class);
			try {
				SurveyResponseAnswer surveyResponseAnswer = surveyResponseAnswerService
						.getSurveyResponseAnswer(surveyResponse.getId(), surveyQuestion.getId());
				if (surveyResponseAnswer != null) {
					answerFld.setValue(surveyResponseAnswer.getResponseText());
				}
				answerFld.addValueChangeListener(new ValueChangeListener<String>() {

					@Override
					public void valueChange(ValueChangeEvent<String> event) {
						String value = event.getValue();
						ISurveyResponseAnswerService surveyResponseAnswerService = ContextProvider
								.getBean(ISurveyResponseAnswerService.class);
						SurveyResponseAnswerVO surveyResponseAnswerVO = new SurveyResponseAnswerVO();
						surveyResponseAnswerVO.setId(surveyResponseAnswer==null?null:surveyResponseAnswer.getId());
						surveyResponseAnswerVO.setActive(true);
						surveyResponseAnswerVO.setUniqueId(surveyResponseAnswer==null?null:surveyResponseAnswer.getUniqueId());
						surveyResponseAnswerVO.setSurveyResponseId(surveyResponse.getId());
						surveyResponseAnswerVO.setSurveyQuestionId(surveyQuestion.getId());
						surveyResponseAnswerVO.setCurrentRoleId(sessionHolder.getSelectedRole().getId());
						surveyResponseAnswerVO.setCurrentUserId(sessionHolder.getApplicationUser().getId());
						surveyResponseAnswerVO.setResponseText(value);
						try {
							surveyResponseAnswerService.updateAnswerForSingle(surveyResponseAnswerVO);
						} catch (FrameworkException e) {
							e.printStackTrace();
						}
					}
				});
			} catch (FrameworkException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
		updateFields();
	}

	private void updateFields() {
		if (answerFld != null) {
			answerFld.setEnabled(editable);
		}
	}

}
