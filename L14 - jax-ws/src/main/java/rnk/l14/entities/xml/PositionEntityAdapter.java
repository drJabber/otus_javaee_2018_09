package rnk.l14.entities.xml;

import rnk.l14.entities.PositionEntity;
import rnk.l14.utils.Finder;

import javax.servlet.ServletException;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class PositionEntityAdapter extends XmlAdapter<String, PositionEntity> {
    @Override
    public PositionEntity unmarshal(String v) throws ServletException{
        return new Finder().findPosition(v);
    }

    @Override
    public String marshal(PositionEntity v) {
        return v==null ? null : v.getPosition();
    }
}
