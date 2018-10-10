package rnk.l03.xml;

import org.apache.commons.lang3.NotImplementedException;
import rnk.l03.jpa_entities.DepartamentEntity;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DepartamentEntityAdapter extends XmlAdapter<String, DepartamentEntity> {
    @Override
    public DepartamentEntity unmarshal(String v) {
        throw new NotImplementedException("cant unmarshal Departaments");
    }

    @Override
    public String marshal(DepartamentEntity v) {
        return v==null ? null : v.getDepartament();
    }
}
