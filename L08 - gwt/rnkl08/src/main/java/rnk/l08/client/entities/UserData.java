package rnk.l08.client.entities;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;
import rnk.l08.shared.dto.User;

@JsType(isNative=true, namespace= JsPackage.GLOBAL, name="Object")
public class UserData {

    public String login;
    public String password;

    public UserData(){}

    @JsOverlay
    public static UserData as(User user){
        UserData data = new UserData();
        data.login = user.getLogin();
        data.password = user.getPassword();
        return data;
    }
}
