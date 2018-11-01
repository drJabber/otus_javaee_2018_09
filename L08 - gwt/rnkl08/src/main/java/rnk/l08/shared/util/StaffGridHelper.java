public class StaffGridHelper{
    private static final StaffGridHelper instance;

    public static StaffGridHelper get() {
        if (instance == null) {
            instance = new AdminGridHelper();
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
  
}