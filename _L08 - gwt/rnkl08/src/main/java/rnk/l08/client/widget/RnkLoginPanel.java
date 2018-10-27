package rnk.l08.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class RnkLoginPanel extends Composite {
    interface RnkLoginPanelUiBinder extends UiBinder<VerticalPanel, RnkLoginPanel> {
    }

    private static RnkLoginPanelUiBinder ourUiBinder = GWT.create(RnkLoginPanelUiBinder.class);

    public RnkLoginPanel() {
        initWidget(ourUiBinder.createAndBindUi(this));
    }
}