package rnk.l08.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class RnkMainPanel extends Composite {
    interface RnkMainPanelUiBinder extends UiBinder<VerticalPanel, RnkMainPanel> {
    }

    private static RnkMainPanelUiBinder ourUiBinder = GWT.create(RnkMainPanelUiBinder.class);

    public RnkMainPanel(GwtUI parent) {
        initWidget(ourUiBinder.createAndBindUi(this));
    }
}