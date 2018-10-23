package rnk.l08.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;
import rnk.l08.client.bundle.Resources;

import java.util.ArrayList;
import java.util.List;

public class GwtUI extends Composite {
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

    @UiField Resources res;

    public GwtUI() {
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


    }
}

