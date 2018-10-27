package rnk.l08.client.widget;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DeckLayoutPanel;
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
        deckPanel.showWidget(deckPanel.getWidgetIndex(widget));
    }
}