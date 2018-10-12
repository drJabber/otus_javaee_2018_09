package rnk.l03.xml;

import rnk.l03.jpa_entities.DepartamentEntity;
import rnk.l03.utils.Finder;

import javax.servlet.ServletException;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DepartamentEntityAdapter extends XmlAdapter<String, DepartamentEntity> {
    @Override
    public DepartamentEntity unmarshal(String v) throws ServletException {
        return new Finder().findDepartament(v);
    }

    @Override
    public String marshal(DepartamentEntity v) {
        return v==null ? null : v.getDepartament();
    }
}
