package rnk.l08.client.bundle;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;

public interface Resources extends ClientBundle {
    @Source("styles.css")
    Style style();

    ImageResource logo();

    public interface  Style extends CssResource{
        String dockLayoutPanel();
        String rightFlowPanel();
        String footerPanel();
        String menuPanel();
        String search_form();
        String search_btn();
        String search_box();
        String leftMenuPanel();
        String logoImg();
        String logoPanel();
        String currencies();
        String news();


        String mainDeckPanel();
    }

}
