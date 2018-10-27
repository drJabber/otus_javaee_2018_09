package rnk.l08.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;

public class NewsPanel extends Composite {
    @UiField
    Label newsLabel;

    interface NewsPanelUiBinder extends UiBinder<FlowPanel, NewsPanel> {
    }

    private static NewsPanelUiBinder ourUiBinder = GWT.create(NewsPanelUiBinder.class);

    public NewsPanel() {
        initWidget(ourUiBinder.createAndBindUi(this));

    }

    void setText(String text){
        newsLabel.setText(text);
    }
}