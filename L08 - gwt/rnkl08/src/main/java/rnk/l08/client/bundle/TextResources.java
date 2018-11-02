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

    @Key("admin.panel.label.text")
    String adminPanelLabelText();

    @Key("admin.grid.no.data")
    String no_data_to_display();
//
    @Key("admin.grid.fio.caption")
    String fio_caption();

    @Key("admin.grid.position.caption")
    String position_caption();

    @Key("admin.grid.dept.caption")
    String dept_caption();

    @Key("admin.grid.role.caption")
    String role_caption();

    @Key("admin.grid.salary.caption")
    String salary_caption();

    @Key("admin.grid.login.caption")
    String login_caption();

    @Key("admin.grid.password.caption")
    String password_caption();

    @Key("admin.grid.password.create.caption")
    String create_password_caption();

    @Key("agmin.grid.save.column.caption")
    String save_column_caption();

    @Key("agmin.grid.save.password.column.caption")
    String save_password_column_caption();

    @Key("agmin.grid.remove.column.caption")
    String remove_column_caption();

//    @Key("newsapi.key")
//    String newsapi_key();


}
