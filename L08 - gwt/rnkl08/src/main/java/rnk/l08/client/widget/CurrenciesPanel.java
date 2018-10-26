package rnk.l08.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;

public class CurrenciesPanel extends Composite {
    @UiField
    Label currLabel;

    interface CurrenciesPanelUiBinder extends UiBinder<FlowPanel, CurrenciesPanel> {
    }

    private static CurrenciesPanelUiBinder ourUiBinder = GWT.create(CurrenciesPanelUiBinder.class);

    public CurrenciesPanel() {
        initWidget(ourUiBinder.createAndBindUi(this));

    }

    void setText(String text){
        currLabel.setText(text);
    }
}