
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
 *         &lt;element name="CredprgNumber" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "credprgNumber"
})
@XmlRootElement(name = "GetDatesForF102")
public class GetDatesForF102 {

    @XmlElement(name = "CredprgNumber")
    protected int credprgNumber;

    /**
     * Gets the value of the credprgNumber property.
     * 
     */
    public int getCredprgNumber() {
        return credprgNumber;
    }

    /**
     * Sets the value of the credprgNumber property.
     * 
     */
    public void setCredprgNumber(int value) {
        this.credprgNumber = value;
    }

}
