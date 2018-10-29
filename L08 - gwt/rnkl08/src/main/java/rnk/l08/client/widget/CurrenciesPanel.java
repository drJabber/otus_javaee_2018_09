package rnk.l08.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.TableCellElement;
import com.google.gwt.dom.client.TableElement;
import com.google.gwt.dom.client.TableRowElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.http.client.*;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.xml.client.*;

public class CurrenciesPanel extends Composite {
    @UiField
    TableElement currTable;




    interface CurrenciesPanelUiBinder extends UiBinder<FlowPanel, CurrenciesPanel> {
    }

    private static CurrenciesPanelUiBinder ourUiBinder = GWT.create(CurrenciesPanelUiBinder.class);


    public CurrenciesPanel() {
        initWidget(ourUiBinder.createAndBindUi(this));

    }

    void populateCurrencies(String xml){
        Document dom= XMLParser.parse(xml);
        Element root=dom.getDocumentElement();
        NodeList valutes=root.getElementsByTagName("Valute");
        com.google.gwt.dom.client.NodeList<TableRowElement> rows=currTable.getRows();

        for (Integer index =0; index<valutes.getLength();index++){
            Node node=valutes.item(index);
            if (node.getNodeType()==Node.ELEMENT_NODE){
                Element valuteElt=(Element)node;

                Node codeNode= valuteElt.getElementsByTagName("CharCode").item(0);
                String code=codeNode.getFirstChild().getNodeValue();

                Node valueNode=valuteElt.getElementsByTagName("Value").item(0);
                String value=valueNode.getFirstChild().getNodeValue();

                TableRowElement row=rows.getItem(index+1);
                com.google.gwt.dom.client.NodeList<TableCellElement> cells=row.getCells();
                if (cells!=null){
                    TableCellElement c0=cells.getItem(0);
                    TableCellElement c1=cells.getItem(1);
                    c0.getFirstChild().setNodeValue(code);
                    c1.getFirstChild().setNodeValue(value);
                }
                if (index>=3){
                    break;
                }
            }
        }
    }
}