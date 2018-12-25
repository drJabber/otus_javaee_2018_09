package rnk.t03.entities.json;

import rnk.t03.entities.DepartamentEntity;
import rnk.t03.utils.Finder;

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