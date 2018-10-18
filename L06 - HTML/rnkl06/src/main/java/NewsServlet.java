import com.google.gson.JsonArray;
import entities.ArticleEntity;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@WebServlet("/news")
public class NewsServlet extends HttpServlet {
    private static final int TIMEOUT_VALUE=10; //seconds
    private static final String NEWS_URL="https://m.lenta.ru";
    @Override
    protected void doGet(HttpServletRequest rq, HttpServletResponse rsp) throws ServletException, IOException {
        try{
            Document document= Jsoup.connect(NEWS_URL).get();
            Elements list= document.select("a.b-list-item__link");

            rsp.setContentType("application/json");
            rsp.setCharacterEncoding("UTF-8");

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

            rsp.getWriter().println(json_nodes);

        }catch(Exception ex){
            throw new ServletException(ex);
        }
    }
}
