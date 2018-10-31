package rnk.l08.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import rnk.l08.client.LoginServiceAsync;
import rnk.l08.client.ServiceAsync;
import rnk.l08.client.bundle.Resources;

import java.util.ArrayList;
import java.util.List;

import rnk.l08.client.entities.NewsList;
import rnk.l08.shared.dto.User;

import javax.inject.Inject;

import static rnk.l08.client.gin.SvcInjector.injector;

public class GwtUI extends Composite {
    @UiTemplate("GwtUI.ui.xml")
    public interface MainUIBinder extends UiBinder<HTMLPanel, GwtUI> { }


    private static ServiceAsync service=injector.getService();
    private static LoginServiceAsync loginService=injector.getLoginService();

    private static MainUIBinder ourUiBinder = injector.getUIBinder();

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
    @UiField NewsPanel news;

    @UiField Resources res;

    private void updateUI(){
        formSearch=new FormPanel("_blank");
        formSearch.setMethod("GET");
        formSearch.addStyleName(res.style().search_form());
        formSearch.setAction("http://google.com/search");

        initWidget(ourUiBinder.createAndBindUi(this));

        res.style().ensureInjected();

        List<Widget> widgets=new ArrayList<>();
        Label l=new Label();
        l.setText("Label text");

        widgets.add(new RnkMainPanel(this));
        widgets.add(new RnkContactsPanel(this));
        widgets.add(new RnkMembersPanel(this));
        widgets.add(new RnkFaqPanel(this));
        widgets.add(new RnkAdminPanel(this));
        widgets.add(new RnkLoginPanel(this));
        widgets.add(new RnkLogoutPanel(this));

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

        selectPanel();
    }


    public void updateLoggedInMenu(){
        menuItemAdmin.setVisible(true);
        menuItemLogout.setVisible(true);
        menuItemLogin.setVisible(false);
    }

    public void updateLoggedOutMenu(){
        menuItemAdmin.setVisible(false);
        menuItemLogout.setVisible(false);
        menuItemLogin.setVisible(true);
    }

    private void displayMainPanel(){
        deckPanel.showWidget(0);
    }

    private void checkSessionIsLegal(String session){
        try{
            loginService.authorize_from_session(new AsyncCallback<User>() {
                @Override
                public void onFailure(Throwable caught) {
                    updateLoggedOutMenu();
                    displayMainPanel();
                }

                @Override
                public void onSuccess(User result) {
                    if (result==null){
                        updateLoggedOutMenu();
                        displayMainPanel();
                    }else
                    {
                        if (result.getSession() !=null){
                            updateLoggedInMenu();
                            menuItemAdmin.getScheduledCommand().execute();
                        }else{
                            updateLoggedOutMenu();
                            displayMainPanel();
                        }
                    }
                }
            });
        }catch(Exception ex){
            Window.alert(injector.getConstants().login_error());
        }
    }

    private void selectPanel(){
        String session= Cookies.getCookie("sid");
        if (session == null){
            displayMainPanel();
        }else{
            checkSessionIsLegal(session);
        }
    }

    @Inject
    public GwtUI() {

        updateUI();
    }

    @UiHandler("currencies")
    public void attachCurrenciesHandler(AttachEvent event){
        try{
            service.getCurrencies(new AsyncCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    currencies.populateCurrencies(result);
                }
                @Override
                public void onFailure(Throwable caught) {
                    Window.alert(caught.getLocalizedMessage());
                }
            });
        }catch(Exception ex){
            Window.alert(ex.getLocalizedMessage());
        }
    }

    @UiHandler("news")
    public void attachNewsHandler(AttachEvent event){
        String url = "/rnk/news";
        JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
        jsonp.setTimeout(10000);
        jsonp.requestObject(url,
                new AsyncCallback<NewsList>() {
                    public void onFailure(Throwable caught) {
                        Window.alert(caught.getLocalizedMessage());
                    }
                    public void onSuccess(NewsList list) {
                        news.populateNews(list);
                    }
                });
    }
}

