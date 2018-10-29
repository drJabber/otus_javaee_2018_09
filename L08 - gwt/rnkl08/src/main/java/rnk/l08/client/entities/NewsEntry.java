package rnk.l08.client.entities;

import com.google.gwt.core.client.JavaScriptObject;

public class NewsEntry extends JavaScriptObject {
    protected NewsEntry() {}

    public final native String getTitle() /*-{
        return this.text;
    }-*/;

    public final native String getUrl() /*-{
        return this.link;
    }-*/;

}

