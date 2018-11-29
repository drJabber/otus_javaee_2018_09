
package rnk.l10.soap.cbko;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected         content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CredorgNumber" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="IndID" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="DateFrom" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="DateTo" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "credorgNumber",
    "indID",
    "dateFrom",
    "dateTo"
})
@XmlRootElement(name = "Data101FormXML")
public class Data101FormXML {

    @XmlElement(name = "CredorgNumber")
    protected int credorgNumber;
    @XmlElement(name = "IndID")
    protected int indID;
    @XmlElement(name = "DateFrom", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateFrom;
    @XmlElement(name = "DateTo", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateTo;

    /**
     * Gets the value of the credorgNumber property.
     * 
     */
    public int getCredorgNumber() {
        return credorgNumber;
    }

    /**
     * Sets the value of the credorgNumber property.
     * 
     */
    public void setCredorgNumber(int value) {
        this.credorgNumber = value;
    }

    /**
     * Gets the value of the indID property.
     * 
     */
    public int getIndID() {
        return indID;
    }

    /**
     * Sets the value of the indID property.
     * 
     */
    public void setIndID(int value) {
        this.indID = value;
    }

    /**
     * Gets the value of the dateFrom property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateFrom() {
        return dateFrom;
    }

    /**
     * Sets the value of the dateFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateFrom(XMLGregorianCalendar value) {
        this.dateFrom = value;
    }

    /**
     * Gets the value of the dateTo property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateTo() {
        return dateTo;
    }

    /**
     * Sets the value of the dateTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateTo(XMLGregorianCalendar value) {
        this.dateTo = value;
    }

}
