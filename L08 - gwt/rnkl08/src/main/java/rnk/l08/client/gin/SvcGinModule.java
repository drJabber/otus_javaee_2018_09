package rnk.l08.client.gin;

import com.google.gwt.inject.client.AbstractGinModule;
import rnk.l08.client.LoginService;
import rnk.l08.client.Service;
import rnk.l08.client.bundle.TextResources;
import rnk.l08.client.bundle.images.ImgResources;
import rnk.l08.client.validation.ValidatorFactory;
import rnk.l08.client.widget.GwtUI;

public class SvcGinModule extends AbstractGinModule {
    @Override
    protected void configure() {

        bind(Service.class);
        bind(LoginService.class);
        bind(GwtUI.MainUIBinder.class);
        bind(ValidatorFactory.GwtValidator.class);
        bind(TextResources.class);
        bind(ImgResources.class);
    }
}
