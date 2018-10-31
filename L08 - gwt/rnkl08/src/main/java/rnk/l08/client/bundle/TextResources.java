package rnk.l08.client.bundle;

import com.google.gwt.i18n.client.Constants;
//import com.google.gwt.i18n.client.LocalizableResource;

public interface TextResources  extends Constants {
    @Key("login.label.text")
    String loginLabelText();

    @Key("password.label.text")
    String passwordLabelText();

    @Key("logon.button.text")
    String logonButtonText();

    @Key("logon.success")
    String logon_success();

    @Key("logon.failed")
    String login_failed();

    @Key("logon.error")
    String login_error();


    @Key("logout.error")
    String logout_error();

//    @Key("newsapi.key")
//    String newsapi_key();


}
