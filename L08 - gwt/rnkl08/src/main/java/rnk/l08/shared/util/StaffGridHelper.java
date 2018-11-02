package rnk.l08.shared.util;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import rnk.l08.client.widget.RnkAdminPanel;
import rnk.l08.shared.dto.StaffDTO;

public class StaffGridHelper{
    private static StaffGridHelper instance;

    public static StaffGridHelper get() {
        if (instance == null) {
            instance = new StaffGridHelper();
        }
        return instance;
    }

    private ListDataProvider<StaffDTO> dataProvider = new ListDataProvider<StaffDTO>();

    public ListDataProvider<StaffDTO> getDataProvider() {
        return dataProvider;
    }
        
    public void refreshDisplays() {
        dataProvider.refresh();
    }

    public static final ProvidesKey<StaffDTO> KEY_PROVIDER = new ProvidesKey<StaffDTO>() {
        @Override
        public Object getKey(StaffDTO item) {
            return item == null ? null : item.getId();
        }
    };

    public void addDisplay(HasData<StaffDTO> display) {
        dataProvider.addDataDisplay(display);
    }



}