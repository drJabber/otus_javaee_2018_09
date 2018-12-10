package rnk.l10.rest.model;

import lombok.Data;
import org.apache.log4j.Logger;
import rnk.l10.entities.DepartamentEntity;
import rnk.l10.entities.GenderEntity;
import rnk.l10.entities.PositionEntity;
import rnk.l10.entities.RoleEntity;
import rnk.l10.exception.RnkWebServiceException;
import rnk.l10.startup.StartupServlet;
import rnk.l10.utils.StaffUtils;

import java.util.ArrayList;
import java.util.List;

@Data
public class StaffEditorModel {
    private static final Logger logger = Logger.getLogger(StaffEditorModel.class.getName());

    private static List<PositionEntity> positions0=null;
    private static List<DepartamentEntity> departaments0=null;
    private static List<RoleEntity> roles0=null;
    private static List<GenderEntity> genders0=null;

    StaffDto staff;
    String page;
    String submitMethod;
    String submitPage;
    String cancelPage;

    static {
        try{
            positions0=StaffUtils.getPositions();
            departaments0=StaffUtils.getDepartaments();
            roles0=StaffUtils.getRoles();
            genders0=makeGenders();
        }catch (Exception ex){
            logger.error(ex);
        }
    }

    static List<GenderEntity> makeGenders(){
        List<GenderEntity> result=new ArrayList<>();
        result.add(new GenderEntity("М","Мужской"));
        result.add(new GenderEntity("Ж","Женский"));
        return result;
    }


    public List<DepartamentEntity> getDepartaments(){
        return departaments0;
    }

    public List<PositionEntity> getPositions(){
        return positions0;
    }

    public List<RoleEntity> getRoles(){
        return roles0;
    }

    public List<GenderEntity> getGenders(){
        return genders0;
    }

    public StaffEditorModel(String id) throws RnkWebServiceException {
        this.staff=new StaffDto(id);
    }
}
