package rnk.l08.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import rnk.l08.client.Service;
import rnk.l08.entities.StaffEntity;
import rnk.l08.shared.GwtServiceException;
import rnk.l08.shared.dto.SessionInfo;
import rnk.l08.shared.dto.StaffDTO;

import javax.persistence.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class ServiceImpl extends RemoteServiceServlet implements Service {
    private static final int TIMEOUT_VALUE=10; //seconds
    private static final String CBR_CURRENCIES_URL="http://www.cbr.ru/scripts/XML_daily.asp";
    private static LoginServiceImpl loginSvc=null;
    private static final String PERSISTENCE_UNIT_NAME="rnk-jpa";
    private static final EntityManagerFactory emf= Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME); //tomcat, see
    private static final Logger logger = Logger.getLogger(ServiceImpl.class.getName());

    private static final LoginServiceImpl getLoginSvcInstance(){
        if (loginSvc==null){
            loginSvc=new LoginServiceImpl();
        }
        return loginSvc;
    }

    @Override
    public String getCurrencies() throws  GwtServiceException{
        try{
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
                String contentType = httpEntity.getContentType().getValue();
                try(InputStream respIn = httpEntity.getContent();
                ){
                    DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
                    DocumentBuilder builder=factory.newDocumentBuilder();
                    Document doc=builder.parse(respIn);
                    XPathFactory xpf=XPathFactory.newInstance();
                    XPath xpath=xpf.newXPath();
                    XPathExpression xpe=xpath.compile("/ValCurs/Valute[CharCode='USD' or CharCode='EUR' or CharCode='GBP']");

                    NodeList list= (NodeList) xpe.evaluate(doc, XPathConstants.NODESET);
                    Element elt = doc.createElement("Valutes");
                    for(int index=0;index<list.getLength();index++){
                        elt.appendChild(list.item(index));
                    }
                    StringWriter sw=new StringWriter();
                    Transformer xform= TransformerFactory.newInstance().newTransformer();
                    xform.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,"yes");
                    xform.setOutputProperty(OutputKeys.INDENT,"no");
                    xform.setOutputProperty(OutputKeys.ENCODING,"UTF-8");
                    xform.transform( new DOMSource(elt),new StreamResult(sw));


                    return sw.toString();
                }

            }else
            {
                throw new Exception("cant open CB url");
            }

        }catch(Exception ex){
            logger.error("currencies error:",ex);
            return "";
//            throw new GwtServiceException(ex);
        }
    }

    @Override
    public String getNews()  throws  GwtServiceException {
        return "Hello from server news!";
    }

    private List<StaffDTO> makeStaff() throws GwtServiceException{
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            Query q = em.createQuery(
                    "select staff "+
                            "from StaffEntity staff "+
                            "order by staff.id desc");

            List<StaffEntity> result = q.getResultList();

            transaction.commit();

            return createStaffListDTO(result);

        }
        catch (Exception e){
            logger.error("news error:",e);
            transaction.rollback();
//            throw new GwtServiceException(e);
            return null;
        }
        finally {
            em.close();
        }
    }

    private void persistStaff(StaffDTO staff) throws GwtServiceException{
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            StaffEntity se=new StaffEntity(staff);
            transaction.begin();
            em.merge(se);
            transaction.commit();
        }
        catch (Exception e){
            logger.error("staff error:",e);
            if (transaction.isActive()){
                transaction.rollback();
            }
        }
        finally {
            em.close();
        }
    }

    private void deleteStaff(StaffDTO staff) throws GwtServiceException{
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            StaffEntity se=new StaffEntity(staff);

            transaction.begin();
            se=em.merge(se);
            em.remove(se);
            transaction.commit();
        }
        catch (Exception e){
            logger.error("staff error:",e);
            if (transaction.isActive()){
                transaction.rollback();
            }
        }
        finally {
            em.close();
        }
    }

    private List<StaffDTO> createStaffListDTO(List<StaffEntity> list){
        List<StaffDTO> result=new ArrayList<>();
        list.stream().forEach(e->result.add(createStaffDTO(e)));
        return result;
    }

    private StaffDTO createStaffDTO(StaffEntity staff){
        return new StaffDTO(staff.getId()
                    ,staff.getFio()
                    ,staff.getPosition().getPosition()
                    ,staff.getDepartament().getDepartament()
                    ,staff.getRole().getRole()
                    ,staff.getSalary()
                    ,staff.getLogin()
                    ,"hashed"
                    ,staff.getPasswd_hash()
                    ,staff.getPasswd_salt()
                    ,0);
    }

    @Override
    public  List<StaffDTO>  getStaff(String session) throws GwtServiceException{
        SessionInfo si =getLoginSvcInstance().get_user_from_session(session);
        if (si!=null && si.getIsValid()==1){
            return makeStaff();

        }else{
            throw new GwtServiceException("ошибка входа");
        }

    }

    @Override
    public void saveStaff(String session, StaffDTO staff) throws GwtServiceException{
        SessionInfo si =getLoginSvcInstance().get_user_from_session(session);
        if (si!=null && si.getIsValid()==1){
            persistStaff(staff);

        }else{
            throw new GwtServiceException("ошибка входа");
        }
    }

    @Override
    public void removeStaff(String session, StaffDTO staff) throws GwtServiceException{
        SessionInfo si =getLoginSvcInstance().get_user_from_session(session);
        if (si!=null && si.getIsValid()==1){
            deleteStaff(staff);

        }else{
            throw new GwtServiceException("ошибка входа");
        }
    }

}