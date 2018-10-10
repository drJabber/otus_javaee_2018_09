package rnk.l03;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import rnk.l03.xml.StaffEntitiesList;

import javax.persistence.*;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;

@WebServlet("/rnk_xml")
public class XMLServlet extends HttpServlet {
    public static final String PERSISTENCE_UNIT_NAME="rnk-jpa";
    private static final EntityManagerFactory emf= Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME); //tomcat, see

    @Override
    protected void doGet(HttpServletRequest rq, HttpServletResponse rsp) throws ServletException, IOException {
        try{
            make_xml(rsp);

            DocumentBuilderFactory df=DocumentBuilderFactory.newInstance();
            df.setNamespaceAware(true);
            Document document = df.newDocumentBuilder().parse(Paths.get(getServletContext().getAttribute(ServletContext.TEMPDIR)+"/xml/employees.xml").toFile());
            XPathFactory xpf=XPathFactory.newInstance();
            XPath xpath=xpf.newXPath();
            XPathExpression exp_avg=xpath.compile("sum(/employees/employee/@salary) div count(/employees/employee)");
            String avg=exp_avg.evaluate(document, XPathConstants.STRING).toString();
            XPathExpression exp_gt_avg=xpath.compile("/employees/employee[@salary>"+avg+"]");
            NodeList nodes=(NodeList) exp_gt_avg.evaluate(document,XPathConstants.NODESET);
            if (nodes!=null){
                StringBuilder sb=new StringBuilder();
                sb.append(String.format("Staff with salary GT %s salary are: ",avg));
                for (int i=0; i<nodes.getLength();i++) {
                    Node node=nodes.item(i);
                    sb.append(node.getAttributes().getNamedItem("fio").getNodeValue()).append(",");
                }

                rsp.setCharacterEncoding("utf-8");
                rsp.getWriter().println(sb.toString());
            }else
            {
                rsp.getWriter().println("No staff");
            }
        }catch(Exception ex){
            throw new ServletException(ex);
        }

    }

    private void make_xml(HttpServletResponse rsp) throws ServletException{
        EntityManager em=emf.createEntityManager();
        EntityTransaction et=em.getTransaction();
        try{
            try{
                et.begin();
                Query q = em.createQuery(
                        "select staff "+
                                "from StaffEntity staff "+
                                "order by staff.id desc");
                StaffEntitiesList sl=new StaffEntitiesList();
                sl.setStaff_list(q.getResultList());

                JAXBContext jc=JAXBContext.newInstance(sl.getClass());
                Marshaller m=jc.createMarshaller();
                m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
                Path path=Paths.get(getServletContext().getAttribute(ServletContext.TEMPDIR)+"/xml/employees.xml");
                Files.createDirectories(path.getParent());
                m.marshal(sl, path.toFile());

                rsp.setCharacterEncoding("utf-8");
                rsp.getWriter().println(path.toAbsolutePath().toString());
                et.commit();
            }catch(Exception ex){
                et.rollback();
                throw new ServletException(ex);
            }
        }finally{
            em.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest rq, HttpServletResponse rsp) throws ServletException, IOException {
        make_xml(rsp);
    }

}
