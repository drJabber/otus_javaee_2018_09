package rnk.l03.xml;

import rnk.l03.jpa_entities.RoleEntity;
import rnk.l03.utils.Finder;

import javax.servlet.ServletException;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class RoleEntityAdapter extends XmlAdapter<String, RoleEntity> {
    @Override
    public RoleEntity unmarshal(String v) throws ServletException{
        return new Finder().findRole(v);
    }

    @Override
    public String marshal(RoleEntity v) {
        return v==null ? null : v.getRole();
    }
}