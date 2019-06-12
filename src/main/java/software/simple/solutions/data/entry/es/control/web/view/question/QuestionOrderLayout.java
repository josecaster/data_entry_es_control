package software.simple.solutions.data.entry.es.control.web.view.question;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import io.reactivex.subjects.BehaviorSubject;
import software.simple.solutions.data.entry.es.control.components.PositionSelect;
import software.simple.solutions.data.entry.es.control.constants.Position;
import software.simple.solutions.data.entry.es.control.entities.Survey;
import software.simple.solutions.data.entry.es.control.entities.SurveyQuestion;
import software.simple.solutions.data.entry.es.control.properties.SurveyQuestionProperty;
import software.simple.solutions.data.entry.es.control.service.facade.SurveyQuestionServiceFacade;
import software.simple.solutions.data.entry.es.control.valueobjects.SurveyQuestionVO;
import software.simple.solutions.framework.core.components.CButton;
import software.simple.solutions.framework.core.components.CComboBox;
import software.simple.solutions.framework.core.components.MessageWindowHandler;
import software.simple.solutions.framework.core.components.SessionHolder;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.pojo.ComboItem;
import software.simple.solutions.framework.core.properties.SystemProperty;

public class QuestionOrderLayout extends VerticalLayout {

	private static final Logger logger = LogManager.getLogger(QuestionOrderLayout.class);

	private final BehaviorSubject<SurveyQuestion> observer;

	private HorizontalLayout horizontalLayout;
	private PositionSelect positionFld;
	private CComboBox surveyQuestionsFld;
	private CButton submitBtn;

	private SurveyQuestion surveyQuestion;
	private Survey survey;
	private SessionHolder sessionHolder;

	public QuestionOrderLayout() {
		sessionHolder = (SessionHolder) UI.getCurrent().getData();
		observer = BehaviorSubject.create();
		createMainLayout();
	}

	@Override
	public void detach() {
		observer.onComplete();
		super.detach();
	}

	private void createMainLayout() {
		horizontalLayout = new HorizontalLayout();
		horizontalLayout.setWidth("100%");
		addComponent(horizontalLayout);

		positionFld = new PositionSelect();
		positionFld.setWidth("150px");
		positionFld.setCaptionByKey(SurveyQuestionProperty.QUESTION_ORDER_POSITION);
		horizontalLayout.addComponent(positionFld);
		positionFld.setValue(Position.BEFORE);
		positionFld.setEmptySelectionAllowed(false);

		surveyQuestionsFld = new CComboBox();
		surveyQuestionsFld.setWidth("100%");
		surveyQuestionsFld.setCaptionByKey(SurveyQuestionProperty.QUESTION);
		horizontalLayout.addComponent(surveyQuestionsFld);
		horizontalLayout.setExpandRatio(surveyQuestionsFld, 1);

		submitBtn = new CButton();
		submitBtn.setCaptionByKey(SystemProperty.SYSTEM_BUTTON_SUBMIT);
		submitBtn.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		addComponent(submitBtn);

		submitBtn.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					handleSaveOrUpdate();
					observer.onNext(surveyQuestion);
				} catch (FrameworkException e) {
					logger.error(e.getMessage(), e);
					new MessageWindowHandler(e);
				}
			}
		});
	}

	private void initSurveyQuestionsData() {

		try {
			List<ComboItem> questions = SurveyQuestionServiceFacade.get(UI.getCurrent())
					.getQuestionListForOrder(survey.getId(), surveyQuestion.getId());
			surveyQuestionsFld.setItems(questions);
		} catch (FrameworkException e) {
			logger.error(e.getMessage(), e);
			new MessageWindowHandler(e);
		}
	}

	protected void handleSaveOrUpdate() throws FrameworkException {
		SurveyQuestionVO vo = new SurveyQuestionVO();
		vo.setSurveyId(survey == null ? null : survey.getId());
		vo.setId(surveyQuestion == null ? null : surveyQuestion.getId());
		vo.setPosition(positionFld.getStringValue() + "-" + surveyQuestionsFld.getStringValue());

		surveyQuestion = SurveyQuestionServiceFacade.get(UI.getCurrent()).updateOrder(vo);
	}

	public BehaviorSubject<SurveyQuestion> getObserver() {
		return observer;
	}

	public void setSurveyQuestion(SurveyQuestion surveyQuestion) {
		this.surveyQuestion = surveyQuestion;
		setSurvey(surveyQuestion.getSurvey());
		initSurveyQuestionsData();
	}

	public void setSurvey(Survey survey) {
		this.survey = survey;
	}

}
