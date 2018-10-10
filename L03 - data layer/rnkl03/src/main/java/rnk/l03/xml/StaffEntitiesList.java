package rnk.l03.xml;

import lombok.Data;
import rnk.l03.jpa_entities.StaffEntity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Data
@XmlRootElement(name="employees")
@XmlAccessorType(XmlAccessType.FIELD)
public class StaffEntitiesList {
    @XmlElement(name="employee")
    private List<StaffEntity> staff_list=null;
}
