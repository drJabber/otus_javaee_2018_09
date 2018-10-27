package rnk.l08.client.bundle;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public interface Resources extends ClientBundle {
    @Source("styles.css")
    Style style();

    public interface  Style extends CssResource{
        String dockLayoutPanel();
        String rightFlowPanel();
        String footerPanel();
        String menuPanel();
        String menuItem();
        String search_form();
        String search_btn();
        String search_box();


        String mainDeckPanel();
    }

}
