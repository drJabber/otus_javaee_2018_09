package rnk.l10.entities.xml;

import rnk.l10.entities.RoleEntity;
import rnk.l10.utils.Finder;

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