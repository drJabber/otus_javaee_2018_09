
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
 *         &lt;element name="BicToRegNumberResult" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
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
    "bicToRegNumberResult"
})
@XmlRootElement(name = "BicToRegNumberResponse")
public class BicToRegNumberResponse {

    @XmlElement(name = "BicToRegNumberResult")
    protected int bicToRegNumberResult;

    /**
     * Gets the value of the bicToRegNumberResult property.
     * 
     */
    public int getBicToRegNumberResult() {
        return bicToRegNumberResult;
    }

    /**
     * Sets the value of the bicToRegNumberResult property.
     * 
     */
    public void setBicToRegNumberResult(int value) {
        this.bicToRegNumberResult = value;
    }

}
