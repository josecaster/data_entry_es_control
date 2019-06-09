package software.simple.solutions.data.entry.es.control.pojo;

import java.util.List;

public class ResponseJsonPojo {

	private List<Long> selectedChoices;
	private String otherValue;

	public ResponseJsonPojo(List<Long> selectedChoices, String otherValue) {
		super();
		this.selectedChoices = selectedChoices;
		this.otherValue = otherValue;
	}

	public List<Long> getSelectedChoices() {
		return selectedChoices;
	}

	public void setSelectedChoices(List<Long> selectedChoices) {
		this.selectedChoices = selectedChoices;
	}

	public String getOtherValue() {
		return otherValue;
	}

	public void setOtherValue(String otherValue) {
		this.otherValue = otherValue;
	}

}
