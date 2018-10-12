package rnk.l03.json;

import rnk.l03.jpa_entities.PositionEntity;
import rnk.l03.utils.Finder;

import javax.json.bind.adapter.JsonbAdapter;
import javax.servlet.ServletException;

public class PositionEntityJsonAdapter implements JsonbAdapter<PositionEntity, String> {
    @Override
    public String adaptToJson(PositionEntity p){
        return p.getPosition();
    }

    @Override
    public PositionEntity adaptFromJson(String adapted) throws ServletException {
        return new Finder().findPosition(adapted);
    }
}