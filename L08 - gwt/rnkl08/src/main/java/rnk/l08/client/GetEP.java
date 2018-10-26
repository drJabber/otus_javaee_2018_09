package rnk.l08.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.*;
import rnk.l08.client.widget.GwtUI;


public class GetEP implements EntryPoint {
    public void onModuleLoad() {
        initHeaderAndTitle();
        initBody();
    }

    private void initBody() {
        RootPanel.get("id-main-container").add(new GwtUI());
    }

    private void initHeaderAndTitle() {
//        Document.get().getElementById("gwt-title").setInnerText("GWT App Title");
//        Document.get().getElementById("gwt-header").setInnerText("GWT App HEader");
    }
}
