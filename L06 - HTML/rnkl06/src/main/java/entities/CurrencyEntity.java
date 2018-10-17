import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import org.w3c.dom.Node;

import javax.servlet.ServletException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.InputStream;

@XmlRootElement(name="Valute")
class CurrencyEntity{
    @XmlElement(name="CharCode")
    private String charCode;
    @XmlElement(name="Value")
    private String value;

    public static CurrencyEntity fromXML(Node node) throws ServletException {
        try{
            JAXBContext context = JAXBContext.newInstance(CurrencyEntity.class);
            Unmarshaller um = context.createUnmarshaller();
            return (CurrencyEntity) um.unmarshal(node);
        }catch(Exception ex){
            throw new ServletException(ex.getMessage());
        }
    }

    public JsonElement toJson() throws ServletException {
        try{
            GsonBuilder gb = new GsonBuilder();
            Gson gson = gb.create();
            return gson.toJsonTree(this);
        }catch(Exception ex){
            throw new ServletException(ex.getMessage());
        }
    }
}