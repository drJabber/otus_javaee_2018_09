package rnk.l10.entities.json;

import rnk.l10.entities.DepartamentEntity;

import javax.json.bind.adapter.JsonbAdapter;
import javax.servlet.ServletException;

public class DepartamentEntityJsonAdapter implements JsonbAdapter<DepartamentEntity, String> {
    @Override
    public String adaptToJson(DepartamentEntity p){
        return p.getDepartament();
    }

    @Override
    public DepartamentEntity adaptFromJson(String adapted) throws ServletException {
        DepartamentEntity dept =new DepartamentEntity();
        dept.setDepartament(adapted);
        return dept;
    }
}