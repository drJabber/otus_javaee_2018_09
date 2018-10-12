package rnk.l03.json;

import rnk.l03.jpa_entities.DepartamentEntity;
import rnk.l03.utils.Finder;

import javax.json.bind.adapter.JsonbAdapter;
import javax.servlet.ServletException;

public class DepartamentEntityJsonAdapter implements JsonbAdapter<DepartamentEntity, String> {
    @Override
    public String adaptToJson(DepartamentEntity p){
        return p.getDepartament();
    }

    @Override
    public DepartamentEntity adaptFromJson(String adapted) throws ServletException {
        return new Finder().findDepartament(adapted);
    }
}