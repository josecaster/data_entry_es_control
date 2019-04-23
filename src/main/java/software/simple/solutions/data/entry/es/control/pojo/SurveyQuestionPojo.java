package software.simple.solutions.data.entry.es.control.pojo;

public class SurveyQuestionPojo {

	private Long id;
	private String question;

	public SurveyQuestionPojo() {
		super();
	}

	public SurveyQuestionPojo(Long id, String question) {
		this();
		this.id = id;
		this.question = question;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

}
