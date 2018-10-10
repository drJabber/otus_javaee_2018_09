package rnk.l03.xml;

import org.apache.commons.lang3.NotImplementedException;
import rnk.l03.jpa_entities.RoleEntity;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class RoleEntityAdapter extends XmlAdapter<String, RoleEntity> {
    @Override
    public RoleEntity unmarshal(String v) {
        throw new NotImplementedException("cant unmarshal Departaments");
    }

    @Override
    public String marshal(RoleEntity v) {
        return v==null ? null : v.getRole();
    }
}