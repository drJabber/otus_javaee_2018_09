package rnk.l08.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import rnk.l08.client.LoginServiceAsync;
import rnk.l08.shared.validation.ValidationRule;
import rnk.l08.shared.dto.User;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static rnk.l08.client.gin.SvcInjector.injector;

public class RnkLoginPanel extends Composite {

    @UiField
    TextBox login;

    @UiField
    PasswordTextBox password;

    @UiField
    HorizontalPanel loginpanel;
    @UiField
    HorizontalPanel passwordpanel;

    private Image loginInvalidFieldImage;
    private Image passwordInvalidFieldImage;
    private GwtUI parent;


    private static LoginServiceAsync service=injector.getLoginService();

    interface RnkLoginPanelUiBinder extends UiBinder<VerticalPanel, RnkLoginPanel> {
    }

    private static RnkLoginPanelUiBinder ourUiBinder = GWT.create(RnkLoginPanelUiBinder.class);

    public RnkLoginPanel(GwtUI parent) {

        initWidget(ourUiBinder.createAndBindUi(this));
        this.parent=parent;
    }

    @UiHandler("loginsubmit")
    public void loginSubmitHandler(ClickEvent event){
        User user=new User(login.getValue(),password.getValue(),null,null);
        Set<ConstraintViolation<User>> errors= ValidationRule.getErrors(user);
        clearErrors();
        if (errors.isEmpty()) {
            try{
                service.authorize(user, new AsyncCallback<User>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        Window.alert(caught.getLocalizedMessage());
                    }

                    @Override
                    public void onSuccess(User result) {
                        if (result.getSession()!=null){
                            setupSession(result);
                            parent.updateLoggedInMenu();
                            parent.menuItemAdmin.getScheduledCommand().execute();
                        }else{
                            Window.alert(injector.getConstants().login_failed());
                        }
                    }
                });
            }catch(Exception ex){
                Window.alert(injector.getConstants().login_error());
            }
        }else
        {
            errors.stream().forEach(e->{
                String propertyName = e.getPropertyPath().toString();
                if (propertyName.equals(User.LOGIN)) {
                    loginInvalidFieldImage=showError(login,loginpanel,e.getMessage());
                }else
                {
                    passwordInvalidFieldImage=showError(password,passwordpanel,e.getMessage());
                }
            });
        }

    }

    private void setupSession(User result){
        String session= result.getSession();
        Cookies.setCookie("sid", session, result.getExpires(), null, "/", false);
    }


    public Image showError(TextBox textBox, Panel panel, String msg){
        textBox.getElement().getStyle().setBorderColor("red");
        final Image img=new  Image(injector.getImages().field_invalid());
        Style style=img.getElement().getStyle();
        style.setCursor(Style.Cursor.POINTER);
        style.setMargin(6, Style.Unit.PX);
        img.setTitle(msg);
        panel.add(img);

        return img;

    }

    public void clearErrors(){
        login.getElement().getStyle().clearBorderColor();
        if (loginInvalidFieldImage!=null){
            loginInvalidFieldImage.removeFromParent();
            loginInvalidFieldImage=null;
        }
        password.getElement().getStyle().clearBorderColor();

        if (passwordInvalidFieldImage!=null){
            passwordInvalidFieldImage.removeFromParent();
            passwordInvalidFieldImage=null;
        }
    }
}