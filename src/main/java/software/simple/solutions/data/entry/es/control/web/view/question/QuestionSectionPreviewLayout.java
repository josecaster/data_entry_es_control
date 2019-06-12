package software.simple.solutions.data.entry.es.control.web.view.question;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import software.simple.solutions.data.entry.es.control.constants.EsControlStyle;
import software.simple.solutions.data.entry.es.control.entities.SurveyQuestion;
import software.simple.solutions.data.entry.es.control.entities.SurveyResponse;
import software.simple.solutions.data.entry.es.control.entities.SurveySection;
import software.simple.solutions.data.entry.es.control.properties.SurveyQuestionProperty;
import software.simple.solutions.data.entry.es.control.service.facade.SurveyQuestionServiceFacade;
import software.simple.solutions.framework.core.components.CGridLayout;
import software.simple.solutions.framework.core.components.CaptionLabel;
import software.simple.solutions.framework.core.components.SessionHolder;
import software.simple.solutions.framework.core.constants.Style;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.util.PropertyResolver;

public class QuestionSectionPreviewLayout extends VerticalLayout {

	private static final Logger logger = LogManager.getLogger(QuestionSectionPreviewLayout.class);

	private CaptionLabel sectionFld;
	private Label infoFld;
	private SessionHolder sessionHolder;
	private boolean showInfo = false;
	private boolean useGrid = false;
	private boolean editable = false;

	private SurveySection surveySection;
	private SurveyResponse surveyResponse;

	@Override
	public void detach() {
		super.detach();
	}

	public QuestionSectionPreviewLayout() {
		sessionHolder = (SessionHolder) UI.getCurrent().getData();
	}

	public QuestionSectionPreviewLayout(SessionHolder sessionHolder) {
		this.sessionHolder = sessionHolder;
	}

	public void setUpLayout() throws FrameworkException {
		setSpacing(false);
		removeAllComponents();

		infoFld = new Label();
		infoFld.setWidth("100%");
		infoFld.setValue(PropertyResolver.getPropertyValueByLocale(SurveyQuestionProperty.QUESTION_SAVE_BEFORE_PREVIEW,
				sessionHolder.getLocale()));
		infoFld.addStyleName(ValoTheme.LABEL_COLORED);
		infoFld.addStyleName(ValoTheme.LABEL_H4);
		addComponent(infoFld);
		infoFld.setVisible(showInfo);

		sectionFld = new CaptionLabel();
		sectionFld.setWidth("100%");
		sectionFld.addStyleName(Style.WORD_WRAP);
		sectionFld.addStyleName(ValoTheme.LABEL_LIGHT);
		sectionFld.addStyleName(ValoTheme.LABEL_H3);
		sectionFld.addStyleName(EsControlStyle.SECTION_LABEL);
		addComponent(sectionFld);

		sectionFld.setValue(surveySection.getName());

		List<SurveyQuestion> questionList = SurveyQuestionServiceFacade.get(UI.getCurrent())
				.getQuestionList(surveySection.getSurvey().getId(), null, surveySection.getId());
		CGridLayout gridLayout = new CGridLayout();
		gridLayout.setWidth("100%");
		gridLayout.setColumns(2);
		gridLayout.setSpacing(true);
		if (useGrid) {
			addComponent(gridLayout);
		}
		for (SurveyQuestion question : questionList) {
			QuestionPreviewLayout questionPreviewLayout = new QuestionPreviewLayout(sessionHolder);
			questionPreviewLayout.setShowInfo(showInfo);
			questionPreviewLayout.setShowSection(false);
			questionPreviewLayout.setEditable(editable);
			questionPreviewLayout.setPreviewData(question, surveyResponse);
			if (useGrid) {
				gridLayout.addComponent(questionPreviewLayout);
			} else {
				addComponent(questionPreviewLayout);
			}
		}
	}

	public void reset() throws FrameworkException {
		removeAllComponents();
		setUpLayout();
	}

	public SurveySection getSurveySection() {
		return surveySection;
	}

	public void setSurveySection(SurveySection surveySection) {
		setSurveySection(surveySection, null);
	}

	public void setSurveySection(SurveySection surveySection, SurveyResponse surveyResponse) {
		this.surveySection = surveySection;
		this.surveyResponse = surveyResponse;
		try {
			setUpLayout();
		} catch (FrameworkException e) {
			e.printStackTrace();
		}
	}

	public boolean isShowInfo() {
		return showInfo;
	}

	public void setShowInfo(boolean showInfo) {
		this.showInfo = showInfo;
	}

	public boolean isUseGrid() {
		return useGrid;
	}

	public void setUseGrid(boolean useGrid) {
		this.useGrid = useGrid;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

}
