package rnk.l14.servlets.ws;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.skyscreamer.jsonassert.JSONCompare;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONCompareResult;
import org.w3c.dom.NodeList;
import rnk.l14.entities.ArticleEntity;
import rnk.l14.entities.StatsEntity;
import rnk.l14.entities.beans.StatsReportProducer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import javax.websocket.EncodeException;
import javax.websocket.Session;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class UpdaterThread implements Runnable{
    private static final Logger logger = Logger.getLogger(UpdaterThread.class.getName());

    private Session session;

    private static final String NEWS_URL="https://m.lenta.ru";
    private static final int TIMEOUT_VALUE=10; //seconds
    private static final String CBR_CURRENCIES_URL="http://www.cbr.ru/scripts/XML_daily.asp";


    private static Cache cache=new Cache();

    public UpdaterThread(Session session){
        this.session=session;
    }

    public static void resetCacheState(){
        synchronized (cache){
            cache.resesState();
        }
    }

    public static void resetCache(){
        synchronized (cache){
            cache.resetCached();
        }
    }


    @Override
    public void run(){
        process();
    }

    public void process(){
        try{
            String endpoint=(String) session.getUserProperties().get("endpoint");
            if (endpoint!=null){
                synchronized (cache){
                    switch (endpoint){
                        case "news":{
                            LoadResult r=loadNews(session);
                            if (r.isResult()){
                                cache.getCached().put("news",r.getValue());
                                sendNews(session);
                            }
                            break;
                        }
                        case "currencies":{
                            LoadResult r=loadCurrencies(session);
                            if (r.isResult()){
                                cache.getCached().put("currencies",r.getValue());
                                sendCurrencies(session);
                            }
                            break;
                        }
                        case "stats":{
                            LoadResult r=loadStats(session);
                            if (r.isResult()){
                                cache.getCached().put("stats",r.getValue());
                                sendStats(session);
                            }
                            break;
                        }
                        default:{
                            break;
                        }
                    }
                }
            }else{
                logger.error("ws endpoint invalid");
            }
        }catch(Exception ex){
            logger.error("ws load and send thread get error:", ex);
            synchronized (cache){
                cache.resetCached();
            }
        }
    }

    private LoadResult update_cached_json_array(Session session, JsonArray source, JsonArray target) throws JSONException {
        if (!session.isOpen()){
            logger.info("ws session already closed");
            return new LoadResult(false,target);
        }else{
            if (source==null){
                logger.info("ws loaded empty cached");
                return new LoadResult(false,target);
            }else{
                String json_string=source.toString();
                if (target==null){
                    return new LoadResult(true,source);
                }else{
                    JSONCompareResult r= JSONCompare.compareJSON(target.toString(),json_string, JSONCompareMode.LENIENT);
                    if (r.failed()){
                        return new LoadResult(true, source);
                    }else
                    {
                        if (session.isOpen()){
                            logger.info("ws loaded cached equals cached");
                            return new LoadResult(false,target);
                        }else{
                            logger.info("ws session already closed");
                            return new LoadResult(true,target);
                        }
                    }
                }
            }
        }
    }

    private LoadResult loadNews(Session session) throws ServletException {
        try{
            LoadResult result=null;
            Map<String, Cache.State> states=cache.getCachedState();
            if (states.get("news")==Cache.State.NEW){
                logger.info("ws before load news");
                Document document= Jsoup.connect(NEWS_URL).get();
                Elements list= document.select("a.b-list-item__link");

                int index=0;
                JsonArray json_nodes=new JsonArray();
                for (Element element:list) {
                    Element text=element.selectFirst("span.b-list-item__title");
                    if (text!=null){
                        ArticleEntity article=new ArticleEntity();
                        article.setLink(NEWS_URL+element.attr("href"));
                        article.setText(text.text());
                        json_nodes.add(article.toJson());

                        index++;
                        if (index==10) {
                            break;
                        }
                    }
                }

                ArticleEntity article=new ArticleEntity();
                article.setLink(NEWS_URL);
                article.setText("Все новости...");
                json_nodes.add(article.toJson());

                result=update_cached_json_array(session,json_nodes,cache.getCached().get("news"));
                cache.getCachedResult().put("news",result);
                states.put("news",Cache.State.UPDATED);
            }else{
                result=cache.getCachedResult().get("news");
            }

            return result;
        }catch(Exception ex){
            throw new ServletException(ex);
        }
    }

    private LoadResult loadCurrencies(Session session) throws ServletException{
        try {
            LoadResult result=null;
            Map<String, Cache.State> states=cache.getCachedState();
            if (states.get("currencies")==Cache.State.NEW){
                logger.info("ws before load currencies");
                HttpGet get=new HttpGet(CBR_CURRENCIES_URL);
                HttpClient httpClient = HttpClients.createDefault();
                // optional configuration
                RequestConfig config=RequestConfig.custom().setSocketTimeout(TIMEOUT_VALUE * 1000).build();
                // more configuration
                get.setConfig(config);

                HttpResponse internResponse = httpClient.execute(get);
                int status = internResponse.getStatusLine().getStatusCode();
                if (status== HttpStatus.SC_OK) {

                    HttpEntity httpEntity=internResponse.getEntity();
                    try(InputStream respIn = httpEntity.getContent();
                    ){
                        DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
                        DocumentBuilder builder=factory.newDocumentBuilder();
                        org.w3c.dom.Document doc=builder.parse(respIn);
                        XPathFactory xpf=XPathFactory.newInstance();
                        XPath xpath=xpf.newXPath();
                        XPathExpression xpe=xpath.compile("/ValCurs/Valute[CharCode='USD' or CharCode='EUR' or CharCode='GBP']");

                        NodeList list= (NodeList) xpe.evaluate(doc, XPathConstants.NODESET);
                        JsonArray json_nodes=new JsonArray();
                        for (int index=0;index<list.getLength();index++){
                            json_nodes.add(entities.CurrencyEntity.fromXML(list.item(index)).toJson() );
                        }

                        result=update_cached_json_array(session,json_nodes,cache.getCached().get("currencies"));
                        cache.getCachedResult().put("currencies",result);
                        states.put("currencies",Cache.State.UPDATED);
                    }
                }else
                {
                    throw new ServletException("cant open CB url");
                }
            }else{
                result=cache.getCachedResult().get("currencies");
            }

            return result;
        }catch(Exception ex){
            throw new ServletException(ex);
        }
    }

    private LoadResult loadStats(Session session) throws ServletException{
        try {
            LoadResult result=null;
            Map<String, Cache.State> states=cache.getCachedState();
            if (states.get("stats")==Cache.State.NEW){
                logger.info("ws before load stats");
                if (session.isOpen()){
                    HttpSession s=(HttpSession)session.getUserProperties().get("http-session");
                    ServletContext ctx=s.getServletContext();
                    List<StatsEntity> list= new StatsReportProducer().produce(ctx);
                    Gson gson=new Gson();
                    JsonArray json_nodes=gson.toJsonTree(list).getAsJsonArray();

                    result=update_cached_json_array(session,json_nodes,cache.getCached().get("stats"));
                    cache.getCachedResult().put("stats",result);
                    states.put("stats",Cache.State.UPDATED);
                }else{
                    result=new LoadResult(false,cache.getCached().get("stats"));
                }
            }else{
                result=cache.getCachedResult().get("stats");
            }

            return result;
        }catch(Exception ex){
            throw new ServletException(ex);
        }
    }

    private void sendNews(Session session) throws EncodeException, IOException {
        if (session.isOpen()){
            session.getBasicRemote().sendObject( cache.getCached().get("news"));
        }
    }

    private void sendCurrencies(Session session) throws EncodeException, IOException {
        if (session.isOpen()) {
            session.getBasicRemote().sendObject( cache.getCached().get("currencies"));
        }
    }

    private void sendStats(Session session) throws EncodeException,IOException {
        if (session.isOpen()){
            session.getBasicRemote().sendObject( cache.getCached().get("stats"));
        }
    }

}
