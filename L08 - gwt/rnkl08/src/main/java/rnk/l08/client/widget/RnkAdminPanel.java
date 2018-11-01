package rnk.l08.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import rnk.l08.client.ServiceAsync;
import rnk.l08.shared.GwtServiceException;

import static rnk.l08.client.gin.SvcInjector.injector;

public class RnkAdminPanel extends Composite {
    private static ServiceAsync service=injector.getService();

    interface RnkAdminPanelUiBinder extends UiBinder<VerticalPanel, RnkAdminPanel> {
    }

    private static RnkAdminPanelUiBinder ourUiBinder = GWT.create(RnkAdminPanelUiBinder.class);

    public RnkAdminPanel(GwtUI parent) {
        initWidget(ourUiBinder.createAndBindUi(this));
    }


    public void reloadData() {
        try{
            String session= Cookies.getCookie("sid");
            service.getStaff(session, new AsyncCallback< List<StaffDTO>>() {
                @Override
                public void onFailure(Throwable caught) {
                    Window.alert(caught.getLocalizedMessage());
                }

                @Override
                public void onSuccess( List<StaffDTO>  result) {
                    Window.alert(result.toString());
                }
            });
        }catch(Exception e){
            Window.alert(e.getLocalizedMessage());
        }
    }
}