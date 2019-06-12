package software.simple.solutions.data.entry.es.control.web.view.question.configuration;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

import software.simple.solutions.data.entry.es.control.entities.SurveyQuestionAnswerChoice;
import software.simple.solutions.data.entry.es.control.entities.SurveyQuestionAnswerChoiceSelection;
import software.simple.solutions.data.entry.es.control.service.facade.SurveyQuestionAnswerChoiceSelectionServiceFacade;
import software.simple.solutions.framework.core.components.CButton;
import software.simple.solutions.framework.core.components.CTextField;
import software.simple.solutions.framework.core.components.MessageWindowHandler;
import software.simple.solutions.framework.core.constants.Style;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.icons.CxodeIcons;

public class SingleSelectionColumnConfigurationView extends HorizontalLayout {

	private static final Logger logger = LogManager.getLogger(SingleSelectionColumnConfigurationView.class);

	private SurveyQuestionAnswerChoice surveyQuestionAnswerChoice;
	private HorizontalLayout selectionContainer;
	private CButton addBtn;

	private final class DeleteSelection implements ClickListener {

		private SurveyQuestionAnswerChoiceSelection surveyQuestionAnswerChoiceSelection;
		private HorizontalLayout h1;
		private Integer componentIndex;

		public DeleteSelection(SurveyQuestionAnswerChoiceSelection surveyQuestionAnswerChoiceSelection,
				HorizontalLayout h1, Integer componentIndex) {
			this.surveyQuestionAnswerChoiceSelection = surveyQuestionAnswerChoiceSelection;
			this.h1 = h1;
			this.componentIndex = componentIndex;
		}

		@Override
		public void buttonClick(ClickEvent event) {
			selectionContainer.removeComponent(h1);
			try {
				SurveyQuestionAnswerChoiceSelectionServiceFacade.get(UI.getCurrent()).deleteAndUpdateIndex(
						SurveyQuestionAnswerChoiceSelection.class, surveyQuestionAnswerChoiceSelection.getId(),
						surveyQuestionAnswerChoiceSelection.getSurveyQuestionAnswerChoice().getId(), componentIndex);
			} catch (FrameworkException e) {
				logger.error(e.getMessage(), e);
				new MessageWindowHandler(e);
			}
		}
	}

	private final class LabelValueChangeListener implements ValueChangeListener<String> {

		private SurveyQuestionAnswerChoiceSelection surveyQuestionAnswerChoiceSelection;

		public LabelValueChangeListener(SurveyQuestionAnswerChoiceSelection surveyQuestionAnswerChoiceSelection) {
			this.surveyQuestionAnswerChoiceSelection = surveyQuestionAnswerChoiceSelection;
		}

		@Override
		public void valueChange(ValueChangeEvent<String> event) {
			try {
				SurveyQuestionAnswerChoiceSelectionServiceFacade.get(UI.getCurrent())
						.updateLabel(surveyQuestionAnswerChoiceSelection.getId(), event.getValue());
			} catch (FrameworkException e) {
				logger.error(e.getMessage(), e);
				new MessageWindowHandler(e);
			}
		}
	}

	private final class AddSelectionListener implements ClickListener {

		@Override
		public void buttonClick(ClickEvent event) {
			createSelectionLayout(null);
		}

	}

	public SingleSelectionColumnConfigurationView(SurveyQuestionAnswerChoice surveyQuestionAnswerChoice) {
		this.surveyQuestionAnswerChoice = surveyQuestionAnswerChoice;
		try {
			buildMainLayout();
		} catch (FrameworkException e) {
			logger.error(e.getMessage(), e);
			new MessageWindowHandler(e);
		}
	}

	public void buildMainLayout() throws FrameworkException {
		setMargin(new MarginInfo(false, false, false, true));
		setWidth("-1px");

		addBtn = new CButton();
		addBtn.setIcon(CxodeIcons.ADD);
		addBtn.addStyleName(Style.RESIZED_ICON_80);
		addBtn.addClickListener(new AddSelectionListener());
		addComponent(addBtn);

		selectionContainer = new HorizontalLayout();
		selectionContainer.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		selectionContainer.setMargin(false);
		addComponent(selectionContainer);

		List<SurveyQuestionAnswerChoiceSelection> selections = SurveyQuestionAnswerChoiceSelectionServiceFacade
				.get(UI.getCurrent()).getBySurveyQuestionAnswerChoice(surveyQuestionAnswerChoice.getId());
		if (selections != null) {
			selections.forEach(p -> createSelectionLayout(p));
		}
	}

	private void createSelectionLayout(SurveyQuestionAnswerChoiceSelection surveyQuestionAnswerChoiceSelection) {
		HorizontalLayout h1 = new HorizontalLayout();
		h1.setWidth("-1px");
		h1.setSpacing(false);
		h1.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		selectionContainer.addComponent(h1);

		int componentIndex = selectionContainer.getComponentIndex(h1);

		if (surveyQuestionAnswerChoiceSelection == null) {
			try {
				surveyQuestionAnswerChoiceSelection = SurveyQuestionAnswerChoiceSelectionServiceFacade
						.get(UI.getCurrent()).create(surveyQuestionAnswerChoice.getId(), componentIndex);
			} catch (FrameworkException e) {
				logger.error(e.getMessage(), e);
				new MessageWindowHandler(e);
			}
		}

		CTextField labelFld = new CTextField();
		labelFld.setWidth("150px");
		if (surveyQuestionAnswerChoiceSelection != null) {
			labelFld.setValue(surveyQuestionAnswerChoiceSelection.getLabel());
		}
		h1.addComponent(labelFld);
		labelFld.addValueChangeListener(new LabelValueChangeListener(surveyQuestionAnswerChoiceSelection));

		CButton delBtn = new CButton();
		delBtn.setIcon(CxodeIcons.DELETE);
		delBtn.addStyleName(Style.RESIZED_ICON_80);
		h1.addComponent(delBtn);
		delBtn.addClickListener(new DeleteSelection(surveyQuestionAnswerChoiceSelection, h1, componentIndex));
	}

}
