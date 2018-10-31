package rnk.l08.client.widget;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.Widget;

public class MainMenuCommand implements Command {

    private final DeckPanel deckPanel;
    private final Widget widget;

    public MainMenuCommand(DeckPanel deckPanel, Widget widget){
        this.deckPanel=deckPanel;
        this.widget=widget;
    }
    @Override
    public void execute(){
//        Window.alert("Ok");
        if (widget instanceof RnkLogoutPanel){
            RnkLogoutPanel panel=(RnkLogoutPanel)widget;
            panel.logout();

        }else
        {
            deckPanel.showWidget(deckPanel.getWidgetIndex(widget));
        }

    }
}