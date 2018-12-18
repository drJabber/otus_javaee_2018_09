
package rnk.l10.soap.ws;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.3.1-SNAPSHOT
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "StaffAccounterWebService", targetNamespace = "urn://rnk.l10.soap", wsdlLocation = "http://xjabber:18080/rnkapp/StaffAccounterWebService?wsdl")
public class StaffAccounterWebService
    extends Service
{

    private final static URL STAFFACCOUNTERWEBSERVICE_WSDL_LOCATION;
    private final static WebServiceException STAFFACCOUNTERWEBSERVICE_EXCEPTION;
    private final static QName STAFFACCOUNTERWEBSERVICE_QNAME = new QName("urn://rnk.l10.soap", "StaffAccounterWebService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://xjabber:18080/rnkapp/StaffAccounterWebService?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        STAFFACCOUNTERWEBSERVICE_WSDL_LOCATION = url;
        STAFFACCOUNTERWEBSERVICE_EXCEPTION = e;
    }

    public StaffAccounterWebService() {
        super(__getWsdlLocation(), STAFFACCOUNTERWEBSERVICE_QNAME);
    }

    public StaffAccounterWebService(WebServiceFeature... features) {
        super(__getWsdlLocation(), STAFFACCOUNTERWEBSERVICE_QNAME, features);
    }

    public StaffAccounterWebService(URL wsdlLocation) {
        super(wsdlLocation, STAFFACCOUNTERWEBSERVICE_QNAME);
    }

    public StaffAccounterWebService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, STAFFACCOUNTERWEBSERVICE_QNAME, features);
    }

    public StaffAccounterWebService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public StaffAccounterWebService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns StaffAccounter
     */
    @WebEndpoint(name = "staffutils")
    public StaffAccounter getStaffutils() {
        return super.getPort(new QName("urn://rnk.l10.soap", "staffutils"), StaffAccounter.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns StaffAccounter
     */
    @WebEndpoint(name = "staffutils")
    public StaffAccounter getStaffutils(WebServiceFeature... features) {
        return super.getPort(new QName("urn://rnk.l10.soap", "staffutils"), StaffAccounter.class, features);
    }

    private static URL __getWsdlLocation() {
        if (STAFFACCOUNTERWEBSERVICE_EXCEPTION!= null) {
            throw STAFFACCOUNTERWEBSERVICE_EXCEPTION;
        }
        return STAFFACCOUNTERWEBSERVICE_WSDL_LOCATION;
    }

}
