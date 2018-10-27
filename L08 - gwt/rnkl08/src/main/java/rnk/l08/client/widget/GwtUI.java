package rnk.l08.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.http.client.*;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
//import com.google.gwt.xml.client.*;
//import rnk.l08.client.entities.CurrencyEntity;
import rnk.l08.client.ServiceAsync;
import rnk.l08.client.bundle.Resources;

import java.util.ArrayList;
import java.util.List;

import static rnk.l08.client.gin.SvcInjector.injector;

public class GwtUI extends Composite {
    private static ServiceAsync service=injector.getService();

    @UiTemplate("GwtUI.ui.xml")
    public interface GwtUIUiBinder extends UiBinder<HTMLPanel, GwtUI> { }

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
    @UiField NewsPanel news;

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
    }

//    private List<CurrencyEntity> getCurrenctieList(Document dom){
//        Element root=dom.getDocumentElement();
//        NodeList valutes=root.getElementsByTagName("Valute");
//
//        List<CurrencyEntity> list = new ArrayList<CurrencyEntity>();
//
//        for (Integer index =0; index<valutes.getLength();index++){
//            Node node=valutes.item(index);
//            if (node.getNodeType()==Node.ELEMENT_NODE){
//                Element valuteElt=(Element)node;
//
//                Node codeNode=valuteElt.getElementsByTagName("CharCode").item(0);
//                String code=codeNode.getNodeValue();
//                Node valueNode=valuteElt.getElementsByTagName("Value").item(0);
//                String value=valueNode.getNodeValue();
//                switch (code){
//                    case "USD" :
//                    case "GBP":
//                    case "EUR" :{
//                        list.add(new CurrencyEntity(code,value));
//                        break;
//                    }
//                }
//                if (list.size()>=3){
//                    break;
//                }
//            }
//        }
//
//        return list;
//    }

//    private void processCurrenciesXML(String text){
//        currencies.populateCurrencies(getCurrenctieList(XMLParser.parse(text)));
//
//    }

    @UiHandler("currencies")
    public void attachCurrenciesHandler(AttachEvent event){
        String url="http://www.cbr.ru/scripts/XML_daily.asp";
        RequestBuilder builder=new RequestBuilder(RequestBuilder.GET, URL.encode(url));
        try{
            builder.sendRequest(null, new RequestCallback() {
                @Override
                public void onResponseReceived(Request request, Response response) {
                    if (response.getStatusCode()==200){
                        Window.alert("Ok");
//                        processCurrenciesXML(response.getText());
                    }else{
                        Window.alert("Cant get currencies information from CBRF service\ncode:"+
                                ((Integer)response.getStatusCode()).toString()+","+response.getStatusText()+"\n"+
                                          response.getText());
                    }

                }

                @Override
                public void onError(Request request, Throwable caught) {
                    Window.alert(caught.getLocalizedMessage());
                }
            });
        }catch(RequestException ex){
            Window.alert(ex.getLocalizedMessage());
        }
    }

    @UiHandler("news")
    public void attachNewsHandler(AttachEvent event){
        service.getNews(new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable caught) {

                Window.alert(caught.getLocalizedMessage());
            }

            @Override
            public void onSuccess(String result) {

            }
        });
    }
}

