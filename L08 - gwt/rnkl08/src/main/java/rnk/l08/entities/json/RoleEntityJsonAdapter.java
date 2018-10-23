package rnk.l08.entities.json;

import rnk.l08.entities.RoleEntity;
import rnk.l08.utils.Finder;

import javax.json.bind.adapter.JsonbAdapter;
import javax.servlet.ServletException;

public class RoleEntityJsonAdapter implements JsonbAdapter<RoleEntity, String> {
    @Override
    public String adaptToJson(RoleEntity p){
        return p.getRole();
    }

    @Override
    public RoleEntity adaptFromJson(String adapted) throws ServletException {
        return new Finder().findRole(adapted);
    }
}