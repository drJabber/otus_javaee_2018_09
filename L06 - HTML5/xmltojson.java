// <!-- https://mvnrepository.com/artifact/com.google.code.gson/gson -->
// <dependency>
//     <groupId>com.google.code.gson</groupId>
//     <artifactId>gson</artifactId>
//     <version>2.3.1</version>
// </dependency>

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@XmlRootElement(name="Valute")
class CurrencyEntity{
    private String charCode;
    private Double value;

    public static CurrencyEntity fromXML(InputStream in) throws ServletException {
        try{
            JAXBContext context = JAXBContext.newInstance(CurrencyEntity.class);
            Unmarshaller um = context.createUnmarshaller();
            return (CurrencyEntity) um.unmarshal(in);
        }catch(Exception ex){
            throw new ServletException(ex.getMessage());
        }
    }

    public static String toJson(InputStream in) throws ServletException {
        try{
                GsonBuilder gb = new GsonBuilder();
                Gson gson = gb.create();
                return gson.toJson(this,CurrencyEntity.class);
            }catch(Exception ex){
            throw new ServletException(ex.getMessage());
        }
    }
}