package rnk.l03.xml;

import org.apache.commons.lang3.NotImplementedException;
import rnk.l03.jpa_entities.PositionEntity;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class PositionEntityAdapter extends XmlAdapter<String, PositionEntity> {
    @Override
    public PositionEntity unmarshal(String v) {
        throw new NotImplementedException("Cant unmarshal Positions");
    }

    @Override
    public String marshal(PositionEntity v) {
        return v==null ? null : v.getPosition();
    }
}
