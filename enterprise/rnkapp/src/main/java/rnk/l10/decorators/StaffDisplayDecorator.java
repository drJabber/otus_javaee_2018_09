package rnk.l10.decorators;

import org.displaytag.decorator.TableDecorator;
import rnk.l10.entities.StaffEntity;

public class StaffDisplayDecorator extends TableDecorator {
    private StaffEntity getStaff(){
        return ((StaffEntity) getCurrentRowObject());
    }

    public String getPosition(){
        return getStaff().getPosition().getPosition();
    }

    public String getDepartament(){
        return getStaff().getDepartament().getDepartament();
    }

    public String getTown(){
        return getStaff().getDepartament().getTown();
    }

    public String getEdit(){
        int id=getStaff().getId();
        if (id>=0){
            return String.format("<a href='/rnkapp/api/v2/staffeditor/%d'>edit</a>", id);
        }else{
            return "";
        }
    }
    public String getRemove(){
        int id=getStaff().getId();
        if (id>=0) {
            return String.format("<a href='/rnkapp/api/v2/staffremover/%d'>del</a>", id);
        }else{
            return "";
        }
    }
}
