
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
 *         &lt;element name="InternalCode" type="{http://www.w3.org/2001/XMLSchema}double"/>
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
    "internalCode"
})
@XmlRootElement(name = "CreditInfoByIntCode")
public class CreditInfoByIntCode {

    @XmlElement(name = "InternalCode")
    protected double internalCode;

    /**
     * Gets the value of the internalCode property.
     * 
     */
    public double getInternalCode() {
        return internalCode;
    }

    /**
     * Sets the value of the internalCode property.
     * 
     */
    public void setInternalCode(double value) {
        this.internalCode = value;
    }

}
