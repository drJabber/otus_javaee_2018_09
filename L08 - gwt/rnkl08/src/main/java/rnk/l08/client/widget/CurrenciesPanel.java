package rnk.l08.client.widget;

import com.google.gwt.core.client.GWT;
//import com.google.gwt.dom.client.NodeList;
//import com.google.gwt.dom.client.TableCellElement;
import com.google.gwt.dom.client.TableElement;
//import com.google.gwt.dom.client.TableRowElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
//import rnk.l08.client.entities.CurrencyEntity;

//import java.util.List;

public class CurrenciesPanel extends Composite {
    @UiField
    TableElement currTable;


    interface CurrenciesPanelUiBinder extends UiBinder<FlowPanel, CurrenciesPanel> {
    }

    private static CurrenciesPanelUiBinder ourUiBinder = GWT.create(CurrenciesPanelUiBinder.class);

    public CurrenciesPanel() {
        initWidget(ourUiBinder.createAndBindUi(this));

    }

//    void populateCurrencies(List<CurrencyEntity> list){
//        NodeList<TableRowElement> rows=currTable.getRows();
//        for (int index=0; index<3;index++) {
//            CurrencyEntity c=list.get(index);
//            TableRowElement row=rows.getItem(index);
//            NodeList<TableCellElement> cells=row.getCells();
//            cells.getItem(0).setNodeValue(c.getCharCode());
//            cells.getItem(1).setNodeValue(c.getValue());
//
//        }
//    }
}