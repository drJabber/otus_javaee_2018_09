package rnk.l08.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import rnk.l08.client.ServiceAsync;
import rnk.l08.shared.dto.StaffDTO;

import java.util.List;

import static rnk.l08.client.gin.SvcInjector.injector;


public class RnkAdminPanel extends Composite {
    private static ServiceAsync service=injector.getService();

    @UIField(provided=true)
    DataGrid<StaffDTO> grid;

    interface RnkAdminPanelUiBinder extends UiBinder<VerticalPanel, RnkAdminPanel> {
    }

    private static RnkAdminPanelUiBinder ourUiBinder = GWT.create(RnkAdminPanelUiBinder.class);

    public RnkAdminPanel(GwtUI parent) {
    }


    @Override 
    public Widget onInitialize() {
        grid=new DataGrid<StaffDTO>(StaffDTO.KEY_PROVIDER);
        grid.setWidth("100%");
        grid.setAutoHeaderRefreshDisabled(true);
        grid.setEmptyTableWidget(injector.getConstants().no_data_to_display());
        
        ListHandler<StaffDTO> listHandler = new ListHandler<StaffDTO>(StaffGridHelper.get().getDataProvider().getList());
        grid.addColumnSortHandler(listHandler);

        // SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
        SimplePager.Resources pagerResources=injector.getPagerResources();
        pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
        pager.setDisplay(grid);        

        final SelectionModel<ContactInfo> selectionModel = new MultiSelectionModel<StaffDTO>(StaffGridHelper.ContactInfo.KEY_PROVIDER);
        grid.setSelectionModel(selectionModel, DefaultSelectionEventManager.<StaffDTO> createCheckboxManager());

        initColumns(selectionModel, listHandler);

        return ourUiBinder.createAndBindUi(this);
    }

    private void initColumns(final SelectionModel<StaffDTO> selectionModel,ListHandler<StaffDTO> listHandler){
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
        grid.setColumnWidth(checkColumn, 40, Unit.PX);

        // Id
        Column<StaffDTO, Integer> idColumn = new Column<StaffDTO, Integer>(new IntegerCell()) {
            @Override
            public Integer getValue(StaffDTO object) {
                return object.getId();
            }
        };
        idColumn.setSortable(true);
        sortHandler.setComparator(idColumn, new Comparator<StaffDTO>() {
            @Override
            public int compare(StaffDTO o1, StaffDTO o2) {
                return o1.getId().compareTo(o2.getId());
            }
        });
        dataGrid.addColumn(idColumn, "id");
        dataGrid.setColumnWidth(idColumn, 4, Unit.EM);
        
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
        grid.setColumnWidth(fioColumn, 20, Unit.PCT);
        
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
        grid.setColumnWidth(positionColumn, 15, Unit.PCT);
        
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
        grid.setColumnWidth(deptColumn, 15, Unit.PCT);
        
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
        grid.setColumnWidth(roleColumn, 15, Unit.PCT);
        
        // Salary
        Column<StaffDTO, Integer> salaryColumn = new Column<StaffDTO, Integer>(new IntegerCell()) {
            @Override
            public Integer getValue(StaffDTO object) {
                return object.getSalary();
            }
        };
        salaryColumn.setSortable(true);
        sortHandler.setComparator(salaryColumn, new Comparator<StaffDTO>() {
            @Override
            public int compare(StaffDTO o1, StaffDTO o2) {
                return o1.getSalary().compareTo(o2.getSalary());
            }
        });
        dataGrid.addColumn(salaryColumn,  injector.getConstants().salary_caption());
        roleColumn.setFieldUpdater(new FieldUpdater<StaffDTO, Integer>() {
            @Override
            public void update(int index, StaffDTO object, Integer value) {
                object.setsalary(value);
                StaffGridHelper.get().refreshDisplays();
            }
        });
        dataGrid.setColumnWidth(salaryColumn, 7, Unit.EM);
        

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
        grid.setColumnWidth(loginColumn, 10, Unit.PCT);
        
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
        grid.setColumnWidth(passwordColumn, 10, Unit.PCT);
        
        //checkBox column for create password
        Column<StaffDTO, Boolean> createPasswordColumn =
            new Column<StaffDTO, Boolean>(new CheckboxCell(true, false)) {
                @Override
                public Boolean getValue(StaffDTO object) {
                    // Get the value from the selection model.
                    return object.getCreatePassword());
                }
            };

        grid.addColumn(createPasswordColumn, injector.getConstants().create_password_caption());
        createPasswordColumn.setFieldUpdater(new FieldUpdater<StaffDTO, Boolean>() {
            @Override
            public void update(int index, StaffDTO object, Boolean value) {
                object.setCreatePassword(value);
                StaffGridHelper.get().refreshDisplays();
            }
        });
        grid.setColumnWidth(createPasswordColumn, 40, Unit.PX);
    }

    public void reloadData() {
        try{
            String session= Cookies.getCookie("sid");
            service.getStaff(session, new AsyncCallback<List<StaffDTO>>() {
                @Override
                public void onFailure(Throwable caught) {
                    Window.alert(caught.getLocalizedMessage());
                }

                @Override
                public void onSuccess( List<StaffDTO>  result) {
                    Window.alert(result.toString());
                }
            });
        }catch(Exception e){
            Window.alert(e.getLocalizedMessage());
        }
    }
}