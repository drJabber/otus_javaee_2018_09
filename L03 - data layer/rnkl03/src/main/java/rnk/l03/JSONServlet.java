package rnk.l03;

import rnk.l03.jpa_entities.StaffEntity;
import rnk.l03.xml.StaffEntitiesList;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.json.bind.config.PropertyNamingStrategy;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@WebServlet("/rnk_json")
public class JSONServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest rq, HttpServletResponse rsp) throws ServletException, IOException {
        try{
            JAXBContext jc=JAXBContext.newInstance(StaffEntitiesList.class);
            Unmarshaller m=jc.createUnmarshaller();

            Path xml_path= Paths.get(getServletContext().getAttribute(ServletContext.TEMPDIR)+"/xml/employees.xml");
            Path json_path= Paths.get(getServletContext().getAttribute(ServletContext.TEMPDIR)+"/xml/employees.json");
            Files.createDirectories(xml_path.getParent());
            StaffEntitiesList sl=(StaffEntitiesList)m.unmarshal(xml_path.toFile());

            JsonbConfig jsoncfg=new JsonbConfig()
                    .withFormatting(Boolean.TRUE)
                    .withPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CASE_WITH_UNDERSCORES);
            Jsonb jsonb= JsonbBuilder.create(jsoncfg);
            OutputStream os=new FileOutputStream(json_path.toFile());
            jsonb.toJson(sl.getStaff_list(), os);
            rsp.setCharacterEncoding("utf-8");
            rsp.getWriter().println(json_path.toAbsolutePath().toString());
        }catch(Exception ex){
            throw new ServletException(ex);
        }
        finally{
        }
    }


    @Override
    protected void doGet(HttpServletRequest rq, HttpServletResponse rsp) throws ServletException, IOException {
        try{
            Path json_path= Paths.get(getServletContext().getAttribute(ServletContext.TEMPDIR)+"/xml/employees.json");
            JsonbConfig jsoncfg=new JsonbConfig()
                    .withFormatting(Boolean.TRUE)
                    .withPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CASE_WITH_UNDERSCORES);
            Jsonb jsonb= JsonbBuilder.create(jsoncfg);
            InputStream s=new FileInputStream(json_path.toFile());


            Type type = new ArrayList<StaffEntity>() {}.getClass().getGenericSuperclass();
            List<StaffEntity> sl =jsonb.fromJson(s, type);
            rsp.setCharacterEncoding("utf-8");
            IntStream
                    .iterate(1, i ->i+2)
                    .limit(sl.size())
                    .mapToObj(i->sl.get(i))
                    .forEach(rsp.getWriter()::println);
        }catch(Exception ex){
            throw new ServletException(ex);
        }
        finally{
        }
    }
}
