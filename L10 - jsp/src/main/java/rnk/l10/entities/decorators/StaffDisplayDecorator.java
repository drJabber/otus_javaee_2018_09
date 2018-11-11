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
}
