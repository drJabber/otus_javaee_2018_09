package rnk.l08.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class RnkFaqPanel extends Composite {
    interface RnkFaqPanelUiBinder extends UiBinder<VerticalPanel, RnkFaqPanel> {
    }

    private static RnkFaqPanelUiBinder ourUiBinder = GWT.create(RnkFaqPanelUiBinder.class);

    public RnkFaqPanel(GwtUI parent) {
        initWidget(ourUiBinder.createAndBindUi(this));
    }
}