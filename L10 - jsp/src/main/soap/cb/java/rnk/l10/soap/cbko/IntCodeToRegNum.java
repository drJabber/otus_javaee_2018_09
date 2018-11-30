
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
 *         &lt;element name="IntNumber" type="{http://www.w3.org/2001/XMLSchema}double"/>
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
    "intNumber"
})
@XmlRootElement(name = "IntCodeToRegNum")
public class IntCodeToRegNum {

    @XmlElement(name = "IntNumber")
    protected double intNumber;

    /**
     * Gets the value of the intNumber property.
     * 
     */
    public double getIntNumber() {
        return intNumber;
    }

    /**
     * Sets the value of the intNumber property.
     * 
     */
    public void setIntNumber(double value) {
        this.intNumber = value;
    }

}
