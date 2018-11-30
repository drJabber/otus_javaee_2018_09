
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
 *         &lt;element name="GetDatesForF102Result" type="{http://web.cbr.ru/}ArrayOfDateTime" minOccurs="0"/>
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
    "getDatesForF102Result"
})
@XmlRootElement(name = "GetDatesForF102Response")
public class GetDatesForF102Response {

    @XmlElement(name = "GetDatesForF102Result")
    protected ArrayOfDateTime getDatesForF102Result;

    /**
     * Gets the value of the getDatesForF102Result property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfDateTime }
     *     
     */
    public ArrayOfDateTime getGetDatesForF102Result() {
        return getDatesForF102Result;
    }

    /**
     * Sets the value of the getDatesForF102Result property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfDateTime }
     *     
     */
    public void setGetDatesForF102Result(ArrayOfDateTime value) {
        this.getDatesForF102Result = value;
    }

}
