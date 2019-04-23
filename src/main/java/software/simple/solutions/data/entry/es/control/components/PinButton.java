package software.simple.solutions.data.entry.es.control.components;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import software.simple.solutions.framework.core.components.CButton;
import software.simple.solutions.framework.core.components.SessionHolder;
import software.simple.solutions.framework.core.constants.Style;
import software.simple.solutions.framework.core.icons.CxodeIcons;

public class PinButton extends CustomField<Boolean> {

	private static final Logger logger = LogManager.getLogger(PinButton.class);

	private SessionHolder sessionHolder;
	private Boolean pinned = false;

	private VerticalLayout cssLayout;
	private CButton button;

	public PinButton() {
		super();
		sessionHolder = (SessionHolder) UI.getCurrent().getData();
		initContent();
	}

	@Override
	public Boolean getValue() {
		return pinned;
	}

	@Override
	protected Component initContent() {
		if (cssLayout == null) {
			cssLayout = new VerticalLayout();
			// cssLayout.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
			// cssLayout.addStyleName(Style.LOOKUP_VALUE_FIELD);
			cssLayout.setWidth("100%");
			cssLayout.setMargin(false);
			button = new CButton();
			button.setWidth("100%");
			cssLayout.addComponent(button);
			button.addStyleName(ValoTheme.BUTTON_BORDERLESS);
			button.addStyleName(Style.RESIZED_ICON);
			button.addStyleName(Style.NO_PADDING);
			button.setIcon(CxodeIcons.UNPIN);
			button.addClickListener(new ClickListener() {

				@Override
				public void buttonClick(ClickEvent event) {
					setValue(!pinned);
				}
			});
		}
		return cssLayout;
	}

	@Override
	protected void doSetValue(Boolean value) {
		pinned = value;
		if (pinned == null) {
			pinned = false;
		}
		if (pinned) {
			button.setIcon(CxodeIcons.PIN);
		} else {
			button.setIcon(CxodeIcons.UNPIN);
		}
	}

}
