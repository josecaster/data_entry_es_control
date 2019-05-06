package software.simple.solutions.data.entry.es.control.web.view.question.type;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.ui.VerticalLayout;

import io.reactivex.functions.Consumer;
import io.reactivex.subjects.BehaviorSubject;
import software.simple.solutions.data.entry.es.control.entities.SurveyGroup;
import software.simple.solutions.data.entry.es.control.entities.SurveyQuestion;
import software.simple.solutions.data.entry.es.control.properties.SurveyQuestionProperty;
import software.simple.solutions.data.entry.es.control.service.ISurveyGroupService;
import software.simple.solutions.data.entry.es.control.service.ISurveyQuestionService;
import software.simple.solutions.framework.core.components.CComboBox;
import software.simple.solutions.framework.core.components.MessageWindowHandler;
import software.simple.solutions.framework.core.components.SessionHolder;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.pojo.ComboItem;
import software.simple.solutions.framework.core.util.ContextProvider;

public class QuestionTypeSingleResponseLayout extends VerticalLayout {

	private static final Logger logger = LogManager.getLogger(QuestionTypeSingleResponseLayout.class);

	private CComboBox makeGroupQuestionRequiredFld;
	private SurveyQuestion surveyQuestion;
	private SessionHolder sessionHolder;

	public QuestionTypeSingleResponseLayout(SurveyQuestion surveyQuestion) {
		this.surveyQuestion = surveyQuestion;
		setMargin(false);
		try {
			buildMainLayout();
		} catch (FrameworkException e) {
			logger.error(e.getMessage(), e);
			new MessageWindowHandler(e);
		}
	}

	public void buildMainLayout() throws FrameworkException {
		ISurveyGroupService surveyGroupService = ContextProvider.getBean(ISurveyGroupService.class);
		List<ComboItem> surveyGroups = surveyGroupService.findBySurvey(surveyQuestion.getSurvey().getId());
		makeGroupQuestionRequiredFld = new CComboBox();
		makeGroupQuestionRequiredFld.setItems(surveyGroups);
		makeGroupQuestionRequiredFld.setWidth("300px");
		makeGroupQuestionRequiredFld
				.setValue(surveyQuestion.getSurveyGroup() == null ? null : surveyQuestion.getSurveyGroup().getId());
		makeGroupQuestionRequiredFld.setCaptionByKey(SurveyQuestionProperty.MAKE_SELECTED_GROUP_REQUIRED);
		addComponent(makeGroupQuestionRequiredFld);
		makeGroupQuestionRequiredFld.addValueChangeListener(new ValueChangeListener<ComboItem>() {

			@Override
			public void valueChange(ValueChangeEvent<ComboItem> event) {
				ISurveyQuestionService surveyQuestionService = ContextProvider.getBean(ISurveyQuestionService.class);
				try {
					surveyQuestionService.updateSurveyQuestionGroup(surveyQuestion.getId(),
							makeGroupQuestionRequiredFld.getLongValue());
				} catch (FrameworkException e) {
					logger.error(e.getMessage(), e);
					new MessageWindowHandler(e);
				}
			}
		});
	}

	public void setSurveyGroupObserver(BehaviorSubject<SurveyGroup> surveyGroupObserver) {
		if (surveyGroupObserver != null) {
			surveyGroupObserver.subscribe(new Consumer<SurveyGroup>() {

				@Override
				public void accept(SurveyGroup t) throws Exception {
					ISurveyGroupService surveyGroupService = ContextProvider.getBean(ISurveyGroupService.class);
					List<ComboItem> surveyGroups = surveyGroupService.findBySurvey(surveyQuestion.getSurvey().getId());
					makeGroupQuestionRequiredFld.setItems(surveyGroups);
				}
			});
		}
	}

}
