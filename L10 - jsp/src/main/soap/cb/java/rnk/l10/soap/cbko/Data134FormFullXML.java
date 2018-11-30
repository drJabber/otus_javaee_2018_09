
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
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CredorgNumber" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="OnDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "credorgNumber",
    "onDate"
})
@XmlRootElement(name = "Data134FormFullXML")
public class Data134FormFullXML {

    @XmlElement(name = "CredorgNumber")
    protected int credorgNumber;
    @XmlElement(name = "OnDate", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar onDate;

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
     * Gets the value of the onDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getOnDate() {
        return onDate;
    }

    /**
     * Sets the value of the onDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setOnDate(XMLGregorianCalendar value) {
        this.onDate = value;
    }

}
