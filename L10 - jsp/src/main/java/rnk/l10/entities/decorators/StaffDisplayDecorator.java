package rnk.l10.entities.decorators;

import org.displaytag.decorator.TableDecorator;
import rnk.l10.entities.StaffEntity;

public class StaffDisplayDecorator extends TableDecorator {
    public String getPosition(){
        return ((StaffEntity) getCurrentRowObject()).getPosition().getPosition();
    }

    public String getDepartament(){
        return ((StaffEntity) getCurrentRowObject()).getDepartament().getDepartament();
    }

    public String getTown(){
        return ((StaffEntity) getCurrentRowObject()).getDepartament().getTown();
    }

    public String getEdit(){
        int id=((StaffEntity) getCurrentRowObject()).getId();
        if (id>=0){
            return String.format("<a href='/api/v2/staffeditor/%d'>edit</a>", id);
        }else{
            return "";
        }
    }
    public String getRemove(){
        int id=((StaffEntity) getCurrentRowObject()).getId();
        if (id>=0) {
            return String.format("<a href='/api/v2/staffremover/%d'>del</a>", ((StaffEntity) getCurrentRowObject()).getId());
        }else{
            return "";
        }
    }
}
