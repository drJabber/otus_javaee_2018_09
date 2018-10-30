package rnk.l08.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import rnk.l08.client.ServiceAsync;
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


    private static ServiceAsync service=injector.getService();

    interface RnkLoginPanelUiBinder extends UiBinder<VerticalPanel, RnkLoginPanel> {
    }

    private static RnkLoginPanelUiBinder ourUiBinder = GWT.create(RnkLoginPanelUiBinder.class);

    public RnkLoginPanel() {
        initWidget(ourUiBinder.createAndBindUi(this));
    }

    @UiHandler("loginsubmit")
    public void loginSubmitHandler(ClickEvent event){
        User user=new User(login.getValue(),password.getValue());
        Set<ConstraintViolation<User>> errors= ValidationRule.getErrors(user);
        clearErrors();
        if (errors.isEmpty()) {
            try{
                service.authorize(user, new AsyncCallback<Integer>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        Window.alert(caught.getLocalizedMessage());
                    }

                    @Override
                    public void onSuccess(Integer result) {
                        if (result>=0){
                            Window.alert(injector.getConstants().logon_success());
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