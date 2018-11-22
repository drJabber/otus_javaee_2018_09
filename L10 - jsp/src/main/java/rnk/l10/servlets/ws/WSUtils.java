package rnk.l10.servlets.ws;

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
import rnk.l10.entities.ArticleEntity;

import javax.servlet.ServletException;
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
import java.util.ArrayList;
import java.util.Queue;

public class WSUtils {
    private static final Logger logger = Logger.getLogger(WSUtils.class.getName());

    public static void loadAndSend(Queue<Session> sessions){
        try{
            ArrayList<Session> closedSessions=new ArrayList<>();
            if (sessions.size()==0){
                logger.warn("ws sessions list empty");
            }

            for (Session session: sessions) {
                if (!session.isOpen()){
                    closedSessions.add(session);
                    logger.error("ws session is closed:"+session.getId());
                }else{
                    String endpoint=(String) session.getUserProperties().get("endpoint");
                    if (endpoint!=null){
                        switch (endpoint){
                            case "news":{
                                LoadResult r=loadNews(session);
                                if (r.isResult()){
                                    cached_news=r.getValue();
                                    sendNews(session);
                                }
                                break;
                            }
                            case "currencies":{
                                LoadResult r=loadCurrencies(session);
                                if (r.isResult()){
                                    cached_currencies=r.getValue();
                                    sendCurrencies(session);
                                }
                                break;
                            }
                            case "stats":{
                                LoadResult r=loadStats(session);
                                if (r.isResult()){
                                    cached_stats=r.getValue();
                                    sendStats(session);
                                }
                                break;
                            }
                            default:{
                                break;
                            }
                        }
                    }else{
                        logger.error("ws endpoint invalid");
                    }

                }

            }

            sessions.removeAll(closedSessions);
        }catch(Exception ex){
            logger.error("ws payload get error:", ex);
            cached_news=null;
            cached_currencies=null;
            cached_stats=null;
        }
    }

    private static final String NEWS_URL="https://m.lenta.ru";
    private static JsonArray cached_news=null;
    private static JsonArray cached_currencies=null;
    private static JsonArray cached_stats=null;

    private static LoadResult update_cached_json_array(Session session,JsonArray source, JsonArray target) throws JSONException {
        if (source==null){
            logger.info("ws loaded empty values");
            return new LoadResult(true,target);
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
                    boolean new_session=session.getUserProperties().get("isnew").equals("Y");
                    logger.info("ws loaded values equals cached");
                    return new LoadResult(new_session,target);
                }
            }
        }
    }

    private static LoadResult loadNews(Session session) throws ServletException {
        try{
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

            return update_cached_json_array(session,json_nodes,cached_news);
        }catch(Exception ex){
            throw new ServletException(ex);
        }
    }

    private static final int TIMEOUT_VALUE=10; //seconds
    private static final String CBR_CURRENCIES_URL="http://www.cbr.ru/scripts/XML_daily.asp";

    private static LoadResult loadCurrencies(Session session) throws ServletException{
        try {
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

                    return update_cached_json_array(session,json_nodes,cached_currencies);
                }
            }else
            {
                throw new ServletException("cant open CB url");
            }
        }catch(Exception ex){
            throw new ServletException(ex);
        }
    }

    private static LoadResult loadStats(Session session) throws ServletException{
        try {
            logger.info("ws before load stats");
            return new LoadResult(false,null);
        }catch(Exception ex){
            throw new ServletException(ex);
        }
    }

    private static void sendNews(Session session) throws EncodeException,IOException {
        session.getBasicRemote().sendObject( cached_news);
        session.getUserProperties().put("isnew","N");
    }

    private static void sendCurrencies(Session session) throws EncodeException, IOException {
        session.getBasicRemote().sendObject(cached_currencies);
        session.getUserProperties().put("isnew","N");
    }

    private static void sendStats(Session session) throws EncodeException,IOException {
        session.getBasicRemote().sendObject( cached_stats);
        session.getUserProperties().put("isnew","N");
    }

}
