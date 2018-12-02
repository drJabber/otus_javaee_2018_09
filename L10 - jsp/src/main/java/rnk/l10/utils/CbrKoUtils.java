package rnk.l10.utils;

import com.sun.org.apache.xerces.internal.dom.ElementNSImpl;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import rnk.l10.soap.cbko.*;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.soap.*;
import javax.xml.ws.handler.MessageContext;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CbrKoUtils {
    private CreditOrgInfoSoap cbr=(new CreditOrgInfo()).getCreditOrgInfoSoap();

    public SOAPMessage LastUpdate(MessageContext ctx) throws SOAPException {
        XMLGregorianCalendar calendar=cbr.lastUpdate();
        LocalDateTime dt=calendar.toGregorianCalendar().toZonedDateTime().toLocalDateTime();
        DateTimeFormatter fmt=DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String result=fmt.format(dt);

        MessageFactory mf= MessageFactory.newInstance();
        SOAPMessage response=mf.createMessage();
        SOAPBody body=response.getSOAPBody();
        SOAPElement content=body.addChildElement("result");
        content.setValue(result);
        response.saveChanges();

        return response;
    }

    public SOAPMessage ListOfBanks(MessageContext ctx) throws SOAPException {
        try {
            EnumBICXMLResponse.EnumBICXMLResult bics=cbr.enumBICXML();

            StringWriter writer = new StringWriter();

            List<Object> content=bics.getContent();
            ElementNSImpl root=(ElementNSImpl)content.get(0);
            NodeList nodes=root.getChildNodes();

            MessageFactory mf= MessageFactory.newInstance();
            SOAPMessage response=mf.createMessage();
            SOAPBody body=response.getSOAPBody();
            SOAPElement result=body.addChildElement("result");

            for(int i=0; i<nodes.getLength();i++){
                Node node=nodes.item(i).cloneNode(true);
                body.getOwnerDocument().adoptNode(node);
                result.appendChild(node);
            }

            response.saveChanges();

            return response;
        }catch(Exception ex){
            throw new SOAPException(ex);
        }
    }

    public SOAPMessage CreditInfoByIntCode(MessageContext ctx, Double intCode) throws SOAPException{
        try {
            ArrayOfDouble codes=new ArrayOfDouble();
            codes.getDoubles().add(intCode);
            CreditInfoByIntCodeExXMLResponse.CreditInfoByIntCodeExXMLResult info=cbr.creditInfoByIntCodeExXML(codes);

            List<Object> content=info.getContent();
            ElementNSImpl root=(ElementNSImpl)content.get(0);
            NodeList nodes=root.getChildNodes();

            MessageFactory mf= MessageFactory.newInstance();
            SOAPMessage response=mf.createMessage();
            SOAPBody body=response.getSOAPBody();
            SOAPElement result=body.addChildElement("result");

            for(int i=0; i<nodes.getLength();i++){
                Node node=nodes.item(i).cloneNode(true);
                body.getOwnerDocument().adoptNode(node);
                result.appendChild(node);
            }

            response.saveChanges();

            return response;
        }catch(Exception ex){
            throw new SOAPException(ex);
        }
    }

    public SOAPMessage CreditInfoByName(MessageContext ctx, String bank) throws SOAPException{
        try {
            SearchByNameXMLResponse.SearchByNameXMLResult info=cbr.searchByNameXML(bank);

            List<Object> content=info.getContent();
            ElementNSImpl root=(ElementNSImpl)content.get(0);
            NodeList nodes=root.getChildNodes();

            MessageFactory mf= MessageFactory.newInstance();
            SOAPMessage response=mf.createMessage();
            SOAPBody body=response.getSOAPBody();
            SOAPElement result=body.addChildElement("result");

            for(int i=0; i<nodes.getLength();i++){
                Node node=nodes.item(i).cloneNode(true);
                body.getOwnerDocument().adoptNode(node);
                result.appendChild(node);
            }

            response.saveChanges();

            return response;
        }catch(Exception ex){
            throw new SOAPException(ex);
        }
    }

}
