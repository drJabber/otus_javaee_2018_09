package rnk.l08.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class RnkAdminPanel extends Composite {
    interface RnkAdminPanelUiBinder extends UiBinder<VerticalPanel, RnkAdminPanel> {
    }

    private static RnkAdminPanelUiBinder ourUiBinder = GWT.create(RnkAdminPanelUiBinder.class);

    public RnkAdminPanel() {
        initWidget(ourUiBinder.createAndBindUi(this));
    }
}