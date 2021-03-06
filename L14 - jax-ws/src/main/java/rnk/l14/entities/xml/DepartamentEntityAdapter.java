package rnk.l14.entities.xml;

import rnk.l14.entities.DepartamentEntity;
import rnk.l14.utils.Finder;

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
