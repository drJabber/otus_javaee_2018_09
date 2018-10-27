package rnk.l08.client.gin;

import com.google.gwt.inject.client.AbstractGinModule;
import rnk.l08.client.Service;
import rnk.l08.client.widget.GwtUI;

public class SvcGinModule extends AbstractGinModule {
    @Override
    protected void configure() {

        bind(Service.class);
        bind(GwtUI.GwtUIUiBinder.class);
    }
}
