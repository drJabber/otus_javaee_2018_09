package rnk.l10.listeners;

import rnk.l10.entities.StaffEntity;
import rnk.l10.entities.beans.SearchResultCache;
import rnk.l10.entities.beans.SearchResultCacheItem;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.annotation.WebListener;
import java.util.List;

@WebListener
public class RequestAttributeListener implements ServletRequestAttributeListener {

    private SearchResultCache getCache(ServletContext ctx){
        SearchResultCache cache=(SearchResultCache)ctx.getAttribute("admin-search-cache");
        if (cache==null){
            cache=new SearchResultCache();
            ctx.setAttribute("admin-search-cache",cache);
        }

        return cache;
    }

    private void addSearchResultToCache(ServletRequestAttributeEvent evt){
        if (evt.getName().equals("admin-search-result")){
            SearchResultCacheItem item=(SearchResultCacheItem)evt.getValue();
            SearchResultCache cache=getCache(evt.getServletContext());
            cache.addQueryResult(item.getJpa_query(),item.getResult());
        }
    }

    @Override
    public void attributeAdded(ServletRequestAttributeEvent evt) {
        addSearchResultToCache(evt);
    }

    @Override
    public void attributeRemoved(ServletRequestAttributeEvent evt) {
        if (evt.getName().equals("admin-search-result")){
            SearchResultCache cache=getCache(evt.getServletContext());
            cache.removeQueryResult(evt.getName());
        }
    }

    @Override
    public void attributeReplaced(ServletRequestAttributeEvent evt) {
        addSearchResultToCache(evt);
    }
}
