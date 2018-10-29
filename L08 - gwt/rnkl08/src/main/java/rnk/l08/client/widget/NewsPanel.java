package rnk.l08.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.*;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import rnk.l08.client.entities.NewsEntry;
import rnk.l08.client.entities.NewsList;

public class NewsPanel extends Composite {

    @UiField
    TableElement newsTable;

    interface NewsPanelUiBinder extends UiBinder<FlowPanel, NewsPanel> {
    }

    private static NewsPanelUiBinder ourUiBinder = GWT.create(NewsPanelUiBinder.class);

    public NewsPanel() {
        initWidget(ourUiBinder.createAndBindUi(this));

    }

    void populateNews(NewsList list){
        JsArray<NewsEntry> entries = list.getEntries();
        Document doc=newsTable.getOwnerDocument();
        for (int i = 0; i < entries.length(); i++) {
            NewsEntry entry = entries.get(i);

            TableRowElement row=doc.createTRElement();
            TableCellElement cell=doc.createTDElement();
            AnchorElement ref=doc.createAnchorElement();

            ref.setHref(entry.getUrl());
            ref.setInnerText(entry.getTitle());

            cell.appendChild(ref);
            row.appendChild(cell);

            newsTable.appendChild(row);
        }
    }
}