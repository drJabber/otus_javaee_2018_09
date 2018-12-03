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
        return String.format("<a href='/main/admin/staffeditor/%d'>edit</a>", ((StaffEntity) getCurrentRowObject()).getId());
    }
    public String getRemove(){
        return String.format("<a href='/main/admin/staffremover/%d'>del</a>", ((StaffEntity) getCurrentRowObject()).getId());
    }
}
