package rnk.l08.entities.json;

import rnk.l08.entities.DepartamentEntity;
import rnk.l08.utils.Finder;

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