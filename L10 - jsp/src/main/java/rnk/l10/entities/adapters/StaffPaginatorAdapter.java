import org.displaytag.pagination.PaginatedList;
import org.displaytag.properties.SortOrderEnum;

import java.util.List;
import rnk.l10.entities.StaffEntity;

class StaffPaginatorAdapter implements PaginatedList{
    private final List<StaffEntity> list;

    public StaffPaginatorAdapter(List<StaffEntity> list){
        this.list=list;
    }

    public List<StaffEntity> getList(){
        return list;
    }

    public int getPageNumber(){
        return 0;
    }

    public int getObjectsPerPage(){
        return 10;
    }

    public int getFullListSize(){
        return 100;
    }

    public String getSortCriterion(){
        return "";
    }

    public SortOrderEnum getSortDirection(){
        return SortOrderEnum.ASCENDING;
    }

    public String getSearchId(){
        return null;
    }
}