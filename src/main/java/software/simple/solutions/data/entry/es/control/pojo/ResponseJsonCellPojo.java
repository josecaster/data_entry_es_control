package software.simple.solutions.data.entry.es.control.pojo;

import java.util.List;

public class ResponseJsonCellPojo {

	private Long rowId;
	private Long columnId;
	private List<Long> selectedChoices;
	private String responseText;

	public ResponseJsonCellPojo(Long rowId, Long columnId, String responseText) {
		this();
		this.rowId = rowId;
		this.columnId = columnId;
		this.responseText = responseText;
	}

	public ResponseJsonCellPojo(Long rowId, Long columnId, List<Long> selectedChoices) {
		this();
		this.rowId = rowId;
		this.columnId = columnId;
		this.selectedChoices = selectedChoices;
	}

	public ResponseJsonCellPojo() {
		super();
	}

	public Long getRowId() {
		return rowId;
	}

	public void setRowId(Long rowId) {
		this.rowId = rowId;
	}

	public Long getColumnId() {
		return columnId;
	}

	public void setColumnId(Long columnId) {
		this.columnId = columnId;
	}

	public List<Long> getSelectedChoices() {
		return selectedChoices;
	}

	public void setSelectedChoices(List<Long> selectedChoices) {
		this.selectedChoices = selectedChoices;
	}

	public String getResponseText() {
		return responseText;
	}

	public void setResponseText(String responseText) {
		this.responseText = responseText;
	}

}
