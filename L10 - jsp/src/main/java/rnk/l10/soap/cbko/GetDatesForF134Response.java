
package rnk.l10.soap.cbko;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="GetDatesForF134Result" type="{http://web.cbr.ru/}ArrayOfDateTime" minOccurs="0"/&gt;
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
    "getDatesForF134Result"
})
@XmlRootElement(name = "GetDatesForF134Response")
public class GetDatesForF134Response {

    @XmlElement(name = "GetDatesForF134Result")
    protected ArrayOfDateTime getDatesForF134Result;

    /**
     * Gets the value of the getDatesForF134Result property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfDateTime }
     *     
     */
    public ArrayOfDateTime getGetDatesForF134Result() {
        return getDatesForF134Result;
    }

    /**
     * Sets the value of the getDatesForF134Result property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfDateTime }
     *     
     */
    public void setGetDatesForF134Result(ArrayOfDateTime value) {
        this.getDatesForF134Result = value;
    }

}
