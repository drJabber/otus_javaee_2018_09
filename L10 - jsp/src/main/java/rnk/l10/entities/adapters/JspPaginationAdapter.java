import org.displaytag.pagination.PaginatedList;
import org.displaytag.properties.SortOrderEnum;

import java.util.List;

class JspPaginatorAdapter implements PaginatedList{
    private final List<Object> list;
    private int page;
    private int page_size;
    private int full_size;

    public JspPaginatorAdapter(List<Object> list, Long page, Long page_size, Long full_size, sort : String; direction : String){
        this.list=list;
        this.page=page;
        this.page_size=page_size;
        this.full_size=full_size;
        this.sort=sort;
        this.direction=direction.toLowerCase();
    }

    public List<Object> getList(){
        int ofs = this.page_size * (this.page - 1);
        List<Object> sublist = this.list.subList(ofs, Math.min(this.full_size, ofs + this.page_size));
        return sublist;
    }

    public int getPageNumber(){
        return this.page ;
    }

    public int getObjectsPerPage(){
        return this.page_size ;
    }

    public int getFullListSize(){
        return this.full_size;
    }

    public String getSortCriterion(){
        return this.sort;
    }

    public SortOrderEnum getSortDirection(){
        if (this.direction.equals("asc")){
            return SortOrderEnum.ASCENDING;
        }else{
            return SortOrderEnum.DESCENDING;
        }
    }

    public String getSearchId(){
        return null;
    }
}