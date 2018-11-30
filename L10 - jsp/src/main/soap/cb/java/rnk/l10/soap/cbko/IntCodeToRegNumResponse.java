
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
 *         &lt;element name="IntCodeToRegNumResult" type="{http://www.w3.org/2001/XMLSchema}double"/>
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
    "intCodeToRegNumResult"
})
@XmlRootElement(name = "IntCodeToRegNumResponse")
public class IntCodeToRegNumResponse {

    @XmlElement(name = "IntCodeToRegNumResult")
    protected double intCodeToRegNumResult;

    /**
     * Gets the value of the intCodeToRegNumResult property.
     * 
     */
    public double getIntCodeToRegNumResult() {
        return intCodeToRegNumResult;
    }

    /**
     * Sets the value of the intCodeToRegNumResult property.
     * 
     */
    public void setIntCodeToRegNumResult(double value) {
        this.intCodeToRegNumResult = value;
    }

}
