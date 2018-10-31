package rnk.l08.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class RnkContactsPanel extends Composite {
    interface RnkContactsPanelUiBinder extends UiBinder<VerticalPanel, RnkContactsPanel> {
    }

    private static RnkContactsPanelUiBinder ourUiBinder = GWT.create(RnkContactsPanelUiBinder.class);

    public RnkContactsPanel(GwtUI parent) {
        initWidget(ourUiBinder.createAndBindUi(this));
    }
}