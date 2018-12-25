package rnk.l10.entities.json;

import rnk.l10.entities.RoleEntity;

import javax.json.bind.adapter.JsonbAdapter;
import javax.servlet.ServletException;
import java.util.HashSet;

public class RoleEntityJsonAdapter implements JsonbAdapter<RoleEntity, String> {
    @Override
    public String adaptToJson(RoleEntity p){
        return p.getRole();

    }

    @Override
    public RoleEntity adaptFromJson(String adapted) throws ServletException {
        RoleEntity role =new RoleEntity();
        role.setRole(adapted);
        role.setAuthorities(new HashSet<>());
        return role;
    }
}