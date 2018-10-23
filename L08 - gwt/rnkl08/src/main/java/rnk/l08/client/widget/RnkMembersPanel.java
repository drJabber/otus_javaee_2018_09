package rnk.l08.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class RnkMembersPanel extends Composite {
    interface RnkMembersPanelUiBinder extends UiBinder<VerticalPanel, RnkMembersPanel> {
    }

    private static RnkMembersPanelUiBinder ourUiBinder = GWT.create(RnkMembersPanelUiBinder.class);

    public RnkMembersPanel() {
        initWidget(ourUiBinder.createAndBindUi(this));
    }
}