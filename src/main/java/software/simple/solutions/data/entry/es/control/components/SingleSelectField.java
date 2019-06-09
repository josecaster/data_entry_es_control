package software.simple.solutions.data.entry.es.control.components;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.google.gson.Gson;
import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CheckBoxGroup;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.ItemCaptionGenerator;
import com.vaadin.ui.themes.ValoTheme;

import software.simple.solutions.data.entry.es.control.constants.Axis;
import software.simple.solutions.data.entry.es.control.entities.SurveyQuestion;
import software.simple.solutions.data.entry.es.control.entities.SurveyQuestionAnswerChoice;
import software.simple.solutions.data.entry.es.control.entities.SurveyResponse;
import software.simple.solutions.data.entry.es.control.entities.SurveyResponseAnswer;
import software.simple.solutions.data.entry.es.control.entities.SurveyResponseAnswerHistory;
import software.simple.solutions.data.entry.es.control.pojo.ResponseJsonPojo;
import software.simple.solutions.data.entry.es.control.service.ISurveyQuestionAnswerChoiceService;
import software.simple.solutions.data.entry.es.control.service.ISurveyResponseAnswerService;
import software.simple.solutions.data.entry.es.control.valueobjects.SurveyResponseAnswerVO;
import software.simple.solutions.framework.core.components.CTextField;
import software.simple.solutions.framework.core.components.SessionHolder;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.util.ContextProvider;

public class SingleSelectField extends HorizontalLayout {

	private static final long serialVersionUID = 1329989473546839657L;

	private CheckBoxGroup<SurveyQuestionAnswerChoice> radioButtonGroup;
	private CTextField otherFld;
	private SessionHolder sessionHolder;
	private List<SurveyQuestionAnswerChoice> items;
	private SurveyResponse surveyResponse;
	private SurveyQuestion surveyQuestion;
	private SurveyResponseAnswer surveyResponseAnswer;
	private boolean clearEnabled = true;
	private boolean editable = false;
	private SurveyQuestionAnswerChoice surveyQuestionAnswerChoice;
	private SurveyResponseAnswerHistory surveyResponseAnswerHistory;

	public SingleSelectField(SessionHolder sessionHolder, List<SurveyQuestionAnswerChoice> items,
			SurveyResponse surveyResponse, SurveyQuestion surveyQuestion, SurveyResponseAnswer surveyResponseAnswer,
			SurveyResponseAnswerHistory surveyResponseAnswerHistory) {
		this.sessionHolder = sessionHolder;
		this.items = items;
		this.surveyResponse = surveyResponse;
		this.surveyQuestion = surveyQuestion;
		this.surveyResponseAnswer = surveyResponseAnswer;
		this.surveyResponseAnswerHistory = surveyResponseAnswerHistory;
	}

	public void build() {
		try {
			initContent();
		} catch (FrameworkException e) {
			e.printStackTrace();
		}
	}

	public void initContent() throws FrameworkException {
		setMargin(false);
		radioButtonGroup = new CheckBoxGroup<SurveyQuestionAnswerChoice>();
		radioButtonGroup.addStyleName(ValoTheme.CHECKBOX_LARGE);
		radioButtonGroup.setItemCaptionGenerator(new ItemCaptionGenerator<SurveyQuestionAnswerChoice>() {

			@Override
			public String apply(SurveyQuestionAnswerChoice item) {
				return item.getLabel();
			}
		});
		radioButtonGroup.setItems(items);

		addComponent(radioButtonGroup);
		otherFld = new CTextField();
		otherFld.setVisible(false);
		otherFld.setEnabled(editable);
		addComponent(otherFld);
		setComponentAlignment(otherFld, Alignment.BOTTOM_LEFT);

		if (surveyResponseAnswerHistory != null) {
			String responseJson = surveyResponseAnswerHistory.getResponseJson();
			ResponseJsonPojo responseJsonPojo = new Gson().fromJson(responseJson, ResponseJsonPojo.class);
			List<Long> selectedChoices = responseJsonPojo.getSelectedChoices();
			ISurveyQuestionAnswerChoiceService surveyQuestionAnswerChoiceService = ContextProvider
					.getBean(ISurveyQuestionAnswerChoiceService.class);
			List<SurveyQuestionAnswerChoice> choiceIds = surveyQuestionAnswerChoiceService
					.findBySurveyQuestionChoiceIds(selectedChoices, Axis.ROW);

			if (choiceIds.size() == 1) {
				SurveyQuestionAnswerChoice surveyQuestionAnswerChoiceRow = choiceIds.get(0);
				if (surveyQuestionAnswerChoiceRow != null) {
					Optional<SurveyQuestionAnswerChoice> optional = items.stream()
							.filter(p -> p.getId().compareTo(surveyQuestionAnswerChoiceRow.getId()) == 0).findFirst();
					if (optional.isPresent()) {
						radioButtonGroup
								.setValue(new HashSet<SurveyQuestionAnswerChoice>(Arrays.asList(optional.get())));
					}
					if (surveyQuestionAnswerChoiceRow.getIsOther() != null
							&& surveyQuestionAnswerChoiceRow.getIsOther()) {
						if (otherFld != null) {
							otherFld.setVisible(true);
							otherFld.setValue(responseJsonPojo.getOtherValue());
						}
					}
				}
			}
		} else if (surveyResponseAnswer != null) {
			SurveyQuestionAnswerChoice surveyQuestionAnswerChoiceRow = surveyResponseAnswer
					.getSurveyQuestionAnswerChoiceRow();
			if (surveyQuestionAnswerChoiceRow != null) {
				Optional<SurveyQuestionAnswerChoice> optional = items.stream()
						.filter(p -> p.getId().compareTo(surveyQuestionAnswerChoiceRow.getId()) == 0).findFirst();
				if (optional.isPresent()) {
					radioButtonGroup.setValue(new HashSet<SurveyQuestionAnswerChoice>(Arrays.asList(optional.get())));
				}
				if (surveyQuestionAnswerChoiceRow.getIsOther() != null && surveyQuestionAnswerChoiceRow.getIsOther()) {
					if (otherFld != null) {
						otherFld.setVisible(true);
						otherFld.setValue(surveyResponseAnswer.getOtherValue());
					}
				}
			}
		}

		radioButtonGroup.addValueChangeListener(new ValueChangeListener<Set<SurveyQuestionAnswerChoice>>() {

			private boolean valueSet = false;
			private Set<SurveyQuestionAnswerChoice> values;

			@Override
			public void valueChange(ValueChangeEvent<Set<SurveyQuestionAnswerChoice>> event) {
				if (clearEnabled) {
					otherFld.setVisible(false);
					otherFld.setEnabled(false);
					Set<SurveyQuestionAnswerChoice> oldValue = event.getOldValue();
					values = event.getValue();
					if (oldValue != null && values != null) {
						Set<SurveyQuestionAnswerChoice> duplicate = new HashSet<>(values);
						duplicate.removeAll(oldValue);
						values = duplicate;
						surveyQuestionAnswerChoice = null;
						if (values.size() == 1) {
							surveyQuestionAnswerChoice = (SurveyQuestionAnswerChoice) values.toArray()[0];
							if (surveyQuestionAnswerChoice.getIsOther() != null
									&& surveyQuestionAnswerChoice.getIsOther()) {
								otherFld.setVisible(true);
								otherFld.setEnabled(true);
							}
						}
						update();
					}
					clearEnabled = false;
					if (values == null || values.isEmpty()) {
						clearEnabled = true;
					}
					radioButtonGroup.clear();
				} else {
					if (valueSet) {
						clearEnabled = true;
						valueSet = false;
					} else {
						valueSet = true;
						radioButtonGroup.setValue(values);
					}
				}
			}
		});
		if (otherFld != null) {
			otherFld.addValueChangeListener(new ValueChangeListener<String>() {

				@Override
				public void valueChange(ValueChangeEvent<String> event) {
					updateOther(event.getValue());
				}
			});
		}
	}

	private void update() {
		ISurveyResponseAnswerService surveyResponseAnswerService = ContextProvider
				.getBean(ISurveyResponseAnswerService.class);
		SurveyResponseAnswerVO surveyResponseAnswerVO = new SurveyResponseAnswerVO();
		surveyResponseAnswerVO.setId(surveyResponseAnswer == null ? null : surveyResponseAnswer.getId());
		surveyResponseAnswerVO.setActive(true);
		surveyResponseAnswerVO.setUniqueId(surveyResponseAnswer == null ? null : surveyResponseAnswer.getUniqueId());
		surveyResponseAnswerVO.setSurveyResponseId(surveyResponse.getId());
		surveyResponseAnswerVO.setSurveyQuestionId(surveyQuestion.getId());
		surveyResponseAnswerVO.setCurrentRoleId(sessionHolder.getSelectedRole().getId());
		surveyResponseAnswerVO.setCurrentUserId(sessionHolder.getApplicationUser().getId());
		surveyResponseAnswerVO.setQuestionAnswerChoiceRowId(
				surveyQuestionAnswerChoice == null ? null : surveyQuestionAnswerChoice.getId());
		surveyResponseAnswerVO.setSelected(surveyQuestionAnswerChoice != null);
		try {
			surveyResponseAnswer = surveyResponseAnswerService.updateAnswerSingleSelection(surveyResponseAnswerVO);
		} catch (FrameworkException e) {
			e.printStackTrace();
		}
	}

	private void updateOther(String otherValue) {
		ISurveyResponseAnswerService surveyResponseAnswerService = ContextProvider
				.getBean(ISurveyResponseAnswerService.class);
		SurveyResponseAnswerVO surveyResponseAnswerVO = new SurveyResponseAnswerVO();
		surveyResponseAnswerVO.setId(surveyResponseAnswer == null ? null : surveyResponseAnswer.getId());
		surveyResponseAnswerVO.setActive(true);
		surveyResponseAnswerVO.setUniqueId(surveyResponseAnswer == null ? null : surveyResponseAnswer.getUniqueId());
		surveyResponseAnswerVO.setSurveyResponseId(surveyResponse.getId());
		surveyResponseAnswerVO.setSurveyQuestionId(surveyQuestion.getId());
		surveyResponseAnswerVO.setCurrentRoleId(sessionHolder.getSelectedRole().getId());
		surveyResponseAnswerVO.setCurrentUserId(sessionHolder.getApplicationUser().getId());
		surveyResponseAnswerVO.setQuestionAnswerChoiceRowId(
				surveyQuestionAnswerChoice == null ? null : surveyQuestionAnswerChoice.getId());
		surveyResponseAnswerVO.setOtherValue(otherValue);
		try {
			surveyResponseAnswer = surveyResponseAnswerService.updateAnswerOtherSelection(surveyResponseAnswerVO);
		} catch (FrameworkException e) {
			e.printStackTrace();
		}
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

}
