package rnk.l08.client.gin;


import com.google.gwt.core.client.GWT;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import rnk.l08.client.ServiceAsync;

@GinModules(SvcGinModule.class)
public interface SvcInjector extends Ginjector {
    SvcInjector injector= GWT.create(SvcInjector.class);

    ServiceAsync getService();
}
