package rnk.l08.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import rnk.l08.client.ServiceAsync;
import rnk.l08.client.bundle.Resources;

import java.util.ArrayList;
import java.util.List;

import static rnk.l08.client.gin.SvcInjector.injector;

public class GwtUI extends Composite {
    private static ServiceAsync service=injector.getService();

    interface GwtUIUiBinder extends UiBinder<HTMLPanel, GwtUI> { }

    private static GwtUIUiBinder ourUiBinder = GWT.create(GwtUIUiBinder.class);

    @UiField MenuBar menuBar;
    @UiField MenuItem menuItemMain;
    @UiField MenuItem menuItemContacts;
    @UiField MenuItem menuItemMembers;
    @UiField MenuItem menuItemFaq;
    @UiField MenuItem menuItemAdmin;
    @UiField MenuItem menuItemLogin;
    @UiField MenuItem menuItemLogout;

    @UiField DeckPanel deckPanel;


    @UiField(provided = true) FormPanel formSearch;

    @UiField TextBox searchBox;

    @UiField CurrenciesPanel currencies;

    @UiField Resources res;

    public GwtUI() {

//        method="GET" addStyleNames="{res.style.search_form}" action="http://google.com/search
        formSearch=new FormPanel("_blank");
        formSearch.setMethod("GET");
        formSearch.addStyleName(res.style().search_form());
        formSearch.setAction("http://google.com/search");

        initWidget(ourUiBinder.createAndBindUi(this));

        res.style().ensureInjected();

        List<Widget> widgets=new ArrayList<>();
        Label l=new Label();
        l.setText("Label text");

        widgets.add(new RnkMainPanel());
        widgets.add(new RnkContactsPanel());
        widgets.add(new RnkMembersPanel());
        widgets.add(new RnkFaqPanel());
        widgets.add(new RnkAdminPanel());
        widgets.add(new RnkLoginPanel());
        widgets.add(new RnkLogoutPanel());

        widgets.stream().forEach(w->{
            deckPanel.add(w);
        });

        menuItemMain.setScheduledCommand(new MainMenuCommand(deckPanel, widgets.get(0)));
        menuItemContacts.setScheduledCommand(new MainMenuCommand(deckPanel, widgets.get(1)));
        menuItemMembers.setScheduledCommand(new MainMenuCommand(deckPanel, widgets.get(2)));
        menuItemFaq.setScheduledCommand(new MainMenuCommand(deckPanel, widgets.get(3)));
        menuItemAdmin.setScheduledCommand(new MainMenuCommand(deckPanel, widgets.get(4)));
        menuItemLogin.setScheduledCommand(new MainMenuCommand(deckPanel, widgets.get(5)));
        menuItemLogout.setScheduledCommand(new MainMenuCommand(deckPanel, widgets.get(6)));


        deckPanel.showWidget(0);


//        searchBox.set
//        formSearch.fo

    }

    @UiHandler("currencies")
    void attachHandler(AttachEvent event){
        service.getCurrencies(new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable caught) {

                Window.alert(caught.getLocalizedMessage());
            }

            @Override
            public void onSuccess(String result) {
                Window.alert("Ok");

                currencies.setText(result);
            }
        });
    }
}

