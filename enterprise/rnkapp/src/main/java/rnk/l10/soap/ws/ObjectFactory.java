
package rnk.l10.soap.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the rnk.l10.soap.ws package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _RnkWebServiceException_QNAME = new QName("http://soap.l10.rnk/", "RnkWebServiceException");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: rnk.l10.soap.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RnkWebServiceException }
     * 
     */
    public RnkWebServiceException createRnkWebServiceException() {
        return new RnkWebServiceException();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RnkWebServiceException }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link RnkWebServiceException }{@code >}
     */
    @XmlElementDecl(namespace = "http://soap.l10.rnk/", name = "RnkWebServiceException")
    public JAXBElement<RnkWebServiceException> createRnkWebServiceException(RnkWebServiceException value) {
        return new JAXBElement<RnkWebServiceException>(_RnkWebServiceException_QNAME, RnkWebServiceException.class, null, value);
    }

}
