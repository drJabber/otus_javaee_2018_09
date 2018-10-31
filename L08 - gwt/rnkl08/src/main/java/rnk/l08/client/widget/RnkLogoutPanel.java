package rnk.l08.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import rnk.l08.client.LoginServiceAsync;
import rnk.l08.shared.GwtServiceException;

import static rnk.l08.client.gin.SvcInjector.injector;

public class RnkLogoutPanel extends Composite {

    private static LoginServiceAsync service=injector.getLoginService();

    private GwtUI parent;

    interface RnkLogoutPanelUiBinder extends UiBinder<VerticalPanel, RnkLogoutPanel> {
    }

    private static RnkLogoutPanelUiBinder ourUiBinder = GWT.create(RnkLogoutPanelUiBinder.class);

    public RnkLogoutPanel(GwtUI parent) {

        initWidget(ourUiBinder.createAndBindUi(this));
        this.parent=parent;
    }

    public void logout(){
        try{
            service.logout(new AsyncCallback<Void>() {
                @Override
                public void onFailure(Throwable caught) {
                    Window.alert(caught.getLocalizedMessage());
                }

                @Override
                public void onSuccess(Void result) {
                    parent.menuItemAdmin.setVisible(false);
                    parent.menuItemLogout.setVisible(false);
                    parent.menuItemLogin.setVisible(true);
                    parent.menuItemMain.getScheduledCommand().execute();
                }
            });
        }catch(GwtServiceException ex){
            Window.alert(injector.getConstants().logout_error());
        }
    }
}