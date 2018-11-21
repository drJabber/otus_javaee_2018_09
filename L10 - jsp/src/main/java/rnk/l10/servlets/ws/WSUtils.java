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
import org.json.JSONArray;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompare;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONCompareResult;
import org.w3c.dom.NodeList;
import rnk.l10.entities.ArticleEntity;

import javax.servlet.ServletException;
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
            for (Session session: sessions) {
                if (!session.isOpen()){
                    closedSessions.add(session);
                    logger.error("ws session is closed:"+session.getId());
                }else{
                    String endpoint=(String) session.getUserProperties().get("endpoint");
                    if (endpoint!=null){
                        switch (endpoint){
                            case "news":{
                                if (loadNews(session)){
                                    sendNews(session);
                                }
                                break;
                            }
                            case "currencies":{
                                if (loadCurrencies(session)) {
                                    sendCurrencies(session);
                                }
                                break;
                            }
                            default:{
                                break;
                            }
                        }
                    }
                }

            }
        }catch(Exception ex){
            logger.error("ws payload get error:", ex);
        }
    }

    private static final String NEWS_URL="https://m.lenta.ru";
    private static String cached_news=null;
    private static String cached_currencies=null;

    private static boolean loadNews(Session session) throws ServletException {
        try{
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

            String json_string=json_nodes.getAsString();
            JSONCompareResult r= JSONCompare.compareJSON(cached_news,json_string, JSONCompareMode.LENIENT);
            if (r.failed()){
                cached_news=json_string;
                return true;
            }else
            {
                logger.info("loaded news equals cached");
                return false;
            }
        }catch(Exception ex){
            throw new ServletException(ex);
        }
    }

    private static final int TIMEOUT_VALUE=10; //seconds
    private static final String CBR_CURRENCIES_URL="http://www.cbr.ru/scripts/XML_daily.asp";

    private static boolean loadCurrencies(Session session) throws ServletException{
        try {
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

                    String json_string=json_nodes.getAsString();

                    JSONCompareResult r= JSONCompare.compareJSON(cached_currencies,json_string, JSONCompareMode.LENIENT);
                    if (r.failed()){
                        cached_currencies=json_string;
                        return true;
                    }else
                    {
                        logger.info("loaded currencies equals cached");
                        return false;
                    }
                }
            }else
            {
                throw new ServletException("cant open CB url");
            }
        }catch(Exception ex){
            throw new ServletException(ex);
        }
    }

    private static void sendNews(Session session) throws  IOException{
        session.getBasicRemote().sendText(cached_news);
    }

    private static void sendCurrencies(Session session) throws IOException{
        session.getBasicRemote().sendText(cached_currencies);

    }
}
