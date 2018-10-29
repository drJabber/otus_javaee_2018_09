package rnk.l08;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import rnk.l08.entities.ArticleEntity;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/news")
public class NewsServlet extends HttpServlet {
    private static final int TIMEOUT_VALUE=10; //seconds
    private static final String NEWS_URL="https://m.lenta.ru";
    @Override
    protected void doGet(HttpServletRequest rq, HttpServletResponse rsp) throws ServletException, IOException {
        try{
            Document document= Jsoup.connect(NEWS_URL).get();
            Elements list= document.select("a.b-list-item__link");
            String callbackName=rq.getParameter("callback");

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

            JsonObject root=new JsonObject();
            root.add("articles",json_nodes);


            rsp.getWriter().println(callbackName+"("+ root+");");

        }catch(Exception ex){
            throw new ServletException(ex);
        }
    }
}
