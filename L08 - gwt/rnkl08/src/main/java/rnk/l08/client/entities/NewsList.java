package rnk.l08.client.entities;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

public class NewsList extends JavaScriptObject {
    protected NewsList() {}

    public final native JsArray<NewsEntry> getEntries() /*-{
        return this.articles;
    }-*/;
}