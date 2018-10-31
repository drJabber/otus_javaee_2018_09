package rnk.l08.client.gin;


import com.google.gwt.core.client.GWT;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import rnk.l08.client.LoginServiceAsync;
import rnk.l08.client.ServiceAsync;
import rnk.l08.client.bundle.TextResources;
import rnk.l08.client.bundle.images.ImgResources;
import rnk.l08.client.validation.ValidatorFactory;
import rnk.l08.client.widget.GwtUI;

@GinModules(SvcGinModule.class)
public interface SvcInjector extends Ginjector {
    SvcInjector injector= GWT.create(SvcInjector.class);

    ServiceAsync getService();
    LoginServiceAsync getLoginService();
    GwtUI.MainUIBinder getUIBinder();
    ValidatorFactory.GwtValidator getValidator();
    TextResources getConstants();
    ImgResources getImages();


}
