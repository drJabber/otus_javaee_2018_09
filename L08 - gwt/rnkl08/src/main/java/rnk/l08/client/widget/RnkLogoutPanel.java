package rnk.l08.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class RnkLogoutPanel extends Composite {
    interface RnkLogoutPanelUiBinder extends UiBinder<VerticalPanel, RnkLogoutPanel> {
    }

    private static RnkLogoutPanelUiBinder ourUiBinder = GWT.create(RnkLogoutPanelUiBinder.class);

    public RnkLogoutPanel() {
        initWidget(ourUiBinder.createAndBindUi(this));
    }
}