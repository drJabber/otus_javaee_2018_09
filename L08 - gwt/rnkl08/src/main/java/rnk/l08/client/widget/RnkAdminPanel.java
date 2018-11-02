package rnk.l08.client.widget;

import com.google.gwt.cell.client.*;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionModel;
import rnk.l08.client.ServiceAsync;
import rnk.l08.shared.GwtServiceException;
import rnk.l08.shared.dto.StaffDTO;
import rnk.l08.shared.util.StaffGridHelper;

import java.util.Comparator;
import java.util.List;

import static rnk.l08.client.gin.SvcInjector.injector;


public class RnkAdminPanel extends Composite {
    private static ServiceAsync service=injector.getService();

    @UiField(provided=true)
    DataGrid<StaffDTO> grid;

    @UiField(provided = true)
    SimplePager pager;

    interface RnkAdminPanelUiBinder extends UiBinder<FlowPanel, RnkAdminPanel> {
    }

    private static interface GetValue<C> {
        C getValue(StaffDTO o);
    }

    private static RnkAdminPanelUiBinder ourUiBinder = GWT.create(RnkAdminPanelUiBinder.class);

    public RnkAdminPanel(GwtUI parent) {
        initGrid();
        initWidget(ourUiBinder.createAndBindUi(this));
    }


    private void initGrid() {
        try{
            grid=new DataGrid<StaffDTO>( StaffGridHelper.KEY_PROVIDER);
            grid.setWidth("100%");
            grid.setAutoHeaderRefreshDisabled(true);
            grid.setEmptyTableWidget(new Label(injector.getConstants().no_data_to_display()));

            ColumnSortEvent.ListHandler<StaffDTO> listHandler = new ColumnSortEvent.ListHandler<StaffDTO>(StaffGridHelper.get().getDataProvider().getList());
            grid.addColumnSortHandler(listHandler);


            // SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
            SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
            pager = new SimplePager(SimplePager.TextLocation.CENTER, pagerResources, false, 0, true);
            pager.setDisplay(grid);

            final SelectionModel<StaffDTO> selectionModel = new MultiSelectionModel<StaffDTO>(StaffGridHelper.KEY_PROVIDER);
            grid.setSelectionModel(selectionModel, DefaultSelectionEventManager.<StaffDTO> createCheckboxManager());

            initColumns(selectionModel, listHandler);

            StaffGridHelper.get().addDisplay(grid);

        }catch(Exception ex){
            Window.alert(ex.getLocalizedMessage());
        }
    }


    private void initColumns(final SelectionModel<StaffDTO> selectionModel, ColumnSortEvent.ListHandler<StaffDTO> listHandler) throws GwtServiceException{
        //checkBox column for selection
        Column<StaffDTO, Boolean> checkColumn =
            new Column<StaffDTO, Boolean>(new CheckboxCell(true, false)) {
                @Override
                public Boolean getValue(StaffDTO object) {
                    // Get the value from the selection model.
                    return selectionModel.isSelected(object);
                }
            };

        grid.addColumn(checkColumn, SafeHtmlUtils.fromSafeConstant("<br/>"));
        grid.setColumnWidth(checkColumn, 5, Style.Unit.PCT);

        // Id
        Column<StaffDTO, Number> idColumn = new Column<StaffDTO, Number>(new NumberCell()) {
            @Override
            public Integer getValue(StaffDTO object) {
                return object.getId();
            }
        };
        idColumn.setSortable(true);
        listHandler.setComparator(idColumn, new Comparator<StaffDTO>() {
            @Override
            public int compare(StaffDTO o1, StaffDTO o2) {
                return o1.getId().compareTo(o2.getId());
            }
        });
        grid.addColumn(idColumn, "id");
        grid.setColumnWidth(idColumn, 4, Style.Unit.EM);

        // Fio
        Column<StaffDTO, String> fioColumn =
            new Column<StaffDTO, String>(new EditTextCell()) {
                @Override
                public String getValue(StaffDTO object) {
                    return object.getFio();
                }
            };
        fioColumn.setSortable(true);
        listHandler.setComparator(fioColumn, new Comparator<StaffDTO>() {
            @Override
            public int compare(StaffDTO o1, StaffDTO o2) {
                return o1.getFio().compareTo(o2.getFio());
            }
        });
        grid.addColumn(fioColumn, injector.getConstants().fio_caption());
        fioColumn.setFieldUpdater(new FieldUpdater<StaffDTO, String>() {
            @Override
            public void update(int index, StaffDTO object, String value) {
                object.setFio(value);
                StaffGridHelper.get().refreshDisplays();
            }
        });
        grid.setColumnWidth(fioColumn, 15, Style.Unit.PCT);

        // position
        Column<StaffDTO, String> positionColumn =
            new Column<StaffDTO, String>(new EditTextCell()) {
                @Override
                public String getValue(StaffDTO object) {
                    return object.getPosition();
                }
            };
        positionColumn.setSortable(true);
        listHandler.setComparator(positionColumn, new Comparator<StaffDTO>() {
            @Override
            public int compare(StaffDTO o1, StaffDTO o2) {
                return o1.getPosition().compareTo(o2.getPosition());
            }
        });
        grid.addColumn(positionColumn, injector.getConstants().position_caption());
        positionColumn.setFieldUpdater(new FieldUpdater<StaffDTO, String>() {
            @Override
            public void update(int index, StaffDTO object, String value) {
                object.setPosition(value);
                StaffGridHelper.get().refreshDisplays();
            }
        });
        grid.setColumnWidth(positionColumn, 6, Style.Unit.PCT);

        // departament
        Column<StaffDTO, String> deptColumn =
            new Column<StaffDTO, String>(new EditTextCell()) {
                @Override
                public String getValue(StaffDTO object) {
                    return object.getDepartament();
                }
            };
        deptColumn.setSortable(true);
        listHandler.setComparator(deptColumn, new Comparator<StaffDTO>() {
            @Override
            public int compare(StaffDTO o1, StaffDTO o2) {
                return o1.getDepartament().compareTo(o2.getDepartament());
            }
        });
        grid.addColumn(deptColumn, injector.getConstants().dept_caption());
        deptColumn.setFieldUpdater(new FieldUpdater<StaffDTO, String>() {
            @Override
            public void update(int index, StaffDTO object, String value) {
                object.setDepartament(value);
                StaffGridHelper.get().refreshDisplays();
            }
        });
        grid.setColumnWidth(deptColumn, 6, Style.Unit.PCT);

        // role
        Column<StaffDTO, String> roleColumn =
            new Column<StaffDTO, String>(new EditTextCell()) {
                @Override
                public String getValue(StaffDTO object) {
                    return object.getRole();
                }
            };
        roleColumn.setSortable(true);
        listHandler.setComparator(roleColumn, new Comparator<StaffDTO>() {
            @Override
            public int compare(StaffDTO o1, StaffDTO o2) {
                return o1.getRole().compareTo(o2.getRole());
            }
        });
        grid.addColumn(roleColumn, injector.getConstants().role_caption());
        roleColumn.setFieldUpdater(new FieldUpdater<StaffDTO, String>() {
            @Override
            public void update(int index, StaffDTO object, String value) {
                object.setRole(value);
                StaffGridHelper.get().refreshDisplays();
            }
        });
        grid.setColumnWidth(roleColumn, 4, Style.Unit.PCT);

        // Salary
        Column<StaffDTO, String> salaryColumn = new Column<StaffDTO, String>(new EditTextCell()) {
            @Override
            public String getValue(StaffDTO object) {
                return object.getSalary().toString();
            }
        };
        salaryColumn.setSortable(true);
        listHandler.setComparator(salaryColumn, new Comparator<StaffDTO>() {
            @Override
            public int compare(StaffDTO o1, StaffDTO o2) {
                return o1.getSalary().compareTo(o2.getSalary());
            }
        });
        grid.addColumn(salaryColumn,  injector.getConstants().salary_caption());
        salaryColumn.setFieldUpdater(new FieldUpdater<StaffDTO, String>() {
            @Override
            public void update(int index, StaffDTO object, String value) {
                object.setSalary(Integer.parseInt(value));
                StaffGridHelper.get().refreshDisplays();
            }
        });
        grid.setColumnWidth(salaryColumn, 6, Style.Unit.PCT);


        // login
        Column<StaffDTO, String> loginColumn =
            new Column<StaffDTO, String>(new EditTextCell()) {
                @Override
                public String getValue(StaffDTO object) {
                    return object.getLogin();
                }
            };
        loginColumn.setSortable(true);
        listHandler.setComparator(loginColumn, new Comparator<StaffDTO>() {
            @Override
            public int compare(StaffDTO o1, StaffDTO o2) {
                return o1.getLogin().compareTo(o2.getLogin());
            }
        });
        grid.addColumn(loginColumn, injector.getConstants().login_caption());
        loginColumn.setFieldUpdater(new FieldUpdater<StaffDTO, String>() {
            @Override
            public void update(int index, StaffDTO object, String value) {
                object.setLogin(value);
                StaffGridHelper.get().refreshDisplays();
            }
        });
        grid.setColumnWidth(loginColumn, 6, Style.Unit.PCT);

        // password
        Column<StaffDTO, String> passwordColumn =
            new Column<StaffDTO, String>(new EditTextCell()) {
                @Override
                public String getValue(StaffDTO object) {
                    return object.getPassword();
                }
            };
        passwordColumn.setSortable(true);
        listHandler.setComparator(passwordColumn, new Comparator<StaffDTO>() {
            @Override
            public int compare(StaffDTO o1, StaffDTO o2) {
                return o1.getPassword().compareTo(o2.getPassword());
            }
        });
        grid.addColumn(passwordColumn, injector.getConstants().password_caption());
        passwordColumn.setFieldUpdater(new FieldUpdater<StaffDTO, String>() {
            @Override
            public void update(int index, StaffDTO object, String value) {
                object.setPassword(value);
                StaffGridHelper.get().refreshDisplays();
            }
        });
        grid.setColumnWidth(passwordColumn, 8, Style.Unit.PCT);

        // save
        ActionCell saveCell=new ActionCell<StaffDTO>("S", new ActionCell.Delegate<StaffDTO>() {
            @Override
            public void execute(StaffDTO object) {
                object.setCreatePassword(0);
                String session= Cookies.getCookie("sid");
                try{
                    service.saveStaff(session, object, new AsyncCallback<Void>(){
                        @Override
                        public void onFailure(Throwable caught) {
                            Window.alert(caught.getLocalizedMessage());
                        }

                        @Override
                        public void onSuccess( Void  result) {
                            Window.alert(object.getFio()+"сохранен");
                        }

                    });
                }catch(Exception ex){
                    Window.alert(ex.getLocalizedMessage());
                }
            }
        });

        Column<StaffDTO,StaffDTO> saveColumn=addColumn(saveCell,"save",new GetValue<StaffDTO>(){

            @Override
            public StaffDTO getValue(StaffDTO o) {
                return o;
            }
        },null);

        grid.setColumnWidth(saveColumn, 5, Style.Unit.PCT);


        // save
        ActionCell passCell=new ActionCell<StaffDTO>("P", new ActionCell.Delegate<StaffDTO>() {
            @Override
            public void execute(StaffDTO object) {
                try{
                    String session= Cookies.getCookie("sid");
                    object.setCreatePassword(1);
                    service.saveStaff(session, object,  new AsyncCallback<Void>(){
                        @Override
                        public void onFailure(Throwable caught) {
                            Window.alert(caught.getLocalizedMessage());
                        }

                        @Override
                        public void onSuccess( Void  result) {
                            Window.alert(object.getFio()+"сохранен");
                        }

                    });
                }catch(Exception ex){
                    Window.alert(ex.getLocalizedMessage());
                }
            }
        });

        Column<StaffDTO,StaffDTO> passColumn=addColumn(passCell,"cp",new GetValue<StaffDTO>(){

            @Override
            public StaffDTO getValue(StaffDTO o) {
                return o;
            }
        },null);

        grid.setColumnWidth(passColumn, 5, Style.Unit.PCT);
    }

    public void reloadData() {
        try{
            String session= Cookies.getCookie("sid");
            service.getStaff(session, new AsyncCallback<List<StaffDTO>>() {
                @Override
                public void onFailure(Throwable caught) {
                    StaffGridHelper.get().getDataProvider().getList().clear();
                }

                @Override
                public void onSuccess( List<StaffDTO>  result) {
                    List<StaffDTO> list=StaffGridHelper.get().getDataProvider().getList();
                    list.clear();
                    result.stream().forEach(r->list.add(r));
                    StaffGridHelper.get().refreshDisplays();
                }
            });
        }catch(Exception e){
            Window.alert(e.getLocalizedMessage());
        }
    }


    private <C> Column<StaffDTO, C> addColumn(Cell<C> cell, String headerText,
                                                 final GetValue<C> getter, FieldUpdater<StaffDTO, C> fieldUpdater) {
        Column<StaffDTO, C> column = new Column<StaffDTO, C>(cell) {
            @Override
            public C getValue(StaffDTO object) {
                return getter.getValue(object);
            }
        };
        column.setFieldUpdater(fieldUpdater);
        grid.addColumn(column, headerText);
        return column;
    }


    @UiHandler("adminaddbutton")
    void addButtonClick(ClickEvent e){
        List<StaffDTO> list=StaffGridHelper.get().getDataProvider().getList();
        if (list.stream().filter(i->i.getId()==null).count()==0) {
            list.add(new StaffDTO());
            StaffGridHelper.get().refreshDisplays();
        }
    }

}