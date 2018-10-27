package rnk.l08.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.*;
import rnk.l08.client.widget.GwtUI;


public class GetEP implements EntryPoint {
    public void onModuleLoad() {
        initBody();
    }

    private void initBody() {
        RootPanel.get("id-main-container").add(new GwtUI());
    }

}
