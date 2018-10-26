package rnk.l08.client.widget;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Image;

public class Anchor extends com.google.gwt.user.client.ui.Anchor {
    public Anchor(){

    }

    public void setResource(ImageResource imageResource){
        Image img=new Image(imageResource);
        DOM.insertBefore(getElement(),img.getElement(),DOM.getFirstChild(getElement()));
    }
}
