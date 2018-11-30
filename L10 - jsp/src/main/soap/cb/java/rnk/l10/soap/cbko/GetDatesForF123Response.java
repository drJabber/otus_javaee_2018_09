
package rnk.l10.soap.cbko;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="GetDatesForF123Result" type="{http://web.cbr.ru/}ArrayOfDateTime" minOccurs="0"/>
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
    "getDatesForF123Result"
})
@XmlRootElement(name = "GetDatesForF123Response")
public class GetDatesForF123Response {

    @XmlElement(name = "GetDatesForF123Result")
    protected ArrayOfDateTime getDatesForF123Result;

    /**
     * Gets the value of the getDatesForF123Result property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfDateTime }
     *     
     */
    public ArrayOfDateTime getGetDatesForF123Result() {
        return getDatesForF123Result;
    }

    /**
     * Sets the value of the getDatesForF123Result property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfDateTime }
     *     
     */
    public void setGetDatesForF123Result(ArrayOfDateTime value) {
        this.getDatesForF123Result = value;
    }

}
