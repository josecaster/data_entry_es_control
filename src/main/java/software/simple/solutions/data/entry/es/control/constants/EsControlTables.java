package software.simple.solutions.data.entry.es.control.constants;

import software.simple.solutions.framework.core.constants.CxodeTables;

public class EsControlTables extends CxodeTables {

	public final class SURVEYS_ {

		public static final String NAME = "SURVEYS_";

		public final class COLUMNS {
			public static final String NAME_ = "NAME_";
			public static final String DESCRIPTION_ = "DESCRIPTION_";
			public static final String ACTIVE_ = "ACTIVE_";
		}
	}

	public final class SURVEY_QUESTIONS_ {

		public static final String NAME = "SURVEY_QUESTIONS_";

		public final class COLUMNS {
			public static final String SURVEY_ID_ = "SURVEY_ID_";
			public static final String QUESTION_ = "QUESTION_";
			public static final String QUESTION_DESCRIPTION_ = "QUESTION_DESCRIPTION_";
			public static final String ACTIVE_ = "ACTIVE_";
			public static final String ORDER_ = "ORDER_";
			public static final String QUESTION_TYPE_ = "QUESTION_TYPE_";
			public static final String REQUIRED_ = "REQUIRED_";
			public static final String REQUIRED_ERROR_ = "REQUIRED_ERROR_";
			public static final String MULTIPLE_SELECTION_ = "MULTIPLE_SELECTION_";
			public static final String SURVEY_SECTION_ID_ = "SURVEY_SECTION_ID_";
			public static final String SURVEY_GROUP_ID_ = "SURVEY_GROUP_ID_";
		}
	}

	public final class SURVEY_APPLICATION_USERS_ {

		public static final String NAME = "SURVEY_APPLICATION_USERS_";

		public final class COLUMNS {
			public static final String SURVEY_ID_ = "SURVEY_ID_";
			public static final String APPLICATION_USER_ID_ = "APPLICATION_USER_ID_";
		}
	}

	public final class SURVEY_GROUPS_ {

		public static final String NAME = "SURVEY_GROUPS_";

		public final class COLUMNS {
			public static final String SURVEY_ID_ = "SURVEY_ID_";
			public static final String NAME_ = "NAME_";
			public static final String DESCRIPTION_ = "DESCRIPTION_";
			public static final String ACTIVE_ = "ACTIVE_";
		}
	}

	public final class SURVEY_SECTIONS_ {

		public static final String NAME = "SURVEY_SECTIONS_";

		public final class COLUMNS {
			public static final String SURVEY_ID_ = "SURVEY_ID_";
			public static final String CODE_ = "CODE_";
			public static final String NAME_ = "NAME_";
			public static final String DESCRIPTION_ = "DESCRIPTION_";
			public static final String ACTIVE_ = "ACTIVE_";
			public static final String PINNED_ = "PINNED_";
			public static final String ENABLE_APPLICABILITY_ = "ENABLE_APPLICABILITY_";
		}
	}

	public final class SURVEY_QUESTION_ANSWER_CHOICES_ {

		public static final String NAME = "SURVEY_QUESTION_ANSWER_CHOICES_";

		public final class COLUMNS {
			public static final String SURVEY_QUESTION_ID_ = "SURVEY_QUESTION_ID_";
			public static final String LABEL_ = "LABEL_";
			public static final String QUESTION_TYPE_ = "QUESTION_TYPE_";
			public static final String MULTIPLE_SELECTION_ = "MULTIPLE_SELECTION_";
			public static final String AXIS_ = "AXIS_";
			public static final String MATRIX_COLUMN_TYPE_ = "MATRIX_COLUMN_TYPE_";
			public static final String INDEX_ = "INDEX_";
			public static final String MIN_LENGTH_ = "MIN_LENGTH_";
			public static final String MAX_LENGTH_ = "MAX_LENGTH_";
			public static final String MIN_VALUE_ = "MIN_VALUE_";
			public static final String MAX_VALUE_ = "MAX_VALUE_";
			public static final String MIN_DATE_ = "MIN_DATE_";
			public static final String MAX_DATE_ = "MAX_DATE_";
			public static final String VALIDATE_ = "VALIDATE_";
			public static final String VALIDATION_ERROR_ = "VALIDATION_ERROR_";
			public static final String IS_OTHER_ = "IS_OTHER_";
			public static final String MAKE_SELECTED_QUESTION_REQUIRED_ = "MAKE_SELECTED_QUESTION_REQUIRED_";
			public static final String MAKE_SELECTED_GROUP_REQUIRED_ = "MAKE_SELECTED_GROUP_REQUIRED_";
		}
	}

	public final class SURVEY_QUESTION_ANSWER_CHOICE_SELECTIONS_ {

		public static final String NAME = "SURVEY_QUESTION_ANSWER_CHOICE_SELECTIONS_";

		public final class COLUMNS {
			public static final String SURVEY_QUESTION_ANSWER_CHOICE_ID_ = "SURVEY_QUESTION_ANSWER_CHOICE_ID_";
			public static final String LABEL_ = "LABEL_";
			public static final String INDEX_ = "INDEX_";
		}
	}

	public final class SURVEY_RESPONSE_ {

		public static final String NAME = "SURVEY_RESPONSE_";

		public final class COLUMNS {
			public static final String ACTIVE_ = "ACTIVE_";
			public static final String UNIQUE_ID_ = "UNIQUE_ID_";
			public static final String FORM_NAME_ = "FORM_NAME_";
			public static final String CREATED_ON_ = "CREATED_ON_";
			public static final String SURVEY_ID_ = "SURVEY_ID_";
			public static final String APPLICATION_USER_ID_ = "APPLICATION_USER_ID_";
		}
	}

	public final class SURVEY_RESPONSE_ANSWER_ {

		public static final String NAME = "SURVEY_RESPONSE_ANSWER_";

		public final class COLUMNS {
			public static final String ACTIVE_ = "ACTIVE_";
			public static final String UNIQUE_ID_ = "UNIQUE_ID_";
			public static final String SURVEY_RESPONSE_ID_ = "SURVEY_RESPONSE_ID_";
			public static final String QUESTION_ID_ = "QUESTION_ID_";
			public static final String QUESTION_CHOICE_ROW_ID_ = "QUESTION_CHOICE_ROW_ID_";
			public static final String QUESTION_CHOICE_COLUMN_ID_ = "QUESTION_CHOICE_COLUMN_ID_";
			public static final String QUESTION_CHOICE_SELECTION_ID_ = "QUESTION_CHOICE_SELECTION_ID_";
			public static final String QUESTION_TYPE_ = "QUESTION_TYPE_";
			public static final String MATRIX_COLUMN_TYPE_ = "MATRIX_COLUMN_TYPE_";
			public static final String RESPONSE_TEXT_ = "RESPONSE_TEXT_";
			public static final String OTHER_VALUE_ = "OTHER_VALUE_";
			public static final String SELECTED_ = "SELECTED_";
			public static final String STATE_ = "STATE_";
		}
	}

	public final class SURVEY_RESPONSE_ANSWER_HISTORY_ {

		public static final String NAME = "SURVEY_RESPONSE_ANSWER_HISTORY_";

		public final class COLUMNS {
			public static final String UNIQUE_ID_ = "UNIQUE_ID_";
			public static final String SURVEY_RESPONSE_ID_ = "SURVEY_RESPONSE_ID_";
			public static final String QUESTION_ID_ = "QUESTION_ID_";
			public static final String QUESTION_TYPE_ = "QUESTION_TYPE_";
			public static final String RESPONSE_JSON_ = "RESPONSE_JSON_";
		}
	}

	public final class SURVEY_RESPONSE_SECTION_ {

		public static final String NAME = "SURVEY_RESPONSE_SECTIONS_";

		public final class COLUMNS {
			public static final String ACTIVE_ = "ACTIVE_";
			public static final String UNIQUE_ID_ = "UNIQUE_ID_";
			public static final String SURVEY_RESPONSE_ID_ = "SURVEY_RESPONSE_ID_";
			public static final String SURVEY_SECTION_ID_ = "SURVEY_SECTION_ID_";
			public static final String NOT_APPLICABLE_ = "NOT_APPLICABLE_";
		}
	}

}
