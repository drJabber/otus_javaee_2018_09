
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
 *         &lt;element name="RegNumToIntCodeResult" type="{http://www.w3.org/2001/XMLSchema}double"/>
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
    "regNumToIntCodeResult"
})
@XmlRootElement(name = "RegNumToIntCodeResponse")
public class RegNumToIntCodeResponse {

    @XmlElement(name = "RegNumToIntCodeResult")
    protected double regNumToIntCodeResult;

    /**
     * Gets the value of the regNumToIntCodeResult property.
     * 
     */
    public double getRegNumToIntCodeResult() {
        return regNumToIntCodeResult;
    }

    /**
     * Sets the value of the regNumToIntCodeResult property.
     * 
     */
    public void setRegNumToIntCodeResult(double value) {
        this.regNumToIntCodeResult = value;
    }

}
